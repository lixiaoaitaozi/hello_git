package com.china.kuaiyou.netbean.user;
import java.io.Serializable;

public class AppLogin implements Serializable{

	String lS;//loginString may be wxName or phoneName or userName
	String pW;//passWord
	String dId;//deviceId
	String mac;
	

	public AppLogin() {
		super();
	}


	public AppLogin(String lS, String pW, String dId, String mac) {
		super();
		this.lS = lS;
		this.pW = pW;
		this.dId = dId;
		this.mac = mac;
	}


	public String getlS() {
		return lS;
	}


	public void setlS(String lS) {
		this.lS = lS;
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
		return "LoginBean [lS=" + lS + ", pW=" + pW + ", dId=" + dId + ", mac="
				+ mac + "]";
	}
	
	
	
	
}
