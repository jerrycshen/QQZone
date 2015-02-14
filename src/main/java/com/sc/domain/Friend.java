package com.sc.domain;

/**
 * @author shenchao
 * 
 * 好友的信息
 *
 */
public class Friend {
	
	/**
	 * 即好友的qq号
	 */
	private long friend_id;
	/**
	 * 好友的姓名
	 */
	private String friend_name;
	/**
	 * 好友的性别
	 * 0 femail
	 * 1 man
	 */
	private int friend_gender;
	/**
	 * 好友的星座
	 */
	private String friend_constellation;
	/**
	 * 好友的住址
	 */
	private String friend_address;
	
	public long getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(long friend_id) {
		this.friend_id = friend_id;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public int getFriend_gender() {
		return friend_gender;
	}
	public void setFriend_gender(int friend_gender) {
		this.friend_gender = friend_gender;
	}
	public String getFriend_constellation() {
		return friend_constellation;
	}
	public void setFriend_constellation(String friend_constellation) {
		this.friend_constellation = friend_constellation;
	}
	public String getFriend_address() {
		return friend_address;
	}
	public void setFriend_address(String friend_address) {
		this.friend_address = friend_address;
	}
	
}
