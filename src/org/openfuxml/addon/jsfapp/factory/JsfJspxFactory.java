package org.openfuxml.addon.jsfapp.factory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class JsfJspxFactory
{
	public static Document createDOMjspx()
	{
		Namespace html  = Namespace.getNamespace("http://www.w3.org/1999/xhtml");		
		Namespace jsp   = Namespace.getNamespace("jsp", "http://java.sun.com/JSP/Page");
		
		Document doc    = new Document();
			
		Element rootElement = new Element("root");
		rootElement.setNamespace(jsp);
		rootElement.addNamespaceDeclaration(html);
		for(Namespace ns : NsFactory.getNs("f","h","a4j","rich"))
		{
			rootElement.addNamespaceDeclaration(ns);
		}
		rootElement.setAttribute("version","2.0");
		
		Element output = new Element("output", jsp);
		output.setAttribute("doctype-root-element","html");
		output.setAttribute("doctype-public", "-//W3C//DTD XHTML 1.1//EN");
		output.setAttribute("doctype-system", "http://www.w3c.org/TR/xhtml11/DTD/xhtml11.dtd");
		
		rootElement.addContent(output);
		
		Element directivePage = new Element("directive.page",jsp);
		directivePage.setAttribute("contentType", "text/html; charset=ISO-8859-1");
		directivePage.setAttribute("language", "java");
		
		rootElement.addContent(directivePage);
    	
		doc.setRootElement(rootElement);
		return doc;
	}
}
