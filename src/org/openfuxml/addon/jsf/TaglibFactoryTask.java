package org.openfuxml.addon.jsf;

import java.io.File;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.addon.jsf.data.jaxb.Component;
import org.openfuxml.addon.jsf.data.jaxb.FacesConfig;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.Renderer;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

import de.kisner.util.LoggerInit;

public class TaglibFactoryTask extends Task
{
	private static Logger logger = Logger.getLogger(TaglibFactoryTask.class); 
	
	private String tagConfig, xPathPrefix, tagBaseDir, tldFile, fcFile;

	private boolean useLog4j;

	private Taglib taglib;
	private FacesConfig facesconfig;
	
	private Configuration config;
	
	public TaglibFactoryTask()
	{
		setUseLog4j(false);
	}
	
    public void execute() throws BuildException
    {
    	ConfigLoader.add(tagConfig);
		config = ConfigLoader.init();
		
    	String fileName = config.getString("taglib");
    	taglib = (Taglib)JaxbUtil.loadJAXB(tagBaseDir+"/"+fileName, Taglib.class);
    	String msg = "Taglib: "+tagBaseDir+"/"+fileName;
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
    	
		facesconfig = new FacesConfig();
		FacesConfig.RenderKit rk = new FacesConfig.RenderKit();
		facesconfig.setRenderKit(rk);
		
    	addTagElements();
    	writeTld();
    	writeFc();
    }
    
    private void writeTld()
    {
    	Namespace ns = Namespace.getNamespace("http://java.sun.com/xml/ns/javaee");
    	Namespace ns1 = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
    	
    	Attribute attVersion = new Attribute("version", "2.1");
    	Attribute attSchemaLocation = new Attribute("schemaLocation", "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd",ns1);
    	
    	Document doc = JaxbUtil.toDocument(taglib);
    	doc.getRootElement().setNamespace(ns);
    	doc.getRootElement().addNamespaceDeclaration(ns1);
    	doc.getRootElement().setAttribute(attVersion);
    	doc.getRootElement().setAttribute(attSchemaLocation);
    	
    	doc.setRootElement(JDomUtil.unsetNameSpace(doc.getRootElement(),ns));
    	
    	File f = new File(tldFile);
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
    	
    	doc.setRootElement(JDomUtil.unsetNameSpace(doc.getRootElement(),ns));
    	
    	File f = new File(fcFile);
    	JDomUtil.save(doc, f, Format.getPrettyFormat());
    	
    	String msg = "Facesconfig written to "+f.getAbsolutePath();
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
    }
	
	private void addTagElements()
	{	
		int numberTranslations = config.getStringArray(xPathPrefix).length;
		String msg = "Found "+numberTranslations+" Tags in "+tagConfig;
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
		for(int i=1;i<=numberTranslations;i++)
		{
			String fileName = config.getString(xPathPrefix+"["+i+"]");
			String dirName = config.getString(xPathPrefix+"["+i+"]/@dir");
			Metatag metatag = (Metatag)JaxbUtil.loadJAXB(tagBaseDir+"/"+dirName+"/"+fileName, Metatag.class);
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
		for(org.openfuxml.addon.jsf.data.jaxb.Attribute att : tag.getAttribute())
		{
			logger.debug("Att"+ tag.getName()+" "+att.getName());
	
			if(att.getDescription()!=null && att.getDescription().equals("@@@"))
			{
				String description = config.getString("descriptions/description[@name='"+att.getName()+"']");
				att.setDescription(description);
			}
		}
	}
	
	public void setTagConfig(String tagConfig) {this.tagConfig = tagConfig;}
	public void setxPathPrefix(String xPathPrefix) {this.xPathPrefix = xPathPrefix;}
	public void setTagBaseDir(String tagBaseDir) {this.tagBaseDir = tagBaseDir;}
	public void setTldFile(String tldFile) {this.tldFile = tldFile;}
	public void setUseLog4j(boolean useLog4j) {JaxbUtil.useLog4j=useLog4j;this.useLog4j = useLog4j;}
	public void setFcFile(String fcFile) {this.fcFile = fcFile;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TaglibFactoryTask jtf = new TaglibFactoryTask();
		jtf.setUseLog4j(true);
		jtf.setTagConfig(args[0]);
		jtf.setTagBaseDir(args[1]);
		jtf.setxPathPrefix(args[2]);
		jtf.setTldFile(args[3]);
		jtf.setFcFile(args[4]);
		jtf.execute();
	}
}