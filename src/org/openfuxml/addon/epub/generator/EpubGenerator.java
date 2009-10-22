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
	private IdTagger idTagger;
	
	public EpubGenerator()
	{
		ncxGenerator = new NcxGenerator();
		idTagger = new IdTagger();
	}
	
	public void process(File f)
	{
		ExternalMerger exMerger = new ExternalMerger(f);
		Document doc = exMerger.merge();
		
		idTagger.tag(doc);
		
		ncxGenerator.create(doc);
		ncxGenerator.save();
		
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		File f = new File("resources/data/xml/epub/helloworld.xml");
		EpubGenerator epub = new EpubGenerator();
		epub.process(f);
	}
}
