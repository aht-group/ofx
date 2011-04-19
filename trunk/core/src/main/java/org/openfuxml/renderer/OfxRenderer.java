package org.openfuxml.renderer;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.exception.OfxRenderingException;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.renderer.processor.pre.OfxPreProcessor;
import org.openfuxml.renderer.util.OfxRenderConfiguration;

public class OfxRenderer
{
	static Log logger = LogFactory.getLog(OfxRenderer.class);
		
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge,containerMerge,externalMerge,phaseTemplate,mergeTemplate};
	
	private OfxRenderConfiguration cmpConfigUtil;
	private Configuration config;
	private Cmp cmp;

	
	public OfxRenderer(Configuration config)
	{
		this.config=config;
	}

	public void chain() throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		cmpConfigUtil = new OfxRenderConfiguration();
		cmp = cmpConfigUtil.readCmp(config.getString("ofx.xml.cmp"));
		
		OfxPreProcessor preProcessor = new OfxPreProcessor(cmpConfigUtil,config);
		preProcessor.chain();
		
		if(cmp.isSetTargets())
		{
			OfxTargetRenderer targetRenderer = new OfxTargetRenderer(cmpConfigUtil);
			targetRenderer.renderTargets();
		}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		MultiResourceLoader mrl = new MultiResourceLoader();
		logger.debug(mrl.isAvailable("config/cmp/wiki/replacementsWiki.xml"));
			
	}
}