package org.openfuxml.renderer;

import java.io.File;
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
import org.openfuxml.addon.wiki.processor.net.WikiContentFetcher;
import org.openfuxml.addon.wiki.processor.pre.WikiExternalIntegrator;
import org.openfuxml.addon.wiki.util.WikiBotFactory;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.renderer.data.jaxb.Merge;
import org.openfuxml.renderer.data.jaxb.Wiki;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;
import org.openfuxml.util.xml.CmpJaxbXpathLoader;

public class OfxRenderer
{
	static Log logger = LogFactory.getLog(OfxRenderer.class);
		
	public static enum Phase {iniMerge,wikiIntegrate};
	
	private Configuration config;
	private Cmp cmp;
	
	private Ofxdoc ofxDoc;
	private List<Content> lWikiQueries;
	
	int phaseCounter;
	String tmpDir;
	
	public OfxRenderer(Configuration config)
	{
		this.config=config;
		phaseCounter = 1;
	}
	
	private void readConfig(String fName)
	{
		cmp = (Cmp)JaxbUtil.loadJAXB(fName, Cmp.class);
		tmpDir = config.getString("ofx.dir.tmp");
	}
	
	public void chain()
	{
		readConfig(config.getString("ofx.xml.cmp"));
		phaseMergeInitial(config.getString("ofx.xml.root"),tmpDir);
		phaseWikiExternalIntegrator(tmpDir, "wiki-plain");
//		phaseWikiFetcher(tmpDir);
		phaseWikiProcessing(tmpDir);
	}
	
	private void phaseMergeInitial(String rootFileName, String tmpDir)
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
	}
	
	private void phaseWikiExternalIntegrator(String tmpDir, String subDir)
	{
		File dirWiki = new File(tmpDir,subDir);
		if(!dirWiki.exists()){dirWiki.mkdir();}
		
		WikiExternalIntegrator wikiExIntegrator = new WikiExternalIntegrator(subDir);
		wikiExIntegrator.integrateWikiAsExternal(ofxDoc);
		ofxDoc = wikiExIntegrator.getResult();
		lWikiQueries = wikiExIntegrator.getWikiQueries();
		
		JaxbUtil.save(new File(tmpDir,getPhaseXmlFileName(Phase.wikiIntegrate)), ofxDoc, true);
	}
	
	private void phaseWikiFetcher(String tmpDir)
	{
		if(lWikiQueries!=null)
		{
			WikiBotFactory wbf = new WikiBotFactory();
			wbf.setUrl(config.getString("wiki.url"));
			wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
			wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
			
			WikiContentFetcher contentFetcher = new WikiContentFetcher(wbf);
			contentFetcher.setTargetDir(tmpDir);
			contentFetcher.fetch(lWikiQueries);
		}
	}
	
	private void phaseWikiProcessing(String tmpDir)
	{
		JaxbUtil.debug(cmp);
	}
	
	private String getPhaseXmlFileName(Phase phase)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(phaseCounter);phaseCounter++;
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