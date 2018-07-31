package com.qfang.examples.cloud;

import java.io.Serializable;

/**
 * 
 * @author huxianyong
 *
 */
public class TradeSearch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4729773370643800362L;

	private String id;
	
	private String number;
	
	private String tenementDetail;
	
	private String city;
	
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTenementDetail() {
		return tenementDetail;
	}

	public void setTenementDetail(String tenementDetail) {
		this.tenementDetail = tenementDetail;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TradeSearch{" +
				"id='" + id + '\'' +
				", number='" + number + '\'' +
				", tenementDetail='" + tenementDetail + '\'' +
				", city='" + city + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
