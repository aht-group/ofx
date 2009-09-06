package org.openfuxml.addon.jsf;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class TagTransformationTask extends Task
{		
	private String targetDir,tagLib;
	private int dtdLevel;

	public TagTransformationTask()
	{
		targetDir="";
		dtdLevel=0;
	}
	
    public void execute() throws BuildException
    {
    	File baseDir = new File(targetDir);
    	if(!baseDir.exists()){throw new BuildException(targetDir+" does not exist.");}
    	if(!baseDir.isDirectory()){throw new BuildException(targetDir+" not a directory.");}
    	JsfTagTransformator tagTransformator = new JsfTagTransformator(baseDir,dtdLevel,false);
    	tagTransformator.readTaglib(tagLib);
    	tagTransformator.transform();
    }
    
	public void setTargetDir(String targetDir) {this.targetDir = targetDir;}
	public void setDtdLevel(int dtdLevel) {this.dtdLevel = dtdLevel;}
	public void setTagLib(String tagLib) {this.tagLib = tagLib;}
}