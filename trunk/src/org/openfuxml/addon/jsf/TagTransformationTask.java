package org.openfuxml.addon.jsf;

import java.io.File;

import net.sf.exlp.io.LoggerInit;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;


public class TagTransformationTask extends Task
{		
	private String outputDir,tagDef,baseDir;
	private String tagDir,docDir;
	private String xslt;

	private int dtdLevel;
	private boolean useLog4j;

	public TagTransformationTask()
	{
		dtdLevel=0;
		useLog4j=false;
	}
	
    public void execute() throws BuildException
    {
    	checkParameter();
    	
    	File fOutputDir = new File(outputDir);
    	File fBaseDir = new File(baseDir);
    	File fDocBase = new File(baseDir,docDir);
    	
    	JsfTagTransformator jtf = new JsfTagTransformator(fOutputDir, fBaseDir, fDocBase, dtdLevel, useLog4j, xslt);
    	jtf.readTags(tagDef,tagDir);
    	jtf.transform();
    }
    
    private void checkParameter()
    {
    	if(outputDir==null){throw new BuildException("targetDir must be specified.");}
    	File fOutputDir = new File(outputDir);
    	if(!fOutputDir.exists()){throw new BuildException(outputDir+" does not exist.");}
    	if(!fOutputDir.isDirectory()){throw new BuildException(outputDir+" not a directory.");}
    	
    	if(baseDir==null){throw new BuildException("tagDir must be specified.");}
    	File fBaseDir = new File(baseDir);
    	if(!fBaseDir.exists()){throw new BuildException(baseDir+" does not exist.");}
    	if(!fBaseDir.isDirectory()){throw new BuildException(baseDir+" not a directory.");}
    	
    	if(tagDef==null){throw new BuildException("tagDef must be specified.");}
    	File fTagDef = new File(tagDef);
    	if(!fTagDef.exists()){throw new BuildException(tagDef+" does not exist.");}
    	
    	if(tagDir==null){throw new BuildException("tagDir must be specified.");}
    	File fTagDir = new File(fBaseDir,tagDir);
    	if(!fTagDir.exists()){throw new BuildException(tagDir+" does not exist.");}
    	if(!fTagDir.isDirectory()){throw new BuildException(tagDir+" not a directory.");}
    	
    	if(docDir==null){throw new BuildException("docDir must be specified.");}
    	File fDocDir = new File(fBaseDir,tagDir);
    	if(!fDocDir.exists()){throw new BuildException(docDir+" does not exist.");}
    	if(!fDocDir.isDirectory()){throw new BuildException(docDir+" not a directory.");}
    }
    
	public void setOutputDir(String outputDir) {this.outputDir = outputDir;}
	public void setDtdLevel(int dtdLevel) {this.dtdLevel = dtdLevel;}
	public void setTagDef(String tagDef) {this.tagDef = tagDef;}
	public void setBaseDir(String tagDir) {this.baseDir = tagDir;}
	public void setTagDir(String tagDir) {this.tagDir = tagDir;}
	public void setDocDir(String docDir) {this.docDir = docDir;}
	public void setUseLog4j(boolean useLog4j) {this.useLog4j = useLog4j;}
	public void setXslt(String xslt) {this.xslt = xslt;}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TagTransformationTask ttf = new TagTransformationTask();
		ttf.setUseLog4j(true);
		ttf.setOutputDir(args[0]);
		ttf.setTagDef(args[1]);
		ttf.setBaseDir(args[2]);
		ttf.setTagDir(args[3]);
		ttf.setDocDir(args[4]);
		ttf.setXslt(args[5]);
		ttf.execute();
	}
}