package com.sc.maven;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.sc.dao.ShuoShuoDao;
import com.sc.utils.ConfigIO;
import com.sc.utils.HelpUtils;

public class ZanExtract implements PageProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(ZanExtract.class);
	
	private static String zan_url;
	
	private static List<String> shuoshuoid_list;
	private static int count = 0;
	
	private static int shuoshuo_num = ConfigIO.readShuoShuoNum();
	
	/**
	 *  每次运行都必须记得修改
	 */
	public ZanExtract() {
		shuoshuoid_list = new ShuoShuoDao().getAllShuoShuo_id(Long.parseLong(HelpUtils.getHost_id()));
		zan_url = HelpUtils.getZanUrl();
	}
	
	private Site site = Site
			.me()
			.setCharset("utf-8")
			.setUserAgent(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.91 Safari/537.36")
			.setSleepTime(3000)
			.setTimeOut(20000)
			.addHeader("Host", "users.qzone.qq.com")
			.addCookie("Loading", "Yes")
			.addCookie("QZ_FE_WEBP_SUPPORT", "1")
			.addCookie("RK", "yOXDvGGsW4")
			.addCookie("__Q_w_s__QZN_TodoMsgCnt", "1")
			.addCookie("__Q_w_s__appDataSeed", "1")
			.addCookie("__Q_w_s_hat_seed", "1")
			.addCookie("blabla", "dynamic")
			.addCookie("cpu_performance_v8", "2")
			.addCookie("o_cookie", "670304196")
			.addCookie("p_skey", "d6FwzZXKLYbu8pIftZ3ILENkxrVfFJ7VdKZlFvCuMgs_")
			.addCookie("pgv_info", "ssid=s1043298460")
			.addCookie("pgv_pvid", "3778040260")
			.addCookie("pt4_token", "pI2BsPBQ98DvPCcemxdbrQ__")
			.addCookie("pt2gguin", "o0670304196")
			.addCookie("ptcz", "59734da6a19d837afbeec175175d42b45ee31e83394172f61bfac029b48deecf")
			.addCookie("ptisp", "ctc")
			.addCookie("qm_username", "670304196")
			.addCookie("ptui_loginuin", "670304196")
			.addCookie("qzspeedup", "sdch")
			.addCookie("skey", HelpUtils.getSkey())
			.addCookie("uin", "o0670304196");

	/* 
	 *记得这里修改qq号
	 */
	public void process(Page page) {
		
		logger.info("共" + shuoshuo_num + "条说说");
		
		
		page.putField("host_id", HelpUtils.getHost_id());
		page.putField("shuoshuo_id", shuoshuoid_list.get(count));
		System.out.println(shuoshuoid_list.get(count));
		System.out.println(page.getRawText());
		page.putField("zan", HelpUtils.getJsonFromJsonp(HelpUtils.formatJsonp(page.getRawText().trim())));
		if (++count < shuoshuoid_list.size()) {
			page.addTargetRequest(getZanUrl(count));
		}
		logger.info("当前正抽取第 " + count +"条赞");
	}

	public Site getSite() {
		return site;
	}
	
	/**
	 * @param shuoshuo_id
	 * @return zan url
	 */
	private static String getZanUrl(int index) {
		return zan_url.replace("zanurl", shuoshuoid_list.get(index));
	}

	public static void main(String[] args) {
		Spider.create(new ZanExtract()).thread(10)
				.addUrl(getZanUrl(0))
				.addPipeline(new ZanPipeline())
				.run();
		
		new ShuoShuoDao().mergeFriends(Long.parseLong(HelpUtils.getHost_id()));
		logger.info("数据下载完成......");
	}

}
