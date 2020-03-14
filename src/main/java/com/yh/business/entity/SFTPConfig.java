package com.yh.business.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: sftp默认配置类
 * @Since: YH007
 * @Date: 2020/3/13
 */
@Component
@ConfigurationProperties(prefix="sftp")
public class SFTPConfig {


    private  String host;//ip地址

    private  Integer port;//端口号

    private  String username;//用户名

    private  String password;//密码

    private  String path;//aaa路径


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
