package org.openfuxml.addon.jsfapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.RecursiveFileFinder;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.XpathUtil;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Task;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;
import org.openfuxml.addon.jsfapp.factory.JsfJspxFactory;

public class AppInjection extends Task
{	
	private static Logger logger = Logger.getLogger(AppInjection.class);
	
	private boolean useLog4j;
	private RecursiveFileFinder rfi;
	private Ofxinjections ofxIs;
	private File jsfDir;
	
	public AppInjection(Ofxinjections ofxIs, File jsfDir)
	{
		this(ofxIs,jsfDir,false);
	}
	
	public AppInjection(Ofxinjections ofxIs,File jsfDir, boolean useLog4j)
	{
		this.ofxIs=ofxIs;
		this.jsfDir=jsfDir;
		this.useLog4j=useLog4j;
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
			idsearch:
			for(Ofxinjection ofxI : ofxIs.getOfxinjection())
			{
				List<?> l = XpathUtil.processXPath("//div[@id=\""+ofxI.getId()+"\"]",docHtml);
				if(l.size()>0)
				{
					StringBuffer sb = new StringBuffer();
					Element element = (Element)l.get(0);
					if(ofxI.isSetIframe())
					{
						sb.append("iframe: ");
						injectIframe(ofxI, element);
						JDomUtil.save(docHtml, f, Format.getRawFormat(),"ISO-8859-1");
					}
					else if(ofxI.isSetJsf())
					{
						sb.append("jsf: ");
						injectJsf(ofxI, docHtml,f.getParentFile());
					}
					sb.append(f.getName());
					if(useLog4j){logger.debug(sb);}
					else{System.out.println(sb.toString());}
					break idsearch;
				}
			}
		}
	}
	
	private void injectIframe(Ofxinjection ofxI, Element element)
	{
		Document d = JaxbUtil.toDocument(ofxI.getIframe());
		Element eIframe = d.getRootElement();
		eIframe.detach();
		eIframe.setNamespace(null);
		element.addContent(eIframe);
	}
	
	private void injectJsf(Ofxinjection ofxI, Document docHtml, File htmlDir)
	{
		Document doc = JsfJspxFactory.createDOMjspx();
		
		Element rootJsf = doc.getRootElement();
		Element rootHtml = docHtml.getRootElement();
		
		rootHtml.detach();
		rootJsf.addContent(rootHtml);
		
		Element eHtmlLehrinhalt = XpathUtil.processSingleXPath("//div[@id='lehrinhalt']",doc);
		eHtmlLehrinhalt.removeContent();
		
		File fOrigJsf = new File(jsfDir,ofxI.getJsf().getJsfile());
		Document docOrigJsf = JDomUtil.load(fOrigJsf);
		Element eJsfLehrinhalt = XpathUtil.processSingleXPath("//div[@id='lehrinhalt']",docOrigJsf);
		
		eHtmlLehrinhalt.addContent(eJsfLehrinhalt.cloneContent());
	
		File fInjectJsf = new File(htmlDir,ofxI.getId()+".jspx");
		JDomUtil.save(doc, fInjectJsf, Format.getRawFormat(),"ISO-8859-1");

	}
}