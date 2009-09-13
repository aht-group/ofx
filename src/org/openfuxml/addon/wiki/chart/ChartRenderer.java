package org.openfuxml.addon.wiki.chart;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.log4j.Logger;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.openfuxml.addon.wiki.chart.factory.BarChartFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;

public class ChartRenderer
{
	private static Logger logger = Logger.getLogger(ChartRenderer.class);
	
	private MultiResourceLoader mrl;
	
	private Ofxchart ofxChart;
	private int width,height;
	
	public ChartRenderer()
	{
		mrl = new MultiResourceLoader();
		width = 400;
		height = 300;
	}
	
	public void loadChart(String fileName)
	{
		ofxChart =null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Ofxchart.class);
			Unmarshaller u = jc.createUnmarshaller();
			ofxChart = (Ofxchart)u.unmarshal(mrl.searchIs(fileName));
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public void render()
	{
		logger.debug(ofxChart.getType());
		BarChartFactory bcf = new BarChartFactory();
        JFreeChart chart = bcf.createChart(ofxChart);
        save(chart);
	}
	
	private void save(JFreeChart chart)
	{
		File baseDir = new File("dist");
		savePNG(chart,new File(baseDir,"xx.png"));
		saveEPS(chart,new File(baseDir,"test.eps"));
	}
	
	private void savePNG(JFreeChart chart, File f)
	{
		try
		{
			OutputStream os = new FileOutputStream(f);
			ChartUtilities.writeChartAsPNG(os,chart,width,height);
			os.close();
		}
		catch (FileNotFoundException e) {{logger.error(e);}}
		catch (IOException e) {{logger.error(e);}}
	}
	
	private void saveEPS(JFreeChart chart, File f)
	{	
        EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
        g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());

		try
		{
			FileOutputStream out = new FileOutputStream(f);
			g2d.setupDocument(out, width, height);
	        chart.draw(g2d, new Rectangle2D.Double(0,0,width, height));
	        g2d.finish();
	        
	        out.close();
		}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ChartRenderer cr = new ChartRenderer();	
		cr.loadChart("resources/data/timeline-ofxchart.xml");
		cr.render();
	}
}