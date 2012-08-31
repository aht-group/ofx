package org.openfuxml.addon.jsf.factory;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.xml.addon.jsf.tld.Component;
import org.openfuxml.xml.addon.jsf.tld.FaceletTaglib;
import org.openfuxml.xml.addon.jsf.tld.FacesConfig;
import org.openfuxml.xml.addon.jsf.tld.Metatag;
import org.openfuxml.xml.addon.jsf.tld.Renderer;
import org.openfuxml.xml.addon.jsf.tld.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaglibFactoryTask extends Task
{
	final static Logger logger = LoggerFactory.getLogger(TaglibFactoryTask.class);
	
	public static String ftNsDefault = "http://java.sun.com/xml/ns/javaee";
	public static String ftNsXsi = "http://www.w3.org/2001/XMLSchema-instance";
	public static String ftAttSchemaLocation = "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facelettaglibary_2_0.xsd";
	public static String ftAttVersion = "2.0";
	
	private String tldConfig, tagRoot, tld, facesConfig, l4jFile;

	private boolean useLog4j;

	private FaceletTaglib taglib;
	private FacesConfig facesconfig;
	
	private Configuration config;
	
	public TaglibFactoryTask()
	{
		setUseLog4j(false);
	}
	
	private void initLog4j()
	{
		int index = l4jFile.lastIndexOf("/");
		
		String path = ".";
		String file = l4jFile;
		
		if(index>=0)
		{
			path = l4jFile.substring(0, index);
			file = l4jFile.substring(index+1, l4jFile.length());
		}
		
		LoggerInit loggerInit = new LoggerInit(file);	
			loggerInit.addAltPath(path);
			loggerInit.init();	
			
		setUseLog4j(true);
	}
	
    public void execute() throws BuildException
    {
    	if(l4jFile==null){setUseLog4j(false);}
    	else{initLog4j();}
    
    	checkParameter();
    	
    	ConfigLoader.add(tldConfig);
		config = ConfigLoader.init();
		
    	String fileName = config.getString("taglib");
    	try
    	{
			taglib = (FaceletTaglib)JaxbUtil.loadJAXB(tagRoot+"/"+fileName, FaceletTaglib.class);
		}
    	catch (FileNotFoundException e) {throw new BuildException(e.getMessage());}

    	String msg = "Using Taglib: "+tagRoot+"/"+fileName;
		if(useLog4j){logger.debug(msg);}else{System.out.println(msg);}
    	
		facesconfig = new FacesConfig();
		FacesConfig.RenderKit rk = new FacesConfig.RenderKit();
		facesconfig.setRenderKit(rk);
		
    	try
    	{
			addTagElements();
	    	writeTld();
	    	writeFc();
		}
    	catch (FileNotFoundException e) {throw new BuildException(e.getMessage());}

    }
    
    private void writeTld()
    {
    	Namespace ns = Namespace.getNamespace(ftNsDefault);
    	Namespace nsXsi = Namespace.getNamespace("xsi",ftNsXsi);
    	
    	Attribute attVersion = new Attribute("version", ftAttVersion);
    	Attribute attSchemaLocation = new Attribute("schemaLocation", ftAttSchemaLocation, nsXsi);
    	
    	Document doc = JaxbUtil.toDocument(taglib);
    	doc.getRootElement().setNamespace(ns);
    	doc.getRootElement().addNamespaceDeclaration(nsXsi);
    	doc.getRootElement().setAttribute(attVersion);
    	doc.getRootElement().setAttribute(attSchemaLocation);
    	
    	doc.setRootElement(JDomUtil.setNameSpaceRecursive(doc.getRootElement(),ns));
    	
    	File f = new File(tld);
    	JDomUtil.save(doc, f, Format.getPrettyFormat());
    	
    	String msg = "Taglib written to "+f.getAbsolutePath();
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
    }
    
    private void writeFc()
    {
    	Namespace ns = Namespace.getNamespace("http://java.sun.com/xml/ns/javaee");
    	Namespace nsXi = Namespace.getNamespace("xi", "http://www.w3.org/2001/XInclude");
    	Namespace nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
    	
    	Attribute attVersion = new Attribute("version", "1.2");
    	Attribute attSchemaLocation = new Attribute("schemaLocation", "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facesconfig_1_2.xsd",nsXsi);
    	
    	
    	Document doc = JaxbUtil.toDocument(facesconfig);
    	doc.getRootElement().setNamespace(ns);
    	doc.getRootElement().addNamespaceDeclaration(nsXsi);
    	doc.getRootElement().addNamespaceDeclaration(nsXi);
    	doc.getRootElement().setAttribute(attVersion);
    	doc.getRootElement().setAttribute(attSchemaLocation);
    	
    	doc.setRootElement(JDomUtil.setNameSpaceRecursive(doc.getRootElement(),ns));
    	
    	File f = new File(facesConfig);
    	JDomUtil.save(doc, f, Format.getPrettyFormat());
    	
    	String msg = "Facesconfig written to "+f.getAbsolutePath();
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
    }
	
	private void addTagElements() throws FileNotFoundException
	{	
		String xPath = "tag";
		int numberTranslations = config.getStringArray(xPath).length;
		String msg = "Found "+numberTranslations+" Tags in "+tldConfig;
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
		for(int i=1;i<=numberTranslations;i++)
		{
			String fileName = config.getString(xPath+"["+i+"]");
			String dirName = config.getString(xPath+"["+i+"]/@dir");
			Metatag metatag = (Metatag)JaxbUtil.loadJAXB(tagRoot+"/"+dirName+"/"+fileName, Metatag.class);
			TaglibFactoryTask.fillDescription(metatag.getTag(),config);
			
			taglib.getTag().add(metatag.getTag());
			
			Component component = metatag.getComponent();
			if(component!=null){facesconfig.getComponent().add(component);}
			
			Renderer renderer = metatag.getRenderer();
			if(renderer!=null){facesconfig.getRenderKit().getRenderer().add(renderer);}
		}
	}
	
	public static void fillDescription(Tag tag, Configuration config)
	{
		for(org.openfuxml.xml.addon.jsf.tld.Attribute att : tag.getAttribute())
		{
			logger.debug("Att"+ tag.getName()+" "+att.getName());
			logger.warn("Description is disabled"); //TODO Check description
/*			if(att.getDescription()!=null && att.getDescription().equals("@@@"))
			{
				String description = config.getString("descriptions/description[@name='"+att.getName()+"']");
				
				att.setDescription(description);
			}
*/		}
	}
	
	public void setTldConfig(String tldConfig) {this.tldConfig = tldConfig;}
	public void setTagRoot(String tagRoot) {this.tagRoot = tagRoot;}
	
	public void setTld(String tld) {this.tld = tld;}
	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	public void setFacesConfig(String facesConfig) {this.facesConfig = facesConfig;}
	public void setL4jFile(String l4jFile) {this.l4jFile = l4jFile;}
	
	public void checkParameter() throws BuildException
    {
    	if(tldConfig==null){throw new BuildException("fileTldConfig must be specified.");}
    	File fTldConfig = new File(tldConfig);
    	if(!fTldConfig.exists()){throw new BuildException("File "+tldConfig+" does not exist.");}
    	if(!fTldConfig.isFile()){throw new BuildException(fTldConfig+" not a file.");}
        	
    	if(tagRoot==null){throw new BuildException("tagRoot must be specified.");}
    	File fTagRoot = new File(tagRoot);
    	if(!fTagRoot.exists()){throw new BuildException("Directory "+tagRoot+" does not exist.");}
    	if(!fTagRoot.isDirectory()){throw new BuildException(tagRoot+" not a directory.");}
    	
    	if(tld==null){throw new BuildException("tld must be specified.");}
    	File fTld = new File(tld);
    	if(!fTld.getParentFile().exists()){throw new BuildException("Parent Directory for "+tld+" does not exist.");}
    	
    	if(facesConfig==null){throw new BuildException("facesConfig must be specified.");}
    	File fFacesConfig = new File(facesConfig);
    	if(!fFacesConfig.getParentFile().exists()){throw new BuildException("Parent Directory for "+facesConfig+" does not exist.");}
    }
}