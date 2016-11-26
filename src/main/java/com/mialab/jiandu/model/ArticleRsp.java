package com.mialab.jiandu.model;

public class ArticleRsp extends Article {
	
	private User writer;

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

}
