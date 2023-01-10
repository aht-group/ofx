package org.openfuxml.jsf.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentAttribute
{
	final static Logger logger = LoggerFactory.getLogger(ComponentAttribute.class);
	
	public static <E extends Enum<E>> int getInteger(E attribute, int defaultValue, FacesContext context, UIComponent component)
	{
		String s = get(attribute.toString(),  context, component);
		if(s==null) {return defaultValue;}
		else return Integer.valueOf(s);
	}

	public static <E extends Enum<E>> String get(E attribute, String defaultValue, FacesContext context, UIComponent component)
	{
		String s = get(attribute.toString(),  context, component);
		if(s==null) {return defaultValue;}
		else {return s;}
	}
	
	private static String get(String attribute, FacesContext context, UIComponent component)
	{
		String value = null;
		if(component.getAttributes().containsKey(attribute))
		{
			value = component.getAttributes().get(attribute).toString();
		}
		else
		{
			ValueExpression ve = component.getValueExpression(attribute);
			if(ve!=null)
			{
				Object o = ve.getValue(context.getELContext());
				if(o!=null) {value = o.toString();}
			}
		}
		return value;
	}
}