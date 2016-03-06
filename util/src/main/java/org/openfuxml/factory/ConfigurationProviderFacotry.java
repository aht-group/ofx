package org.openfuxml.factory;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;

public class ConfigurationProviderFacotry
{
	public static ConfigurationProvider build(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		OfxConfigurationProvider ocp = new OfxConfigurationProvider();
		ocp.setCrossMediaManager(cmm);
		ocp.setDefaultSettingsManager(dsm);
		return ocp;
	}
}
