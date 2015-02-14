package com.sc.domain;

import java.util.List;

/**
 * @author shenchao
 * 说说信息的封装
 *
 */
public class ShuoShuo {
	
	private String shuoshuo_id;
	/**
	 * 代表谁发的说说
	 */
	private Long host_id;
	/**
	 * 发表的时间，字符串记
	 */
	private String createTime;
	/**
	 * 发表的时间，毫秒值记
	 */
	private long create_time;
	/**
	 * 说说的内容
	 */
	private String content;
	/**
	 * 通过什么客户端发表说说，ipad，iphone，网页等（为空）
	 */
	private String client;
	/**
	 * 是否为图片说说
	 * 1 代表是
	 * 0 代表不是
	 */
	private int isPic;
	/**
	 * 评论列表
	 */
	private List<Comment> comment_list;
	/**
	 * 赞列表
	 */
	private List<Zan> like_list;
	
	public Long getHost_id() {
		return host_id;
	}
	public void setHost_id(Long host_id) {
		this.host_id = host_id;
	}
	public String getShuoshuo_id() {
		return shuoshuo_id;
	}
	public void setShuoshuo_id(String shuoshuo_id) {
		this.shuoshuo_id = shuoshuo_id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public int isPic() {
		return isPic;
	}
	public void setPic(int isPic) {
		this.isPic = isPic;
	}
	public List<Comment> getComment_list() {
		return comment_list;
	}
	public void setComment_list(List<Comment> comment_list) {
		this.comment_list = comment_list;
	}
	public List<Zan> getLike_list() {
		return like_list;
	}
	public void setLike_list(List<Zan> like_list) {
		this.like_list = like_list;
	}
}
