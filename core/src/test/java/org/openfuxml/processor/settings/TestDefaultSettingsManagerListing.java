package org.openfuxml.processor.settings;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.model.xml.core.ofx.Listing;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDefaultSettingsManagerListing
{
	final static Logger logger = LoggerFactory.getLogger(TestDefaultSettingsManagerListing.class);
	
	private OfxDefaultSettingsManager dsm;
	private Listing listing,setting;
	
	@BeforeEach
	public void init()
	{	
		setting = new Listing();
		setting.setSetting("test");
		setting.setCodeLang("myX");
		setting.setNumbering(false);
		setting.setLinebreak(false);
		
		dsm = new OfxDefaultSettingsManager();
		dsm.addSetting(Listing.class.getName(),setting.getSetting(),setting);
		listing = new Listing();
	}
	
	@Test
    public void withoutSetting() throws OfxConfigurationException 
    {
        Assertions.assertThrows(OfxConfigurationException.class, () -> {
    	listing.setSetting("na");
    	dsm.apply(listing);
        });
    }
	
	@Test
    public void codeLang() throws OfxConfigurationException 
    {
    	Assertions.assertFalse(Objects.nonNull(listing.getCodeLang()));
    	dsm.apply(listing);
    	Assertions.assertTrue(Objects.nonNull(listing.getCodeLang()));
    	Assertions.assertEquals("XML", listing.getCodeLang());
    }
	
	@Test
    public void settingCodeLang() throws OfxConfigurationException 
    {
    	listing.setSetting(setting.getSetting());
    	dsm.apply(listing);
    	Assertions.assertEquals(setting.getCodeLang(), listing.getCodeLang());
    }
}