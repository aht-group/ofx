package org.openfuxml.util.editorial;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Map;

import org.openfuxml.content.editorial.Acronyms;
import org.openfuxml.content.editorial.Term;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;

public class AcronymMerger
{
	final static Logger logger = LoggerFactory.getLogger(AcronymMerger.class);
	
	private Map<String,String> mapOrigin;
	private MultiResourceLoader mrl;
	
	private Acronyms acronyms;
	
	public AcronymMerger()
	{
		mrl = new MultiResourceLoader();
		mapOrigin = new Hashtable<String,String>();
		acronyms = new Acronyms();
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		acronyms.setComment(comment);
	}
	
	public void addResource(String resource) throws FileNotFoundException
	{
		Acronyms g = JaxbUtil.loadJAXB(mrl.searchIs(resource),Acronyms.class);
		for(Term t : g.getTerm())
		{
			if(mapOrigin.containsKey(t.getCode()))
			{
				logger.warn("Glossary Term '"+t.getCode()+"' defined multiple times");
				logger.warn("\tPrevious occurence: "+mapOrigin.get(t.getCode()));
				logger.warn("\tActual occurence: "+resource);
			}
			else
			{
				mapOrigin.put(t.getCode(), resource);
				acronyms.getTerm().add(t);
			}
		}
	}
	
	public Acronyms getAcronyms() {return acronyms;}
}
