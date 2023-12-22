package org.openfuxml.factory.xml.editorial;

import org.openfuxml.model.xml.core.editorial.Index;

public class XmlIndexFactory
{	
	public static Index build(String code)
	{
		Index xml = new Index();
		xml.setCode(code);
		return xml;
	}
}
