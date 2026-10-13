package org.openfuxml.util.translation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxDefaultTranslationProvider extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxDefaultTranslationProvider.class);

	private OfxDefaultTranslationProvider tp;
	
	@BeforeEach
	public void init()
	{
		tp = new OfxDefaultTranslationProvider();
	}
	
	@Test
	public void currency() throws OfxAuthoringException, OfxInternalProcessingException
	{
		double value = 12345.678;
		Assertions.assertEquals("12346", tp.toCurrency("en", false, 0, value), "en-f-0");
		Assertions.assertEquals("12,346", tp.toCurrency("en", true, 0, value), "en-t-0");
		Assertions.assertEquals("12345.7", tp.toCurrency("en", false, 1, value), "en-f-1");
		Assertions.assertEquals("12,345.7", tp.toCurrency("en", true, 1, value), "en-t-1");
		Assertions.assertEquals("12345.68", tp.toCurrency("en", false, 2, value), "en-f-2");
		Assertions.assertEquals("12,345.68", tp.toCurrency("en", true, 2, value), "en-t-2");
		
		Assertions.assertEquals("12346", tp.toCurrency("de", false, 0, value), "de-f-0");
		Assertions.assertEquals("12.346", tp.toCurrency("de", true, 0, value), "de-t-0");
		Assertions.assertEquals("12345,7", tp.toCurrency("de", false, 1, value), "de-f-1");
		Assertions.assertEquals("12.345,7", tp.toCurrency("de", true, 1, value), "de-t-1");
		Assertions.assertEquals("12345,68", tp.toCurrency("de", false, 2, value), "de-f-2");
		Assertions.assertEquals("12.345,68", tp.toCurrency("de", true, 2, value), "de-t-2");
		
		Assertions.assertEquals("12346", tp.toCurrency("fr", false, 0, value), "fr-f-0");
		Assertions.assertEquals("12.346", tp.toCurrency("fr", true, 0, value), "fr-t-0");
		Assertions.assertEquals("12345,7", tp.toCurrency("fr", false, 1, value), "fr-f-1");
		Assertions.assertEquals("12.345,7", tp.toCurrency("fr", true, 1, value), "fr-t-1");
		Assertions.assertEquals("12345,68", tp.toCurrency("fr", false, 2, value), "fr-f-2");
		Assertions.assertEquals("12.345,68", tp.toCurrency("fr", true, 2, value), "fr-t-2");
	}

}