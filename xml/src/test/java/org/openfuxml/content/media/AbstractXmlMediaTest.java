package org.openfuxml.content.media;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlMediaTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlMediaTest(Class<T> cXml)
	{
		super(cXml,"content/media");
	}
}