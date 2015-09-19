package org.openfuxml.content.list;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlListTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlListTest(Class<T> cXml)
	{
		super(cXml,"content/list");
	}
}