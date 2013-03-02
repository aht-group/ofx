package org.openfuxml.addon.chart.factory.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbWithId;

import org.openfuxml.addon.chart.factory.xml.XmlDataFactory;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesDataSetFactory
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesDataSetFactory.class);
	
	private Map<EjbWithId,List<Data>> map;
	private List<EjbWithId> list;

	public TimeSeriesDataSetFactory()
	{
		map = new Hashtable<EjbWithId,List<Data>>();
		list = new ArrayList<EjbWithId>();
	}
	
	public void add(EjbWithId id, double value, int year, int month)
	{
		getList(id).add(XmlDataFactory.buildForYearMonth(value, year, month));

	}
	
	public void add(EjbWithId id, double value, Date date)
	{
		logger.info(""+id);
		
	}
	
	public DataSet build(EjbWithId id)
	{
		DataSet ds = new DataSet();
		ds.getData().addAll(map.get(id));
		return ds;
	}
	
	private List<Data> getList(EjbWithId id)
	{
		if(!map.containsKey(id))
		{
			list.add(id);
			map.put(id, new ArrayList<Data>());
		}
		return map.get(id);
	}
	
	public List<EjbWithId> getList() {return list;}
}
