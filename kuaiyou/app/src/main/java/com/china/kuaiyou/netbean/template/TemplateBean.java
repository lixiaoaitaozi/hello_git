package com.china.kuaiyou.netbean.template;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class TemplateBean implements Serializable {
	private String id;
	private String name;// 名称
	private int type;//0图片，1视频，2WEB
	private boolean ispublic;// 是否公共模板
	private boolean isNet;// 是否网路模板
    private boolean isVertical;
	private String downUrl;// 下载地址
	private String imageUrl;// 图片地址
	private String templateTypeName;// 模板种类名称
	private String userId;//用户ID
	private String createTime;//创建时间
	public TemplateBean() {
	}

	public TemplateBean(String id, String name, int type, boolean ispublic,
                        boolean isNet, boolean isVertical, String downUrl, String imageUrl,
                        String templateTypeName, String userId, String createTime) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.ispublic = ispublic;
		this.isNet = isNet;
		this.isVertical = isVertical;
		this.downUrl = downUrl;
		this.imageUrl = imageUrl;
		this.templateTypeName = templateTypeName;
		this.userId = userId;
		this.createTime = createTime;
	}







	public int getType() {
		return type;
	}







	public void setType(int type) {
		this.type = type;
	}







	public boolean isVertical() {
		return isVertical;
	}



	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public boolean isIspublic() {
		return ispublic;
	}

	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}

	public boolean isNet() {
		return isNet;
	}

	public void setNet(boolean isNet) {
		this.isNet = isNet;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	@Override
	public String toString() {
		return "TemplateBean [id=" + id + ", name=" + name + ", type=" + type
				+ ", ispublic=" + ispublic + ", isNet=" + isNet
				+ ", isVertical=" + isVertical + ", downUrl=" + downUrl
				+ ", imageUrl=" + imageUrl + ", templateTypeName="
				+ templateTypeName + ", userId=" + userId + ", createTime="
				+ createTime + "]";
	}

}
