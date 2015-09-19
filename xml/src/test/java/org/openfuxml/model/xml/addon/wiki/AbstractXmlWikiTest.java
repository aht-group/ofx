package org.openfuxml.model.xml.addon.wiki;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlWikiTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlWikiTest(Class<T> cXml)
	{
		super(cXml,"addon/wiki");
	}
}