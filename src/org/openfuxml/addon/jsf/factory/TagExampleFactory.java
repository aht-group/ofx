package org.openfuxml.addon.jsf.factory;

import java.io.File;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.JsfTagTransformator;
import org.openfuxml.addon.jsf.data.jaxb.Example;
import org.openfuxml.addon.jsf.data.jaxb.Listing;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

public class TagExampleFactory
{
	static Logger logger = Logger.getLogger(JsfTagTransformator.class);
	
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