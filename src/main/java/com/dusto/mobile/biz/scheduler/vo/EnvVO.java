package com.dusto.mobile.biz.scheduler.vo;

import java.io.Serializable;

public class EnvVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 902694808056572294L;
	private String envKey;
	private String envValue;
	private String description;
	public String getEnvKey() {
		return envKey;
	}
	public void setEnvKey(String envKey) {
		this.envKey = envKey;
	}
	public String getEnvValue() {
		return envValue;
	}
	public void setEnvValue(String envValue) {
		this.envValue = envValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
