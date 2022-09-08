package com.monitev.devices.dto;

import java.io.Serializable;

public class RequestDataDTO  implements Serializable{
	
	private static final long serialVersionUID = -5612223156911770178L;
	
	private String username;
	private String password;
	private String deviceId;
	private String tokenDevice;
	
	public RequestDataDTO() {
		super();
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getTokenDevice() {
		return tokenDevice;
	}
	public void setTokenDevice(String tokenDevice) {
		this.tokenDevice = tokenDevice;
	}

}
