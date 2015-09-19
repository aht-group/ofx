package org.openfuxml.content.text;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlOfxTextTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlOfxTextTest(Class<T> cXml)
	{
		super(cXml,"content/text");
	}
}