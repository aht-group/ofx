package org.openfuxml.addon.epub.generator.epub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.openfuxml.content.ofx.Metadata;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class OpfGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	private Document doc;
	private Namespace nsXsi,nsOpf,nsDc;
	
	private List<String> spineItemRef;
	
	public OpfGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
		nsOpf = Namespace.getNamespace("http://www.idpf.org/2007/opf");
		nsDc = Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/");
		spineItemRef = new ArrayList<String>();
	}
	
	public void create(Ofxdoc ofxDoc)
	{
		doc = new Document();
		
		Element ePackage = new Element("package");
		ePackage.setAttribute("version","2.0");
		ePackage.setNamespace(nsOpf);
		
		ePackage.addNamespaceDeclaration(nsXsi);
		ePackage.addNamespaceDeclaration(nsDc);
		
		ePackage.addContent(getMetadata(ofxDoc.getMetadata()));
		ePackage.addContent(getManifest(ofxDoc));
		ePackage.addContent(getSpine(ofxDoc));
		
		doc.setRootElement(ePackage);
		save();
	}
	
	private Element getMetadata(Metadata metadata)
	{
		Element eMetadata = new Element("metadata",nsOpf);
		
		Element eLanguage = new Element("language",nsDc);
		eLanguage.addContent("de-DE");
		Attribute aType = new Attribute("type","dcterms:RFC3066",nsXsi);
		eLanguage.setAttribute(aType);
		eMetadata.addContent(eLanguage);
		
		Element eTitle = new Element("title",nsDc);
		eTitle.addContent(metadata.getTitle().getValue());
		eMetadata.addContent(eTitle);
		
		Element eId = new Element("identifier",nsDc);
		eId.setAttribute("id", "idddddd");
		eId.addContent("identifierssss");
		eMetadata.addContent(eId);
		
		return eMetadata;
	}
	
	private Element getManifest(Ofxdoc ofxDoc)
	{
		Element eManifest = new Element("manifest",nsOpf);
		
		eManifest.addContent(getItem("ncx","toc.ncx","application/x-dtbncx+xml"));
		
		int partNr=1;
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				eManifest.addContent(getItem(section.getId(),"part-"+partNr+".xhtml","application/xhtml+xml"));
				partNr++;
			}
		}
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
	
	private Element getSpine(Ofxdoc ofxDoc)
	{
		Element eSpine = new Element("spine",nsOpf);
		eSpine.setAttribute("toc","ncx");
		
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				Element eRef = new Element("itemref",nsOpf);
				eRef.setAttribute("idref",section.getId());
				eSpine.addContent(eRef);
			}
		}
		
		return eSpine;
	}
	
	private void save()
	{
		File f = new File(targetDir,"inhalt.opf");
		JDomUtil.save(doc, f, Format.getPrettyFormat());
	}
}