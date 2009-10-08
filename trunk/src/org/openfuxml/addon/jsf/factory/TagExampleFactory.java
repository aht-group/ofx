package org.openfuxml.addon.jsf.factory;

import java.io.File;

import org.jdom.Element;
import org.openfuxml.addon.jsf.data.jaxb.Example;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;

public class TagExampleFactory
{
	private ProglistFactory factoryProglist;
	
	public TagExampleFactory(File fDocBase)
	{
		factoryProglist = new ProglistFactory(fDocBase);
	}
		
	
	public Element createSecExamples(Metatag.Examples examples)
	{
		Element abRoot = new Element("abschnitt");
		
		Element elTitle = new Element("titel");
		elTitle.setText("Example");
		abRoot.addContent(elTitle);
		
		for(Example example : examples.getExample())
		{
			abRoot.addContent(createExamples(example));
		}
		
		return abRoot;
	}
	
	public Element createExamples(Example example)
	{
		Element abExample = new Element("abschnitt");
		
		Element elTitle = new Element("titel");
		elTitle.setText(example.getTitle());
		abExample.addContent(elTitle);
		
		abExample.addContent(factoryProglist.createProglist(example.getFile(), "Progtitle"));
		
		return abExample;
	}
}
