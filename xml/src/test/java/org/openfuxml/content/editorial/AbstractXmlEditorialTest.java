package org.openfuxml.content.editorial;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlEditorialTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlEditorialTest(Class<T> cXml)
	{
		super(cXml,"content/editorial");
	}
}