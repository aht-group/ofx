package org.openfuxml.addon.jsf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.util.JDomUtil;
import net.sf.exlp.util.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.addon.jsf.factory.JsfTagSection;
import org.openfuxml.addon.jsf.factory.JsfTagSubSection;
import org.openfuxml.addon.jsf.factory.JsfTagTable;

public class JsfTagTransformator
{
	private static Logger logger = Logger.getLogger(JsfTagTransformator.class);
	
	private File outputDir,fTagBase,fDocBase;
	private int dtdLevel;
	public static boolean useLog4j;
	private String logMsg;
	private List<Metatag> lMetaTag;
	private Taglib taglib;
	
	public JsfTagTransformator(File baseDir, int dtdLevel)
	{
		this(baseDir,null,null,dtdLevel,true);
	}
	
	public JsfTagTransformator(File outputDir, File fTagBase, File fDocBase, int dtdLevel,boolean useLog4j)
	{
		this.outputDir=outputDir;
		this.fTagBase=fTagBase;
		this.fDocBase=fDocBase;
		this.dtdLevel=dtdLevel;
		JsfTagTransformator.useLog4j=useLog4j;
		logMsg="Using baseDir="+outputDir.getAbsolutePath();
		if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		lMetaTag = new ArrayList<Metatag>(); 
	}
	
	public void readTags(String tagDef,String tagDir)
	{
		String dir = fTagBase.getAbsolutePath()+"/"+tagDir;
		String xPathPrefix = "tag";
		ConfigLoader.add(tagDef);
		Configuration config = ConfigLoader.init();
		int numberTranslations = config.getStringArray(xPathPrefix).length;
		String msg = "Found "+numberTranslations+" Tags in "+tagDef;
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
		for(int i=1;i<=numberTranslations;i++)
		{
			String fileName = config.getString(xPathPrefix+"["+i+"]");
			String dirName = config.getString(xPathPrefix+"["+i+"]/@dir");
			Metatag metatag = (Metatag)JaxbUtil.loadJAXB(dir+"/"+dirName+"/"+fileName, Metatag.class);
			lMetaTag.add(metatag);
		}
		taglib =readTaglib(dir+"/"+config.getString("taglib"));
	}
	
	public Taglib readTaglib(String xmlFile)
	{
		return (Taglib)JaxbUtil.loadJAXB(xmlFile,Taglib.class);
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
		JsfTagTable jftTagTable = new JsfTagTable(taglib);
		JsfTagSubSection jftTagSection = new JsfTagSubSection(taglib,fDocBase);
		
		for(Metatag metatag : lMetaTag)
		{
			Document docTagTable = jftTagTable.createTagTable(metatag,getDocType());
			File f = new File(outputDir,"tab-"+metatag.getTag().getName()+".xml");
			save(docTagTable,f);
			
			f = new File(outputDir,"ab-"+metatag.getTag().getName()+".xml");
			Document docSubTag = jftTagSection.createTagSection(metatag,getDocType());
			save(docSubTag,f);
		}
		JsfTagSection jftSection = new JsfTagSection(taglib);
		File f = new File(outputDir,"jsf-taglib.xml");
		Document docTag = jftSection.createTagSection(lMetaTag, getDocType());
		save(docTag,f);
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
		JDomUtil.save(doc, f, Format.getRawFormat());
		logMsg="Saved: "+f.getName();
		if(useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
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