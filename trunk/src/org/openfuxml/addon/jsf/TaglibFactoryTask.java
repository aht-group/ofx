package org.openfuxml.addon.jsf;

import java.io.File;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.util.JDomUtil;
import net.sf.exlp.util.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

import de.kisner.util.LoggerInit;

public class TaglibFactoryTask extends Task
{
	private static Logger logger = Logger.getLogger(TaglibFactoryTask.class); 
	
	private String tagConfig, xPathPrefix, tagBaseDir, tldFile;
	private boolean useLog4j;

	private Taglib taglib;
	private Configuration config;
	
	public TaglibFactoryTask()
	{
		useLog4j=false;
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
    	
    	addTags();
    	write();
    }
    
    private void write()
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
    	
    	String msg = "Written to "+f.getAbsolutePath();
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
    }
	
	public void addTags()
	{	
		int numberTranslations = config.getStringArray(xPathPrefix).length;
		String msg = "Found "+numberTranslations+" Tags in "+tagConfig;
		if(useLog4j){logger.debug(msg);}
		else{System.out.println(msg);}
		for(int i=1;i<=numberTranslations;i++)
		{
			String fileName = config.getString(xPathPrefix+"["+i+"]");
			String dirName = config.getString(xPathPrefix+"["+i+"]/@dir");
			Tag tag = (Tag)JaxbUtil.loadJAXB(tagBaseDir+"/"+dirName+"/"+fileName, Tag.class);
			taglib.getTag().add(tag);
		}
	}
	
	public void setTagConfig(String tagConfig) {this.tagConfig = tagConfig;}
	public void setxPathPrefix(String xPathPrefix) {this.xPathPrefix = xPathPrefix;}
	public void setTagBaseDir(String tagBaseDir) {this.tagBaseDir = tagBaseDir;}
	public void setTldFile(String tldFile) {this.tldFile = tldFile;}
	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TaglibFactoryTask jtf = new TaglibFactoryTask();
		jtf.setTagConfig(args[0]);
		jtf.setTagBaseDir(args[1]);
		jtf.setxPathPrefix(args[2]);
		jtf.setTldFile(args[3]);
		jtf.execute();
	}
}