package org.openfuxml.interfaces.configuration;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.exception.OfxConfigurationException;

public interface DefaultSettingsManager
{
	public void apply(Listing listing) throws OfxConfigurationException;
	public boolean includeEscapeTexlipse();
	public String lineSeparator();
}