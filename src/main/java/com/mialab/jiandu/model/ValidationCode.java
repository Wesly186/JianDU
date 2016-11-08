package com.mialab.jiandu.model;

import java.util.Date;

public class ValidationCode {
	private String phone;

	private Integer validationCode;

	private Date expires;

	private Date nextTime;

	private Date generateTime;

	public ValidationCode() {
		super();
	}

	public ValidationCode(String phone, Integer validationCode, Date expires,
			Date nextTime, Date generateTime) {
		super();
		this.phone = phone;
		this.validationCode = validationCode;
		this.expires = expires;
		this.nextTime = nextTime;
		this.generateTime = generateTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Integer getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(Integer validationCode) {
		this.validationCode = validationCode;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
}