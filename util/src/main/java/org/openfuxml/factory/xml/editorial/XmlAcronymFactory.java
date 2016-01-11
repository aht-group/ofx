package org.openfuxml.factory.xml.editorial;

import org.openfuxml.content.editorial.Acronym;

public class XmlAcronymFactory
{	
	public static Acronym shrt(String code)
	{
		Acronym xml = new Acronym();
		xml.setCode(code);
		return xml;
	}
}
