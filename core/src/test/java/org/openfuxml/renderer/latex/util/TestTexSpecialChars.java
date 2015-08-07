package org.openfuxml.renderer.latex.util;

import org.junit.Assert;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTexSpecialChars extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestTexSpecialChars.class);
	
	@Test
	public void underScore() throws OfxAuthoringException
	{
		String txt = "xx_2";
        String actual = TexSpecialChars.replace(txt);
        logger.info(actual);
	}

    public void backslash()
    {
        String txt = "x\\x";
        String result = TexSpecialChars.replace(txt);
        logger.info(result);
    }
    
    @Test
    public void dollar()
    {
    	String txt = "$";
    	String expected = "\\$";
    	String actual = TexSpecialChars.replace(txt);
    	Assert.assertEquals(expected, actual);
    }
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestTexSpecialChars cli = new TestTexSpecialChars();
//   	cli.underScore();
//   	cli.backslash();
    	cli.dollar();
    }
}