package org.openfuxml.factory.xml.table;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Test;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.test.OfxUtilTestBootstrap;

public class TestTableFactory
{
	public static String[] columnNames = {"First Name","Last Name","University","Years","Active"};
	
	public static Object[][] data = {
		    {"David",   "Wright", "Harvard University", Integer.valueOf(5), Boolean.valueOf(false)},
		    {"Andrew",  "Donnel", "University of Oxford", Integer.valueOf(3), Boolean.valueOf(true)},
		    {"Lisa",    "Barke",  "Massachusetts Institute of Technology", Integer.valueOf(2), Boolean.valueOf(false)},
		    {"Richard", "Snow",   "University of California, Los Angeles", Integer.valueOf(5), Boolean.valueOf(true)},
		    {"Marry",   "Winter", "University of Cambridge", Integer.valueOf(4), Boolean.valueOf(false)}
		};
	
	@Test
	public void tableFactory()
	{
		Table table = XmlTableFactory.build(columnNames,data);
		JaxbUtil.trace(table);
		Assert.assertEquals(columnNames.length,table.getContent().getHead().getRow().get(0).getCell().size());
		Assert.assertEquals(data.length,table.getContent().getBody().get(0).getRow().size());
	}
	
	public static void main(String[] args) throws Exception
    {
		OfxUtilTestBootstrap.init();
			
    	TestTableFactory cli = new TestTableFactory();
    	cli.tableFactory();
    }
}
