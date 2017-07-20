package com.jaf.examples.expert.lesson1.q2;

import java.util.Date;
import java.util.Map;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
public class MyCloneableObj {

	private Map<String, MyInfo> myInfos;
	private String myId;
	private Date validDate;

	public Map<String, MyInfo> getMyInfos() {
		return myInfos;
	}

	public void setMyInfos(Map<String, MyInfo> myInfos) {
		this.myInfos = myInfos;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

}
