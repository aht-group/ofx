package org.openfuxml.util.translation;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.interfaces.configuration.TranslationProvider;

public class OfxTranslationProvider implements TranslationProvider
{
	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	
	public OfxTranslationProvider()
	{
		localeCodes = new ArrayList<String>();
	}
	
	

	@Override
	public String getTranslation(String localeCode, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
}