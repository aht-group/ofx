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
import org.openfuxml.renderer.html.OfxHTMLRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

@FacesComponent("org.openfuxml.jsf.component.OutputSection")
@ResourceDependency(library="ofxCss", name="ofxBasic.css")
public class OutputSection extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(OutputSection.class);
	
	private static enum Properties {value}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{			
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		
		ValueExpression ve = this.getValueExpression(Properties.value.toString());
		Section s = (Section)ve.getValue(context.getELContext());
		
		if(s!=null)
		{
			JaxbUtil.info(s);
			try
			{
				OfxHTMLRenderer htmlRenderer = new OfxHTMLRenderer(new OfxConfigurationProvider(),"");
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