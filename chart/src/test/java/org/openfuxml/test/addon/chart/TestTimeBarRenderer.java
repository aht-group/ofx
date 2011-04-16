package org.openfuxml.test.addon.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.addon.chart.data.jaxb.Charttype;
import org.openfuxml.addon.chart.data.jaxb.Container;
import org.openfuxml.addon.chart.data.jaxb.Data;
import org.openfuxml.xml.util.OfxNsPrefixMapper;

public class TestTimeBarRenderer
{
	static Log logger = LogFactory.getLog(TestTimeBarRenderer.class);
	
	public TestTimeBarRenderer()
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
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestTimeBarRenderer test = new TestTimeBarRenderer();
		Chart chart = test.getTimeSeries();
		JaxbUtil.debug(chart, new OfxNsPrefixMapper());
			
		OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
