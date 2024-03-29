package org.openfuxml.util.configuration.settings;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.model.xml.core.ofx.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDefaultSettingsManager implements DefaultSettingsManager
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDefaultSettingsManager.class);
	
	protected Map<String,Object> mapDefaults;
	protected Map<String,Map<String,Object>> mapSettings;
	
	public AbstractDefaultSettingsManager()
	{
		mapDefaults = new Hashtable<String,Object>();
		mapSettings = new Hashtable<String,Map<String,Object>>();
	}
	
	public void applyDefaultsToSettings()
	{
		for(String cl : mapSettings.keySet())
		{
			for(String key : mapSettings.get(cl).keySet())
			{
				String s = key+"";s=s+"";
			}
		}
	}
	
	public void addSetting(String cl,String key,Object setting)
	{
		if(!mapSettings.containsKey(cl)){mapSettings.put(cl, new Hashtable<String,Object>());}
		mapSettings.get(cl).put(key, setting);
	}

	@Override
	public void apply(Listing listing) throws OfxConfigurationException
	{
		if(Objects.nonNull(listing.getSetting()))
		{
			if(!mapSettings.containsKey(Listing.class.getName()) || !mapSettings.get(Listing.class.getName()).containsKey(listing.getSetting()))
			{
				throw new OfxConfigurationException("No Setting for "+Listing.class.getSimpleName()+" defined with code="+listing.getSetting());
			}
			DsmListing.apply(listing, (Listing)mapSettings.get(Listing.class.getName()).get(listing.getSetting()));
		}
		if(mapDefaults.containsKey(Listing.class.getName())){DsmListing.apply(listing, (Listing)mapDefaults.get(Listing.class.getName()));}
	}
	
	@Override public boolean includeEscapeTexlipse(){return false;}
	@Override public String lineSeparator() {return System.lineSeparator();}
}
