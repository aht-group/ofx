package org.openfuxml.test.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.addon.chart.data.jaxb.Charttype;
import org.openfuxml.addon.chart.data.jaxb.Container;
import org.openfuxml.addon.chart.data.jaxb.Data;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstTimeSeriesChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TstTimeSeriesChartRenderer.class);
	
	public TstTimeSeriesChartRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setCharttype(getType());
		chart.setColors(getColors());
		
		chart.getContainer().add(getX("a"));
		chart.getContainer().add(getX("b"));
		return chart;
	}
	
	private Charttype getType()
	{
		Charttype type = new Charttype();
		Charttype.Timeseries tsType = new Charttype.Timeseries();
		tsType.setGap(true);
		type.setTimeseries(tsType);
		return type;
	}
	
	private Chart.Colors getColors()
	{
		Chart.Colors colors = new Chart.Colors();
		colors.getColor().add(ChartColorFactory.create(255, 255, 255, 255, ChartColorFactory.Area.backgroundChart));
		
		return colors;
	}
	
	private Container getX(String label)
	{
		Random rnd = new Random();
		Container x = new Container();
		x.setLabel(label);
		for(int i=1;i<20;i++)
		{
			Data data = new Data();
			data.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, i)));
			data.setY(rnd.nextInt(i));
			if(rnd.nextInt(100)<70){x.getData().add(data);}
		}
		return x;
	}
	
	public Chart load(String fileName) throws FileNotFoundException
	{
		Chart chart = (Chart)JaxbUtil.loadJAXB(fileName, Chart.class);
		return chart;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		TstTimeSeriesChartRenderer test = new TstTimeSeriesChartRenderer();
		Chart chart;
		chart = test.getTimeSeries();
//		chart = test.load(args[0]);
		
		JaxbUtil.debug(chart);
			
		OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
