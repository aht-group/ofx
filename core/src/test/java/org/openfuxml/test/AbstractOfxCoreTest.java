package org.openfuxml.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxCharacterRenderer;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.interfaces.renderer.wiki.OfxWikiRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCoreTest.class);
	
	protected static LoremIpsum li;
	protected String fileSuffix;
	private boolean saveReference;
	
	protected File referenceDir;
	protected File f;
	
	protected DefaultSettingsManager dsm;
	protected CrossMediaManager cmm;
	
	public AbstractOfxCoreTest()
	{
		saveReference = false;
		dsm = new OfxDefaultSettingsManager();
		cmm = new NoOpCrossMediaManager();
	}
	
	protected void setEnvironment(boolean saveReference)
	{
		this.saveReference = saveReference;
	}
	
	protected <E extends Enum<E>> void initFile(E key)
	{
		f = new File(referenceDir,key.toString()+"."+fileSuffix);
	}

	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ofx-core.test");
			loggerInit.init();
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
	
	@Deprecated
	protected void save(OfxLatexRenderer renderer, File f) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(f)+" "+f.getAbsolutePath());
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
	}
	
	protected void save(OfxCharacterRenderer renderer) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(f));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
	}
	
	@Deprecated
	protected void assertText(OfxLatexRenderer renderer, File f) throws IOException
	{
		StringWriter actual = new StringWriter();
		renderer.write(actual);
		
		String expected = StringIO.loadTxt(f);
		Assert.assertEquals(expected, actual.toString());
	}
	
	protected void assertText(OfxCharacterRenderer renderer) throws IOException
	{
		StringWriter actual = new StringWriter();
		renderer.write(actual);
		
		String expected = StringIO.loadTxt(f);
		Assert.assertEquals(expected, actual.toString());
	}
		
	@Deprecated
	protected void renderTest(OfxLatexRenderer renderer, File f) throws IOException
	{
		debugCharacter(renderer);
    	if(saveReference){save(renderer,f);}
    	assertText(renderer,f);
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