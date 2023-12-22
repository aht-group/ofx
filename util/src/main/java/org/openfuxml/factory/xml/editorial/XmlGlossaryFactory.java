package org.openfuxml.factory.xml.editorial;

import org.openfuxml.model.xml.core.editorial.Glossary;

public class XmlGlossaryFactory
{	
	public static enum Classifier {name,text}
	
	public static Glossary build(String code)
	{
		Glossary xml = new Glossary();
		xml.setCode(code);
		return xml;
	}
}
