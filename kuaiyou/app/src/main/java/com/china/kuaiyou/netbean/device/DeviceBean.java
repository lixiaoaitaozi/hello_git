package com.china.kuaiyou.netbean.device;

import java.io.Serializable;

public class DeviceBean implements Serializable {

	private String id;
	private String name;
	private String userId;
	private String startTime;
	private String mac;
	private boolean isVertical;

	public DeviceBean() {
		super();
	}

	public DeviceBean(String id, String name, String userId, String startTime,
			String mac, boolean isVertical) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.startTime = startTime;
		this.mac = mac;
		this.isVertical = isVertical;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	
	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}

	@Override
	public String toString() {
		return "DeviceBean [id=" + id + ", name=" + name + ", userId=" + userId
				+ ", startTime=" + startTime + ", mac=" + mac + ", isVertical="
				+ isVertical + "]";
	}
	

}
