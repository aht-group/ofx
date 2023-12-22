package org.openfuxml.util.editorial;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.model.xml.core.editorial.Acronyms;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAcronymMerger extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestAcronymMerger.class);

	private AcronymMerger gm;
	private String a1 = "data.ofx-util.test/editorial/acronyms/a1.xml";
	private String a2 = "data.ofx-util.test/editorial/acronyms/a2.xml";
	
	@Before
	public void init()
	{
		gm = new AcronymMerger();
	}
	
	@Test
	public void single() throws FileNotFoundException
	{
		gm.addResource(a1);
		Acronyms glossary = gm.getAcronyms();
		Assert.assertEquals(2, glossary.getTerm().size());
	}
	
	@Test
	public void multiple() throws FileNotFoundException
	{
		gm.addResource(a1);
		gm.addResource(a2);
		Acronyms glossary = gm.getAcronyms();
		Assert.assertEquals(3, glossary.getTerm().size());
	}
	
	@Test(expected=FileNotFoundException.class)
	public void missing() throws FileNotFoundException
	{
		gm.addResource("xx");
	}
}