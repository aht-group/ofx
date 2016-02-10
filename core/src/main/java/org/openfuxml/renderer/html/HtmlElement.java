package org.openfuxml.renderer.html;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.List;

/*Klasse erzeugt tags für Html Elemente*/
public class HtmlElement extends Element
{
	public HtmlElement(String name)
	{
		super(name);
	}

	HtmlElement meta(List<Attribute> attr)
	{
		HtmlElement meta = new HtmlElement("meta");
		meta.setAttributes(attr);
		return meta;
	}
}
