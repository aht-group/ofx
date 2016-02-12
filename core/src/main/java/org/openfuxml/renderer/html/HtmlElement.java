package org.openfuxml.renderer.html;

import org.jdom2.*;

import java.util.ArrayList;
import java.util.List;

/*Klasse erzeugt Objekte, welche letztendlich die einzelnen Tags
* im Html Dokument darstellen*/
public class HtmlElement extends Element
{
	public HtmlElement(String name)
	{
		super(name);
	}
//	public List<HtmlElement> getChildren()
//	{
//		List<HtmlElement> childern = new ArrayList<HtmlElement>();
//		for(Element e : this.getChildren())
//		{
//			childern.add(((HtmlElement)e));
//		}
//		return childern;
//	}


	public static Document docType(HtmlElement element)
	{
		return new Document(element);
	}
}