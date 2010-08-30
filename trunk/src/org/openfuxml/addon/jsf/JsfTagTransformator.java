package org.openfuxml.addon.jsf;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.XsltUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.addon.jsf.factory.TagChapterFactory;
import org.openfuxml.addon.jsf.factory.TagSectionFactory;
import org.openfuxml.content.ofx.Section;

public class JsfTagTransformator
{
	static Log logger = LogFactory.getLog(JsfTagTransformator.class);
	
	private File outputDir,fTagBase,fDocBase;
	private int dtdLevel;
	public static boolean useLog4j;
	private String logMsg,xslt;
	private List<Metatag> lMetaTag;
	private Taglib taglib;
	
	public JsfTagTransformator(File baseDir, int dtdLevel)
	{
		this(baseDir,null,null,dtdLevel,true,null);
	}
	
	public JsfTagTransformator(File outputDir, File fTagBase, File fDocBase, int dtdLevel,boolean useLog4j,String xslt)
	{
		this.outputDir=outputDir;
		this.fTagBase=fTagBase;
		this.fDocBase=fDocBase;
		this.dtdLevel=dtdLevel;
		this.xslt=xslt;
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
			TaglibFactoryTask.fillDescription(metatag.getTag(),config);
			lMetaTag.add(metatag);
		}
		taglib =readTaglib(dir+"/"+config.getString("taglib"));
	}
	
	public Taglib readTaglib(String xmlFile)
	{
		return (Taglib)JaxbUtil.loadJAXB(xmlFile,Taglib.class);
	}
	
	public void transform()
	{
		TagChapterFactory chapterFactory = new TagChapterFactory(taglib);
		TagSectionFactory sectionFactory = new TagSectionFactory(taglib,fDocBase);
		
		logger.debug("Transforming "+lMetaTag.size());
		for(Metatag metatag : lMetaTag)
		{
			File fSection = new File(outputDir,"section-"+metatag.getTag().getName()+".xml");
			Section section = sectionFactory.create(metatag);
			
//			File fSectionEn = new File(outputDir,"section-"+metatag.getTag().getName()+"-en.xml");
//			JaxbUtil.save(fSectionEn, section, new JsfNsPrefixMapper(), true);
			
			Document doc = JaxbUtil.toDocument(section);
			doc = JDomUtil.correctNsPrefixes(doc, new JsfNsPrefixMapper());
			logger.debug("Debug for "+metatag.getTag().getName());
			InputStream isXML = JDomUtil.toInputStream(doc, Format.getRawFormat());
			transformSave(isXML, fSection);
		}
		
		File fChapter = new File(outputDir,"jsf-taglib.xml");
		Section section = chapterFactory.create(lMetaTag);
		InputStream isXML = JaxbUtil.toInputStream(section,null,false);
		transformSave(isXML, fChapter);
	}
	
	private void transformSave(InputStream isXML, File f)
	{
		InputStream isTransformed = XsltUtil.toInputStream(isXML, xslt);
		JDomUtil.save(isTransformed, f, Format.getRawFormat(),getDocType());
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