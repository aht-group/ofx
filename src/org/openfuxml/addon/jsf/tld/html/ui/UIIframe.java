package org.openfuxml.addon.jsf.tld.html.ui;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UIIframe extends UIOutput
{	
	static Log logger = LogFactory.getLog(UIIframe.class);
	
	private String content;

	public Object saveState(FacesContext context)
	{
		logger.debug("saveState");
		Object values[] = new Object[2];
		values[0] = super.saveState(context);
		values[1] = content;
		return values;
	}
	
	public void restoreState(FacesContext context, Object state)
	{
		logger.debug("restoreState");
		Object values[] = (Object[])state;
		super.restoreState(context, values[0]);
		content = (String)values[1];
	}
	
	public String getContent(){return content;}
	public void setContent(String content){this.content = content;}
}
