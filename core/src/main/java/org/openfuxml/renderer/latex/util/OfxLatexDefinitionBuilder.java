package org.openfuxml.renderer.latex.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.io.IOUtils;
import org.openfuxml.exception.OfxConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLatexDefinitionBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(OfxLatexDefinitionBuilder.class);
			
	protected MultiResourceLoader mrl;
	protected File baseLatex;
	
	public OfxLatexDefinitionBuilder(String baseLatex)
	{
		this.baseLatex= new File(baseLatex);
		mrl = new MultiResourceLoader();
	}
	
	public void copyPackages() throws OfxConfigurationException {copyResource("tex.ofx-core","packages");}
	
	protected void copyResource(String library, String resource) throws OfxConfigurationException
	{
		try
		{
			File fTarget = new File(baseLatex,"tex"+File.separator+resource+".tex");
			InputStream is = mrl.searchIs(library+"/"+resource+".tex");
			byte[] bytes = IOUtils.toByteArray(is);
			FileIO.writeFileIfDiffers(bytes, fTarget);
		}
		catch (FileNotFoundException e) {throw new OfxConfigurationException(e.getMessage());}
		catch (IOException e) {throw new OfxConfigurationException(e.getMessage());}
	}
}