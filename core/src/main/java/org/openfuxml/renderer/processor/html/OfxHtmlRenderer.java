package org.openfuxml.renderer.processor.html;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Template;

public class OfxHtmlRenderer
{
	static Log logger = LogFactory.getLog(OfxHtmlRenderer.class);
	
	public static enum HtmlDir {template};
	
	private Html html;
	private OfxRenderConfiguration cmpConfigUtil;
	
	public OfxHtmlRenderer(OfxRenderConfiguration cmpConfigUtil, Html html)
	{
		this.html=html;
		this.cmpConfigUtil=cmpConfigUtil;
	}
	
	public void render(String ofxDocFileName) throws OfxAuthoringException, OfxConfigurationException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Ofxdoc ofxdoc = (Ofxdoc)JaxbUtil.loadJAXB(ofxDocFileName, Ofxdoc.class);
			
			for(Template template : html.getTemplate())
			{
				File fTemplate = cmpConfigUtil.getFile(html.getDirs(), HtmlDir.template.toString(), template.getFileCode());
				processTemplate(template, fTemplate);
			}
			
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	private void processTemplate(Template template, File fTemplate)
	{
		Document doc = JDomUtil.load(fTemplate);
		JDomUtil.debug(doc);
	}
}