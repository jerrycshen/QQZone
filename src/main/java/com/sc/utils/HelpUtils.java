package com.sc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenchao 此类为工具类，提供静态方法
 *
 */
public class HelpUtils {
	
	
	/*
	 * 
	 * 每次修改url时候注意
	 * start_url pos 值用 page替代 g_tk去掉
	 * 
	 * http://taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=825051757&inCharset=utf-8&outCharset=utf-8&hostUin=825051757&notice=0&sort=0&pos=20&num=20&cgi_host=http%3A%2F%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6&code_version=1&format=jsonp&need_private_comment=1&g_tk=1868489154
	 * 
	 * zan——url id值用zanurl替代 g_tk去掉
	 * 
	 * http://users.qzone.qq.com/cgi-bin/likes/get_like_list_app?uin=670304196&unikey=http%3A%2F%2Fuser.qzone.qq.com%2F1262937377%2Fmood%2F21e5464b61fd81533b740b00.1&begin_uin=0&query_count=60&if_first_page=1&g_tk=1868489154
	 * 
	 * 
	 * */
	private static  String START_URL = "http://taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=qqhao&inCharset=utf-8&outCharset=utf-8&hostUin=qqhao&notice=0&sort=0&pos=page&num=20&cgi_host=http%3A%2F%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6&code_version=1&format=jsonp&need_private_comment=1&g_tk=";
	private static  String ZAN_URL = "http://users.qzone.qq.com/cgi-bin/likes/get_like_list_app?uin=670304196&unikey=http%3A%2F%2Fuser.qzone.qq.com%2Fqqhao%2Fmood%2Fzanurl.1&begin_uin=0&query_count=60&if_first_page=1&g_tk=";
	private static final String HOST_ID = "1224097343";
	private static final String SKEY = "@6QppBvLLy";
	
	/**
	 * 时间戳转化为时间
	 * 
	 * @param time 
	 * <br>
	 *            1 year<br>
	 *            2 month<br>
	 *            3 date<br>
	 */
	public static int getDateByLongTime(long time, int flag) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(time * 1000);
		Date date;
		try {
			date = format.parse(d);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (flag == 1) {
				return calendar.get(Calendar.YEAR);
			}
			if (flag == 2) {
				return calendar.get(Calendar.MONTH)+1;
			} else {
				return calendar.get(Calendar.DATE);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static String getStringDate(long longtime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String d = format.format(longtime * 1000);
		return d;
	}

	/**
	 * @param str
	 *            cookie中skey值
	 * @return 计算g_tk值
	 */
	public static String getG_TK(String str) {
		int hash = 5381;
		for (int i = 0, len = str.length(); i < len; ++i) {
			hash += (hash << 5) + (int) (char) str.charAt(i);
		}
		return (hash & 0x7fffffff) + "";
	}

	/**
	 * @param jsonp
	 *            服务端直接返回过来的数据
	 * @return 抽取出json
	 */
	public static String getJsonFromJsonp(String jsonp) {
		Pattern pattern = Pattern.compile("_Callback\\((.*)\\);");
		Matcher matcher = pattern.matcher(jsonp);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * the zan jsonp is not format,so must rebuild the jsonp
	 * 
	 * @param jsonp
	 * @return
	 */
	public static String formatJsonp(String jsonp) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new StringReader(jsonp));
			String temp = null;
			StringBuilder builder = new StringBuilder();
			while ((temp = bufferedReader.readLine()) != null) {
				builder.append(temp);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the start url with g_tk
	 */
	public static String getStartUrl() {
		return (START_URL + getG_TK(SKEY)).replace("qqhao", HOST_ID);
	}

	/**
	 * @param skey
	 * @return the zan url with g_tk
	 */
	public static String getZanUrl() {
		return (ZAN_URL + getG_TK(SKEY)).replace("qqhao", HOST_ID);
	}

	/**
	 * @return host_id
	 */
	public static String getHost_id() {
		return HOST_ID;
	}
	
	public static String getSkey() {
		return SKEY;
	}

	public static void main(String[] args) {
//		String s = "_Callback({'code':0,'subcode':0,'message':'succ!','cost_time':0,'data':{'total_number':0,'is_dolike':0,'like_uin_info':[]}});";
//		System.out.println(getJsonFromJsonp(s));
		long time = 1361592577;
		System.out.println(getStringDate(time));
	}

}
