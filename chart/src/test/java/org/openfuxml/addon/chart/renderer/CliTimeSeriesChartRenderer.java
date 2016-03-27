package org.openfuxml.addon.chart.renderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.metachart.factory.chart.TimeSeriesChartFactory;
import org.metachart.xml.chart.Chart;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.addon.chart.test.OfxChartTestBootstrap;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliTimeSeriesChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(CliTimeSeriesChartRenderer.class);
	
	public CliTimeSeriesChartRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		TimeSeriesChartFactory cf = new TimeSeriesChartFactory();
//		cf.setWithGaps(true);
		Chart chart = cf.build();
		
		chart.setColors(getColors());
		
		chart.getDataSet().add(getX("a"));
		chart.getDataSet().add(getX("b"));
		return chart;
	}
	
	private Chart.Colors getColors()
	{
		Chart.Colors colors = new Chart.Colors();
		colors.getColor().add(ChartColorFactory.create(255, 255, 255, 255, ChartColorFactory.Area.backgroundChart));
		
		return colors;
	}
	
	private DataSet getX(String label)
	{
		Random rnd = new Random();
		DataSet dataSet = new DataSet();
		dataSet.setLabel(label);
		for(int i=1;i<20;i++)
		{
			Data data = new Data();
			data.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, i)));
			data.setY(rnd.nextInt(i));
			if(rnd.nextInt(100)<70){dataSet.getData().add(data);}
		}
		return dataSet;
	}
	
	public Chart load(String fileName) throws FileNotFoundException
	{
		Chart chart = (Chart)JaxbUtil.loadJAXB(fileName, Chart.class);
		return chart;
	}
	
	public static void main (String[] args) throws Exception
	{
		OfxChartTestBootstrap.init();
			
		CliTimeSeriesChartRenderer test = new CliTimeSeriesChartRenderer();
		Chart chart = test.getTimeSeries();
		
		JaxbUtil.info(chart);
			
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("target/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
