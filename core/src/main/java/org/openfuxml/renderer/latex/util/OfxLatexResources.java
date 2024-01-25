package org.openfuxml.renderer.latex.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.openfuxml.exception.OfxConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class OfxLatexResources
{	
	final static Logger logger = LoggerFactory.getLogger(OfxLatexResources.class);
			
	protected MultiResourceLoader mrl;
	protected File baseLatex;
	
	public OfxLatexResources(String baseLatex)
	{
		this(new File(baseLatex));
	}
	
	public OfxLatexResources(File baseLatex)
	{
		this.baseLatex=baseLatex;
		mrl = MultiResourceLoader.instance();
	}
	
	public void copyPackages() throws OfxConfigurationException {copyResource("ofx-core/tex","packages");}
	public void copyTest() throws OfxConfigurationException {copyResource("tex.ofx-core.test","","render");}
	
	protected void copyResource(String library, String resource) throws OfxConfigurationException
	{
		copyResource(library, "tex"+File.separator, resource);
	}
	protected void copyResource(String library, String subDir, String resource) throws OfxConfigurationException
	{
		File fTarget = new File(baseLatex,subDir+resource+".tex");
		
		if(!fTarget.getParentFile().exists()){throw new OfxConfigurationException(fTarget.getParentFile().getAbsolutePath()+" does not exist");}
//		logger.info("Checking fTarget "+fTarget.getAbsolutePath());
		
		try
		{
			
			InputStream is = mrl.searchIs(library+"/"+resource+".tex");
			FileIO.writeFileIfDiffers(IOUtils.toByteArray(is),fTarget);
		}
		catch (FileNotFoundException e) {throw new OfxConfigurationException(e.getMessage());}
		catch (IOException e) {throw new OfxConfigurationException(e.getMessage());}
	}
}