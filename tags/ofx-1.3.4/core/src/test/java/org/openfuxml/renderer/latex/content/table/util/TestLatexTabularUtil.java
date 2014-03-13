package org.openfuxml.renderer.latex.content.table.util;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Columns;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTabularUtil extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTabularUtil.class);
	
	private Columns cols;
	
	@Before
	public void initRenderer()
	{
		cols = createColumns();
	}
		
	public static Columns createColumns()
	{
		Columns cols = new Columns();
		
		cols.getColumn().add(OfxColumnFactory.createCol(10));
		cols.getColumn().add(OfxColumnFactory.createCol(20));
		
		return cols;
	}
	
    @Test
    public void getColDefinition() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarUtil tabUtil = new LatexTabluarUtil(createColumns());
    	Assert.assertEquals("p{\\tabLenA}", tabUtil.getColDefinition(0));
    	Assert.assertEquals("p{\\tabLenB}", tabUtil.getColDefinition(1));
    }
    
    @Test
    public void divide() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarUtil tabUtil = new LatexTabluarUtil(createColumns());
    	for(int i=0;i<cols.getColumn().size();i++)
    	{
    		Assert.assertEquals((int)cols.getColumn().get(i).getWidth().getValue()*LatexTabluarUtil.muliplier, tabUtil.getMultiplier(i));
    	}
    }
    
    @Test
    public void multiplier() throws IOException, OfxAuthoringException
    {    	    	
    	int expected = 30*LatexTabluarUtil.muliplier;
    	LatexTabluarUtil tabUtil = new LatexTabluarUtil(createColumns());
    	Assert.assertEquals(expected, tabUtil.getDivide());
    } 
    
    @Test
    public void lenghtCla() throws IOException, OfxAuthoringException
    {    	    	
    	LatexTabluarUtil tabUtil = new LatexTabluarUtil(createColumns());
    	List<String> list = tabUtil.getLatexLengthCalculations();
    	for(String s : list){logger.debug(s);}
    }
}