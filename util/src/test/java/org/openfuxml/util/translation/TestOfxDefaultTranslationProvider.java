package org.openfuxml.util.translation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxDefaultTranslationProvider extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxDefaultTranslationProvider.class);

	private OfxDefaultTranslationProvider tp;
	
	@Before
	public void init()
	{
		tp = new OfxDefaultTranslationProvider();
	}
	
	@Test
	public void currency() throws OfxAuthoringException, OfxInternalProcessingException
	{
		double value = 12345.678;
		Assert.assertEquals("en-f-0","12346", tp.toCurrency("en", false, 0, value));
		Assert.assertEquals("en-t-0","12,346", tp.toCurrency("en", true, 0, value));
		Assert.assertEquals("en-f-1","12345.7", tp.toCurrency("en", false, 1, value));
		Assert.assertEquals("en-t-1","12,345.7", tp.toCurrency("en", true, 1, value));
		Assert.assertEquals("en-f-2","12345.68", tp.toCurrency("en", false, 2, value));
		Assert.assertEquals("en-t-2","12,345.68", tp.toCurrency("en", true, 2, value));
		
		Assert.assertEquals("de-f-0","12346", tp.toCurrency("de", false, 0, value));
		Assert.assertEquals("de-t-0","12.346", tp.toCurrency("de", true, 0, value));
		Assert.assertEquals("de-f-1","12345,7", tp.toCurrency("de", false, 1, value));
		Assert.assertEquals("de-t-1","12.345,7", tp.toCurrency("de", true, 1, value));
		Assert.assertEquals("de-f-2","12345,68", tp.toCurrency("de", false, 2, value));
		Assert.assertEquals("de-t-2","12.345,68", tp.toCurrency("de", true, 2, value));
		
		Assert.assertEquals("fr-f-0","12346", tp.toCurrency("fr", false, 0, value));
		Assert.assertEquals("fr-t-0","12.346", tp.toCurrency("fr", true, 0, value));
		Assert.assertEquals("fr-f-1","12345,7", tp.toCurrency("fr", false, 1, value));
		Assert.assertEquals("fr-t-1","12.345,7", tp.toCurrency("fr", true, 1, value));
		Assert.assertEquals("fr-f-2","12345,68", tp.toCurrency("fr", false, 2, value));
		Assert.assertEquals("fr-t-2","12.345,68", tp.toCurrency("fr", true, 2, value));
	}

}