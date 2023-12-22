package org.openfuxml.interfaces.configuration;

import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.model.xml.core.ofx.Listing;

public interface DefaultSettingsManager
{
	public void apply(Listing listing) throws OfxConfigurationException;
	public boolean includeEscapeTexlipse();
	public String lineSeparator();
}