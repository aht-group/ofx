package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.WikiInlineProcessor;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.net.WikiContentFetcher;
import org.openfuxml.addon.wiki.processor.ofx.WikiXmlProcessor;
import org.openfuxml.addon.wiki.processor.pre.WikiExternalIntegrator;
import org.openfuxml.addon.wiki.processor.template.WikiTemplateCorrector;
import org.openfuxml.addon.wiki.processor.template.WikiTemplateProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.exception.OfxRenderingException;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.openfuxml.xml.renderer.cmp.Merge;
import org.openfuxml.xml.renderer.cmp.Preprocessor;
import org.openfuxml.xml.xpath.cmp.CmpJaxbXpathLoader;

public class OfxPreProcessor
{
	static Log logger = LogFactory.getLog(OfxPreProcessor.class);
		
	public static enum DirCode {working,content};
	public static enum FileCode {root,target};
	
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge,containerMerge,externalMerge,phaseTemplate,mergeTemplate};
	
	private OfxRenderConfiguration cmpConfigUtil;
	private Configuration config;
	
	private Cmp cmp;
	private Preprocessor xmlPreProcessor;
	
	private OfxNsPrefixMapper nsPrefixMapper;
	private Ofxdoc ofxDoc;
	private Contents wikiQueries;

	
	public OfxPreProcessor(OfxRenderConfiguration cmpConfigUtil, Configuration config)
	{
		this.cmpConfigUtil=cmpConfigUtil;
		this.config=config;
		nsPrefixMapper = new OfxNsPrefixMapper();
	}

	public void chain() throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		xmlPreProcessor = cmpConfigUtil.getCmp().getPreprocessor();
		cmp = cmpConfigUtil.getCmp();
		File fOfxRoot = cmpConfigUtil.getFile(cmp.getSource().getDirs(), DirCode.content.toString(), FileCode.root.toString(),false);
		
		File dWorking = cmpConfigUtil.getDir(xmlPreProcessor.getDirs(), DirCode.working.toString());
		logger.debug("Working Dir: "+dWorking.getAbsolutePath());
		
		chain(dWorking, fOfxRoot);
	}
	
	public void chain(File dWorking, File fOfxRoot) throws OfxConfigurationException, OfxAuthoringException, OfxRenderingException, OfxInternalProcessingException, OfxWikiException
	{
		String wikiPlainDir = "wikiPlain";
		File dirWikiPlain = createDir(dWorking,wikiPlainDir);
		File dirWikiTemplate = createDir(dWorking,WikiProcessor.WikiDir.wikiTemplate.toString());
		File dirOfxTemplate = createDir(dWorking,WikiProcessor.WikiDir.ofxTemplate.toString());
		
		String wikiMarkupDir = "wikiMarkup";
		String wikiModelDir = "wikiModel";
		String xhtmlReplaceDir = "xhtmlReplace";
		String xhtmlFinalDir = "xhtmlFinal";
		String ofxXmlDir = "ofxXml";
		
		phaseMergeInitial(dWorking,fOfxRoot);
		phaseWikiExternalIntegrator(dWorking,ofxXmlDir);
		phaseWikiContentFetcher(dWorking,true,dirWikiPlain);
		phaseWikiProcessing(dWorking,wikiPlainDir,wikiMarkupDir,wikiModelDir,xhtmlReplaceDir,xhtmlFinalDir,ofxXmlDir, dirWikiTemplate);
		phaseMerge(dWorking, Phase.wikiMerge);
		phaseContainerMerge(dWorking, Phase.wikiMerge, Phase.containerMerge);
		phaseExternalMerge(dWorking, Phase.containerMerge, new File(dWorking,getPhaseXmlFileName(Phase.externalMerge)));
		phaseTemplate(dWorking, dirWikiTemplate, dirOfxTemplate, Phase.externalMerge, Phase.phaseTemplate);
		phaseExternalMerge(dWorking, Phase.phaseTemplate, cmpConfigUtil.getFile(cmp.getSource().getDirs(), DirCode.content.toString(), FileCode.target.toString(),false));
	}
	
	private void phaseMergeInitial(File dWorking, File fOfxRoot) throws OfxInternalProcessingException
	{
		Document doc = JDomUtil.load(fOfxRoot);
		try
		{
			Merge merge = CmpJaxbXpathLoader.getMerge(xmlPreProcessor.getMerge(), Phase.iniMerge.toString());
			
			OfxExternalMerger exMerger = new OfxExternalMerger(fOfxRoot);
			doc = exMerger.mergeToDoc();
			
			if(merge.isSetWriteIntermediate() && merge.isWriteIntermediate())
			{
				File fIntermediate = new File(dWorking,getPhaseXmlFileName(Phase.iniMerge));
				JDomUtil.save(doc, fIntermediate, Format.getPrettyFormat());
			}
		}
		catch (NoSuchElementException e) {logger.debug("No initial merge");}
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		JaxbUtil.save(new File(dWorking,getPhaseXmlFileName(Phase.wikiIntegrate)), ofxDoc, nsPrefixMapper, true);
//		JaxbUtil.debug(ofxDoc, nsPrefixMapper);
	}
	
	private void phaseMerge(File dWorking, Phase phase) throws OfxInternalProcessingException
	{
		logger.warn("hardcoded filename!!!");
		File f = new File(dWorking,"2-wikiIntegrate.xml");
		Document doc = JDomUtil.load(f);
		try
		{
			Merge merge = CmpJaxbXpathLoader.getMerge(xmlPreProcessor.getMerge(), Phase.iniMerge.toString());
			
			OfxExternalMerger exMerger = new OfxExternalMerger(f);
			doc = exMerger.mergeToDoc();
			
		}
		catch (NoSuchElementException e) {logger.debug("No initial merge");}
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		JaxbUtil.save(new File(dWorking,getPhaseXmlFileName(phase)), ofxDoc, nsPrefixMapper, true);
	}
	
	private void phaseContainerMerge(File dWorking, Phase phaseLoad, Phase phaseSave) throws OfxInternalProcessingException
	{
		File f = new File(dWorking,getPhaseXmlFileName(phaseLoad));
		
		try
		{
			ofxDoc = (Ofxdoc)JaxbUtil.loadJAXB(f.getAbsolutePath(), Ofxdoc.class);
			
			OfxContainerMerger containerMerger = new OfxContainerMerger();
			ofxDoc = containerMerger.merge(ofxDoc);
			
			JaxbUtil.save(new File(dWorking,getPhaseXmlFileName(phaseSave)), ofxDoc, nsPrefixMapper, true);
		}
		catch (FileNotFoundException e)
		{
			logger.warn("OfxPreprocessorException");//TODO new exception
			e.printStackTrace();
		}
	}
	
	private void phaseExternalMerge(File dWorking, Phase phaseLoad, File dstFile) throws OfxInternalProcessingException
	{
		File f = new File(dWorking,getPhaseXmlFileName(phaseLoad));
		
		try
		{
			ofxDoc = (Ofxdoc)JaxbUtil.loadJAXB(f.getAbsolutePath(), Ofxdoc.class);
			
			OfxExternalMerger exMerger = new OfxExternalMerger(f);
			Document doc = exMerger.mergeToDoc();
			ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
			
			JaxbUtil.save(dstFile, ofxDoc, nsPrefixMapper, true);
		}
		catch (FileNotFoundException e)
		{
			logger.warn("OfxPreprocessorException");//TODO new exception
			e.printStackTrace();
		}
	}
	
	private void phaseTemplate(File dWorking, File dirWikiTemplate, File dirOfxTemplate, Phase phaseLoad, Phase phaseSave) throws OfxInternalProcessingException, OfxConfigurationException
	{
		File f = new File(dWorking,getPhaseXmlFileName(phaseLoad));
		
		WikiInlineProcessor wikiInlineProcessor = new WikiInlineProcessor(cmp);
		
		try
		{
			ofxDoc = (Ofxdoc)JaxbUtil.loadJAXB(f.getAbsolutePath(), Ofxdoc.class);
			WikiTemplateCorrector templateCorrector = new WikiTemplateCorrector();
			templateCorrector.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
			ofxDoc = templateCorrector.correctTemplateInjections(ofxDoc);
			JaxbUtil.save(new File(dWorking,getPhaseXmlFileName(phaseSave)), ofxDoc, nsPrefixMapper, true);
			
			WikiTemplateProcessor wtp = new WikiTemplateProcessor(wikiInlineProcessor,xmlPreProcessor.getWiki().getTemplates());
			wtp.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
			wtp.setDirectory(WikiProcessor.WikiDir.ofxTemplate, dirOfxTemplate);
			wtp.process();
		}
		catch (FileNotFoundException e){throw new OfxInternalProcessingException(e.getMessage());}
	}
	
	private void phaseWikiExternalIntegrator(File dWorking,String wikiXmlDir) throws OfxAuthoringException
	{		
		WikiExternalIntegrator wikiExIntegrator = new WikiExternalIntegrator(wikiXmlDir);
		wikiExIntegrator.integrateWikiAsExternal(ofxDoc);
		ofxDoc = wikiExIntegrator.getResult();
		wikiQueries = wikiExIntegrator.getWikiQueries();
		
		JaxbUtil.save(new File(dWorking,getPhaseXmlFileName(Phase.wikiIntegrate)), ofxDoc, true);
	}
	
	private void phaseWikiContentFetcher(File dWorking, boolean netConnection, File dirWikiPlain) throws OfxRenderingException, OfxInternalProcessingException, OfxAuthoringException
	{
		File fContents = new File(dWorking,"contens.xml");
		if(netConnection)
		{
			if(wikiQueries.isSetContent())
			{
				WikiBotFactory wbf = new WikiBotFactory(cmp.getPreprocessor().getWiki().getServers());
				
				//TODO Integrate AUTH in WBF
				logger.warn("NYI AUTH");
//				wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
//				wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
				
				WikiContentFetcher contentFetcher = new WikiContentFetcher(wbf);
				contentFetcher.setDirectories(null,dirWikiPlain);
				try
				{
					contentFetcher.process(wikiQueries);
					JaxbUtil.save(fContents, wikiQueries, nsPrefixMapper, true);
				}
				catch (OfxWikiException e)
				{
					throw new OfxRenderingException(e.getMessage());
				}
			}
		}
		else
		{
			try {wikiQueries = (Contents)JaxbUtil.loadJAXB(fContents.getAbsolutePath(), Contents.class);}
			catch (FileNotFoundException e) {throw new OfxInternalProcessingException(e.getMessage());}
		}
	}
	
	private void phaseWikiProcessing(File dWorking, String wikiPlainDir, String wikiMarkupDir, String wikiModelDir, String xhtmlReplace, String xhtmlFinal, String xmlOfx, File dirWikiTemplate) throws OfxConfigurationException, OfxWikiException, OfxAuthoringException, OfxInternalProcessingException
	{	
		File dirWikiPlain = createDir(dWorking, wikiPlainDir);
		File dirWikiMarkup = createDir(dWorking, wikiMarkupDir);
		File dirWikiModel = createDir(dWorking, wikiModelDir);
		File dirXhtmlReplace = createDir(dWorking, xhtmlReplace);
		File dirXhtmlFinal = createDir(dWorking, xhtmlFinal);
		File dirXmlOfx = createDir(dWorking, xmlOfx);
		
		MarkupProcessor mpXml = xmlPreProcessor.getWiki().getMarkupProcessor();
		XhtmlProcessor xpXml = xmlPreProcessor.getWiki().getXhtmlProcessor();
		
		List<WikiProcessor> lWikiProcessors = new ArrayList<WikiProcessor>();
		
		WikiProcessor wpMarkup = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections());
		wpMarkup.setDirectories(dirWikiPlain, dirWikiMarkup);
		wpMarkup.setDirectory(WikiProcessor.WikiDir.wikiTemplate, dirWikiTemplate);
		lWikiProcessors.add(wpMarkup);
		
		WikiProcessor wpModel = new WikiModelProcessor();
		wpModel.setDirectories(dirWikiMarkup, dirWikiModel);
		lWikiProcessors.add(wpModel);
		
		WikiProcessor wpXhtmlR = new XhtmlReplaceProcessor(xpXml.getReplacements());
		wpXhtmlR.setDirectories(dirWikiModel, dirXhtmlReplace);
		lWikiProcessors.add(wpXhtmlR);
		
		WikiProcessor wpXhtmlF = new XhtmlFinalProcessor();
		wpXhtmlF.setDirectories(dirXhtmlReplace, dirXhtmlFinal);
		lWikiProcessors.add(wpXhtmlF);
		
		for(WikiProcessor wikiProcessor : lWikiProcessors)
		{
			wikiProcessor.process(wikiQueries);
		}	

		WikiXmlProcessor ofxP = new WikiXmlProcessor();
		ofxP.setDirectories(dirXhtmlFinal, dirXmlOfx);
		ofxP.process(wikiQueries);
	}
	
	private File createDir(File dWorking, String dirName)
	{
		File dir = new File(dWorking,dirName);
		if(!dir.exists()){dir.mkdir();}
		return dir;
	}
	
	private String getPhaseXmlFileName(Phase phase)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(phase.ordinal()+1);
		sb.append("-").append(phase.toString()).append(".xml");
		return sb.toString();
	}
}