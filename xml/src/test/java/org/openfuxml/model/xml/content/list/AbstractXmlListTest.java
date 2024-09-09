package org.openfuxml.model.xml.content.list;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlListTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlListTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","list"));
	}
}