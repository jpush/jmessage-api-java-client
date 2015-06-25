[![Build Status](https://travis-ci.org/jpush/jmessage-api-java-client.svg?branch=master)](https://travis-ci.org/jpush/jmessage-api-java-client)
[![Dependency Status](https://www.versioneye.com/user/projects/53eff13a13bb06f0bb000518/badge.svg?style=flat)](https://www.versioneye.com/user/projects/53eff13a13bb06f0bb000518)
[![GitHub version](https://badge.fury.io/gh/jpush%2Fjmessage-api-java-client.svg)](http://badge.fury.io/gh/jpush%2Fjmessage-api-java-client)

# JPush API Java Library

## 概述

这是 JPush REST API 的 Java 版本封装开发包，是由极光推送官方提供的，一般支持最新的 API 功能。

对应的 REST API 文档：尚未对外开放

本开发包 Javadoc：[API Docs](http://jpush.github.io/jmessage-api-java-client/apidocs/)

版本更新：[Release页面](https://github.com/jpush/jmessage-api-java-client/releases)。下载更新请到这里。


## 安装

### maven 方式
将下边的依赖条件放到你项目的 maven pom.xml 文件里。

```Java
<dependency>
    <groupId>cn.jpush.api</groupId>
    <artifactId>jmessage-client</artifactId>
    <version>0.1.0</version>
</dependency>
```
### jar 包方式

请到 [Release页面](https://github.com/jpush/jmessage-api-java-client/releases)下载相应版本的发布包。

### 依赖包
* [slf4j](http://www.slf4j.org/) / log4j (Logger)
* [gson](https://code.google.com/p/google-gson/) (Google JSON Utils)
* [jpush-client](https://github.com/jpush/jpush-api-java-client)

> 其中 slf4j 可以与 logback, log4j, commons-logging 等日志框架一起工作，可根据你的需要配置使用。

> jpush-client的jar包下载。[请点击](https://github.com/jpush/jpush-api-java-client/releases)

如果使用 Maven 构建项目，则需要在你的项目 pom.xml 里增加：

```Java
<dependency>
	<groupId>com.google.code.gson</groupId>
	<artifactId>gson</artifactId>
	<version>2.2.4</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>1.7.5</version>
</dependency>
<dependency>
	<groupId>cn.jpush.api</groupId>
	<artifactId>jpush-client</artifactId>
	<version>3.2.5</version>
</dependency>
<!-- For log4j -->
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>1.7.5</version>
</dependency>
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>1.2.16</version>
</dependency>
```

如果不使用 Maven 构建项目，则项目 libs/ 目录下有依赖的 jar 可复制到你的项目里去。

### 构建本项目

可以用 Eclipse 类 IDE 导出 jar 包。建议直接使用 maven，执行命令：

	maven package

### 自动化测试

在项目目录下执行命令：

	mvn test

## 使用样例

> 以下片断来自项目代码里的文件：example / cn.jmessage.api.examples.UserExample

```Java
	public static void testGetUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            String res = client.getUserInfo("test_user");
            LOG.info(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
```

> 以下片断来自项目代码里的文件：example / cn.jmessage.api.examples.GroupExample
```Java
	public static void testCreateGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            String res = client.createGroup("test_user", "test_gname1", "description", "test_user");
            LOG.info(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
```