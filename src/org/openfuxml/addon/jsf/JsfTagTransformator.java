package org.openfuxml.addon.jsf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.test.xml.jsftaglib.jaxb.TestJsfTagTransformator;

public class JsfTagTransformator
{
	private static Logger logger = Logger.getLogger(TestJsfTagTransformator.class);
	
	private File baseDir;
	private int dtdLevel;
	
	public JsfTagTransformator(File baseDir,int dtdLevel)
	{
		this.baseDir=baseDir;
		this.dtdLevel=dtdLevel;
		logger.debug("Using baseDir="+baseDir.getAbsolutePath());
	}
	
	public void transform(Taglib taglib)
	{
		for(Tag tag : taglib.getTag())
		{
			Document doc = new Document();
			doc.setDocType(getDocType());
			
			Element abschnitt = new Element("abschnitt");
			
			Element abschnittTitel = new Element("titel");
			abschnittTitel.setText("Attributes for "+taglib.getShortname()+":"+tag.getName());
			abschnitt.addContent(abschnittTitel);
			
			Element tabelle = new Element("tabelle");
			tabelle.addContent(getTitel(taglib,tag));
			
			Element tgroup = new Element("tgroup");
			tgroup.setAttribute("cols", "2");
			tgroup.addContent(getColspec(1, "*"));
			tgroup.addContent(getColspec(2, "*"));
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
			debug(doc);
			save(doc, tag);
		}
		
	}
	
	private Element getTitel(Taglib taglib, Tag tag)
	{
		Element titel = new Element("titel");
		titel.setText("Summary of Elements");
		return titel;
	}
	
	private Element getThead()
	{
		Element thead = new Element("thead");
		Element row = new Element("row");
		row.addContent(getEntry(1, "Name"));
		row.addContent(getEntry(2, "Required"));
		
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
	
	private Element getColspec(int i, String width)
	{
		Element colspec = new Element("colspec");
		colspec.setAttribute("colnum",""+i);
		colspec.setAttribute("colname","col"+i);
		colspec.setAttribute("colwidth","*");
		return colspec;
	}
	
	private Element getAttRow(Attribute att)
	{
		Element row = new Element("row");
		row.addContent(getEntry(1, att.getName()));
		row.addContent(getEntry(2, att.isRequired()+""));
		return row;
	}
	
	private void debug(Document doc)
	{	
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output( doc, System.out );
		}
		catch (IOException e) {logger.error(e);}
	}
	
	private void save(Document doc,Tag tag)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			
			File f = new File(baseDir,"tab-"+tag.getName()+".xml");
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			
			xmlOut.output( doc, osw );
			osw.close();os.close();
		} 
		catch (IOException e) {logger.error(e);}
	}
	
	private DocType getDocType()
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<dtdLevel;i++)
		{
			sb.append("../");
		}
		
		DocType docType = new DocType("abschnitt");
		docType.setSystemID(sb.toString()+"system/dtd/fernuni01.dtd");
		return docType;
	}
}
