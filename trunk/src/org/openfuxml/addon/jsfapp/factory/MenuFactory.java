package org.openfuxml.addon.jsfapp.factory;

import java.io.File;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.addon.jsfapp.data.jaxb.Menu;
import org.openfuxml.addon.jsfapp.data.jaxb.Menuitem;

public class MenuFactory
{
	private static Logger logger = Logger.getLogger(MenuFactory.class);
			
	private Menu menu;
	
	public MenuFactory()
	{
		menu = new Menu();
	}
	
	public void save(File xmlToc)
	{
		JaxbUtil.debug(menu);
		JaxbUtil.save(xmlToc, menu, true);
	}
	
	public void parse(File htmlToc)
	{
		logger.debug("Parsing "+htmlToc.getAbsolutePath());
		Document docToc = JDomUtil.load(htmlToc,"ISO-8859-1");
		
		try
		{
			XPath xpath;
			xpath = XPath.newInstance("//div[@class='inhaltsverz']");
			Element eToc = (Element)xpath.selectSingleNode(docToc);
			
			removeElements("//span", eToc);
			
			removeElements("//a[@class='inhalt']", eToc);
			removeElements("//a[@class='inhalt1onum']", eToc);
			removeElements("//a[@class='inhalt1num']", eToc);
			removeElements("//a[@class='inhalt2num']", eToc);
			removeElements("//a[@class='inhalt3num']", eToc);
			
			eToc.removeNamespaceDeclaration(Namespace.getNamespace("http://saxon.sf.net/"));
			eToc.removeNamespaceDeclaration(Namespace.getNamespace("http://www.w3.org/2001/XMLSchema"));
			
			Document doc = new Document();
			doc.setRootElement((Element)eToc.clone());
			parseA(doc);
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	private void parseA(Document doc)
	{	
		try
		{
			XPath xpath = XPath.newInstance("//a");
			List<Element> l = xpath.selectNodes(doc);
			logger.debug(l.size());
			for(Element element : l)
			{
				Menuitem mi = new Menuitem();
				mi.setLabel(element.getValue().trim());
				mi.setLink(element.getAttributeValue("href").trim());
				String c = element.getAttributeValue("class");
				
				int level = new Integer(c.substring(c.length()-1, c.length()));
				if(level==1){menu.getMenuitem().add(mi);}
				if(level==2)
				{
					int index1 = menu.getMenuitem().size()-1;
					menu.getMenuitem().get(index1).getMenuitem().add(mi);
				}
				if(level==3)
				{
					int index1 = menu.getMenuitem().size()-1;
					int index2 = menu.getMenuitem().get(index1).getMenuitem().size()-1;
					menu.getMenuitem().get(index1).getMenuitem().get(index2).getMenuitem().add(mi);
				}
		//		element.detach();
			}
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	private void removeElements(String xPath, Element eRoot)
	{
		try
		{
			XPath xpath = XPath.newInstance(xPath);
			List<Element> l = xpath.selectNodes(eRoot);
			for(Element element : l){element.detach();}
		}
		catch (JDOMException e) {logger.error(e);}
	}	
}