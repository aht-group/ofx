package org.openfuxml.content.editorial;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlEditorialTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlEditorialTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","editorial"));
	}
}