package org.openfuxml.addon.chart.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OFxChartRenderControl;
import org.openfuxml.addon.chart.data.jaxb.Chart;

public class OfxChart extends HttpServlet
{
	static Log logger = LogFactory.getLog(OfxChart.class);
	
	private static final long serialVersionUID = 1;
	
	public void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException
	{
		chart(request,response);
	}
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException
	{
		chart(request,response);
	}
	
	@SuppressWarnings("unchecked")
	private void chart(HttpServletRequest request , HttpServletResponse response)
	{
		String uuid = new String(request.getParameter("uuid"));
		try
		{
			OutputStream out = response.getOutputStream();
			HttpSession session = request.getSession();
			
			Map<String,Chart> mCharts = (Map<String,Chart>)session.getAttribute("charts");
			Chart chart = mCharts.get(uuid);
			
//			JaxbUtil.debug(chart, new MwiNsPrefixMapper());
			
			OFxChartRenderControl ofxRenderer = new OFxChartRenderControl();
			JFreeChart jfreeChart = ofxRenderer.render(JaxbUtil.toDocument(chart));

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out,jfreeChart,chart.getSize().getWidth(),chart.getSize().getHeight());
		}
		catch (IOException e) {e.printStackTrace();}
	}
}