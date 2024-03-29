package org.openfuxml.transform.xhtml;

import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.emitter.GlosstermEmitter;
import org.openfuxml.addon.wiki.emitter.ImageEmitter;
import org.openfuxml.addon.wiki.emitter.injection.OfxInjectionEmitter;
import org.openfuxml.addon.wiki.processor.ofx.emitter.AnchorEmitter;
import org.openfuxml.addon.wiki.processor.ofx.emitter.HeaderEmitter;
import org.openfuxml.addon.wiki.processor.ofx.emitter.OfxListEmitter;
import org.openfuxml.interfaces.transformer.Emitter;
import org.openfuxml.interfaces.xml.OfxEmphasis;
import org.openfuxml.transform.xhtml.emitter.EmphasisEmitter;
import org.openfuxml.transform.xhtml.emitter.NestingEmitter;
import org.openfuxml.transform.xhtml.emitter.SimpleEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmitterFactory
{
	final static Logger logger = LoggerFactory.getLogger(EmitterFactory.class);
	
	private XMLStreamWriter writer;
	private String injectionDir;
	
	public static enum HtmlElement {title,p,i,em,b,strong,ol,ul,li,a,body,sup,sub,wikiinjection};
	
	public EmitterFactory(XMLStreamWriter writer,String injectionDir)
	{
		this.writer=writer;
		this.injectionDir=injectionDir;
	}
	
	public synchronized Emitter getEmitter(String elementName)
	{
		try
		{
			HtmlElement htmlElement = HtmlElement.valueOf(elementName);
			switch (htmlElement)
			{
//				case sup:			return new SimpleEmitter(this,"hochgestellt");
				case p:				return new SimpleEmitter(this,"ofx:paragraph");
				case i:				return new EmphasisEmitter(this,OfxEmphasis.Emphasis.italic);
				case em:			return new EmphasisEmitter(this,OfxEmphasis.Emphasis.italic);
				case b:				return new EmphasisEmitter(this,OfxEmphasis.Emphasis.bold);
				case strong:		return new EmphasisEmitter(this,OfxEmphasis.Emphasis.bold);
				case sup:			return new EmphasisEmitter(this,OfxEmphasis.Emphasis.superscript);
				case sub:			return new EmphasisEmitter(this,OfxEmphasis.Emphasis.subscript);
				case ul:			return new OfxListEmitter(this,OfxListEmitter.Ordering.unordered);
				case ol:			return new OfxListEmitter(this,OfxListEmitter.Ordering.ordered);
				case li:			return new SimpleEmitter(this,"list:item", "ofx:paragraph");
				case a:				return new AnchorEmitter(this);
				case body:			return new NestingEmitter(this);
				case title:			return new SimpleEmitter(this,"ofx:title");
				case wikiinjection: return new OfxInjectionEmitter(this,injectionDir);
			}
		}
		catch (IllegalArgumentException e)
		{
			if (elementName.matches("h\\d"))
			{
				return new HeaderEmitter(this,Integer.parseInt(elementName.substring(1)));
			}
		}
		
		logger.debug("Unknown element \""+elementName+"\" using default structure");
		if ("acronym".equals(elementName)) {return new GlosstermEmitter(this);} 
		else if ("img".equals(elementName)) {return new ImageEmitter(this);}
		else if ("em".equals(elementName)) {return new SimpleEmitter(this,"emphasis");}
		else if ("cite".equals(elementName)) {return new SimpleEmitter(this,"citation");}
		else if ("code".equals(elementName)) {return new SimpleEmitter(this,"code");}
		else if ("pre".equals(elementName)) {return new SimpleEmitter(this,"literallayout");}
		else if ("del".equals(elementName))
		{
			SimpleEmitter emitter = new SimpleEmitter(this,"emphasis");
			emitter.setAttribute("role", "del");
			return emitter;
		}
		else if ("ins".equals(elementName))
		{
			SimpleEmitter emitter = new SimpleEmitter(this,"emphasis");
			emitter.setAttribute("role", "ins");
			return emitter;
		}
		else if ("sub".equals(elementName)){return new SimpleEmitter(this,"subscript");}
		logger.debug("NestingEmitter wird genommen");
		return new NestingEmitter(this);
	}
	
	public XMLStreamWriter getWriter() {return writer;}
	public void setWriter(XMLStreamWriter writer) {this.writer = writer;}
}