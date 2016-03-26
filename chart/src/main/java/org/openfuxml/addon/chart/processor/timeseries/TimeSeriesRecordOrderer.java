package org.openfuxml.addon.chart.processor.timeseries;

import java.util.Collections;
import java.util.Comparator;

import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.DataSet;
import org.metachart.xml.chart.RendererTimeseries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesRecordOrderer
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesRecordOrderer.class);
	
	boolean activated;
	
	private TimeSeriesRecordOrderer(boolean activated)
	{
		this.activated=activated;
	}
	
	public static TimeSeriesRecordOrderer factory(RendererTimeseries renderer)
	{
		if(renderer.isSetOrderRecords())
		{
			if(renderer.isOrderRecords())
			{
				return new TimeSeriesRecordOrderer(true);
			}
		}
		else
		{
			logger.warn("RendererTimeseries@orderRecords not set. Defaulting to false");
		}
		return new TimeSeriesRecordOrderer(false);
	}
	
	public DataSet process(DataSet dataSet)
	{
		if(!activated){return dataSet;}
		Collections.sort(dataSet.getData(), new RecordComparator());
		return dataSet;
	}
	
	private class RecordComparator implements Comparator<Data>
	{
		public int compare(Data a, Data b)
		{
			return a.getRecord().compare(b.getRecord());
	    }
	}
}