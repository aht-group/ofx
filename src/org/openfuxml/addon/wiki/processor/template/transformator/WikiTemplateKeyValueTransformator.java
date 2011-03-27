package org.openfuxml.addon.wiki.processor.template.transformator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;

public class WikiTemplateKeyValueTransformator extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateKeyValueTransformator.class);
	
	private final Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	
	public WikiTemplateKeyValueTransformator() 
	{
		
	}
	
	public Element transform(String wikiMarkup)
	{
		Element e = new Element("paragraph");
		e.setNamespace(nsOfx);
		e.setText("This is the content!!");
		return e;
	}
}