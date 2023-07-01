package org.openfuxml.interfaces.configuration;

import java.io.Serializable;

import org.openfuxml.interfaces.media.CrossMediaManager;

public interface ConfigurationProvider extends Serializable
{
	DefaultSettingsManager getDefaultSettingsManager();
	CrossMediaManager getCrossMediaManager();
}