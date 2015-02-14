package com.sc.main;

import com.sc.service.ViewChartService;
import com.sc.ui.ClientRatioChart;
import com.sc.ui.CommentGenderRatioChart;
import com.sc.ui.FriendsConstellationChart;
import com.sc.ui.FriendsFromChart;
import com.sc.ui.PicRatioChart;
import com.sc.ui.ShuoShuoSummaryChart;
import com.sc.ui.WhoCommentMoreChart;
import com.sc.ui.WhoZanMoreChart;
import com.sc.ui.ZanGenderRatioChart;

public class Main {

	public static void main(String[] args) {
		ViewChartService chartService = new ViewChartService();
		
		new ClientRatioChart(chartService);
		new PicRatioChart(chartService);
		new CommentGenderRatioChart(chartService);
		new ZanGenderRatioChart(chartService);
		new WhoCommentMoreChart(chartService);
		new WhoZanMoreChart(chartService);
		new FriendsFromChart(chartService);
		new FriendsConstellationChart(chartService);
		new ShuoShuoSummaryChart(chartService);
	}

}
