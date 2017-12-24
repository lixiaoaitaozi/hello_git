package com.china.kuaiyou.netbean;

import java.io.Serializable;

public class QueTemplateBean implements Serializable {
	private boolean ispublic;
	private int type;
	private String userId;

	public QueTemplateBean() {
		super();
	}

	public QueTemplateBean(boolean ispublic, int type, String userId) {
		super();
		this.ispublic = ispublic;
		this.type = type;
		this.userId = userId;
	}

	public boolean isIspublic() {
		return ispublic;
	}

	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "QueTemplateBean [ispublic=" + ispublic + ", type=" + type
				+ ", userId=" + userId + "]";
	}

}
