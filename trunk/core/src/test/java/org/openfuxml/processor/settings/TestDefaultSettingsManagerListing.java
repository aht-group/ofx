package org.openfuxml.processor.settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDefaultSettingsManagerListing
{
	final static Logger logger = LoggerFactory.getLogger(TestDefaultSettingsManagerListing.class);
	
	private DefaultSettingsManager dsm;
	private Listing listing;
	
	@Before
	public void init()
	{	
		dsm = new OfxDefaultSettingsManager();
		listing = new Listing();
	}
	
	@Test
    public void codeLang() 
    {
    	Assert.assertFalse(listing.isSetCodeLang());
    	dsm.apply(listing);
    	Assert.assertTrue(listing.isSetCodeLang());
    	Assert.assertEquals("XML", listing.getCodeLang());
    }
}