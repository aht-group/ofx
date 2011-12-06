package org.openfuxml.addon.jsf.factory.ofx;

import java.io.File;
import java.io.Serializable;

import org.openfuxml.addon.jsf.factory.JsfTagTransformator;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.xml.addon.jsf.tld.Example;
import org.openfuxml.xml.addon.jsf.tld.Listing;
import org.openfuxml.xml.addon.jsf.tld.Metatag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagExampleFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsfTagTransformator.class);
	
	private ProglistFactory factoryProglist;
	
	public TagExampleFactory(File fDocBase)
	{
		factoryProglist = new ProglistFactory(fDocBase);
	}	
	
	public Section create(Metatag.Examples examples)
	{
		Section secExamples = new Section();
		
		Title title = new Title();
		title.setValue("Usage examples");
		secExamples.getContent().add(title);
		
		for(Example example : examples.getExample())
		{
			secExamples.getContent().add(createExample(example));
		}
		
		return secExamples;
	}
	
	private Section createExample(Example example)
	{
		Section secExample = new Section();
		
		Title title = new Title();
		title.setValue(example.getTitle());
		secExample.getContent().add(title);
		
		for(Object s : example.getContent())
		{
			if(s instanceof Listing)
			{
				secExample.getContent().add(factoryProglist.createProglist((Listing)s));
			}
			else
			{
				secExample.getContent().add((Serializable)s);
			}
		}
		
		return secExample;
	}
}