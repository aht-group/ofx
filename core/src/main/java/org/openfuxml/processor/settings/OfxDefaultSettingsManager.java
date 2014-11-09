package org.openfuxml.processor.settings;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.DefaultSettingsManager;

public class OfxDefaultSettingsManager implements DefaultSettingsManager
{

	@Override
	public void apply(Listing listing)
	{
		if(!listing.isSetCodeLang()){listing.setCodeLang("XML");}
	}

}
