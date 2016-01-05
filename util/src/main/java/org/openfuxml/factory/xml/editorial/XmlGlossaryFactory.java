package org.openfuxml.factory.xml.editorial;

import org.openfuxml.content.editorial.Glossary;

public class XmlGlossaryFactory
{	
	public static Glossary build(String code)
	{
		Glossary xml = new Glossary();
		xml.setCode(code);
		return xml;
	}
}
