package com.sc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sc.domain.Comment;
import com.sc.domain.Friend;
import com.sc.domain.ShuoShuo;
import com.sc.domain.Zan;
import com.sc.utils.JDBCUtils;

/**
 * 主要用于数据库的读取
 * @author shenchao
 *
 */
public class ViewChartDao {
	
	/**
	 * 得到所有说说
	 * @param host_id
	 * @return
	 */
	public List<ShuoShuo> getAllShuoShuo(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<ShuoShuo> shuoshuoid_list= new ArrayList<ShuoShuo>();
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select shuoshuo_id,shuoshuo_content,shuoshuo_long_time,shuoshuo_client,shuoshuo_pic from shuoshuo where host_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ShuoShuo shuoshuo = new ShuoShuo();
				shuoshuo.setShuoshuo_id(resultSet.getString(1));
				shuoshuo.setContent(resultSet.getString(2));
				shuoshuo.setCreate_time(resultSet.getLong(3));
				shuoshuo.setClient(resultSet.getString(4));
				shuoshuo.setPic(resultSet.getInt(5));
				
				shuoshuoid_list.add(shuoshuo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shuoshuoid_list;
	}
	
	/**
	 * 每条说说都有若干条评论
	 * @param shuoshuoid_list
	 * @return 
	 */
	public Map<String,List<Comment>> getAllComments(List<String> shuoshuoid_list) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<String,List<Comment>> commentlistMap = new HashMap<String, List<Comment>>();

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select comment_id,friend_id,comment_content,comment_long_time from comment where shuoshuo_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			
			for (String shuoshuo_id : shuoshuoid_list) {
				List<Comment> comment_list = new ArrayList<Comment>();
				
				preparedStatement.setString(1, shuoshuo_id);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					Comment comment = new Comment();
					comment.setComment_id(resultSet.getLong(1));
					comment.setShuoshuo_id(shuoshuo_id);
					comment.setFriend_id(resultSet.getLong(2));
					comment.setComment_content(resultSet.getString(3));
					comment.setComment_long_time(resultSet.getLong(4));
					
					comment_list.add(comment);
				}
				commentlistMap.put(shuoshuo_id, comment_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return commentlistMap;
	}
	
	
	/**
	 * 根据host_id 得到所有的朋友
	 * @param host_id
	 * @return
	 */
	public Map<Long,Friend> getAllFriends(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<Long,Friend> friendListMap = new HashMap<Long, Friend>();

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select friend_id,friend_name,friend_gender,friend_constellation,friend_address from friend where host_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Friend friend = new Friend();
				friend.setFriend_id(resultSet.getLong(1));
				friend.setFriend_name(resultSet.getString(2));
				friend.setFriend_gender(resultSet.getInt(3));
				friend.setFriend_constellation(resultSet.getString(4));
				friend.setFriend_address(resultSet.getString(5));
				
				friendListMap.put(resultSet.getLong(1), friend);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return friendListMap;
	}
	
	
	/**
	 * 
	 * @param host_id
	 * @return 返回评论最多的10位
	 */
	public Map<Long,Integer> getWhoCommentMore(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<Long,Integer> map = new HashMap<Long, Integer>();

		try {
			int count = 0;
			conn = JDBCUtils.getConnection();
			String sql = "select friend_id,COUNT(*) as count from comment where host_id = ? group by friend_id order by count desc";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next() && count++ <10) {
				map.put(resultSet.getLong(1), resultSet.getInt(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 根据说说的id返回说说的内容
	 * @param shuoshuo_id
	 * @return
	 */
	public String getShuoShuoContent(String shuoshuo_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select shuoshuo_content from shuoshuo where shuoshuo_id=?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, shuoshuo_id);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	/**
	 * @return 赞的数量
	 */
	public int getZanCount() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select count(*) from zan";
			preparedStatement = conn.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	/**
	 * 返回每条说说所有赞
	 * @param shuoshuoid_list
	 * @return
	 */
	public Map<String,List<Zan>> getAllZans(List<String> shuoshuoid_list) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<String,List<Zan>> zanlistMap = new HashMap<String, List<Zan>>();

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select zan_id,zan_friend_id from zan where zan_shuoshuo_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			
			for (String shuoshuo_id : shuoshuoid_list) {
				List<Zan> zan_list = new ArrayList<Zan>();
				
				preparedStatement.setString(1, shuoshuo_id);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					Zan zan = new Zan();
					
					zan.setZan_id(resultSet.getLong(1));
					zan.setFriend_id(resultSet.getLong(2));
					zan.setShuoshuo_id(shuoshuo_id);
					
					zan_list.add(zan);
				}
				zanlistMap.put(shuoshuo_id, zan_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return zanlistMap;
	}
	
	
	/**
	 * 
	 * @param host_id
	 * @return 返回评论最多的10位
	 */
	public Map<Long,Integer> getWhoZanMore(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<Long,Integer> map = new HashMap<Long, Integer>();

		try {
			int count = 0;
			conn = JDBCUtils.getConnection();
			String sql = "select zan_friend_id,COUNT(*) as count from zan where host_id = ? group by zan_friend_id order by count desc";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next() && count++ <10) {
				map.put(resultSet.getLong(1), resultSet.getInt(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	
	/**
	 * 
	 * @param host_id
	 * @return 返回城市最多的前10
	 */
	public Map<String,Integer> getAddressMore(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<String,Integer> map = new HashMap<String, Integer>();

		try {
			int count = 0;
			conn = JDBCUtils.getConnection();
			String sql = "select friend_address,COUNT(*) as count from friend where host_id = ? and friend_address != '' group by friend_address order by count desc";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next() && count++ <10  ) {
				map.put(resultSet.getString(1), resultSet.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param host_id
	 * @return 好友星座比例
	 */
	public Map<String,Integer> getFriendsConstellation(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Map<String,Integer> map = new HashMap<String, Integer>();

		try {
			conn = JDBCUtils.getConnection();
			String sql = "select friend_constellation,COUNT(*) as count from friend where host_id = ? and friend_constellation != '' group by friend_constellation ";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()  ) {
				map.put(resultSet.getString(1), resultSet.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
}
