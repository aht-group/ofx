package org.openfuxml.addon.jsf.factory;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

public class JsfTagTable
{
	private Taglib taglib;
	
	public JsfTagTable(Taglib taglib)
	{
		this.taglib=taglib;
	}
	
	public Document createTagTable(Metatag metatag,DocType docType)
	{
		Tag tag = metatag.getTag();
		Document doc = new Document();
		doc.setDocType(docType);
		
		Element abschnitt = new Element("abschnitt");
		
		Element abschnittTitel = new Element("titel");
		abschnittTitel.setText("Attributes for "+taglib.getShortname()+":"+tag.getName());
		abschnitt.addContent(abschnittTitel);
		
		Element tabelle = new Element("tabelle");
		tabelle.addContent(getTitel(taglib,tag));
		
		Element tgroup = new Element("tgroup");
		tgroup.setAttribute("cols", "3");
		tgroup.addContent(getColspec(1, "*"));
		tgroup.addContent(getColspec(2, "*"));
		tgroup.addContent(getColspec(3, "*"));
		tgroup.addContent(getThead());
		
		Element tbody = new Element("tbody");
		for(Attribute att : tag.getAttribute())
		{
			tbody.addContent(getAttRow(att));
		}
		tgroup.addContent(tbody);
		
		tabelle.addContent(tgroup);
		abschnitt.addContent(tabelle);
		doc.setRootElement(abschnitt);
		return doc;
	}
	
	private Element getColspec(int i, String width)
	{
		Element colspec = new Element("colspec");
		colspec.setAttribute("colnum",""+i);
		colspec.setAttribute("colname","col"+i);
		colspec.setAttribute("colwidth","*");
		return colspec;
	}
	
	private Element getThead()
	{
		Element thead = new Element("thead");
		Element row = new Element("row");
		row.addContent(getEntry(1, "Name"));
		row.addContent(getEntry(2, "Required"));
		row.addContent(getEntry(3, "Description"));
		
		thead.addContent(row);
		return thead;
	}
	
	private Element getEntry(int i, String text)
	{
		Element entry = new Element("entry");
		entry.setAttribute("colname","col"+i);
		entry.setText(text);
		return entry;
	}
	
	private Element getAttRow(Attribute att)
	{
		Element row = new Element("row");
		row.addContent(getEntry(1, att.getName()));
		row.addContent(getEntry(2, att.isRequired()+""));
		row.addContent(getEntry(3, att.getDescription()));
		return row;
	}
	
	private Element getTitel(Taglib taglib, Tag tag)
	{
		Element titel = new Element("titel");
		titel.setText("Summary of Elements");
		return titel;
	}
}
