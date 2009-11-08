package org.openfuxml.addon.epub.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class OpfFactory
{
	static Logger logger = Logger.getLogger(ExternalMerger.class);
	
	private File targetDir;
	private Document doc;
	private Namespace nsXsi,nsOpf,nsDc;
	
	private List<String> spineItemRef;
	
	public OpfFactory(File targetDir)
	{
		this.targetDir=targetDir;
		nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
		nsOpf = Namespace.getNamespace("http://www.idpf.org/2007/opf");
		nsDc = Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/");
		spineItemRef = new ArrayList<String>();
	}
	
	public void create()
	{
		doc = new Document();
		
		Element ePackage = new Element("package");
		ePackage.setAttribute("version","2.0");
		ePackage.setNamespace(nsOpf);
		
		ePackage.addNamespaceDeclaration(nsXsi);
		ePackage.addNamespaceDeclaration(nsDc);
		
		ePackage.addContent(getMetadata());
		ePackage.addContent(getManifest());
		ePackage.addContent(getSpine());
		
		doc.setRootElement(ePackage);
	}
	
	private Element getMetadata()
	{
		Element eMetadata = new Element("metadata",nsOpf);
		
		Element eLanguage = new Element("language",nsDc);
		eLanguage.addContent("de-DE");
		Attribute aType = new Attribute("type","dcterms:RFC3066",nsXsi);
		eLanguage.setAttribute(aType);
		eMetadata.addContent(eLanguage);
		
		Element eTitle = new Element("title",nsDc);
		eTitle.addContent("Hello World");
		eMetadata.addContent(eTitle);
		
		Element eId = new Element("identifier",nsDc);
		eId.setAttribute("id", "idddddd");
		eId.addContent("identifierssss");
		eMetadata.addContent(eId);
		
		return eMetadata;
	}
	
	private Element getManifest()
	{
		Element eManifest = new Element("manifest",nsOpf);
		
		eManifest.addContent(getItem("ncx","toc.ncx","application/x-dtbncx+xml"));
		
		eManifest.addContent(getItem("datei1","inhatl.xhtml","application/xhtml+xml"));
		spineItemRef.add("datei1");
		
		return eManifest;
	}
	
	private Element getItem(String id,String href, String mediaType)
	{
		Element item = new Element("item",nsOpf);
		item.setAttribute("id", id);
		item.setAttribute("href", href);
		item.setAttribute("media-type", mediaType);
		return item;
	}
	
	private Element getSpine()
	{
		Element eSpine = new Element("spine",nsOpf);
		eSpine.setAttribute("toc","ncx");
		
		for(String ref : spineItemRef)
		{
			Element eRef = new Element("itemref",nsOpf);
			eRef.setAttribute("idref",ref);
			eSpine.addContent(eRef);
		}
		
		return eSpine;
	}
	
	public void save()
	{
		File f = new File(targetDir,"inhalt.opf");
		JDomUtil.save(doc, f, Format.getPrettyFormat());
		JDomUtil.debug(doc);
	}
}
