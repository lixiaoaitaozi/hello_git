package com.china.kuaiyou.netbean.user;

import java.io.Serializable;

public class UserBean implements Serializable {
	public ResultWifiInfoBean getResultWifiInfoBean() {
		return resultWifiInfoBean;
	}

	public void setResultWifiInfoBean(ResultWifiInfoBean resultWifiInfoBean) {
		this.resultWifiInfoBean = resultWifiInfoBean;
	}

	private String id; // 用户ID
	private UserBeanTypeEnum type;// 0:内部员工,1:商家,2:消费者
	private String name;// 用户名称
	private String userName; // 账号
	private String wxName;// 微信号
	private String passWord;// 密码
	private int age;// 年龄
	private String sex;// 性别
	private String introduction;// 用户介绍
	private String phoneName; // 手机号码
	private String createTime;// 创建时间
	private String lastLoginTime;// 最后一次登录的时间 2017-6-5 11:10:12
	private String checkCode;// 校检码
	private String createById;// 是根据谁创建的，
	private String headImage;
	private ResultWifiInfoBean resultWifiInfoBean;

	public UserBean() {
		super();
	}

	public UserBean(UserBeanTypeEnum type) {
		super();
		this.type = type;
	}

	public UserBean(UserBeanTypeEnum type, String name, String userName,
			String wxName, String passWord, int age, String sex,
			String introduction, String phoneName, String createTime,
			String lastLoginTime, String checkCode, String createById,
			String headImage, ResultWifiInfoBean resultWifiInfoBean) {
		super();
		this.type = type;
		this.name = name;
		this.userName = userName;
		this.wxName = wxName;
		this.passWord = passWord;
		this.age = age;
		this.sex = sex;
		this.introduction = introduction;
		this.phoneName = phoneName;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.checkCode = checkCode;
		this.createById = createById;
		this.headImage = headImage;
		this.resultWifiInfoBean = resultWifiInfoBean;
	}

	public UserBean(String id, UserBeanTypeEnum type, String name,
			String userName, String wxName, String passWord, int age,
			String sex, String introduction, String phoneName,
			String createTime, String lastLoginTime, String checkCode,
			String createById, String headImage,
			ResultWifiInfoBean resultWifiInfoBean) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.userName = userName;
		this.wxName = wxName;
		this.passWord = passWord;
		this.age = age;
		this.sex = sex;
		this.introduction = introduction;
		this.phoneName = phoneName;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.checkCode = checkCode;
		this.createById = createById;
		this.headImage = headImage;
		this.resultWifiInfoBean = resultWifiInfoBean;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserBeanTypeEnum getType() {
		return type;
	}

	public void setType(UserBeanTypeEnum type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", type=" + type + ", name=" + name
				+ ", userName=" + userName + ", wxName=" + wxName
				+ ", passWord=" + passWord + ", age=" + age + ", sex=" + sex
				+ ", introduction=" + introduction + ", phoneName=" + phoneName
				+ ", createTime=" + createTime + ", lastLoginTime="
				+ lastLoginTime + ", checkCode=" + checkCode + ", createById="
				+ createById + ", headImage=" + headImage
				+ ", resultWifiInfoBean=" + resultWifiInfoBean + "]";
	}

}
