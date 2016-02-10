package org.openfuxml.renderer.html;

import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AbstractTestHtmlRenderer extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTestHtmlRenderer.class);

	public AbstractTestHtmlRenderer()
	{
		fileSuffix = "html";
	}

	protected void initDir(String dir)
	{
		referenceDir = new File("src/test/resources/data/html", dir);
	}
}
