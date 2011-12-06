package org.openfuxml.addon.jsf.tld.html.tag;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IframeTag extends UIComponentELTag
{
	final static Logger logger = LoggerFactory.getLogger(IframeTag.class);
	
	private ValueExpression src = null;
	private ValueExpression width = null;
	private ValueExpression height = null;
	private ValueExpression style = null;
	
	public String getRendererType(){return "org.openfuxml.jsf.Iframe";}
	public String getComponentType(){return "org.openfuxml.jsf.Iframe";}
	
	public void setProperties(UIComponent component)
	{
		super.setProperties(component);
		component.setValueExpression("src", src);
		component.setValueExpression("width", width);
		component.setValueExpression("height", height);
		component.setValueExpression("style", style);
	}
	
	public void release()
	{
		super.release();
		src=null;
		width=null;
		height=null;
		style=null;
	}

	public void setSrc(ValueExpression src) {this.src = src;}
	public void setWidth(ValueExpression width) {this.width = width;}
	public void setHeight(ValueExpression height) {this.height = height;}
	public void setStyle(ValueExpression style) {this.style = style;}
}