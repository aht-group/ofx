package org.openfuxml.addon.epub.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class ContainerFactory
{
	static Logger logger = Logger.getLogger(ExternalMerger.class);
	
	private File targetDir;
	private Document doc;
	private Namespace nsContainer;
	
	public ContainerFactory(File targetDir)
	{
		this.targetDir=targetDir;
		nsContainer = Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:container");
	}
	
	public void create()
	{
		doc = new Document();
		
		Element eContainer = new Element("container");
		eContainer.setAttribute("version","1.0");
		eContainer.setNamespace(nsContainer);
		
		eContainer.addContent(getRootfiles());
		
		doc.setRootElement(eContainer);
	}
	
	private Element getRootfiles()
	{
		Element eRootfiles = new Element("rootfiles",nsContainer);
		for(Element e : getRootfile()){eRootfiles.addContent(e);}
		return eRootfiles;
	}
	
	private List<Element> getRootfile()
	{
		List<Element> lRootfiles = new ArrayList<Element>();
		
		Element eRootfile = new Element("rootfile",nsContainer);
		eRootfile.setAttribute("media-type", "application/oebps-package+xml");
		eRootfile.setAttribute("full-path","content.opf");
		lRootfiles.add(eRootfile);
		return lRootfiles;
	}
	
	public void save()
	{
		File dMetainf = new File(targetDir,"META-INF");
		dMetainf.mkdir();
		File f = new File(dMetainf,"container.xml");
		JDomUtil.save(doc, f, Format.getPrettyFormat());
//		JDomUtil.debug(doc);
	}
}
