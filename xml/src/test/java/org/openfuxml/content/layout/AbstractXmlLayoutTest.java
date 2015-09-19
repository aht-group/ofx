package org.openfuxml.content.layout;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlLayoutTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlLayoutTest(Class<T> cXml)
	{
		super(cXml,"content/layout");
	}
}