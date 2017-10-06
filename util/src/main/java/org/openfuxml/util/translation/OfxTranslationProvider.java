package org.openfuxml.util.translation;

import java.util.ArrayList;
import java.util.Date;
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
	public String toTranslation(String localeCode, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toDate(String locleCode, Date record) {
		// TODO Auto-generated method stub
		return "NYI:toDate";
	}

	@Override
	public String toTranslation(String localeCode, String key1, String key2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}