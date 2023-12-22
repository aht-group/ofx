package org.openfuxml.factory.xml.editorial;

import org.openfuxml.model.xml.core.editorial.Acronym;

public class XmlAcronymFactory
{	
	public static Acronym shrt(String code)
	{
		Acronym xml = new Acronym();
		xml.setCode(code);
		return xml;
	}
}
