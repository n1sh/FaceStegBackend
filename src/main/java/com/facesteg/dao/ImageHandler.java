package com.facesteg.dao;

public interface ImageHandler {
	public void saveName(int number, String name);
	public String getImage(int number);
	public int saveUser(String username, String name, String dob);
	public String checkUser(int id);
	public String checkUsername(String username);
}
