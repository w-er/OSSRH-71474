# Wencder Common Tools

平时开发会经常配置各种环境配置而浪费很多时间,这里将平时开发的一些东西进行整合

提供一个 Java Web 快速开发包，从而减少开发周期。

### 模块

- wencoder-tools
- wencoder-security

### 使用

```xml

<dependency>
    <groupId>com.wencoder</groupId>
    <artifactId>{模块名称}</artifactId>
    <version>{版本号}</version>
</dependency>
```

如果使用的是 **SNAPSHOT** 开发版，需要在项目**pom.xml**中配置仓库地址

```xml

<repositories>
    <repository>
        <id>ossrh</id>
        <name>Sonatype Snapshots</name>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>
```