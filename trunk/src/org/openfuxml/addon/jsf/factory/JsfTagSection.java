package org.openfuxml.addon.jsf.factory;

import java.util.List;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

public class JsfTagSection
{
	private Taglib taglib;
	
	public JsfTagSection(Taglib taglib)
	{
		this.taglib=taglib;
	}
	
	public Document createTagSection(List<Metatag> lMetatags,DocType docType)
	{		
		Document doc = new Document();
		doc.setDocType(docType);
		
		Element abRoot = new Element("abschnitt");
		abRoot.setAttribute("id","jsf-taglib");
		
		Element titelRoot = new Element("titel");
		titelRoot.setText(taglib.getInfo());
		abRoot.addContent(titelRoot);
				
		abRoot.addContent(getSubsection("../jsf-doku/tag-introduction.xml", "introduction"));
		for(Metatag metatag : lMetatags)
		{
			Tag tag = metatag.getTag();
			abRoot.addContent(getSubsection("ab-"+tag.getName()+".xml", tag.getName()));
		}
		
		
		doc.setRootElement(abRoot);
		return doc;
	}
	
	private Element getSubsection(String src, String title)
	{
		Element abSub = new Element("abschnitt");
		abSub.setAttribute("extern","ja");
		abSub.setAttribute("quelle",src);
		
		Element subRoot = new Element("titel");
		subRoot.setText(title);
		abSub.addContent(subRoot);
		
		return abSub;
	}
}
