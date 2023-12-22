package org.openfuxml.util.configuration.settings;

import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.model.xml.core.ofx.Listing;

public class OfxDefaultSettingsManager extends AbstractDefaultSettingsManager implements DefaultSettingsManager
{
	public OfxDefaultSettingsManager()
	{
		initListing();
	}
	
	private void initListing()
	{
		Listing xml = new Listing();
		xml.setCodeLang("XML");
		xml.setNumbering(false);
		xml.setLinebreak(false);
		mapDefaults.put(Listing.class.getName(), xml);
	}

	@Override public String lineSeparator() {return System.lineSeparator();}
}