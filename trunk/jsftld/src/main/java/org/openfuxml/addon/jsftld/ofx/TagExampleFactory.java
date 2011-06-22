package org.openfuxml.addon.jsftld.ofx;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsf.data.jaxb.Example;
import org.openfuxml.addon.jsf.data.jaxb.Listing;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsftld.JsfTagTransformator;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

public class TagExampleFactory
{
	static Log logger = LogFactory.getLog(JsfTagTransformator.class);
	
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