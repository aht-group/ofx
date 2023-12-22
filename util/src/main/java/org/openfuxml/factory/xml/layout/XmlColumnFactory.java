package org.openfuxml.factory.xml.layout;

import org.openfuxml.model.xml.core.layout.Column;
import org.openfuxml.model.xml.core.layout.Container;
import org.openfuxml.model.xml.core.layout.Height;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.ofx.Paragraph;

public class XmlColumnFactory
{	
	private static Column buildWH(Width width, Height height)
	{
		Column xml = new Column();
		xml.getContent().add(width);
		if(height!=null){xml.getContent().add(height);}
		return xml;
	}
	
	public static Column build(Width width, Paragraph paragraph)
	{
		Column xml = buildWH(width,null);
		xml.getContent().add(paragraph);
		return xml;
	}
	
	public static Column build(Width width, Container container)
	{
		Column xml = buildWH(width,null);
		xml.getContent().add(container);
		return xml;
	}
	
	public static Column build(Width width, Height height, Image image)
	{
		Column xml = buildWH(width,height);
		xml.getContent().add(image);
		return xml;
	}
}
