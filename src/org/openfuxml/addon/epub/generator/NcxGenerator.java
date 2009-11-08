package org.openfuxml.addon.epub.generator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.addon.epub.data.jaxb.ncx.Head;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap;
import org.openfuxml.addon.epub.data.jaxb.ncx.Ncx;
import org.openfuxml.addon.epub.util.NcxFactory;
import org.openfuxml.content.Section;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class NcxGenerator
{
	static Logger logger = Logger.getLogger(ExternalMerger.class);
	
	private File targetDir;
	private Ncx ncx;
	private int playOrder;
	
	public NcxGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		playOrder=1;
	}
	
	public void create(Document doc)
	{
		ncx = new Ncx();
		ncx.setVersion("2005-1");
		ncx.setHead(getHead());
		ncx.setDocTitle(NcxFactory.getTitle("Test Title"));
		ncx.setNavMap(getNavMap(doc));
	}
	
	public void save()
	{
		File f = new File(targetDir,"toc.ncx");
		JaxbUtil.save(f, ncx, true);
	}
	
	private Head getHead()
	{
		Head head = new Head();
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:uid", "helloWorld"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:depth", "1"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:totalPageCount", "0"));
		head.getMeta().add(NcxFactory.getHeadMeta("dtb:maxPageNumber", "0"));		
		return head;
	}
	
	private NavMap getNavMap(Document doc)
	{
		NavMap navmap = new NavMap();
		try
		{
			Namespace ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			XPath xpath = XPath.newInstance("//ofx:ofxdoc/ofx:content/ofx:section");
			xpath.addNamespace(ns);
			
			List<?> list = xpath.selectNodes(doc);
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next();
				Section section = (Section)JDomUtil.toJaxb(childElement, Section.class);
				navmap.getNavPoint().add(NcxFactory.getNavPoint(section.getId(), playOrder, "Title Page", "title.xhtml"));
				playOrder++;
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return navmap;
	}
}
