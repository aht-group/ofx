package org.openfuxml.addon.jsf.factory;

import java.io.File;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

public class JsfTagSubSection
{
	private Taglib taglib;
	private TagExampleFactory factoryExamples;
	
	public JsfTagSubSection(Taglib taglib, File fDocBase)
	{
		this.taglib=taglib;
		factoryExamples = new TagExampleFactory(fDocBase);
	}
	
	public Document createTagSection(Metatag metatag,DocType docType)
	{
		Tag tag = metatag.getTag();
		
		Document doc = new Document();
		doc.setDocType(docType);
		
		Element abRoot = new Element("abschnitt");
		abRoot.setAttribute("id","sec-tags-"+tag.getName());
		
		Element titelRoot = new Element("titel");
		titelRoot.setText(taglib.getShortname()+":"+tag.getName());
		abRoot.addContent(titelRoot);
		
		Element abDescription = new Element("abschnitt");
		Element titelDescription = new Element("titel");
		titelDescription.setText("Description and Key Features");
		abDescription.addContent(titelDescription);
		
		Element abDescAb = new Element("absatz");
		abDescAb.addContent(tag.getDescription());
		abDescription.addContent(abDescAb);
		
		abRoot.addContent(abDescription);
		
		Element tabAbschnitt = new Element("abschnitt");
		tabAbschnitt.setAttribute("extern","ja");
		tabAbschnitt.setAttribute("quelle","tab-"+tag.getName()+".xml");
		Element titelTab = new Element("titel");
		titelTab.setText("Table JSF Elements");
		tabAbschnitt.setContent(titelTab);
		abRoot.addContent(tabAbschnitt);
		
		Element abCreate = new Element("abschnitt");
		Element titelCreate = new Element("titel");
		titelCreate.setText("Creating the Component with a Page Tag");
		abCreate.addContent(titelCreate);
		abRoot.addContent(abCreate);
		
		if(metatag.getExamples() !=null && metatag.getExamples().getExample().size()>0)
		{
			abRoot.addContent(factoryExamples.createSecExamples(metatag.getExamples()));
		}
		
		doc.setRootElement(abRoot);
		return doc;
	}
}
