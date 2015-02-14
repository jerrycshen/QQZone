package com.sc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import com.sc.dao.ViewChartDao;
import com.sc.domain.Comment;
import com.sc.domain.Friend;
import com.sc.domain.ShuoShuo;
import com.sc.domain.Zan;
import com.sc.utils.HelpUtils;

public class ViewChartService {

	private static final long HOST_ID = Long.parseLong(HelpUtils.getHost_id());

	private ViewChartDao chartDao = null;
	private List<ShuoShuo> shuoshuo_list = null;
	private Map<String, List<Comment>> commentlistMap = null;
	private Map<String, List<Zan>> zanlistMap = null;
	private Map<Long, Friend> friendListMap = null;

	/**
	 * 最早的一条说说的日期
	 */
	private long oldestShuoShuoDate;
	/**
	 * 最新的一条说说的日期
	 */
	private long newestShuoShuoDate;

	public ViewChartService() {
		initShuoShuoDate();
		chartDao = new ViewChartDao();
		shuoshuo_list = chartDao.getAllShuoShuo(HOST_ID);
		friendListMap = chartDao.getAllFriends(HOST_ID);
		commentlistMap = initCommentListMap();
		zanlistMap = initZanListMap();
	}

	private Map<String, List<Zan>> initZanListMap() {
		List<String> shuoshuoid_list = new ArrayList<String>();
		for (ShuoShuo shuoshuo : shuoshuo_list) {
			shuoshuoid_list.add(shuoshuo.getShuoshuo_id());
		}
		return chartDao.getAllZans(shuoshuoid_list);
	}

	private Map<String, List<Comment>> initCommentListMap() {
		List<String> shuoshuoid_list = new ArrayList<String>();
		for (ShuoShuo shuoshuo : shuoshuo_list) {
			shuoshuoid_list.add(shuoshuo.getShuoshuo_id());
		}
		return chartDao.getAllComments(shuoshuoid_list);
	}

	private void initShuoShuoDate() {
		newestShuoShuoDate = 0;
		oldestShuoShuoDate = 1561592577;
	}

	// =================================说说有关============================

