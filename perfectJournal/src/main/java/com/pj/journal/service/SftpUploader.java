package com.pj.journal.service;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

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


    public void upload(MultipartFile file, String remoteFilename) throws Exception {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // JSch 세션 설정
            JSch jsch = new JSch();
            session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
            session.setPassword(SFTP_PASS);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 채널 연결 및 업로드
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            InputStream inputStream = file.getInputStream();
            channelSftp.cd(REMOTE_DIR);
            channelSftp.put(inputStream, remoteFilename);

        } finally {
            if (channelSftp != null) channelSftp.disconnect();
            if (session != null) session.disconnect();
        }
    }
}

