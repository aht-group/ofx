package org.openfuxml.addon.chart.util;

import java.util.Date;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;

public class TimePeriodFactory
{	
	public static enum OfxChartTimePeriod {Hour,Day,Month,Year};
	
	public synchronized static RegularTimePeriod getRtp(OfxChartTimePeriod ofxTimePeriod, Date d)
	{
		RegularTimePeriod rtp;
		switch(ofxTimePeriod)
		{
			case Hour: rtp = new Hour(d);break;
			case Day: rtp = new Day(d);break;
			case Month: rtp = new Month(d);break;
			default: rtp = new Hour(d);break;
		}
		return rtp;
	}
}
