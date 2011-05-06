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
import org.jdom.output.Format;
import org.jdom.xpath.XPath;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxImplementationException;
import org.openfuxml.renderer.processor.html.interfaces.OfxHeaderRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxNavigationRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxSectionRenderer;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Renderer;
import org.openfuxml.xml.renderer.html.Template;

public class OfxHtmlRenderer
{
	static Log logger = LogFactory.getLog(OfxHtmlRenderer.class);
	
	public static enum HtmlDir {template,web};
	
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
					//TODO Flexible Template Processing
					//All Templates are processed for all Sections
					if(o instanceof Section){processTemplate((Section)o, ofxdoc, template);}
				}
			}
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	private void processTemplate(Section section, Ofxdoc ofxDoc, Template template) throws OfxConfigurationException, OfxImplementationException
	{
		File fTemplate = cmpConfigUtil.getFile(html.getDirs(), HtmlDir.template.toString(), template.getFileCode(),false);
		Document doc = JDomUtil.load(fTemplate);
		try
		{
			XPath xpath = XPath.newInstance( "//ofx:renderer");
			List<Object> list = xpath.selectNodes(doc);
			logger.debug(list.size()+" <ofx:renderer/> Elements found in template");
			for(Object o : list)
			{
				Element eRenderer = (Element)o;
				Renderer r = (Renderer)JDomUtil.toJaxb(eRenderer, Renderer.class);
				r =  cmpConfigUtil.getHtmlRenderer(html, r);
				
				try
				{
					Class cl = Class.forName(r.getClassName());
					Object oRenderer = cl.getConstructor().newInstance();
					if     (oRenderer instanceof OfxNavigationRenderer) {renderNav(eRenderer,(OfxNavigationRenderer)oRenderer,ofxDoc, section);}
					else if(oRenderer instanceof OfxHeaderRenderer) {renderHeader(eRenderer,(OfxHeaderRenderer)oRenderer,ofxDoc, section);}
					else if(oRenderer instanceof OfxSectionRenderer) {renderSection(eRenderer,(OfxSectionRenderer)oRenderer,ofxDoc, section);}
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
		File fWeb = cmpConfigUtil.getDir(html.getDirs(), HtmlDir.web.toString());
		File fHtml = new File(fWeb,section.getId()+".html");
//		JDomUtil.debug(doc);
		JDomUtil.save(doc, fHtml, Format.getRawFormat());
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
	
	private void renderSection(Element eRenderer, OfxSectionRenderer sectionRenderer, Ofxdoc ofxDoc, Section section)
	{
		List<Content> contents = sectionRenderer.render(section);
		int index = eRenderer.getParentElement().indexOf(eRenderer);
		eRenderer.getParentElement().setContent(index, contents);
		eRenderer.detach();
	}
}