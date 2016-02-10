package org.openfuxml.renderer.html.head;


import org.jdom2.Element;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;

public class HtmlHead extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	public HtmlHead(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public void render(String [] nameValue, String [] contentValue)
	{

	}

	public void render()
	{
		Element root = new Element("html");
		Element head = new Element("Head");
		root.addContent(head);
		head.addContent(new Element("meta"));
		head.getChild("meta").setAttribute("charset","UTF-8");
		txt.add(root);
	}
//	"<meta charset=\"UTF-8\">"
	/*
	* Generiert meta tags inkl. Attribute name und content*/
	String metaData(String attr, String attrValue)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<meta name=\"");
		sb.append(attr);
		sb.append("\" content=\"");
		sb.append(attrValue);
		sb.append("\">");

		return sb.toString();
	}
}
