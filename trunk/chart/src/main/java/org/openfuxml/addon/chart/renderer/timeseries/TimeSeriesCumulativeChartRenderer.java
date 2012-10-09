package org.openfuxml.addon.chart.renderer.timeseries;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.data.general.SeriesException;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.openfuxml.addon.chart.data.jaxb.Container;
import org.openfuxml.addon.chart.data.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesCumulativeChartRenderer extends AbstractTimeSeriesChartRenderer implements OfxChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesCumulativeChartRenderer.class);
	
	public TimeSeriesCumulativeChartRenderer()
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
			double sum = 0;
			TimeSeries ts = new TimeSeries(container.getLabel());
			for(Data data : container.getData())
			{
				JaxbUtil.info(data);
					double value = 0;
					Date d = data.getRecord().toGregorianCalendar().getTime();
					RegularTimePeriod rtp = getRtp(d);
					try
					{
						value = data.getY();
						ts.add(rtp, value+sum);
						
					}
					catch (SeriesException e)
					{
						e.printStackTrace();
						TimeSeriesDataItem di = ts.getDataItem(rtp);
						
						value = data.getY();
						ts.addOrUpdate(rtp, value+di.getValue().doubleValue());
					}
					sum=sum+value;
			}
			dataset.addSeries(ts);	
		}
		switch(ofxTimePeriod)
		{
			case Month: dataset.setXPosition(TimePeriodAnchor.MIDDLE);break;
		}
		return dataset;
	}
}
