package org.openfuxml.test.addon.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Charttype;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class TestOfxBarChartRenderer
{
	static Logger logger = Logger.getLogger(TestOfxBarChartRenderer.class);
	
	public TestOfxBarChartRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setCharttype(getType());
		
		chart.getContainer().add(getX("a"));
//		chart.getContainer().add(getX("b"));
		return chart;
	}
	
	private Charttype getType()
	{
		Charttype type = new Charttype();
		Charttype.Bar tBar = new Charttype.Bar();
		tBar.setVertical(true);
		type.setBar(tBar);
		return type;
	}
	
	private Container getX(String label)
	{
		Random rnd = new Random();
		Container x = new Container();
		x.setLabel(label);
		for(int i=1;i<20;i++)
		{
			Data data = new Data();
			data.setY(rnd.nextInt(i));
			data.setCategory("cat"+rnd.nextInt(3));
			x.getData().add(data);
		}
		return x;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestOfxBarChartRenderer test = new TestOfxBarChartRenderer();
		Chart chart = null;
		
		chart = test.getTimeSeries();
		JaxbUtil.debug(chart, new OfxNsPrefixMapper());
			
		OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
