package org.openfuxml.wiki.emitter;

import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;

public class EmitterFactory
{
	private static Logger logger = Logger.getLogger(EmitterFactory.class);
	private XMLStreamWriter writer;

	public static enum HtmlElement {title,p,i,b,strong,ul,li,a,body,sup};
	
	public EmitterFactory(XMLStreamWriter writer)
	{
		this.writer=writer;
	}
	
	public synchronized Emitter getEmitter(String elementName)
	{
		try
		{
			HtmlElement htmlElement = HtmlElement.valueOf(elementName);
			switch (htmlElement)
			{
				case sup:	return new SimpleMappingEmitter(this,"hochgestellt");
				case p:		return new SimpleMappingEmitter(this,"absatz");
				case i:		return new SimpleMappingEmitter(this,"kursiv");
				case b:		return new SimpleMappingEmitter(this,"fett");
				case strong:return new SimpleMappingEmitter(this,"fett");
				case ul:	return new SimpleMappingEmitter(this,"aufzaehlungsliste");
				case li:	return new SimpleMappingEmitter(this,"listitem", "absatz");
				case a:		return new AnchorEmitter(this);
				case body:	return new NestingEmitter(this);
				case title: return new SimpleMappingEmitter(this,"titel");
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
		else if ("ol".equals(elementName)) {return new SimpleMappingEmitter(this,"orderedlist");}
		else if ("em".equals(elementName)) {return new SimpleMappingEmitter(this,"emphasis");}
		else if ("cite".equals(elementName)) {return new SimpleMappingEmitter(this,"citation");}
		else if ("code".equals(elementName)) {return new SimpleMappingEmitter(this,"code");}
		else if ("pre".equals(elementName)) {return new SimpleMappingEmitter(this,"literallayout");}
		else if ("del".equals(elementName))
		{
			SimpleMappingEmitter emitter = new SimpleMappingEmitter(this,"emphasis");
			emitter.setAttribute("role", "del");
			return emitter;
		}
		else if ("ins".equals(elementName))
		{
			SimpleMappingEmitter emitter = new SimpleMappingEmitter(this,"emphasis");
			emitter.setAttribute("role", "ins");
			return emitter;
		}
		else if ("sub".equals(elementName)){return new SimpleMappingEmitter(this,"subscript");}
		logger.debug("NestingEmitter wird genommen");
		return new NestingEmitter(this);
	}
	
	public XMLStreamWriter getWriter() {return writer;}
	public void setWriter(XMLStreamWriter writer) {this.writer = writer;}
}