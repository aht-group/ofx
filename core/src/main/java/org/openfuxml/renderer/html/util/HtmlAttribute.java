package org.openfuxml.renderer.html.util;
/**
 * Author: Rebecca Roblick
 */
import org.jdom2.Attribute;

public class HtmlAttribute extends Attribute
{
	public HtmlAttribute(String name, String value){super(name, value);}

	public String toString(){return super.toString().replaceAll("(\\[Attribute:)|\\]","");}
}
