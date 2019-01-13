package org.openfuxml.interfaces.configuration;

import java.util.Date;
import java.util.List;

public interface OfxTranslationProvider
{
	List<String> getLocaleCodes();
	boolean hasLocale(String localeCode);
	
	<E extends Enum<E>> String toTranslation(String localeCode, Class<?> c, E code);
	String toTranslation (String localeCode, String key1, String key2);
	String toTranslation (String localeCode, String key);
	String toDate(String localeCode, Date record);
	String toTime(String localeCode, Date record);
}