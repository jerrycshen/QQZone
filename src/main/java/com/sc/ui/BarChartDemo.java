package com.sc.ui;

import java.awt.Color;  
import java.awt.Font;  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartFrame;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.CategoryLabelPositions;  
import org.jfree.chart.axis.NumberAxis;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.title.TextTitle;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;  
 /**  
 * @name 何枫  
 * @date 2010-12-17  
 * @action createBarChart3DTest.java  
 * @time 下午10:35:52  
 * @package_name com.huawei.jfreechart  
 * @project_name jfreechartTest  
 */ 
public class BarChartDemo {  
	
    private static CategoryDataset getDataSet() {  
 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
 
        dataset.addValue(200, "计划", "清华大学");  
 
        dataset.addValue(360, "计划", "天津大学");  
 
        dataset.addValue(100, "计划", "北京大学");  
 
        dataset.addValue(280, "计划", "复旦大学");  
        return dataset;  
    }  
    public static void main(String[] args) {  
        CategoryDataset dataset = getDataSet();  
        JFreeChart chart = ChartFactory.createBarChart3D("评论次数TOP10", "好友昵称","评论的数量", dataset, PlotOrientation.VERTICAL, true, true, true);  
        ChartFrame frame = new ChartFrame("评论次数TOP10", chart, true);  
        // 自定义设定背景色  
        // chart.setBackgroundPaint(Color.getHSBColor(23,192,223));  
        chart.setBackgroundPaint(Color.WHITE);  
        // 获得 plot：3dBar为CategoryPlot  
        CategoryPlot categoryPlot = chart.getCategoryPlot();  
        // 设定图表数据显示部分背景色  
        categoryPlot.setBackgroundPaint(Color.WHITE);  
        // 横坐标网格线  
        categoryPlot.setDomainGridlinePaint(Color.RED);  
        // 设置网格线可见  
        categoryPlot.setDomainGridlinesVisible(true);  
        // 纵坐标网格线  
        categoryPlot.setRangeGridlinePaint(Color.RED);  
        // 重要的类，负责生成各种效果  
        // BarRenderer3D renderer=(BarRenderer3D) categoryPlot.getRenderer();  
        // 获取纵坐标  
        NumberAxis numberaxis = (NumberAxis) categoryPlot.getRangeAxis();  
        // 设置纵坐标的标题字体和大小  
        numberaxis.setLabelFont(new Font("黑体", Font.CENTER_BASELINE, 16));  
        // 设置丛坐标的坐标值的字体颜色  
        numberaxis.setLabelPaint(Color.BLACK);  
        // 设置丛坐标的坐标轴标尺颜色  
        numberaxis.setTickLabelPaint(Color.RED);  
        // 坐标轴标尺颜色  
        numberaxis.setTickMarkPaint(Color.BLUE);  
        // 丛坐标的默认间距值  
        // numberaxis.setAutoTickUnitSelection(true);  
        // 设置丛坐标间距值  
        numberaxis.setAutoTickUnitSelection(false);  
//        numberaxis.setTickUnit(new NumberTickUnit(150));  
        // 获取横坐标  
        CategoryAxis domainAxis = categoryPlot.getDomainAxis();  
        // 设置横坐标的标题字体和大小  
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));  
        // 设置横坐标的坐标值的字体颜色  
        domainAxis.setTickLabelPaint(Color.RED);  
        // 设置横坐标的坐标值的字体  
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 16));  
        // 设置横坐标的显示  
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.4));  
        // 这句代码解决了底部汉字乱码的问题  
        chart.getLegend().setItemFont(new Font("黑体", 0, 16));  
        // 设置图例标题  
        Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE, 20);  
        TextTitle title = new TextTitle("项目状态分布");  
        title.getBackgroundPaint();  
        title.setFont(font);  
        // 设置标题的字体颜色  
        chart.setTitle(title);  
        frame.pack();  
        frame.setVisible(true);  
 
    }  
 
} 
