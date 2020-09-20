package org.openfuxml.jsf.component;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.OfxHtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

@FacesComponent("org.openfuxml.jsf.component.Html")
@ResourceDependency(library="css", name="ofxBasic.css")
public class Html extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(Html.class);
	
	private static enum Properties {section}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{			
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div class=\"html-container\"", this);
		
		ValueExpression ve = this.getValueExpression(Properties.section.toString());
		Section s = (Section)ve.getValue(context.getELContext());
		
		if(s!=null)
		{
			JaxbUtil.info(s);
			try
			{
				OfxHtmlRenderer htmlRenderer = new OfxHtmlRenderer(new OfxConfigurationProvider(),"");
				htmlRenderer.render(responseWriter,s);
			}
			catch (OfxAuthoringException e) {e.printStackTrace();}
			catch (OfxConfigurationException e) {e.printStackTrace();}
		}	
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
	}
}