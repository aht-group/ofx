package org.openfuxml.addon.wiki.processor.xhtml.mods;

import java.util.regex.Pattern;

import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XhtmlAHxMerge
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlAHxMerge.class);
	
	private Pattern p;
	
	public XhtmlAHxMerge()
	{
		p = Pattern.compile("h[\\d](.*)");
	}
	
	public String merge(String xHtmlText)
	{
		Document doc = null;
		try {doc = JDomUtil.txtToDoc(xHtmlText);}
		catch (JDOMException e) {logger.error("",e);}
		Element rootElement = doc.getRootElement();	
		rootElement.detach();
		
		doc.setRootElement(merge(rootElement));
		
		xHtmlText=JDomUtil.docToTxt(doc);
		return xHtmlText;
	}
	
	public Element merge(Element oldRoot)
	{
		Element newRoot = new Element(oldRoot.getName());
		
		for(Object oAtt : oldRoot.getAttributes())
		{
			Attribute att = (Attribute)oAtt;
			Attribute newAtt = new Attribute(att.getName(),att.getValue());
			newRoot.setAttribute(newAtt);
		}
		
		Element prevChild = null;
		
		for(Object o : oldRoot.getContent())
		{
			if(org.jdom2.Text.class.isInstance(o))
			{
				if(prevChild!=null){newRoot.addContent(prevChild);prevChild = null;}
				Text txt = (Text)o;
				Text newText = new Text(txt.getText());
				newRoot.addContent(newText);
			}
			else if(org.jdom2.Element.class.isInstance(o))
			{
				Element oldChild = (Element)o;
				if(prevChild!=null)
				{
					boolean prevA = prevChild.getName().equals("a");
					boolean thisH = p.matcher(oldChild.getName()).matches();
					logger.debug(prevChild.getName()+"-"+oldChild.getName()+" :"+prevA+"-"+thisH);
					if(prevA && thisH)
					{
						
					}
					else
					{
						newRoot.addContent(prevChild);prevChild = null;
					}
				}
				prevChild = (merge(oldChild));
			}
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		if(prevChild!=null){newRoot.addContent(prevChild);prevChild = null;}
		return newRoot;
	}
}