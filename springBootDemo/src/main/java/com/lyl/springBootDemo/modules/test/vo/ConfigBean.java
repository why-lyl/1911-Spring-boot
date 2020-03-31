package com.lyl.springBootDemo.modules.test.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/application2.properties")
@ConfigurationProperties(prefix = "com.lyl2")
public class ConfigBean {
	
	private int port;
	private String name;
	private int age;
	private String desc;
	private String random;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	@Override
	public String toString() {
		return "ConfigBean [port=" + port + ", name=" + name + ", age=" + age + ", desc=" + desc + ", random=" + random
				+ "]";
	}
	
	

}
