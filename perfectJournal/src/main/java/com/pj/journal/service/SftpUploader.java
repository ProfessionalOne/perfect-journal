package com.pj.journal.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Properties;

@Component
public class SftpUploader {

	@Value("${sftp.host}")
	private String SFTP_HOST;

	@Value("${sftp.port}")
	private int SFTP_PORT;

	@Value("${sftp.username}")
	private String SFTP_USER;

	@Value("${sftp.password}")
	private String SFTP_PASS;

	@Value("${sftp.remote-dir}")
	private String REMOTE_DIR;

	private Session getSftpSession() throws Exception {
		JSch jsch = new JSch();
		Session session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
		session.setPassword(SFTP_PASS);

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		return session;
	}

	private ChannelSftp getSftpChannel(Session session) throws Exception {
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
		return channelSftp;
	}

	public void upload(MultipartFile file, String remoteFilename) throws Exception {
		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			session = getSftpSession();
			channelSftp = getSftpChannel(session);
			channelSftp.cd(REMOTE_DIR);

			InputStream inputStream = file.getInputStream();
			channelSftp.put(inputStream, remoteFilename);

		} finally {
			if (channelSftp != null)
				channelSftp.disconnect();
			if (session != null)
				session.disconnect();
		}
	}
	
	public void upload(InputStream inputStream, String remoteFilename) throws Exception {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            session = getSftpSession();
            channelSftp = getSftpChannel(session);
            channelSftp.cd(REMOTE_DIR);

            channelSftp.put(inputStream, remoteFilename);

        } finally {
            if (channelSftp != null) channelSftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

	// ★★★ 파일 읽기 메서드 추가 ★★★
	public byte[] download(String remoteFilename) throws Exception {
		Session session = null;
		ChannelSftp channelSftp = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			session = getSftpSession();
			channelSftp = getSftpChannel(session);
			channelSftp.cd(REMOTE_DIR);

			channelSftp.get(remoteFilename, baos);
			return baos.toByteArray();

		} finally {
			if (channelSftp != null)
				channelSftp.disconnect();
			if (session != null)
				session.disconnect();
			// ByteArrayOutputStream은 close()할 필요 없음
		}
	}
}