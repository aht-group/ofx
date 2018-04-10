package org.openfuxml.jsf.component;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.openfuxml.content.ofx.Section;
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
		
		StringBuffer sb = new StringBuffer();
		

		boolean bold = false;//ComponentAttribute.getBoolean("bold", false, context, this);
		boolean italic = false;//ComponentAttribute.getBoolean("italic", false, context, this);
		
//		logger.info("******************");
//		logger.info("bold: "+bold);
//		logger.info("italic "+italic);
		
		if(bold){sb.append(" ofxBold");}
		if(italic){sb.append(" ofxItalic");}
		
		responseWriter.writeAttribute("class",sb.toString(),null);
		
		ValueExpression ve = this.getValueExpression(Properties.value.toString());
		Section s = (Section)ve.getValue(context.getELContext());
		
		
		responseWriter.write(JaxbUtil.toString(s));
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
	}
}