package com.china.kuaiyou.netbean.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class ResultTemplateBean implements Serializable {
	private List<TemplateBean> list;

	public ResultTemplateBean() {

		this.list = new ArrayList<>();
	}

	public ResultTemplateBean(List<TemplateBean> list) {
		super();
		this.list = list;
	}

	public List<TemplateBean> getList() {
		return list;
	}

	public void setList(List<TemplateBean> list) {
		this.list = list;
	}

	public void add(TemplateBean templateBean) {
		this.list.add(templateBean);
	}

	@Override
	public String toString() {
		return "ResultTemplateBean [list=" + list + "]";
	}

}
