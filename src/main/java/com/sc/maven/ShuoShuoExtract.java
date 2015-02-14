package com.sc.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sc.utils.ConfigIO;
import com.sc.utils.HelpUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class ShuoShuoExtract implements PageProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(ShuoShuoExtract.class);
	
	private static int NOW_PAGE= 1;
	
	private static String start_url;
	
	private static int totalNumOfShuoShuo = 0;
	
	public ShuoShuoExtract() {
		start_url = HelpUtils.getStartUrl();
	}
	
	private Site site = Site
			.me()
			.setUserAgent(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.91 Safari/537.36")
			.setSleepTime(3000)
			.setTimeOut(20000)
			.addHeader("Referer", "http://user.qzone.qq.com/670304196/311")
//			.addHeader("Host", "users.qzone.qq.com")
//			.addCookie("Loading", "Yes")
//			.addCookie("QZ_FE_WEBP_SUPPORT", "1")
			.addCookie("RK", "yOXDvGGsW4")
//			.addCookie("__Q_w_s__QZN_TodoMsgCnt", "1")
//			.addCookie("__Q_w_s__appDataSeed", "1")
//			.addCookie("__Q_w_s_hat_seed", "1")
//			.addCookie("blabla", "dynamic")
//			.addCookie("cpu_performance_v8", "3")
			.addCookie("o_cookie", "670304196")
			.addCookie("p_skey", "AiyBvsrh7wuEenOYd2gUy*WDD2dttmSyvcZdn55g0YM_")
			.addCookie("pgv_info", "ssid=s1043298460")
			.addCookie("property20", "84EB278D50D4CB451FF26E342D6DA90D975316581367C3F29711DA796D0E3AFD97B886A2D35193FB")
			.addCookie("pgv_pvid", "3778040260")
			.addCookie("pt4_token", "3xgzfNBIt-eL8Lrs1fk-XQ__")
			.addCookie("pt2gguin", "o0670304196")
			.addCookie("ptcz", "59734da6a19d837afbeec175175d42b45ee31e83394172f61bfac029b48deecf")
			.addCookie("ptisp", "ctc")
			.addCookie("rv2", "80F477246F2DEE10351A68A516D3A431B715EC2FB9B402B37C")
			.addCookie("ptui_loginuin", "670304196")
			.addCookie("qzspeedup", "sdch")
			.addCookie("skey", HelpUtils.getSkey())
			.addCookie("uin", "o0670304196");

	public void process(Page page) {
		String json = HelpUtils.getJsonFromJsonp(page.getRawText());
//		System.out.println(json);
		totalNumOfShuoShuo =  Integer.parseInt(new JsonPathSelector("$.total").select(json));
		
		logger.info("total shuoshuo: " + totalNumOfShuoShuo);
		logger.info("total page: " + getTotalPages(totalNumOfShuoShuo));
		
		if (NOW_PAGE < getTotalPages(totalNumOfShuoShuo)) {
			page.addTargetRequest(start_url.replace("page", NOW_PAGE*20+""));
			
			logger.info("now download page: " + NOW_PAGE);
			
			NOW_PAGE++;
		}
		page.putField("json", json);
		page.putField("host_id", HelpUtils.getHost_id());
	}

	public Site getSite() {
		return site;
	}
	
	/**
	 * @param totalShuoShuo the num of shuoshuo
	 * @return the num of pages
	 */
	private int getTotalPages(int totalNumOfShuoShuo) {
		if (totalNumOfShuoShuo % 20 == 0) {
			return totalNumOfShuoShuo / 20;
		}else {
			return totalNumOfShuoShuo / 20 + 1;
		}
	}

	public static void main(String[] args) {
		Spider.create(new ShuoShuoExtract()).thread(10)
				.addUrl(start_url.replace("page", "0"))
				.addPipeline(new ShuoShuoPipeline())
				.run();
		ConfigIO.writeShuoShuoNum(totalNumOfShuoShuo);
	}

}
