package org.openfuxml.renderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.net.WikiContentFetcher;
import org.openfuxml.addon.wiki.processor.ofx.OfxProcessor;
import org.openfuxml.addon.wiki.processor.pre.WikiExternalIntegrator;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.renderer.data.jaxb.Merge;
import org.openfuxml.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.util.TxtWriter;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;
import org.openfuxml.util.xml.CmpJaxbXpathLoader;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class OfxRenderer
{
	static Log logger = LogFactory.getLog(OfxRenderer.class);
		
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge};
	
	private Configuration config;
	private Cmp cmp;
	
	private OfxNsPrefixMapper nsPrefixMapper;
	private Ofxdoc ofxDoc;
	private List<Content> lWikiQueries;
	
	private File tmpDir;
	
	public OfxRenderer(Configuration config)
	{
		this.config=config;
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
	
	private void readConfig(String fNameCmp, String fNameTmp) throws OfxConfigurationException
	{
		try {cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);}
		catch (FileNotFoundException e){throw new OfxConfigurationException("CMP configuration not found: "+fNameCmp);}
		
		tmpDir = new File(fNameTmp);
		if(!tmpDir.exists()){throw new OfxConfigurationException("Temporary directory not available: "+fNameTmp);}
		if(!tmpDir.isDirectory()){throw new OfxConfigurationException("Temporary directory is a file! ("+fNameTmp+")");}
	}

	public void chain() throws OfxConfigurationException
	{
		String fNameCmp = config.getString("ofx.xml.cmp");
		String fNameTmp = config.getString("ofx.dir.tmp");
		String ofxRoot = config.getString("ofx.xml.root");
		
		chain(fNameCmp, fNameTmp, ofxRoot);
	}
	
	public void chain(String fNameCmp, String fNameTmp, String ofxRoot) throws OfxConfigurationException
	{
		String wikiPlainDir = "wikiPlain";
		String wikiMarkupDir = "wikiMarkup";
		String wikiModelDir = "wikiModel";
		String xhtmlReplaceDir = "xhtmlReplace";
		String xhtmlFinalDir = "xhtmlFinal";
		String ofxXmlDir = "ofxXml";
		
		readConfig(fNameCmp,fNameTmp);
		phaseMergeInitial(ofxRoot);
		phaseWikiExternalIntegrator(ofxXmlDir);
//		phaseWikiContentFetcher(wikiPlainDir);
		phaseWikiProcessing(wikiPlainDir,wikiMarkupDir,wikiModelDir,xhtmlReplaceDir,xhtmlFinalDir,ofxXmlDir);
		phaseMerge(fNameTmp, Phase.wikiMerge);
		phaseLatex();
	}
	
	private void phaseMergeInitial(String rootFileName)
	{
		Document doc = JDomUtil.load(new File(rootFileName));
		try
		{
			Merge merge = CmpJaxbXpathLoader.getMerge(cmp.getPreprocessor().getMerge(), Phase.iniMerge.toString());
			
			File f = new File(rootFileName);
			OfxExternalMerger exMerger = new OfxExternalMerger(f);
			doc = exMerger.mergeToDoc();
			
			if(merge.isSetWriteIntermediate() && merge.isWriteIntermediate())
			{
				File fIntermediate = new File(tmpDir,getPhaseXmlFileName(Phase.iniMerge));
				JDomUtil.save(doc, fIntermediate, Format.getPrettyFormat());
			}
		}
		catch (NoSuchElementException e) {logger.debug("No initial merge");}
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		JaxbUtil.save(new File(tmpDir,getPhaseXmlFileName(Phase.wikiIntegrate)), ofxDoc, nsPrefixMapper, true);
	}
	
	private void phaseMerge(String fNameTmp, Phase phase)
	{
		File f = new File(fNameTmp,"2-wikiIntegrate.xml");
		Document doc = JDomUtil.load(f);
		try
		{
			Merge merge = CmpJaxbXpathLoader.getMerge(cmp.getPreprocessor().getMerge(), Phase.iniMerge.toString());
			
			OfxExternalMerger exMerger = new OfxExternalMerger(f);
			doc = exMerger.mergeToDoc();
			
		}
		catch (NoSuchElementException e) {logger.debug("No initial merge");}
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		JaxbUtil.save(new File(tmpDir,getPhaseXmlFileName(phase)), ofxDoc, nsPrefixMapper, true);
	}
	
	private void phaseWikiExternalIntegrator(String wikiXmlDir)
	{		
		WikiExternalIntegrator wikiExIntegrator = new WikiExternalIntegrator(wikiXmlDir);
		wikiExIntegrator.integrateWikiAsExternal(ofxDoc);
		ofxDoc = wikiExIntegrator.getResult();
		lWikiQueries = wikiExIntegrator.getWikiQueries();
		
		JaxbUtil.save(new File(tmpDir,getPhaseXmlFileName(Phase.wikiIntegrate)), ofxDoc, true);
	}
	
	@SuppressWarnings("unused")
	private void phaseWikiContentFetcher(String wikiPlainDir)
	{
		File dirWikiPlain = createDir(wikiPlainDir);
		
		if(lWikiQueries!=null)
		{
			WikiBotFactory wbf = new WikiBotFactory();
			wbf.setUrl(config.getString("wiki.url"));
			wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
			wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
			
			WikiContentFetcher contentFetcher = new WikiContentFetcher(wbf);
			contentFetcher.setTargetDir(dirWikiPlain);
			contentFetcher.fetch(lWikiQueries);
		}
	}
	
	private void phaseWikiProcessing(String wikiPlainDir, String wikiMarkupDir, String wikiModelDir, String xhtmlReplace, String xhtmlFinal, String xmlOfx) throws OfxConfigurationException
	{	
		File dirWikiPlain = createDir(wikiPlainDir);
		File dirWikiMarkup = createDir(wikiMarkupDir);
		File dirWikiModel = createDir(wikiModelDir);
		File dirXhtmlReplace = createDir(xhtmlReplace);
		File dirXhtmlFinal = createDir(xhtmlFinal);
		File dirXmlOfx = createDir(xmlOfx);
		
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		
		WikiMarkupProcessor wMaP = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections());
		wMaP.setDirectories(dirWikiPlain, dirWikiMarkup);
		wMaP.process(lWikiQueries);
		
		WikiModelProcessor wMoP = new WikiModelProcessor();
		wMoP.setDirectories(dirWikiMarkup, dirWikiModel);
		wMoP.process(lWikiQueries);
		
		XhtmlProcessor xpXml = cmp.getPreprocessor().getWiki().getXhtmlProcessor();
		XhtmlReplaceProcessor xhtmlReplaceP = new XhtmlReplaceProcessor(xpXml.getReplacements());
		xhtmlReplaceP.setDirectories(dirWikiModel, dirXhtmlReplace);
		xhtmlReplaceP.process(lWikiQueries);
		
		XhtmlFinalProcessor xhtmlFinalP = new XhtmlFinalProcessor();
		xhtmlFinalP.setDirectories(dirXhtmlReplace, dirXhtmlFinal);
		xhtmlFinalP.process(lWikiQueries);
		
		OfxProcessor ofxP = new OfxProcessor();
		ofxP.setDirectories(dirXhtmlFinal, dirXmlOfx);
		ofxP.process(lWikiQueries);
	}
	
	private void phaseLatex()
	{
		OfxLatexRenderer renderer = new OfxLatexRenderer();
		renderer.render(new File(tmpDir,getPhaseXmlFileName(Phase.wikiMerge)).getAbsolutePath());
		
		File dstDir = new File(config.getString("wiki.latex.dir"));
		
		TxtWriter writer = new TxtWriter();
		writer.setTargetDirFile(dstDir, config.getString("wiki.latex.file"));
//		writer.debug(renderer.getContent());
		writer.writeFile(renderer.getContent());
	}
	
	
	private File createDir(String dirName)
	{
		File dir = new File(tmpDir,dirName);
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
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
			
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
		
		OfxRenderer renderer = new OfxRenderer(config);
		renderer.chain();
	}
}