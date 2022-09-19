package org.openfuxml.renderer.html.util;

import org.jdom2.Attribute;

public class HtmlAttribute extends Attribute
{
	private static final long serialVersionUID = 1L;

	public HtmlAttribute(String name, String value){super(name, value);}

	public String toString(){return super.toString().replaceAll("(\\[Attribute:)|\\]","");}
}
