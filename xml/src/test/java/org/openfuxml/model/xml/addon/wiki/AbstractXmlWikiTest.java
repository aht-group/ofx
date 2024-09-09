package org.openfuxml.model.xml.addon.wiki;

import java.nio.file.Paths;

import org.openfuxml.test.AbstractOfxXmlTest;

public abstract class AbstractXmlWikiTest <T extends Object> extends AbstractOfxXmlTest<T>
{
	public AbstractXmlWikiTest(Class<T> cXml)
	{
		super(cXml,Paths.get("addon","wiki"));
	}
}