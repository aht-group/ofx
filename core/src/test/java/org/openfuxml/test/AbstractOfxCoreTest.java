package org.openfuxml.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.io.StringUtil;
import org.exlp.util.jx.JaxbUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxCharacterRenderer;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.interfaces.renderer.OfxWikiRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;

public class AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCoreTest.class);
	
	protected static LoremIpsum li;
	protected String fileSuffix;
	private boolean saveReference;
	
	protected File referenceDir;
	protected File referenceFile;
	
	protected DefaultSettingsManager dsm;
	protected CrossMediaManager cmm;
	
	protected ConfigurationProvider cp;
	
	public AbstractOfxCoreTest()
	{
		saveReference = false;
		dsm = new OfxDefaultSettingsManager();
		cmm = new NoOpCrossMediaManager();
		cp = ConfigurationProviderFacotry.build(cmm, dsm);
	}
	
	protected void setEnvironment(boolean saveReference)
	{
		this.saveReference = saveReference;
	}
	
	protected <E extends Enum<E>> void initFile(E key)
	{
		referenceFile = new File(referenceDir,key.toString()+"."+fileSuffix);
	}

	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerBootstrap.isLog4jInited())
		{
			LoggerBootstrap.instance("ofx.log4j2.xml").path("ofx/system/io/log").init();
		}
    }
	
	@BeforeClass
    public static void initLoremIpsum()
	{
		li = new LoremIpsum();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper( new OfxNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(OfxCharacterRenderer renderer) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(referenceFile));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(referenceFile, actual.toString());
		}
	}
	
	protected void assertText(OfxCharacterRenderer renderer) throws IOException
	{
		StringWriter actual = new StringWriter();
		renderer.write(actual);
		
		String expected = StringUtil.readFile(referenceFile);
		Assert.assertEquals(expected, actual.toString());
	}
	
	protected void renderTest(OfxCharacterRenderer renderer) throws IOException
	{
		debugCharacter(renderer);
    	if(saveReference){save(renderer);}
    	assertText(renderer);
	}
		
	protected void renderTest(OfxWikiRenderer renderer, File f) throws IOException
	{
		System.out.println("************************************");
		for(String s : renderer.getContent())
		{
			System.out.println(s);
		}
		System.out.println("************************************");
	}

	protected void renderTest(OfxHtmlRenderer renderer) throws IOException
	{
		debugCharacter(renderer);
		if(saveReference){save(renderer);}
		assertText(renderer);
	}
	
	protected void debugCharacter(OfxCharacterRenderer renderer)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("Debugging "+renderer.getClass().getSimpleName());
			System.out.println(StringUtils.repeat("\u21E3", 80));
			for(String s : renderer.getContent())
			{
				System.out.println(s);
			}
			System.out.println(StringUtils.repeat("\u21E1", 80));
		}
	}
}