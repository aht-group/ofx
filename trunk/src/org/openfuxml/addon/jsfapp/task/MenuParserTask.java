package org.openfuxml.addon.jsfapp.task;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.openfuxml.addon.jsfapp.factory.MenuFactory;

import de.kisner.util.LoggerInit;

public class MenuParserTask extends Task
{		
	private String htmlToc,xmlToc;
	
	private boolean useLog4j;

	public MenuParserTask()
	{
		useLog4j=false;
	}
	
    public void execute() throws BuildException
    {
    	checkParameter();
    	
    	File fHtmlToc = new File(htmlToc);
    	File fXmlToc = new File(xmlToc);
    	
    	MenuFactory mf = new MenuFactory();
    	mf.parse(fHtmlToc);
    	mf.save(fXmlToc);
    	
    }
    
    private void checkParameter()
    {    	
    	if(xmlToc==null){throw new BuildException("xmlToc must be specified.");}
    	
    	if(htmlToc==null){throw new BuildException("htmlToc must be specified.");}
    	File fHtmlToc = new File(htmlToc);
    	if(!fHtmlToc.exists()){throw new BuildException(htmlToc+" does not exist.");}
    }
    

	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	public void setHtmlToc(String htmlToc) {this.htmlToc = htmlToc;}
	public void setXmlToc(String xmlToc) {this.xmlToc = xmlToc;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		MenuParserTask mpt = new MenuParserTask();
		mpt.setUseLog4j(true);
		mpt.setHtmlToc(args[0]);
		mpt.setXmlToc(args[1]);
		mpt.execute();
	}
}