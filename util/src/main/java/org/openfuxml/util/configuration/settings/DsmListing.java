package org.openfuxml.util.configuration.settings;

import java.util.Objects;

import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.model.xml.core.ofx.Listing;

public class DsmListing extends AbstractDefaultSettingsManager implements DefaultSettingsManager
{	
	public static void apply(Listing xml, Listing defaultXml)
	{
		if(Objects.isNull(xml.getCodeLang())) {xml.setCodeLang(defaultXml.getCodeLang());}
		if(Objects.isNull(xml.isNumbering())) {xml.setNumbering(defaultXml.isNumbering());}
		if(Objects.isNull(xml.isLinebreak())) {xml.setLinebreak(defaultXml.isLinebreak());}
	}
}