package org.openfuxml.content.layout;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlLayoutTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlLayoutTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","layout"));
	}
}