package org.openfuxml.renderer.markdown;

import java.io.File;

import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTestMdRenderer extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTestMdRenderer.class);
	
	public AbstractTestMdRenderer()
	{
		fileSuffix = "txt";
	}
	
	protected void initDir(String dir)
	{
		referenceDir = new File("src/test/resources/data/markdown",dir);
	}
}