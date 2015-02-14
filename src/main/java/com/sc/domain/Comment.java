package com.sc.domain;

/**
 * @author shenchao
 * 
 * 评论
 *
 */
public class Comment {
	
	private long comment_id;
	/**
	 * 外键
	 */
	private long friend_id;
	/**
	 * name
	 */
	private String friend_name;
	/**
	 * shuoshuo_id
	 */
	private String shuoshuo_id;
	/**
	 * host_id
	 * 
	 */
	private long host_id;
	/**
	 * 评论的内容
	 */
	private String comment_content;
	/**
	 * 有两个时间，一个是字符串，还一个是long类型
	 */
	private String comment_string_time;
	private long comment_long_time;
	
	public long getHost_id() {
		return host_id;
	}
	public void setHost_id(long host_id) {
		this.host_id = host_id;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public long getComment_id() {
		return comment_id;
	}
	public void setComment_id(long comment_id) {
		this.comment_id = comment_id;
	}
	public String getShuoshuo_id() {
		return shuoshuo_id;
	}
	public void setShuoshuo_id(String shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}
	public long getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(long friend_id) {
		this.friend_id = friend_id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_string_time() {
		return comment_string_time;
	}
	public void setComment_string_time(String comment_string_time) {
		this.comment_string_time = comment_string_time;
	}
	public long getComment_long_time() {
		return comment_long_time;
	}
	public void setComment_long_time(long comment_long_time) {
		this.comment_long_time = comment_long_time;
	}
	
}
