package org.openfuxml.addon.jsfapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.io.RecursiveFileFinder;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.exception.JDomUtilException;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Task;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.xpath.XPath;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;
import org.openfuxml.addon.jsfapp.factory.JsfJspxFactory;
import org.openfuxml.addon.jsfapp.factory.NsFactory;

public class AppInjection extends Task
{	
	final static Logger logger = LoggerFactory.getLogger(AppInjection.class);
	
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
			File fJsf = getJsfFile(f);
			try
			{
				logger.debug(f.getAbsolutePath());
				Document docJsf = createJsf(JDomUtil.load(f,"ISO-8859-1"));
				addJsfView(docJsf);
				
				if(ofxIs.isSetGenericinjection())
				{
					for(Ofxinjection ofxI : ofxIs.getGenericinjection().getOfxinjection())
					{
						genericInjection(ofxI,docJsf);
					}
				}
				
				idsearch:
				for(Ofxinjection ofxI : ofxIs.getOfxinjection())
				{
					try
					{
						XPath xpath = XPath.newInstance("//html:div[@id=\""+ofxI.getId()+"\"]");
						for(Namespace ns : NsFactory.getNs("html")){xpath.addNamespace(ns);}
						Element element = (Element)xpath.selectSingleNode(docJsf);
						
						if(element!=null)
						{
							StringBuffer sb = new StringBuffer();
							if(ofxI.isSetIframe())
							{
								sb.append("iframe: ");
								injectIframe(ofxI, element);
							}
							else if(ofxI.isSetJsf())
							{
								sb.append("jsf: ");
								injectJsf(ofxI, docJsf);
							}
							sb.append(f.getName());
							if(useLog4j){logger.debug(sb);}
							else{System.out.println(sb.toString());}
							break idsearch;
						}
					}
					catch (JDOMException e) {logger.error("",e);}
				}	
				JDomUtil.save(docJsf, fJsf, Format.getRawFormat(),"ISO-8859-1");
			}
			catch (JDomUtilException e)
			{
				if(useLog4j){logger.error(e.getMessage());}else{System.err.println(e.getMessage());}
			}	
		}
	}
	
	private void injectIframe(Ofxinjection ofxI, Element element)
	{
		logger.debug("Injection iframe");
		Document d = JaxbUtil.toDocument(ofxI.getIframe());
		Element eIframe = d.getRootElement();
		eIframe.detach();
		eIframe.setNamespace(null);
		element.addContent(eIframe);
	}
	
	private void injectJsf(Ofxinjection ofxI, Document docJsf)
	{
		try
		{
			XPath xpathNewJsf = XPath.newInstance("//html:div[@id='lehrinhalt']");
			for(Namespace ns : NsFactory.getNs("html")){xpathNewJsf.addNamespace(ns);}
			
			Element eNewJsf = (Element)xpathNewJsf.selectSingleNode(docJsf);
			
			File fOrigJsf = new File(jsfDir,ofxI.getJsf().getJsfile());
			Document docOrigJsf = JDomUtil.load(fOrigJsf);
			
			XPath xpathOrigJsf = XPath.newInstance("//f:view");
			for(Namespace ns : NsFactory.getNs("f")){xpathOrigJsf.addNamespace(ns);}
			Element eOrigJsf = (Element)xpathOrigJsf.selectSingleNode(docOrigJsf);

			eNewJsf.removeContent();
			eNewJsf.addContent(eOrigJsf.cloneContent());			
		}
		catch (JDOMException e) {logger.error("",e);}
	}
	
	private File getJsfFile(File fHtml)
	{
		File dir = fHtml.getParentFile();
		String name = fHtml.getName();
		name=name.substring(0, name.lastIndexOf(".html"))+".jspx";
		File fJsf = new File(dir,name);
		return fJsf;
	}
	
	private Document createJsf(Document docHtml)
	{
		Namespace htmlNs  = Namespace.getNamespace("http://www.w3.org/1999/xhtml");
		Element html = (Element)docHtml.getRootElement().clone();
		JDomUtil.setNameSpaceRecursive(html, htmlNs);
		Document docJsf = JsfJspxFactory.createDOMjspx();
		docJsf.getRootElement().addContent(html);		
		return docJsf;
	}
	
	@SuppressWarnings("unchecked")
	private void addJsfView(Document docJsf)
	{
		try
		{
			XPath xpath = XPath.newInstance("//html:body");
			for(Namespace ns : NsFactory.getNs("html","jsp")){xpath.addNamespace(ns);}
			
			Element eBody = (Element)xpath.selectSingleNode(docJsf);
			
			if(eBody!=null)
			{
				List<Element> l = eBody.removeContent();
				Element eView = new Element("view",NsFactory.getSingelNs("f"));
				eView.setAttribute("locale", ofxIs.getLocale());
				eView.addContent(l);
				eBody.addContent(eView);
			}
		}
		catch (JDOMException e) {logger.error("",e);}
	}
	
	private void genericInjection(Ofxinjection ofxI, Document docJsf)
	{
		try
		{
			XPath xpathResultJsf = XPath.newInstance("//html:div[@id='navi1']");
			xpathResultJsf.addNamespace(NsFactory.getSingelNs("html"));
			Element element = (Element)xpathResultJsf.selectSingleNode(docJsf);
			
			if(element!=null)
			{
				element.removeContent();
				File fOrigJsf = new File(jsfDir,ofxI.getJsf().getJsfile());
				Document docOrigJsf = JDomUtil.load(fOrigJsf);
				XPath xpath = XPath.newInstance(ofxI.getXsrc());
				for(Namespace ns : NsFactory.getNs("h","jsp")){xpath.addNamespace(ns);}
				
				Element eMenu = (Element)xpath.selectSingleNode(docOrigJsf);
				eMenu.detach();
				element.addContent(eMenu);
			}
		}
		catch (JDOMException e) {logger.error("",e);}
	}
}