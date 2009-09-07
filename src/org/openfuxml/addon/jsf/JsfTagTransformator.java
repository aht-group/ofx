package org.openfuxml.addon.jsf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.log4j.Logger;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

public class JsfTagTransformator
{
	private static Logger logger = Logger.getLogger(JsfTagTransformator.class);
	
	private File baseDir;
	private int dtdLevel;
	private boolean useLog4j;
	private String logMsg;
	private Taglib taglib;
	
	public JsfTagTransformator()
	{
		
	}
	
	public JsfTagTransformator(File baseDir,int dtdLevel)
	{
		this(baseDir,dtdLevel,true);
	}
	
	public JsfTagTransformator(File baseDir,int dtdLevel,boolean useLog4j)
	{
		this.baseDir=baseDir;
		this.dtdLevel=dtdLevel;
		this.useLog4j=useLog4j;
		logMsg="Using baseDir="+baseDir.getAbsolutePath();
		if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
	}
	
	public Taglib readTaglib(String xmlFile)
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		try
		{
			Document doc = new SAXBuilder().build(mrl.searchIs(xmlFile));
			doc.setDocType(null);
			Element rootElement = doc.getRootElement();
			doc.setRootElement(unsetNameSpace(rootElement));
			
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			xmlOut.output( doc, out );
			InputStream isXML = new ByteArrayInputStream(out.toByteArray());
			
			JAXBContext jc = JAXBContext.newInstance(Taglib.class);
			Unmarshaller u = jc.createUnmarshaller();
			taglib = (Taglib)u.unmarshal(isXML);
			logMsg="Taglib read from "+xmlFile;
			if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		}
		catch (JAXBException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
		catch (FileNotFoundException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
		catch (JDOMException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
		catch (IOException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
		return taglib;
	}
	
	private Element unsetNameSpace(Element e)
	{
		e.setNamespace(null);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=unsetNameSpace(eChild);
		}
		return e;
	}
	
	public void transform()
	{
		for(Tag tag : taglib.getTag())
		{
			Document docTagTable = createTagTable(tag);
			File f = new File(baseDir,"tab-"+tag.getName()+".xml");
			save(docTagTable,f);
			
			f = new File(baseDir,"ab-"+tag.getName()+".xml");
			if(!f.exists())
			{
				Document docDummy = createDummyAbschnitt(tag);
				save(docDummy,f);
			}
		}
	}
	
	private Document createDummyAbschnitt(Tag tag)
	{
		Document doc = new Document();
		doc.setDocType(getDocType());
		
		Element abRoot = new Element("abschnitt");
		abRoot.setAttribute("id","sec-tags-"+tag.getName());
		
		Element titelRoot = new Element("titel");
		titelRoot.setText(taglib.getShortname()+":"+tag.getName());
		abRoot.addContent(titelRoot);
		
		Element abDescription = new Element("abschnitt");
		Element titelDescription = new Element("titel");
		titelDescription.setText("Description and Key Features");
		abDescription.addContent(titelDescription);
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
		
		doc.setRootElement(abRoot);
		return doc;
	}
	
	private Document createTagTable(Tag tag)
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
		return doc;
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
	
	@SuppressWarnings("unused")
	private void debug(Document doc)
	{	
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output( doc, System.out );
		}
		catch (IOException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
	}
	
	
	private void save(Document doc, File f)
	{
		try
		{
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
		
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			
			xmlOut.output( doc, osw );
			osw.close();os.close();
			logMsg="Saved: "+f.getName();
			if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		} 
		catch (IOException e) {if(useLog4j){logger.error(e);}else{e.printStackTrace();}}
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