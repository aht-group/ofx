package org.openfuxml.factory.xml.editorial;

import org.openfuxml.content.editorial.Term;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;

public class XmlTermFactory
{	
	public static Term build(String code, String name, String description)
	{
		Term xml = new Term();
		xml.setCode(code);
		if(name!=null){xml.getText().add(OfxTextFactory.build(name));}
		if(description!=null){xml.getParagraph().add(XmlParagraphFactory.text(description));}
		return xml;
	}
}
