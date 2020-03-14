package com.yh.business.utils;

import com.jcraft.jsch.*;
import com.yh.business.entity.SFTPConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Vector;

/**
 * @Description: SFTPUtils
 * @Since: YH007
 * @Date: 2020/3/13
 */
@Component
public class SFTPUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(SFTPUtils.class);

    private volatile static SFTPUtils sftpUtils;

    /**
     * 连接默认配置
     */
    @Autowired
    private SFTPConfig sftpConfig;

    private static ChannelSftp sftp;

    private static Session session;

    private static String host;

    private static int port;

    private static String username;

    private static String password;

    private static String privateKey;


    /**
     * 单例默认的连接工具
     *
     * @return SFTPUtils
     */
    public SFTPUtils getDefultChannelSftp() {
        if (sftpUtils == null) {
            synchronized (SFTPUtils.class) {
                if (sftpUtils == null) {
                    sftpUtils = new SFTPUtils(sftpConfig.getHost(), sftpConfig.getPort(), sftpConfig.getUsername(), sftpConfig.getPassword());
                    connect();
                }
            }
        }
        return sftpUtils;
    }


    /**
     * 构造基于密码认证的sftp对象
     *
     * @param username
     * @param password
     * @param host
     * @param port
     */
    public SFTPUtils(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    public SFTPUtils() {
    }

    /**
     * 连接sftp服务器
     */
    public void connect() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            LOGGER.error("login error:", e);
        }
    }

    /**
     * 关闭连接 server
     */
    public void disconnect() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            if (StringUtils.isEmpty(directory)) {
                directory = "/root/temp";
            }
            sftp.cd(directory);
        } catch (SftpException e) {

            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);


    }


    /**
     * 上传单个文件
     *
     * @param directory  上传到sftp目录
     * @param uploadFile 要上传的文件,包括路径
     */
    public void upload(String directory, File uploadFile) throws FileNotFoundException, SftpException {

        upload(directory, uploadFile.getName(), new FileInputStream(uploadFile));
    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return ChannelSftp.LsEntry
     */
    public Vector<ChannelSftp.LsEntry> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }


    /**
     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param byteArr      要上传的字节数组
     */
    public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param dataStr      待上传的数据
     * @param charsetName  sftp上的文件，按该字符编码保存
     */
    public void upload(String directory, String sftpFileName,
                       String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile,
                         String saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));

    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = IOUtils.toByteArray(is);
        return fileData;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }


}
