package org.openfuxml.addon.chart.renderer.timeseries;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.processor.TimeSeriesGapNullifier;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesChartRenderer extends AbstractTimeSeriesChartRenderer implements ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesChartRenderer.class);
	
	public TimeSeriesChartRenderer()
	{
		super();
		logger.debug("Using: "+this.getClass().getSimpleName());
	}
	
	@Override
	protected TimeSeriesCollection createDataset(List<DataSet> dataSets)
	{
		TimeSeriesGapNullifier gapNuller=null;
		boolean nullifyGaps = TimeSeriesGapNullifier.gapNullerActivated(ofxChart.getRenderer().getRendererTimeseries());
		if(nullifyGaps)
		{
			OfxChartTimePeriod timePeriod = OfxChartTimePeriod.valueOf(ofxChart.getRenderer().getRendererTimeseries().getTimePeriod());
			gapNuller = new TimeSeriesGapNullifier(timePeriod);
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(DataSet dataSet : dataSets)
		{
			if(nullifyGaps){dataSet = gapNuller.nullifyGapsInContainer(dataSet);}
			TimeSeries ts = new TimeSeries(dataSet.getLabel());
			
			for(Data data : dataSet.getData())
			{
				Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
				RegularTimePeriod rtp = getRtp(d);
				if(data.isSetY())
				{
					ts.addOrUpdate(rtp, data.getY());
				}
				else
				{
					ts.addOrUpdate(rtp, null);
				}
			}
			dataset.addSeries(ts);
			
			logger.info("Add here TS with same color from container");
		}
		return dataset;
	}
}