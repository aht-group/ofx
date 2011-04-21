package org.openfuxml.renderer.processor.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxImplementationException;
import org.openfuxml.renderer.processor.html.header.OfxHeaderRenderer;
import org.openfuxml.renderer.processor.html.navigation.OfxNavigationRenderer;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Renderer;
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
	
	public void render(String ofxDocFileName) throws OfxAuthoringException, OfxConfigurationException, OfxImplementationException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Ofxdoc ofxdoc = (Ofxdoc)JaxbUtil.loadJAXB(ofxDocFileName, Ofxdoc.class);
			
			for(Template template : html.getTemplate())
			{
				for(Object o : ofxdoc.getContent().getContent())
				{
					if(o instanceof Section){processTemplate((Section)o, ofxdoc, template);}
				}
			}
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	private void processTemplate(Section section, Ofxdoc ofxDoc, Template template) throws OfxConfigurationException, OfxImplementationException
	{
		File fTemplate = cmpConfigUtil.getFile(html.getDirs(), HtmlDir.template.toString(), template.getFileCode());
		Document doc = JDomUtil.load(fTemplate);
		try
		{
			XPath xpath = XPath.newInstance( "//ofx:renderer");
			List<Object> list = xpath.selectNodes(doc);
			logger.debug(list.size()+" Elements found");
			for(Object o : list)
			{
				Element eRenderer = (Element)o;
				Renderer r = (Renderer)JDomUtil.toJaxb(eRenderer, Renderer.class);
				r =  cmpConfigUtil.getHtmlRenderer(html, r);
				
				
				try
				{
					Class cl = Class.forName(r.getClassName());
					Object oRendere = cl.getConstructor().newInstance();
					if     (oRendere instanceof OfxNavigationRenderer) {renderNav(eRenderer,(OfxNavigationRenderer)oRendere,ofxDoc, section);}
					else if(oRendere instanceof OfxHeaderRenderer) {renderHeader(eRenderer,(OfxHeaderRenderer)oRendere,ofxDoc, section);}
				}
				catch (ClassNotFoundException e) {throw new OfxConfigurationException("Renderer class not found: "+e.getMessage());}
				catch (IllegalArgumentException e) {logger.error(e);}
				catch (SecurityException e) {logger.error(e);}
				catch (InstantiationException e) {logger.error(e);}
				catch (IllegalAccessException e) {logger.error(e);}
				catch (InvocationTargetException e) {logger.error(e);}
				catch (NoSuchMethodException e) {throw new OfxImplementationException("Renderer implementation does not have a empty constructor: "+r.getClassName());}
			}
		}
		catch (JDOMException e) {logger.error(e);}
		JDomUtil.debug(doc);
	}
	
	private void renderNav(Element eRenderer, OfxNavigationRenderer navRenderer, Ofxdoc ofxDoc, Section section)
	{
		Element renderedElement = navRenderer.render(ofxDoc, section);
		int index = eRenderer.getParentElement().indexOf(eRenderer);
		eRenderer.getParentElement().setContent(index, renderedElement);
		eRenderer.detach();
	}
	
	private void renderHeader(Element eRenderer, OfxHeaderRenderer headerRenderer, Ofxdoc ofxDoc, Section section)
	{
		Content content = headerRenderer.render(section);
		int index = eRenderer.getParentElement().indexOf(eRenderer);
		eRenderer.getParentElement().setContent(index, content);
		eRenderer.detach();
	}
}