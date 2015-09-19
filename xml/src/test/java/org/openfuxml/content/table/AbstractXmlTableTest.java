package org.openfuxml.content.table;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlTableTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlTableTest(Class<T> cXml)
	{
		super(cXml,"content/table");
	}
}