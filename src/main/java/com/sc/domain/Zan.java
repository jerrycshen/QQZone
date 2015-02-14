package com.sc.domain;

/**
 * @author shenchao
 * 
 * 赞
 *
 */
public class Zan {
	
	private long zan_id;
	/**
	 * 外键
	 */
	private long friend_id;
	/**
	 * shuoshuo_id
	 */
	private String shuoshuo_id;
	
	private long host_id;
	
	public long getHost_id() {
		return host_id;
	}
	public void setHost_id(long host_id) {
		this.host_id = host_id;
	}
	public String getShuoshuo_id() {
		return shuoshuo_id;
	}
	public void setShuoshuo_id(String shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}
	public long getZan_id() {
		return zan_id;
	}
	public void setZan_id(long zan_id) {
		this.zan_id = zan_id;
	}
	public long getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(long friend_id) {
		this.friend_id = friend_id;
	}
	
}
