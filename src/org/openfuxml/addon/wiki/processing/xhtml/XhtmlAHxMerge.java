package org.openfuxml.addon.wiki.processing.xhtml;

import java.util.regex.Pattern;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;

public class XhtmlAHxMerge
{
	static Log logger = LogFactory.getLog(XhtmlAHxMerge.class);
	
	private Pattern p;
	
	public XhtmlAHxMerge()
	{
		p = Pattern.compile("h[\\d](.*)");
	}
	
	public String merge(String xHtmlText)
	{
		Document doc = JDomUtil.txtToDoc(xHtmlText);
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
			if(org.jdom.Text.class.isInstance(o))
			{
				if(prevChild!=null){newRoot.addContent(prevChild);prevChild = null;}
				Text txt = (Text)o;
				Text newText = new Text(txt.getText());
				newRoot.addContent(newText);
			}
			else if(org.jdom.Element.class.isInstance(o))
			{
				Element oldChild = (Element)o;
				Element newChild = null;
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