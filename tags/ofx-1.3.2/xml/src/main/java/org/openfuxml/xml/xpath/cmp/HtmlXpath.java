package org.openfuxml.xml.xpath.cmp;

import java.util.List;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Renderer;
import org.openfuxml.xml.xpath.OfxXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlXpath
{
	final static Logger logger = LoggerFactory.getLogger(HtmlXpath.class);
	
	@SuppressWarnings("unchecked")
	public static synchronized Renderer getRenderer(Html html, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Renderer result = null;
		try
		{
			Document doc = JaxbUtil.toDocument(html, OfxXpath.getNsPrefixMapper());
			XPath xpath = XPath.newInstance( "//html:renderer[@code='"+code+"']");
			xpath.addNamespace(OfxXpath.getNsHtml());
			List<Object> list = xpath.selectNodes(doc);
			if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Renderer.class.getSimpleName()+" found for code="+code);}
			else if(list.size()>1){throw new ExlpXpathNotUniqueException("No unique "+Renderer.class.getSimpleName()+" for code="+code);}
			Element e = (Element)list.get(0);
			result = (Renderer)JDomUtil.toJaxb(e, Renderer.class);
		}
		catch (JDOMException e) {logger.error("",e);}
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized File getFile(Dir dirs, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		File file = null;
		try
		{
			Document doc = JaxbUtil.toDocument(dirs, OfxXpath.getNsPrefixMapper());
			XPath xpath = XPath.newInstance( "//io:file[@code='"+code+"']");
			List<Object> list = xpath.selectNodes(doc);
			if(list.size()==0){throw new ExlpXpathNotFoundException("No file found for code="+code);}
			else if(list.size()>1){throw new ExlpXpathNotUniqueException("No unique file for code="+code);}
			Element e = (Element)list.get(0);
			file = (File)JDomUtil.toJaxb(e, File.class);
		}
		catch (JDOMException e) {logger.error("",e);}
        return file;
	}
}