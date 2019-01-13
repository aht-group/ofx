package org.openfuxml.renderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openfuxml.interfaces.configuration.OfxTranslationProvider;

public class OfxDefaultTranslationProvider implements OfxTranslationProvider
{
	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	
	private OfxDefaultTranslationProvider()
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

	@Override
	public boolean hasLocale(String localeCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toTime(String localeCode, Date record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends Enum<E>> String toTranslation(String localeCode, Class<?> c, E code) {
		// TODO Auto-generated method stub
		return null;
	}
}