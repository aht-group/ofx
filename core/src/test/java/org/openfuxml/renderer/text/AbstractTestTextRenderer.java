package org.openfuxml.renderer.text;

import java.io.File;

import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTestTextRenderer extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTestTextRenderer.class);
	
	public AbstractTestTextRenderer()
	{
		fileSuffix = "txt";
	}
	
	protected void initDir(String dir)
	{
		referenceDir = new File("src/test/resources/data/text",dir);
	}
}