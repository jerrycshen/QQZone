package com.sc.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sc.service.ViewChartService;

public class ShuoShuoSummaryChart {
	
	ViewChartService chartService = null;

	public ShuoShuoSummaryChart(ViewChartService chartService) {
		this.chartService = chartService;
		JPanel chartPanel = initShuoShuoSummaryChart();
		initFrame(chartPanel);
	}

	private void initFrame(JPanel chartPanel) {
		JFrame frame = new JFrame("Summary");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		JPanel jPanel = new JPanel();
		jPanel.setBackground(Color.WHITE);
		
		jPanel.setLayout(new GridLayout(3, 1, 0, 5));
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("第一条说说始于：" + chartService.getOldestShuoShuoDate() + "   ");
		sb1.append("最后一条说说发表于：" + chartService.getNewestShuoShuoDate()+"   ");
		sb1.append("两者相隔：" + chartService.getIntervalDaysFromOldestToNewestShuoShuo() + "天   ");
		sb1.append("期间你共发表了：" + chartService.getTotalShuoShuoCount() +"  条说说   ");
		sb1.append("平均约：" + Math.round(chartService.getIntervalDaysPerShuoShuo()) + "天／条");
		JLabel jLabel = new JLabel(sb1.toString());
		jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("共收到 "+chartService.getAllCommentsCount()+"　条评论   ");
		sb2.append("其中评论最多的说说有：" + chartService.getMostCommentsShuoShuo().get("num")+" 条评论   ");
		sb2.append("平均约：" + Math.round(chartService.getCommentsPerShuoShuo()) + "条评论/每条说说");
		JLabel jLabe2 = new JLabel(sb2.toString());
		jLabe2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jLabe2.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		StringBuilder sb3 = new StringBuilder();
		sb3.append("共收到 " + chartService.getZanCount()+"个赞   ");
		sb3.append("其中赞最多的说说有：" + chartService.getMostZansShuoShuo().get("num")+" 个赞   ");
		sb3.append("平均约：" + Math.round(chartService.getZansPerShuoShuo()) +"个赞/每条说说");
		JLabel jLabe3 = new JLabel(sb3.toString());
		jLabe3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jLabe3.setHorizontalAlignment(SwingConstants.CENTER);
		
		jPanel.add(jLabel);
		jPanel.add(jLabe2);
		jPanel.add(jLabe3);
		
		frame.add(chartPanel, BorderLayout.NORTH);
		frame.add(jPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	private JPanel initShuoShuoSummaryChart() {

		Map<Integer, Integer> map = chartService.viewShuoShuoDateChart();

		// 创建饼图数据对象
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		Set<Entry<Integer, Integer>> set = map.entrySet();
		for (Entry<Integer, Integer> entry : set) {
			dataSet.setValue(entry.getValue(), "数量", entry.getKey());
		}

		// 如果把createLineChart改为createLineChart3D就变为了3D效果的折线图
		JFreeChart chart = ChartFactory.createLineChart("每年说说的发布量", "年份", "数目",
				dataSet, PlotOrientation.VERTICAL, // 绘制方向
				false, // 显示图例
				true, // 采用标准生成器
				false // 是否生成超链接
				);
		chart.setBackgroundPaint(Color.WHITE);// 设置背景色
		// 获取绘图区对象
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE); // 设置绘图区背景色
		plot.setRangeGridlinePaint(Color.GRAY); // 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true
		plot.setDomainGridlinePaint(Color.GRAY); // 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.01);// 左边距 边框距离
		domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
		domainAxis.setMaximumCategoryLabelLines(2);
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));

		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// Y轴显示整数
		rangeAxis.setAutoRangeMinimumSize(1); // 最小跨度
		rangeAxis.setUpperMargin(0.18);// 上边距,防止最大的一个数据靠近了坐标轴。
		rangeAxis.setLowerBound(0); // 最小值显示0
		rangeAxis.setAutoRange(false); // 不自动分配Y轴数据
		rangeAxis.setTickMarkStroke(new BasicStroke(1.6f)); // 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK); // 设置坐标标记颜色
		rangeAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见

		lineandshaperenderer.setBaseLinesVisible(true);
		// 显示折点数据
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);

		ChartPanel chartPanel = new ChartPanel(chart);

		return chartPanel;
	}
}
