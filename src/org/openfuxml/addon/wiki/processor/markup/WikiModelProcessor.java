package org.openfuxml.addon.wiki.processor.markup;

import info.bliki.wiki.model.WikiModel;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;

public class WikiModelProcessor
{
	static Log logger = LogFactory.getLog(WikiModelProcessor.class);
	
	private File wikiMarkupDir,wikiModelDir;
	
	
	public WikiModelProcessor()
	{

	}
	
	public void setDirectories(File wikiMarkupDir, File wikiModelDir)
	{
		this.wikiMarkupDir=wikiMarkupDir;
		this.wikiModelDir=wikiModelDir;
		logger.debug("Directory Markup: "+wikiMarkupDir.getAbsolutePath());
		logger.debug("Directory Model:  "+wikiModelDir.getAbsolutePath());
	}
	
	public void process(List<Content> lContent)
	{
		for(Content content : lContent)
		{
			String fNameMarkup = WikiContentIO.getFileFromSource(content.getSource(), "txt");
			String fNameModel = WikiContentIO.getFileFromSource(content.getSource(), "xhtml");
			String txtMarkup = WikiContentIO.loadTxt(wikiMarkupDir, fNameMarkup);
			String result = process(txtMarkup);
			WikiContentIO.writeTxt(wikiModelDir, fNameModel, result);
		}
	}
	
	private String process(String txtMarkup)
	{
		logger.warn("Check image and title");
		String wikiImage="file:///c:/temp/${image}";
		String wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(txtMarkup);
        return xHtml;
	}
}