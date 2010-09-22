package org.openfuxml.test.addon.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Charttype;
import org.openfuxml.addon.chart.jaxb.Color;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class TestGanttRenderer
{
	static Logger logger = Logger.getLogger(TestGanttRenderer.class);
	
	public TestGanttRenderer()
	{
		
	}
	
	public Chart getGantt()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		chart.setColors(getColors());
		chart.setCharttype(getType());
		chart.setGrid(getGrid());
		
		chart.getContainer().add(getX("Person A"));
		return chart;
	}
	
	private Chart.Grid getGrid()
	{
		Chart.Grid grid = new Chart.Grid();
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
	
	private Charttype getType()
	{
		Charttype type = new Charttype();
		Charttype.Gantt tGantt = new Charttype.Gantt();
		type.setGantt(tGantt);
		return type;
	}
	
	private Container getX(String label)
	{
		Container c = new Container();
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
		
		TestGanttRenderer test = new TestGanttRenderer();
		
		Chart chart;
//		chart = test.getGantt();
		chart = (Chart)JaxbUtil.loadJAXB(args[0],Chart.class);
		JaxbUtil.debug(chart, new OfxNsPrefixMapper());
			
		OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
