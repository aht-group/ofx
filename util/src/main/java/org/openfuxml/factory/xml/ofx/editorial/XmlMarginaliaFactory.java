package org.openfuxml.factory.xml.ofx.editorial;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;

public class XmlMarginaliaFactory
{	
	public static Marginalia build(Image image)
	{
		Marginalia xml = build();
		xml.getContent().add(image);
		return xml;
	}
	
	public static Marginalia build()
	{
		Marginalia xml = new Marginalia();
		return xml;
	}
}
