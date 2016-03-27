package org.openfuxml.test.addon.chart;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.metachart.xml.chart.Chart;
import org.metachart.xml.chart.Color;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.metachart.xml.chart.Grid;
import org.metachart.xml.chart.Renderer;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class TstGanttRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TstGanttRenderer.class);
	
	public TstGanttRenderer()
	{
		
	}
	
	public Chart getGantt()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		chart.setColors(getColors());
		chart.setRenderer(getType());
		chart.setGrid(getGrid());
		
		chart.getDataSet().add(getX("Person A"));
		return chart;
	}
	
	private Grid getGrid()
	{
		Grid grid = new Grid();
		grid.setDomain(false);
		grid.setRange(false);
		return grid;
	}
	
	private Chart.Colors getColors()
	{
		Chart.Colors colors = new Chart.Colors();
		
		Color c1 = ChartColorFactory.create(200,200,200,1);
		c1.setTyp("task");c1.setCode("na");
		colors.getColor().add(c1);
		
		Color c2 = ChartColorFactory.create(100,200,50,1);
		c2.setTyp("task");c2.setCode("holiday");
		colors.getColor().add(c2);
		
		return colors;
	}
	
	private Renderer getType()
	{
		Renderer type = new Renderer();
		Renderer.Gantt tGantt = new Renderer.Gantt();
		type.setGantt(tGantt);
		return type;
	}
	
	private DataSet getX(String label)
	{
		DataSet c = new DataSet();
		c.setLabel(label);
		
		Data d1 = new Data();
		d1.setFrom(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, 1)));
		d1.setTo(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, 10)));
		d1.setCategory("na");
		c.getData().add(d1);
		
		return c;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();	
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		Chart chart;
		
//		TestGanttRenderer test = new TestGanttRenderer();
//		chart = test.getGantt();
		chart = (Chart)JaxbUtil.loadJAXB(args[0],Chart.class);
		
		logger.info("Using Chart from "+args[0]);
		
		JaxbUtil.debug(chart);
			
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		Dimension d = ofxRenderer.getOfxRenderer().getSuggestedSize();
		logger.debug(d.toString());
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,d.height);
	}
}
