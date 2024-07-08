package org.openfuxml.util.translation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.lang3.ObjectUtils;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxDefaultTranslationProvider implements OfxTranslationProvider
{
	final static Logger logger = LoggerFactory.getLogger(OfxDefaultTranslationProvider.class);


	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	
	private final Map<MultiKey<String>,String> map3;
	private final Map<MultiKey<String>,DecimalFormat> mapDf;
	
	private String localeCode;
	private Class<?> context;
	
	public OfxDefaultTranslationProvider()
	{
		localeCodes = new ArrayList<String>();
		map3 = new HashMap<MultiKey<String>,String>();
		mapDf = new HashMap<MultiKey<String>,DecimalFormat>();
	}
	
	public void addTranslation(String localeCode, String scope, String key, String value)
	{
		MultiKey<String> nk = new MultiKey<String>(localeCode,scope,key);
		map3.put(nk, value);
	}

	@Override public String tlEntity(String localeCode, Class<?> c) {return tlEntity(localeCode,c.getSimpleName());}
	@Override public String tlEntity(String localeCode, String key)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override public void setContext(String localeCode, Class<?> context)
	{
		if(ObjectUtils.anyNull(localeCode,context)) {throw new IllegalArgumentException("Both localeCode and context need to be set");}
		
		this.localeCode=localeCode;
		this.context=context;
//		logger.info("LocaleCode:"+localeCode+" Context:"+context.getSimpleName());
	}
	
	@Override public <E extends Enum<E>> String toLabel(E code)
	{
		if(ObjectUtils.anyNull(localeCode,context)) {throw new IllegalStateException("Both localeCode and context need to be set");}
		return this.toLabel(localeCode,context,code);
	}

	@Override public <E extends Enum<E>> String toLabel(String localeCode, Class<?> c, E code) {return tlAttribute(localeCode,c.getSimpleName(),code.toString());}
	@Override public <E extends Enum<E>> String toDescription(String localeCode, Class<?> c, E code)
	{
		logger.warn("NYI");
		return null;
	}
	@Override public String tlAttribute(String localeCode, String scope, String key)
	{
		MultiKey<String> mk = new MultiKey<String>(localeCode,scope,key);
		if(!map3.containsKey(mk))
		{
			logger.warn("Missing translation {} - {} - {}",scope,key,localeCode);
		}
		return map3.get(mk);
	}

	@Override public boolean hasLocale(String localeCode)
	{
		return false;
	}

	@Override public String toDate(String locleCode, LocalDate record)
	{
		if(record==null){return "";}
		else {return "NYI: "+record;}
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
	
	@Override public String toCurrency(String localeCode, Double value){return toCurrency(localeCode,true,2,value);}
	@Override public String toCurrency(String localeCode, boolean grouping, int decimals, Double value)
	{
		MultiKey<String> mk = new MultiKey<String>(localeCode,Boolean.valueOf(grouping).toString(),Integer.valueOf(decimals).toString());
		return build(mk).format(value);
	}
	
	private DecimalFormat build(MultiKey<String> mk)
	{
		if(!mapDf.containsKey(mk))
		{
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator(resolveDecimalSeparator(mk.getKey(0)));
			if(Boolean.valueOf(mk.getKey(1))) {otherSymbols.setGroupingSeparator(resolveGroupingSeparator(mk.getKey(0)));}
			
			DecimalFormat df = new DecimalFormat(prefix(Boolean.valueOf(mk.getKey(1)))+suffix(Integer.valueOf(mk.getKey(2))),otherSymbols);
			mapDf.put(mk,df);
		}
		return mapDf.get(mk);
	}
	
	private String prefix(boolean grouping)
	{
		if(grouping) {return "#,###";}
		else {return "#";}
	}
	
	private String suffix(int decicmals)
	{
		if(decicmals==0) {return "";}
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append(".");
			for(int i=0;i<decicmals;i++) {sb.append("0");}
			return sb.toString();
		}
	}
	
	private char resolveDecimalSeparator(String localeCode)
	{
		if(localeCode.equals("de")) {return ',';}
		else if(localeCode.equals("en")) {return '.';}
		else if(localeCode.equals("fr")) {return ',';}
		else
		{
			logger.warn("No DecimalSeparator defined for "+localeCode);
			{return ',';}
		}
	}
	
	private char resolveGroupingSeparator(String localeCode)
	{
		if(localeCode.equals("de")) {return '.';}
		else if(localeCode.equals("en")) {return ',';}
		else if(localeCode.equals("fr")) {return '.';}
		else
		{
			logger.warn("No GroupingSeparator defined for "+localeCode);
			{return ',';}
		}
	}
}