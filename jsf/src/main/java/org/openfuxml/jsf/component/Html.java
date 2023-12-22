package org.openfuxml.jsf.component;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.jsf.util.ComponentAttribute;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.html.OfxHtmlRenderer;
import org.openfuxml.util.filter.OfxSectionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("org.openfuxml.jsf.component.Html")
@ResourceDependency(library="css", name="ofxBasic.css")
public class Html extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(Html.class);
	
	private static enum Propertiy {section,maxDepth}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{			
		ResponseWriter responseWriter = ctx.getResponseWriter();
		responseWriter.startElement("div class=\"html-container\"", this);
		
		ValueExpression ve = this.getValueExpression(Propertiy.section.toString());
		Section s = (Section)ve.getValue(ctx.getELContext());
		
		int maxDepth = ComponentAttribute.getInteger(Propertiy.maxDepth,-1,ctx,this);
		if(maxDepth>=0) {s = OfxSectionFilter.toMaxDepth(s, maxDepth);}
		
		if(s!=null)
		{
			try
			{
				ConfigurationProvider cp = ctx.getApplication().evaluateExpressionGet(ctx,"#{appOfxConfigurationProviderBean}",ConfigurationProvider.class);
				
				OfxHtmlRenderer htmlRenderer = new OfxHtmlRenderer(cp,"");
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