package org.openfuxml.interfaces.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OfxTranslationProvider
{
	boolean hasLocale(String localeCode);
	List<String> getLocaleCodes();
	
	
	String tlEntity (Class<?> c);
	String tlEntity (String localeCode, Class<?> c);
	String tlEntity (String localeCode, String key);
	
	
	<E extends Enum<E>> String tAttribute (Class<?> c, E code);
	<E extends Enum<E>> String tAttribute(String localeCode, Class<?> c, E code);
	String tAttribute (String localeCode, String key1, String key2);
	
	void setContext(String localeCode, Class<?> c);
	<E extends Enum<E>> String toLabel(E code);
	
	
	<E extends Enum<E>> String toDescription(String localeCode, Class<?> c, E code);
	
	String toDate(String localeCode, LocalDate record);
	String toDate(String localeCode, Date record);
	String toDate(String localeCode, LocalDateTime ldt);
	
	String toTime(String localeCode, Date record);
	String toTime(String localeCode, LocalDateTime ldt);

	String toCurrency(String localeCode, Double value);
	String toCurrency(String localeCode, boolean grouping, int decimals, Double value);
}