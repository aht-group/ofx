package org.openfuxml.addon.chart.renderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.test.OfxChartTestBootstrap;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Charttype;
import org.openfuxml.xml.addon.chart.Container;
import org.openfuxml.xml.addon.chart.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliTimeBarRenderer
{
	final static Logger logger = LoggerFactory.getLogger(CliTimeBarRenderer.class);
	
	public CliTimeBarRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setCharttype(getType());
		chart.setGrid(getGrid());
		
		chart.getContainer().add(getX("a"));
		return chart;
	}
	
	private Chart.Grid getGrid()
	{
		Chart.Grid grid = new Chart.Grid();
		grid.setDomain(false);
		grid.setRange(false);
		return grid;
	}
	
	private Charttype getType()
	{
		Charttype type = new Charttype();
		Charttype.Timebar tBar = new Charttype.Timebar();
		tBar.setShadow(false);
		tBar.setGradient(false);
		type.setTimebar(tBar);
		return type;
	}
	
	private Container getX(String label)
	{
		Random rnd = new Random();
		Container x = new Container();
		x.setLabel(label);
		for(int i=1;i<5;i++)
		{
			Data data = new Data();
			data.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, i)));
			data.setY(1+rnd.nextInt(i));
			if(rnd.nextInt(100)<70){x.getData().add(data);}
		}
		return x;
	}
	
	public static void main (String[] args) throws Exception
	{		
		OfxChartTestBootstrap.init();
		
		CliTimeBarRenderer test = new CliTimeBarRenderer();
		Chart chart = test.getTimeSeries();
		JaxbUtil.info(chart);
			
		OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("target/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
