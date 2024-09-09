package org.openfuxml.content.structure;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlStructureTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlStructureTest(Class<T> cXml)
	{
		super(cXml,Paths.get("content","structure"));
	}
}