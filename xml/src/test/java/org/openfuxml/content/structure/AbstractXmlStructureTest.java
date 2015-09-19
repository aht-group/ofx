package org.openfuxml.content.structure;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlStructureTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlStructureTest(Class<T> cXml)
	{
		super(cXml,"content/structure");
	}
}