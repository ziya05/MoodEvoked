package com.ziya05.MoodEvoked.backend.pojo;

import java.util.List;

public class Result {
	
	private User user;
	
	private List<Item> data;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}
	
	public void print() {
		System.out.println("用户名：" + this.user.getText());
		for(Item item : this.getData()) {
			System.out.println(item.getName() + ":" + item.getVal());
		}
	}
}
