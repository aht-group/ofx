package org.openfuxml.interfaces.configuration;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OfxTranslationProvider
{
	boolean hasLocale(String localeCode);
	List<String> getLocaleCodes();
	
	String tlEntity (String localeCode, String key);
	String tlEntity (String localeCode, Class<?> c);
	
	String tlAttribute (String localeCode, String key1, String key2);
	<E extends Enum<E>> String toLabel(String localeCode, Class<?> c, E code);
	<E extends Enum<E>> String toDescription(String localeCode, Class<?> c, E code);
	
	String toDate(String localeCode, LocalDate record);
	String toDate(String localeCode, Date record);
	String toTime(String localeCode, Date record);

	String toCurrency(String localeCode, Double value);
	String toCurrency(String localeCode, boolean grouping, int decimals, Double value);
}