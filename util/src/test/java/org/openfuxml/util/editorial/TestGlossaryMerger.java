package org.openfuxml.util.editorial;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestGlossaryMerger extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestGlossaryMerger.class);

	private GlossaryMerger gm;
	private String g1 = "data.ofx-util.test/editorial/glossary/g1.xml";
	private String g2 = "data.ofx-util.test/editorial/glossary/g2.xml";
	
	@Before
	public void init()
	{
		gm = new GlossaryMerger();
	}
	
	@Test
	public void single() throws FileNotFoundException
	{
		gm.addResource(g1);
		Glossary glossary = gm.getGlossary();
		Assert.assertEquals(2, glossary.getTerm().size());
	}
	
	@Test
	public void multiple() throws FileNotFoundException
	{
		gm.addResource(g1);
		gm.addResource(g2);
		Glossary glossary = gm.getGlossary();
		Assert.assertEquals(3, glossary.getTerm().size());
	}
	
	@Test(expected=FileNotFoundException.class)
	public void missing() throws FileNotFoundException
	{
		gm.addResource("xx");
	}
}