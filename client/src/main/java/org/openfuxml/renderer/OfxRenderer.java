package org.openfuxml.renderer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxImplementationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.exception.OfxRenderingException;
import org.openfuxml.renderer.processor.pre.OfxPreProcessor;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;

public class OfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxRenderer.class);
	public static final String exeName = "OfxRenderer";
	
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge,containerMerge,externalMerge,phaseTemplate,mergeTemplate};
	
	private Options options;
	
	private OfxRenderConfiguration cmpConfigUtil;
	private Cmp cmp;
	
	public OfxRenderer()
	{
		
	}

	public void initCmpUtil(String fileName) throws OfxConfigurationException
	{
		cmpConfigUtil = new OfxRenderConfiguration();
		cmp = cmpConfigUtil.readCmp(fileName);
	}
	
	public void preProcessor() throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		OfxPreProcessor preProcessor = new OfxPreProcessor(cmpConfigUtil);
		preProcessor.chain();		
	}
	
	public void renderTarget() throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException, OfxImplementationException
	{	
		logger.debug("Rendering Targets: "+cmp.isSetTargets());
		if(cmp.isSetTargets())
		{
			logger.info("Rendering Target");
			OfxTargetRenderer targetRenderer = new OfxTargetRenderer(cmpConfigUtil);
			targetRenderer.renderTargets();
		}
	}
	
	public void parseArguments(String args[]) throws ParseException, OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException, OfxImplementationException
	{
		options = createOptions();
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options , args); 
	            
        if(line.hasOption("help")) {printHelp();}
        
        if(line.hasOption("debug")) {initLogger("log4j.debug.xml");}
        else{initLogger("log4j.xml");}
        
        if(!line.hasOption("cmp")) {printHelp();}
        else{initCmpUtil(line.getOptionValue("cmp"));}
        
        renderTarget();
	}
	
	private Options createOptions()
	{
		Option oHelp = new Option("help", "Print this message" );
		Option oDebug = new Option("debug", "Debug output");
		Option oPre = new Option("pre", "Do PreProcessing");
		
		Option oConfigCmp = Option.builder("cmp").required(true).hasArg(true).argName("FILENAME").desc("Use cmp configuration file FILENAME (required)").build();
		
		Options options = new Options();
		options.addOption(oHelp);
		options.addOption(oPre);
		options.addOption(oDebug);
		options.addOption(oConfigCmp);
		
		return options;
	}
	
	private void printHelp()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "java -jar "+exeName, options );
		System.exit(0);
	}
	
	private void initLogger(String logConfig)
	{
		LoggerInit loggerInit = new LoggerInit(logConfig);	
		loggerInit.path("src/main/resources/config");
		loggerInit.path("config");
		loggerInit.setAllLoadTypes(LoggerInit.LoadType.File,LoggerInit.LoadType.Resource);
		loggerInit.init();
	}
	
	public static void main (String[] args) throws Exception
	{		
		OfxRenderer ofxRenderer = new OfxRenderer();	
		try {ofxRenderer.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());ofxRenderer.printHelp();}
		catch (OfxConfigurationException e) {e.printStackTrace();logger.error(e.getMessage());ofxRenderer.printHelp();}
	}
}