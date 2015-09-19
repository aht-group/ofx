package org.openfuxml.content.ofx;

public abstract class AbstractXmlOfxTest <T extends Object> extends org.openfuxml.test.AbstractOfxXmlTest<T>
{
	public AbstractXmlOfxTest(Class<T> cXml)
	{
		super(cXml,"content/ofx");
	}
}