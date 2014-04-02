package org.openfuxml.content.table;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTable extends AbstractXmlTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Table.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Table actual = create(true);
    	Table expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Table.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Table create(boolean withChilds)
    {
    	Table xml = new Table();
    	
    	if(withChilds)
    	{
    		xml.setComment(TestXmlComment.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlTable.initFiles();	
		TestXmlTable test = new TestXmlTable();
		test.save();
    }
}