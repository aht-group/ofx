package org.openfuxml.renderer.wiki;

import java.io.File;

import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTestWikiRenderer  extends AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTestWikiRenderer.class);
	
	public AbstractTestWikiRenderer()
	{
		fileSuffix = "txt";
	}
	
	protected void initDir(String dir)
	{
		referenceDir = new File("src/test/resources/data/wiki",dir);
	}
}
