package org.openfuxml.addon.jsf.tld.html.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.log4j.Logger;

public class IframeRenderer extends Renderer
{
	static Logger logger = Logger.getLogger(IframeRenderer.class);
	
	public boolean getRendersChildren(){return false;}
	
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException
	{
		String id = (String)component.getAttributes().get("id");
		String src = (String)component.getAttributes().get("src");
		String style = (String)component.getAttributes().get("style");
		String height = (String)component.getAttributes().get("height");
		String width = (String)component.getAttributes().get("width");
		
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("iframe", component);
		if(id!=null){writer.writeAttribute("id", id, null);}
		writer.writeAttribute("src", src, null);
		writer.writeAttribute("height", height, null);
		writer.writeAttribute("width", width, null);
		if(style!=null){writer.writeAttribute("style", style, null);}
	}
	
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException
	{
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("iframe");
		writer.write("\n");
	}
}