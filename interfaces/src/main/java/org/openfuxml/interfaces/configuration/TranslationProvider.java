package org.openfuxml.interfaces.configuration;

import java.util.List;

public interface TranslationProvider
{
	List<String> getLocaleCodes();
	String getTranslation (String localeCode, String key);
}