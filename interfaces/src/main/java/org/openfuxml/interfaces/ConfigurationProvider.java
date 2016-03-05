package org.openfuxml.interfaces;

import org.openfuxml.interfaces.media.CrossMediaManager;

public interface ConfigurationProvider
{
	DefaultSettingsManager getDefaultSettingsManager();
	CrossMediaManager getCrossMediaManager();
}