	/**
	 * 统计发说说使用的客户端比例
	 */
	@Test
	public Map<String, Integer> viewShuoShuoClientChart() {

		Map<String, Integer> clientRatioMap = new HashMap<String, Integer>();
		for (ShuoShuo shuoshuo : shuoshuo_list) {
			String clientName = shuoshuo.getClient();
			if (clientRatioMap.get(clientName) != null) {
				int time = clientRatioMap.get(clientName);
				clientRatioMap.put(clientName, ++time);
			} else {
				clientRatioMap.put(clientName, 1);
			}
		}

		// test
		Set<Entry<String, Integer>> set = clientRatioMap.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry.getKey() + "======" + entry.getValue());
		}
		
		return clientRatioMap;
	}

	/**
	 * 统计图文说说所在的比例
	 */
	@Test
	public Map<String, Integer> viewShuoShuoPicChart() {
		Map<String, Integer> picRatioMap = new HashMap<String, Integer>();
		int yes_count = 0;
		int no_count = 0;
		for (ShuoShuo shuoShuo : shuoshuo_list) {
			int pic = shuoShuo.isPic();
			if (pic == 1) {
				picRatioMap.put("图文说说", ++yes_count);
			} else {
				picRatioMap.put("纯文本说说", ++no_count);
			}
		}

		// test
		Set<Entry<String, Integer>> set = picRatioMap.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry.getKey() + "======" + entry.getValue());
		}
		
		return picRatioMap;
	}

	/**
	 * @return 说说的总数
	 */
	public int getTotalShuoShuoCount() {
		return shuoshuo_list.size();
	}

	/**
	 * 统计说说的创建时间，按年生成折线图
	 */
	@Test
	public Map<Integer, Integer> viewShuoShuoDateChart() {
		Map<Integer, Integer> shuoshuoCreateDateMap = new TreeMap<Integer, Integer>();
		for (ShuoShuo shuoShuo : shuoshuo_list) {
			long longtime = shuoShuo.getCreate_time();

			// 计算最近与最久的说说发送时间
			calculateOldestNewestShuoShuoDate(longtime);

			int year = HelpUtils.getDateByLongTime(longtime, 1);
			if (shuoshuoCreateDateMap.get(year) != null) {
				int count = shuoshuoCreateDateMap.get(year);
				shuoshuoCreateDateMap.put(year, ++count);
			} else {
				shuoshuoCreateDateMap.put(year, 1);
			}
		}
		// test
		Set<Entry<Integer, Integer>> set = shuoshuoCreateDateMap.entrySet();
		for (Entry<Integer, Integer> entry : set) {
			System.out.println(entry.getKey() + "======" + entry.getValue());
		}

		System.out.println("最近的说说日期:" + getNewestShuoShuoDate());
		System.out.println("最久的说说日期" + getOldestShuoShuoDate());
		System.out.println("最久的说说与最近的说说间隔多少天"
				+ getIntervalDaysFromOldestToNewestShuoShuo());
		System.out.println("总说说数量:" + getTotalShuoShuoCount());
		System.out.println("平均多少天发一条说说:" + getIntervalDaysPerShuoShuo());
		
		return shuoshuoCreateDateMap;
	}

	/**
	 * 计算最近与最久的说说发送时间
	 * 
	 * @param longtime
	 */
	private void calculateOldestNewestShuoShuoDate(long longtime) {
		if (longtime >= newestShuoShuoDate) {
			newestShuoShuoDate = longtime;
		} else if (longtime < oldestShuoShuoDate) {
			oldestShuoShuoDate = longtime;
		}
	}

	/**
	 * @return 返回最早的说说的日期
	 */
	public String getOldestShuoShuoDate() {
		return HelpUtils.getStringDate(oldestShuoShuoDate);
	}

	/**
	 * @return 返回最近的说说的日期
	 */
	public String getNewestShuoShuoDate() {
		return HelpUtils.getStringDate(newestShuoShuoDate);
	}

	/**
	 * @return 返回最近的说说与最早的说说相隔的天数
	 */
	public int getIntervalDaysFromOldestToNewestShuoShuo() {
		return (int) ((newestShuoShuoDate - oldestShuoShuoDate) / 24 / 3600);
	}

	public double getIntervalDaysPerShuoShuo() {
		return getIntervalDaysFromOldestToNewestShuoShuo()
				/ Double.valueOf(getTotalShuoShuoCount());
	}

	// =================================评论有关============================
	/**
	 * 收到总的评论数
	 */
	public int getAllCommentsCount() {
		int count = 0;
		Set<Entry<String, List<Comment>>> set = commentlistMap.entrySet();
		for (Entry<String, List<Comment>> entry : set) {
			count += entry.getValue().size();
		}
		return count;
	}

	/**
	 * 显示评论的男女比例
	 */
	@Test
	public Map<String, Integer> viewCommentGenderRatioChart() {
		Map<String, Integer> genderMap = new HashMap<String, Integer>();
		genderMap.put("帅哥", 0);
		genderMap.put("美女", 0);
		Set<Entry<String, List<Comment>>> set = commentlistMap.entrySet();
		for (Entry<String, List<Comment>> entry : set) {
			List<Comment> comment_list = entry.getValue();
			for (Comment comment : comment_list) {
				Friend friend = friendListMap.get(comment.getFriend_id());
				if (friend.getFriend_gender() == 0) {
					int count = genderMap.get("美女");
					genderMap.put("美女", ++count);
				} else {
					int count = genderMap.get("帅哥");
					genderMap.put("帅哥", ++count);
				}
			}
		}
		System.out.println("帅哥：" + genderMap.get("帅哥") + "\t美女："
				+ genderMap.get("美女"));
		return genderMap;
	}

	/**
	 * 显示评论的好友星座比例
	 */
	@Test
	public Map<String, Integer> viewCommentConstellationRatioChart() {
		Map<String, Integer> commentConstellationMap = new HashMap<String, Integer>();
		Set<Entry<String, List<Comment>>> set = commentlistMap.entrySet();
		for (Entry<String, List<Comment>> entry : set) {
			List<Comment> comment_list = entry.getValue();
			for (Comment comment : comment_list) {
				Friend friend = friendListMap.get(comment.getFriend_id());
				if ("".equals(friend.getFriend_constellation()) || friend.getFriend_constellation()==null) {
					continue;
				}
				if (commentConstellationMap.get(friend
						.getFriend_constellation()) == null) {
					commentConstellationMap.put(
							friend.getFriend_constellation(), 1);
				} else {
					int count = commentConstellationMap.get(friend
							.getFriend_constellation());
					commentConstellationMap.put(
							friend.getFriend_constellation(), ++count);
				}
			}
		}

		// test
		Set<Entry<String, Integer>> set1 = commentConstellationMap.entrySet();
		for (Entry<String, Integer> entry : set1) {
			System.out.println(entry.getKey() + "======" + entry.getValue());
		}
		return commentConstellationMap;

	}
	
	/**
	 * 显示好友中评论最多top10
	 */
	@Test
	public Map<String,Integer> viewWhoCommentMore() {
		
		Map<String,Integer> whoCommentMoreMap = new HashMap<String, Integer>();
		
		Map<Long,Integer> map = chartDao.getWhoCommentMore(HOST_ID);
		Set<Entry<Long, Integer>> set = map.entrySet();
		for (Entry<Long, Integer> entry : set) {
			whoCommentMoreMap.put(friendListMap.get(entry.getKey()).getFriend_name(), entry.getValue());
		}
		return whoCommentMoreMap;
	}
	
	
	/**
	 * 显示评论最多的说说内容和评论次数
	 */
	@Test
	public Map<String,String> getMostCommentsShuoShuo() {
		Map<String,String> map = new HashMap<String, String>();
		int num = 0;
		String shuoshuo_id="";
		Set<Entry<String, List<Comment>>> set = commentlistMap.entrySet();
		for (Entry<String, List<Comment>> entry : set) {
			int commentNum = entry.getValue().size();
			if (commentNum > num) {
				num = commentNum;
				shuoshuo_id = entry.getKey();
			}
		}
		System.out.println(num);
		map.put("shuoshuo_content", chartDao.getShuoShuoContent(shuoshuo_id));
		map.put("num", num+"");
		return map;
	}
	
	
	/**
	 * 得到每条说说评价多少评论
	 */
	@Test
	public double getCommentsPerShuoShuo() {
		return getAllCommentsCount() / Double.valueOf(getTotalShuoShuoCount());
	}
	
	
	//=====================================赞有关===========================
	
	
	/**
	 * 得到赞的数量
	 */
	public int getZanCount() {
		return chartDao.getZanCount();
	}
	
	/**
	 * 显示赞的男女比例
	 */
	@Test
	public Map<String, Integer> viewZanGenderRatioChart() {
		Map<String, Integer> genderMap = new HashMap<String, Integer>();
		genderMap.put("帅哥", 0);
		genderMap.put("美女", 0);
		Set<Entry<String, List<Zan>>> set = zanlistMap.entrySet();
		for (Entry<String, List<Zan>> entry : set) {
			List<Zan> zan_list = entry.getValue();
			for (Zan zan : zan_list) {
				Friend friend = friendListMap.get(zan.getFriend_id());
				if (friend == null) {
					continue;
				}
				if (friend.getFriend_gender() == 0) {
					int count = genderMap.get("美女");
					genderMap.put("美女", ++count);
				} else {
					int count = genderMap.get("帅哥");
					genderMap.put("帅哥", ++count);
				}
			}
		}
		System.out.println("赞中\t帅哥：" + genderMap.get("帅哥") + "\t美女："
				+ genderMap.get("美女"));
		return genderMap;
	}
	
	
	/**
	 * 显示评论的好友星座比例
	 */
	@Test
	public Map<String, Integer> viewZanConstellationRatioChart() {
		Map<String, Integer> zanConstellationMap = new HashMap<String, Integer>();
		Set<Entry<String, List<Zan>>> set = zanlistMap.entrySet();
		for (Entry<String, List<Zan>> entry : set) {
			List<Zan> zan_list = entry.getValue();
			for (Zan zan : zan_list) {
				Friend friend = friendListMap.get(zan.getFriend_id());
				if (friend == null || friend.getFriend_constellation().equals("") || friend.getFriend_constellation().equals("null")) {
					continue;
				}
				if (zanConstellationMap.get(friend
						.getFriend_constellation()) == null) {
					zanConstellationMap.put(
							friend.getFriend_constellation(), 1);
				} else {
					int count = zanConstellationMap.get(friend
							.getFriend_constellation());
					zanConstellationMap.put(
							friend.getFriend_constellation(), ++count);
				}
			}
		}

		// test
		Set<Entry<String, Integer>> set1 = zanConstellationMap.entrySet();
		for (Entry<String, Integer> entry : set1) {
			System.out.println("赞中\t"+entry.getKey() + "======" + entry.getValue());
		}
		
		return zanConstellationMap;

	}
	
	/**
	 * 显示好友中评论最多top10
	 */
	@Test
	public Map<String,Integer> viewWhoZanMore() {
		
		Map<String,Integer> whoZanMoreMap = new HashMap<String, Integer>();
		
		Map<Long,Integer> map = chartDao.getWhoZanMore(HOST_ID);
		Set<Entry<Long, Integer>> set = map.entrySet();
		for (Entry<Long, Integer> entry : set) {
			whoZanMoreMap.put(friendListMap.get(entry.getKey()).getFriend_name(), entry.getValue());
		}
		
		return whoZanMoreMap;
	}
	
	
	/**
	 * 显示评论最多的说说内容和评论次数
	 */
	@Test
	public Map<String,String> getMostZansShuoShuo() {
		Map<String,String> map = new HashMap<String, String>();
		int num = 0;
		String shuoshuo_id="";
		Set<Entry<String, List<Zan>>> set = zanlistMap.entrySet();
		for (Entry<String, List<Zan>> entry : set) {
			int zanNum = entry.getValue().size();
			if (zanNum > num) {
				num = zanNum;
				shuoshuo_id = entry.getKey();
			}
		}
		map.put("shuoshuo_content", chartDao.getShuoShuoContent(shuoshuo_id));
		map.put("num", num+"");
		return map;
		
	}
	
	
	/**
	 * 得到每条说说评价多少评论
	 */
	@Test
	public double getZansPerShuoShuo() {
		return  getZanCount() / Double.valueOf(getTotalShuoShuoCount());
	}
	
	
	//======================================好友相关================================
	
	
	/**
	 * 显示你的好友的城市
	 */
	@Test
	public Map<String,Integer> viewAddressRatio() {
		Map<String,Integer> map = chartDao.getAddressMore(HOST_ID);
		Set<Entry<String, Integer>> set = map.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println("地址是："+entry.getKey() + "******" + entry.getValue());
		}
		return map;
	}
	
	/**
	 * 显示你的好友的星座
	 */
	@Test
	public Map<String,Integer> viewFriendsConstellation() {
		return chartDao.getFriendsConstellation(HOST_ID);
	}

}
