package com.sc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sc.domain.Comment;
import com.sc.domain.Friend;
import com.sc.domain.ShuoShuo;
import com.sc.domain.Zan;
import com.sc.utils.JDBCUtils;

/**
 * @author shenchao
 * 
 * shuoshuo dao
 *
 */
public class ShuoShuoDao {
	
	Set<Long> friend_ids = null;
	
	
	/**
	 * @param shuoshuo_list 
	 */
	public void addShuoShuo(List<ShuoShuo> shuoshuo_list) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = JDBCUtils.getConnection();
			String addShuoShuoSql = "insert into shuoshuo (shuoshuo_id,shuoshuo_content,shuoshuo_string_time,shuoshuo_long_time,shuoshuo_client,shuoshuo_pic,host_id) values (?,?,?,?,?,?,?)";
			
			preparedStatement = conn.prepareStatement(addShuoShuoSql);
			
			for (ShuoShuo shuoshuo : shuoshuo_list) {
				preparedStatement.setString(1, shuoshuo.getShuoshuo_id());
				preparedStatement.setString(2, shuoshuo.getContent());
				preparedStatement.setString(3, shuoshuo.getCreateTime());
				preparedStatement.setLong(4, shuoshuo.getCreate_time());
				preparedStatement.setString(5, shuoshuo.getClient());
				preparedStatement.setInt(6, shuoshuo.isPic());
				preparedStatement.setLong(7, shuoshuo.getHost_id());
				
				//add comment
				addComment(shuoshuo.getComment_list());
				
				preparedStatement.executeUpdate();
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

	}
	
	/**
	 * add comment to db
	 * 
	 * @param comment_list
	 */
	private void addComment(List<Comment> comment_list) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		if (null == comment_list) {
			return;
		}

		try {
			conn = JDBCUtils.getConnection();
			String addCommentSql = "insert into comment (shuoshuo_id,friend_id,comment_content,comment_string_time,comment_long_time,friend_name,host_id) values (?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(addCommentSql);
			
			for (Comment comment : comment_list) {
				preparedStatement.setString(1, comment.getShuoshuo_id());
				preparedStatement.setLong(2, comment.getFriend_id());
				preparedStatement.setString(3, comment.getComment_content());
				preparedStatement.setString(4, comment.getComment_string_time());
				preparedStatement.setLong(5, comment.getComment_long_time());
				preparedStatement.setString(6, comment.getFriend_name());
				preparedStatement.setLong(7, comment.getHost_id());
				
				preparedStatement.executeUpdate();
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
	}
	
	
	/**
	 * @return all shuoshuo_id for get zan url
	 */
	public List<String> getAllShuoShuo_id(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<String> shuoshuoid_list= new ArrayList<String>();
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select shuoshuo_id from shuoshuo where host_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				shuoshuoid_list.add(resultSet.getString(1));
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
	 * add zan
	 * 
	 * @param zan_list
	 */
	public void addZan(List<Zan> zan_list) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String addZanSql = "insert into zan (zan_friend_id,zan_shuoshuo_id,host_id) values (?,?,?)" ;
			preparedStatement = conn.prepareStatement(addZanSql);
			
			for (Zan zan : zan_list) {
				preparedStatement.setLong(1, zan.getFriend_id());
				preparedStatement.setString(2, zan.getShuoshuo_id());
				preparedStatement.setLong(3, zan.getHost_id());
				preparedStatement.executeUpdate();
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
	}

	/**
	 * add friend
	 * 
	 * @param friend_list
	 * @param host_id
	 */
	public void addFriend(List<Friend> friend_list,long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String addFriendSql = "insert into friend (friend_id,friend_name,friend_gender,friend_constellation,friend_address,host_id) values (?,?,?,?,?,?)" ;
			preparedStatement = conn.prepareStatement(addFriendSql);
			
			for (Friend friend : friend_list) {
				
				if (friend_ids == null) {
					friend_ids = getAllFriend_id(host_id);
				} 
				
				if (friend_ids.contains(friend.getFriend_id())) {
					continue;
				}
				friend_ids.add(friend.getFriend_id());
				
				preparedStatement.setLong(1, friend.getFriend_id());
				preparedStatement.setString(2, friend.getFriend_name());
				preparedStatement.setInt(3, friend.getFriend_gender());
				preparedStatement.setString(4, friend.getFriend_constellation());
				preparedStatement.setString(5, friend.getFriend_address());
				preparedStatement.setLong(6, host_id);
				
				try {
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					continue;
				}
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
	}
	
	/**
	 * @param host_id
	 * @return all friend_id by host_id
	 */
	private static Set<Long> getAllFriend_id(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Set<Long> friend_ids = new HashSet<Long>();
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select friend_id from friend where host_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				friend_ids.add(resultSet.getLong(1));
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
		return friend_ids;
	}
	
	
	/**
	 * 将评论里的好友与赞好友合并成最终好友
	 */
	public void mergeFriends(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String addFriendSql = "insert into friend (friend_id,friend_name,host_id) values (?,?,?)" ;
			preparedStatement = conn.prepareStatement(addFriendSql);
			
			Set<Long> set = getAllFriend_id(host_id);
			List<Comment> list = getAllComments(host_id);
			for (Comment comment : list) {
				if (!set.contains(comment.getFriend_id())) {
					preparedStatement.setLong(1, comment.getFriend_id());
					preparedStatement.setString(2, comment.getFriend_name());
					preparedStatement.setLong(3, host_id);
					
					preparedStatement.executeUpdate();
					set = getAllFriend_id(host_id);
				}
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
	}
	
	private static List<Comment> getAllComments(long host_id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Comment> comment_list = new ArrayList<Comment>();
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select friend_id,friend_name from comment where host_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, host_id);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Comment comment = new Comment();
				comment.setFriend_id(resultSet.getLong(1));
				comment.setFriend_name(resultSet.getString(2));
				
				comment_list.add(comment);
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
		return comment_list;
	}
	
	public static void main(String[] args) {
//		mergeFriends(670304196);
	}
}
