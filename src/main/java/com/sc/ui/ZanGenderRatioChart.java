package com.sc.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.sc.service.ViewChartService;

public class ZanGenderRatioChart {
	
	public ZanGenderRatioChart(ViewChartService chartService) {
		initClientRatioChart(chartService);
	}

	/**
	 * @param chartService
	 * @return 发表说说使用的客户端占比
	 */
	private void initClientRatioChart(ViewChartService chartService) {

		Map<String, Integer> map = chartService.viewZanGenderRatioChart();
		// 创建饼图数据对象
		DefaultPieDataset dfp = new DefaultPieDataset();
		Set<Entry<String, Integer>> set = map.entrySet();
		for (Entry<String, Integer> entry : set) {
			dfp.setValue(entry.getKey(), entry.getValue());
		}
		// createpieChart3D创建3D饼图
		JFreeChart chart = ChartFactory.createPieChart3D("你收到赞的男女比例",
				dfp, true, true, true);
		// 图片背景色
		chart.setBackgroundPaint(Color.white);
		// 设置标题文字
		ChartFrame frame = new ChartFrame("你收到赞的男女比例", chart, true);
		// 取得3D饼图对象
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图形边框颜色
		plot.setBaseSectionOutlinePaint(Color.WHITE);
		// 图形边框粗细
		plot.setBaseSectionOutlineStroke(new BasicStroke(1.0f));
		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.45f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(true);
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(360);
		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		// 设置饼图各部分标签字体
		plot.setLabelFont(new Font("微软雅黑", Font.ITALIC, 16));
		// 设置分饼颜色
		plot.setSectionPaint(0, new Color(244, 194, 144));
		// 定义字体格式  
        Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE,20);  
        TextTitle title = new TextTitle("你的所有赞帅哥多还是美女多？");  
        title.setFont(font);  
        // 设置字体,非常关键不然会出现乱码的,下方的字体  
        chart.setTitle(title);  
        frame.pack(); 
        frame.setVisible(true);
	}

}
