package com.m.test.pojo;

/*
 * 定义 pojo 实体类使用 Gson 直接将 Class 转换成 JSON 
 * pojo 实体类 Books，结构如下：
 * 
 * {
 *     "bkno": 1, 
 *     "bkname": "shuxue", 
 *     "bkmoney": 35,
 * }
 * 
 * */

public class Book {

	private int bkno;
	private String bkname;
	private String bkmoney;

	public void setbkno(int bkno) {
		this.bkno = bkno;
	}

	public int getbkno() {
		return bkno;
	}

	public void setbkname(String bkname) {
		this.bkname = bkname;
	}

	public String getbkname() {
		return bkname;
	}

	public void setbkmoney(String bkmoney) {
		this.bkmoney = bkmoney;
	}

	public 	String getbkmoney() {
		return bkmoney;
	}

}