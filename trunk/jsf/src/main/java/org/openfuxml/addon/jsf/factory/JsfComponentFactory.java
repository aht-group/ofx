package org.openfuxml.addon.jsf.factory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.openfuxml.addon.jsf.controller.factory.xml.XmlAttributeFactory;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfComponentFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsfComponentFactory.class);
	
	private Component component;
	
	public JsfComponentFactory()
	{
		
	}
	
	public Component buildComponent(String resourceName)
	{
		component = new Component();
		
		try {read(resourceName);}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return component;
	}
	
	private void read(String resourceName) throws FileNotFoundException, JDOMException
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		logger.info("MRL: "+mrl.isAvailable(resourceName));
		InputStream is = mrl.searchIs(resourceName);
		
		
		
		Document doc = JDomUtil.load(is,"UTF-8");
		JDomUtil.debug(doc);
		
		XPath xpath = XPath.newInstance("//composite:attribute");
		xpath.addNamespace("composite", "http://java.sun.com/jsf/composite");
		List<Element> results = xpath.selectNodes(doc);
		logger.info("Results: "+results.size());
		for(Element e : results)
		{	
			addAttribute(e);
		}
	}
	
	private void addAttribute(Element e) throws DataConversionException
	{
		org.jdom.Attribute attribute;
	
		String name = e.getAttributeValue("name");
		
		boolean required = false;
		attribute = e.getAttribute("required");
		if(attribute!=null){required = attribute.getBooleanValue();}
		
		String sDefault = null;
		attribute = e.getAttribute("default");
		if(attribute!=null){sDefault=attribute.getValue();}
		
		component.getAttribute().add(XmlAttributeFactory.create(name,required,sDefault));
	}
}
