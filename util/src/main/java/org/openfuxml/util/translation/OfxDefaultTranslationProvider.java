package org.openfuxml.util.translation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxDefaultTranslationProvider implements OfxTranslationProvider
{
	final static Logger logger = LoggerFactory.getLogger(OfxDefaultTranslationProvider.class);


	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	
	private final Map<MultiKey<String>,String> map3;
	
	public OfxDefaultTranslationProvider()
	{
		localeCodes = new ArrayList<String>();
		map3 = new HashMap<MultiKey<String>,String>();
	}
	
	public void addTranslation(String localeCode, String scope, String key, String value)
	{
		MultiKey<String> nk = new MultiKey<String>(localeCode,scope,key);
		map3.put(nk, value);
	}

	@Override
	public String tlEntity(String localeCode, String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override public <E extends Enum<E>> String tlAttribute(String localeCode, Class<?> c, E code){return tlAttribute(localeCode,c.getSimpleName(),code.toString());}
	@Override public String tlAttribute(String localeCode, String scope, String key)
	{
		MultiKey<String> mk = new MultiKey<String>(localeCode,scope,key);
		if(!map3.containsKey(mk))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Missing Translation");
			sb.append(localeCode+"-"+scope+"-"+key);
			
			logger.warn(sb.toString());
		}
		return map3.get(mk);
	}

	@Override public boolean hasLocale(String localeCode)
	{
		return false;
	}

	@Override public String toDate(String locleCode, Date record)
	{
		if(record==null){return "";}
		else {return "NYI: "+record;}
	}
	
	@Override public String toTime(String localeCode, Date record)
	{
		if(record==null){return "";}
		else {return "NYI: "+record;}
	}
	
	@Override public String toCurrency(String localeCode, Double value)
	{
		if(value==null){return "";}
		else {return "NYI: "+value;}
	}

	@Override public String toCurrency(String localeCode, Double value, boolean grouping, int decimals)
	{
		if(value==null){return "";}
		else {return "NYI: "+value;}
	}
}