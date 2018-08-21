package org.openfuxml.factory.xml.table;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Test;
import org.openfuxml.content.table.Table;
import org.openfuxml.test.OfxUtilTestBootstrap;

public class TestTableFactory
{
	public static String[] columnNames = {"First Name","Last Name","University","Years","Active"};
	
	public static Object[][] data = {
		    {"David",   "Wright", "Harvard University", new Integer(5), new Boolean(false)},
		    {"Andrew",  "Donnel", "University of Oxford", new Integer(3), new Boolean(true)},
		    {"Lisa",    "Barke",  "Massachusetts Institute of Technology", new Integer(2), new Boolean(false)},
		    {"Richard", "Snow",   "University of California, Los Angeles", new Integer(5), new Boolean(true)},
		    {"Marry",   "Winter", "University of Cambridge", new Integer(4), new Boolean(false)}
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
