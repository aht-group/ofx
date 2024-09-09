package org.openfuxml.content.table;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlTableTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlTableTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","table"));
	}
}