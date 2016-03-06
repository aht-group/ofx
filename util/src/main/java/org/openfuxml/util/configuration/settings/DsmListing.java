package org.openfuxml.util.configuration.settings;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;

public class DsmListing extends AbstractDefaultSettingsManager implements DefaultSettingsManager
{	
	public static void apply(Listing xml,Listing defaultXml)
	{
		if(!xml.isSetCodeLang()){xml.setCodeLang(defaultXml.getCodeLang());}
		if(!xml.isSetNumbering()){xml.setNumbering(defaultXml.isNumbering());}
		if(!xml.isSetLinebreak()){xml.setLinebreak(defaultXml.isLinebreak());}
	}

}
