package com.china.kuaiyou.netbean.user;

import java.io.Serializable;

public class AppRegister implements Serializable {
	private String pN;// phoneName;
	private String pW;// passWord;
	private String dId;// deviceId;
	private String mac;

	public AppRegister() {
		super();
	}

	public AppRegister(String pN, String pW, String dId, String mac) {
		super();
		this.pN = pN;
		this.pW = pW;
		this.dId = dId;
		this.mac = mac;
	}

	public String getpN() {
		return pN;
	}

	public void setpN(String pN) {
		this.pN = pN;
	}

	public String getpW() {
		return pW;
	}

	public void setpW(String pW) {
		this.pW = pW;
	}

	public String getdId() {
		return dId;
	}

	public void setdId(String dId) {
		this.dId = dId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "AppRegisterBean{" + "pN='" + pN + '\'' + ", pW='" + pW + '\''
				+ ", dId='" + dId + '\'' + ", mac='" + mac + '\'' + '}';
	}
}
