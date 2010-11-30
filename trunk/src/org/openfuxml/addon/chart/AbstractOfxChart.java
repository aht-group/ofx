package org.openfuxml.addon.chart;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.chart.jaxb.Chart;

import com.aht.util.facade.aht.AhtTranslationFacade;

public class AbstractOfxChart
{
	private static Log logger = LogFactory.getLog(AbstractOfxChart.class);
	
	public static enum RenderTarget {web,pdf};
	
	protected Configuration config;
	protected Chart chart;
	protected List<Chart> charts;
	protected RenderTarget target;
	private String uuid;

	public AbstractOfxChart(Configuration config,RenderTarget target)
	{
		this.config=config;
		this.target=target;
		uuid = UUID.randomUUID().toString();
	}
		
	protected void loadXmlChartTemplate(String chartFile)
	{
		logger.trace("Loading: "+chartFile);
		chart = (Chart)JaxbUtil.loadJAXB(chartFile, Chart.class);
	}
	
	public void translate(AhtTranslationFacade fTrans,String lang)
	{
		if(chart.isSetXAxis() && chart.getXAxis().isSetKey())
		{
			logger.trace("key="+chart.getXAxis().getKey()+" trans="+fTrans.t(chart.getXAxis().getKey(), lang));
			chart.getXAxis().setLabel(fTrans.t(chart.getXAxis().getKey(), lang));
		}
		if(chart.isSetYAxis() && chart.getYAxis().isSetKey())
		{
			logger.trace("key="+chart.getYAxis().getKey()+" trans="+fTrans.t(chart.getYAxis().getKey(), lang));
			chart.getYAxis().setLabel(fTrans.t(chart.getYAxis().getKey(), lang));
		}
	}
	
	public String getUuid() {return uuid;}
	
	public Chart getChart() {return chart;}
	public List<Chart> getCharts() {return charts;}
	
	@SuppressWarnings("unchecked")
	public void addToSession(FacesContext context)
	{
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		Map<String,Chart> mCharts = (Map<String,Chart>)session.getAttribute("charts");
		if(mCharts==null){mCharts = new Hashtable<String,Chart>();}
		mCharts.put(uuid, chart);
		session.removeAttribute("charts");
		session.setAttribute("charts", mCharts);
	}
	
	private void addTranslations(AhtTranslationFacade fTrans)
	{
		logger.warn("NYI addTranslations");
	}
}