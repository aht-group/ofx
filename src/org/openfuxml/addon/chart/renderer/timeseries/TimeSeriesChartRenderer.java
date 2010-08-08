package org.openfuxml.addon.chart.renderer.timeseries;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;

public class TimeSeriesChartRenderer extends AbstractTimeSeriesChartRenderer implements OfxChartRenderer
{
	static Log logger = LogFactory.getLog(TimeSeriesChartRenderer.class);
	
	public TimeSeriesChartRenderer()
	{
		super();
		logger.debug("Using: "+this.getClass().getSimpleName());
	}
	
	@Override
	protected TimeSeriesCollection createDataset(List<Container> lContainer)
	{
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(Container container : lContainer)
		{
			TimeSeries ts = new TimeSeries(container.getLabel());
			for(Data data : container.getData())
			{
					Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
					ts.addOrUpdate(new Hour(d), data.getY());
			}
			dataset.addSeries(ts);	
		}
		return dataset;
	}
}
