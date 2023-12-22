package org.openfuxml.util.editorial;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Map;

import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.editorial.Term;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;

public class GlossaryMerger
{
	final static Logger logger = LoggerFactory.getLogger(GlossaryMerger.class);
	
	private Map<String,String> mapOrigin;
	private MultiResourceLoader mrl;
	
	private Glossary glossary;
	
	public GlossaryMerger()
	{
		mrl = new MultiResourceLoader();
		mapOrigin = new Hashtable<String,String>();
		glossary = new Glossary();
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		glossary.setComment(comment);
	}
	
	public void addResource(String resource) throws FileNotFoundException
	{
		Glossary g = JaxbUtil.loadJAXB(mrl.searchIs(resource),Glossary.class);
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
				glossary.getTerm().add(t);
			}
		}
	}
	
	public Glossary getGlossary() {return glossary;}
}
