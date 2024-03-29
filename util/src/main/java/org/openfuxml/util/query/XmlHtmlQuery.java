package org.openfuxml.util.query;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.File;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHtmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(XmlHtmlQuery.class);
	
	@SuppressWarnings("unchecked")
	public static synchronized Renderer getRenderer(Html html, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(html);
		List<Renderer> listResult = (List<Renderer>)context.selectNodes("//renderer[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Renderer.class.getSimpleName()+" found for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("No unique "+Renderer.class.getSimpleName()+" for code="+code);}
		
		return listResult.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized File getFile(Dir dirs, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(dirs);
		List<File> listResult = (List<File>)context.selectNodes("//file[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No file found for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("No unique file for code="+code);}
		
		return listResult.get(0);
	}
}