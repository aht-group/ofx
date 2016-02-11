package org.openfuxml.renderer.html;

import org.jdom2.Attribute;
import org.jdom2.DocType;

import java.util.List;

/*Klasse erzeugt tags für Html Elemente*/
public class HtmlElement //extends
{
	public HtmlElement()
	{
	}

	public String meta(List<HtmlAttribute> attr)
	{
		StringBuffer sb = new StringBuffer();
		for(HtmlAttribute ha : attr)
		{
			sb.append(openWithAttributes("meta",ha, true));
		}
		return sb.toString();
	}

	public static String docType()
	{
		return new DocType("html").toString().replaceAll("\\[|\\]|(DocType: )", "");
	}

	public String openTag(String s, boolean emptyTag)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(s);
		if(emptyTag){sb.append("/>");}
		else {sb.append(">");}

		return sb.toString();
	}

	public String openWithAttributes(String tag, HtmlAttribute attr, boolean emptyTag)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(tag);
		sb.append(attr.toString());
		if(emptyTag){sb.append("/>");}
		else {sb.append(">");}

		return sb.toString();
	}

	public String closeTag(String s)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("</");
		sb.append(s);
		sb.append(">");

		return sb.toString();
	}
}