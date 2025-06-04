package com.pj.journal.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.journal.service.SftpUploader; // SftpUploader 서비스 임포트

import net.coobird.thumbnailator.Thumbnails;

@RestController
public class ImageUploadController {

	private final SftpUploader sftpUploader; // SftpUploader 주입 받기

	@Value("${image.base-url}") // application.properties에서 설정한 이미지 기본 URL
	private String imageBaseUrl; // 예: "/uploads/"

	public ImageUploadController(SftpUploader sftpUploader) {
		this.sftpUploader = sftpUploader;
	}

	@PostMapping("/uploads")
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("upload") MultipartFile file) {
		Map<String, Object> response = new HashMap<>();

		if (file.isEmpty()) {
			response.put("uploaded", false);
			response.put("error", Map.of("message", "업로드할 파일이 없습니다."));
			return ResponseEntity.badRequest().body(response);
		}

		try {
			String originalFilename = file.getOriginalFilename();
			String fileExtension = "";
			if (originalFilename != null && originalFilename.contains(".")) {
				fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
			}
			String savedFilename = UUID.randomUUID().toString() + fileExtension;

			InputStream inputStream = file.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			try {
				// 이미지 너비 제한 (최대 1920px) 및 리사이징
				Thumbnails.of(inputStream).width(700).keepAspectRatio(true).toOutputStream(outputStream);
			} catch (Exception e) {
				// 리사이징 실패 시, 원본 이미지 그대로 사용
				System.err.println("이미지 리사이징 실패: " + e.getMessage());
				outputStream = new ByteArrayOutputStream();
				try (InputStream originalInputStream = file.getInputStream()) {
					originalInputStream.transferTo(outputStream);
				}
			}

			// ★★★ 수정: 리사이징된 InputStream을 SftpUploader.upload()에 전달 ★★★
			InputStream resizedInputStream = new ByteArrayInputStream(outputStream.toByteArray());
			sftpUploader.upload(resizedInputStream, savedFilename);

			response.put("uploaded", true);
			response.put("url", imageBaseUrl + savedFilename);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("uploaded", false);
			response.put("error", Map.of("message", "SFTP 파일 업로드 중 오류가 발생했습니다: " + e.getMessage()));
			return ResponseEntity.status(500).body(response);
		}

	}

	@GetMapping("/uploads/{filename:.+}") // CKEditor가 이미지를 가져올 URL
	@ResponseBody
	public ResponseEntity<byte[]> serveImage(@PathVariable String filename) {
		try {
			byte[] imageData = sftpUploader.download(filename); // SftpUploader를 사용하여 이미지 다운로드

			// 파일 확장자를 분석하여 Content-Type 설정 (더 유연하게)
			String contentType = getContentType(filename);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(imageData);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	// 파일 확장자에 따라 Content-Type을 반환하는 헬퍼 메서드
	private String getContentType(String filename) {
		if (filename.toLowerCase().endsWith(".png")) {
			return MediaType.IMAGE_PNG_VALUE;
		} else if (filename.toLowerCase().endsWith(".gif")) {
			return MediaType.IMAGE_GIF_VALUE;
		} else if (filename.toLowerCase().endsWith(".bmp")) {
			return "image/bmp"; // MediaType.IMAGE_BMP_VALUE는 없음
		}
		return MediaType.IMAGE_JPEG_VALUE; // 기본값
	}
}