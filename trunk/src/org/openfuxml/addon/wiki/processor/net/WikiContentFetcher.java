package org.openfuxml.addon.wiki.processor.net;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.util.WikiBotFactory;
import org.openfuxml.renderer.latex.util.TxtWriter;

public class WikiContentFetcher
{
	static Log logger = LogFactory.getLog(WikiContentFetcher.class);
	
	private WikiBotFactory wbf;
	private TxtWriter txtWriter;

	public WikiContentFetcher(WikiBotFactory wbf)
	{
		this.wbf=wbf;
		txtWriter = new TxtWriter();
		setTargetDir(new File("."));
	}

	public void fetch(List<Content> lContent)
	{
		logger.debug("Fetching "+lContent.size()+" content elements");
		
		for(Content content : lContent)
		{
			setTargetFile(content.getSource());
			if(content.isSetPage()){fetchPage(content.getPage());}
		}
	}
	
	private void fetchPage(Page page)
	{
		WikiPageFetcher wpf = new WikiPageFetcher(wbf.getBot());
		wpf.fetchText(page.getName());
		wpf.save(txtWriter);
	}
	
	private void setTargetFile(String ofxSource)
	{
		int indexFrom = ofxSource.lastIndexOf("/");
		int indexTo = ofxSource.lastIndexOf(".xml");
		txtWriter.setTargetFile(ofxSource.substring(indexFrom+1,indexTo)+".txt");
	}
	
	public void setTargetDir(File dstDir)
	{
		txtWriter.setTargetDir(dstDir);
	}
}
