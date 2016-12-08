package com.mialab.jiandu.model;

import java.util.Date;

public class Message extends ArticleRsp {

	// 0:comment,1:collect
	private int type;
	private Date time;
	private User user;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
