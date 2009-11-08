package org.openfuxml.addon.epub.generator;

import java.io.File;

import net.sf.exlp.io.LoggerInit;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.openfuxml.producer.preprocessors.ExternalMerger;
import org.openfuxml.producer.preprocessors.IdTagger;

public class EpubGenerator
{
	private static Logger logger = Logger.getLogger(ExternalMerger.class);
	
	private NcxGenerator ncxGenerator;
	private MimetypeFactory mimeFactory;
	private ContainerFactory containerFactory;
	private OpfFactory opfFactory;
	
	private IdTagger idTagger;
	
	public EpubGenerator(File targetDir)
	{
		ncxGenerator = new NcxGenerator(targetDir);
		mimeFactory = new MimetypeFactory(targetDir);
		containerFactory = new ContainerFactory(targetDir);
		opfFactory = new OpfFactory(targetDir);
		idTagger = new IdTagger();
	}
	
	public void process(File f)
	{
		ExternalMerger exMerger = new ExternalMerger(f);
		Document doc = exMerger.merge();
		
		idTagger.tag(doc);
		
		ncxGenerator.create(doc);
		ncxGenerator.save();
		
		containerFactory.create();
		containerFactory.save();
		
		mimeFactory.save();
		
		opfFactory.create();
		opfFactory.save();
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		File f = new File("resources/data/xml/epub/helloworld.xml");
		File baseDir = new File("dist");
		EpubGenerator epub = new EpubGenerator(baseDir);
		epub.process(f);
	}
}
