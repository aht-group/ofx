package org.openfuxml.interfaces.configuration;

import java.util.Date;
import java.util.List;

public interface TranslationProvider
{
	List<String> getLocaleCodes();
	
	String toTranslation (String localeCode, String key);
	String toDate(String localeCode, Date record);
}