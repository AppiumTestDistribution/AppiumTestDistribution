---
title: Setup & Requirements
hide:
  - navigation
---

## Prerequisite

Appium version 2.X.X

## Installation - Server

Install the plugin using Appium's plugin CLI, either as a named plugin or via NPM:

```
appium plugin install --source=npm appium-device-farm
```

### Please make sure you have installed Appium v2.0 and [appium-device-farm](https://github.com/AppiumTestDistribution/appium-device-farm) plugin

## Maven Dependency 
Add the below dependencies in your pom.xml (Master) if you want to use the latest commit from the master branch.

```
<dependency>
    <groupId>com.github.AppiumTestDistribution</groupId>
    <artifactId>AppiumTestDistribution</artifactId>
    <version>latest_commit</version>
</dependency>
```

```
<repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
</repositories>
```

## [Maven Release Version](https://search.maven.org/artifact/com.github.saikrishna321/AppiumTestDistribution)

```
 <dependency>
   <groupId>com.github.saikrishna321</groupId>
   <artifactId>AppiumTestDistribution</artifactId>
   <version>14.0.1</version>
 </dependency>
```
