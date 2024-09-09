package org.openfuxml.content.ofx;

import java.nio.file.Paths;

public abstract class AbstractXmlOfxTest <T extends Object> extends org.openfuxml.test.AbstractOfxXmlTest<T>
{
	public AbstractXmlOfxTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","ofx"));
	}
}