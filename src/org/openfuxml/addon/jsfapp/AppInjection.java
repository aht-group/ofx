package org.openfuxml.addon.jsfapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.RecursiveFileFinder;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.XpathUtil;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Task;
import org.jdom.Document;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;

public class AppInjection extends Task
{	
	private static Logger logger = Logger.getLogger(AppInjection.class);
	
	private boolean useLog4j;
	private RecursiveFileFinder rfi;
	private Ofxinjections ofxIs;
	
	public AppInjection(Ofxinjections ofxIs)
	{
		this(ofxIs,false);
	}
	
	public AppInjection(Ofxinjections ofxIs,boolean useLog4j)
	{
		this.useLog4j=useLog4j;
		this.ofxIs=ofxIs;
		IOFileFilter df = HiddenFileFilter.VISIBLE;
		IOFileFilter ff = FileFilterUtils.suffixFileFilter(".html");
	
		rfi = new RecursiveFileFinder(df,ff);
	}
	
	public void inject(File htmlDir)
	{
		List<File> htmlFile = new ArrayList<File>();
		try {htmlFile = rfi.find(htmlDir);}
		catch (IOException e)
		{
			if(useLog4j){logger.debug(e.getMessage());}
			else{System.err.println(e.getMessage());}
		}
		for(File f : htmlFile)
		{
			
			Document docHtml = JDomUtil.load(f,"ISO-8859-1");
			for(Ofxinjection ofxI : ofxIs.getOfxinjection())
			{
				List<?> l = XpathUtil.processXPath("//div[@id=\""+ofxI.getId()+"\"]",docHtml);
				for(Object o : l )
				{
					logger.debug(f.getAbsoluteFile());
					logger.debug(o.getClass().getSimpleName());
				}
			}
		}
	}
}