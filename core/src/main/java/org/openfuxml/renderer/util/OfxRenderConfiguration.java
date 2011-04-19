package org.openfuxml.renderer.util;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.jaxb.Cmp;

public class OfxRenderConfiguration
{
	static Log logger = LogFactory.getLog(OfxRenderConfiguration.class);
	
	File fCmpParent;
	File fOfxRoot;

	public OfxRenderConfiguration()
	{
		
	}
	
	public Cmp readCmp(String fileCmp) throws OfxConfigurationException
	{
		Cmp cmp;
		File fCmp = new File(fileCmp);
		try
		{
			cmp = (Cmp)JaxbUtil.loadJAXB(fCmp.getAbsolutePath(), Cmp.class);
//			JaxbUtil.debug(cmp, new OfxNsPrefixMapper());
		}
		catch (FileNotFoundException e){throw new OfxConfigurationException("CMP configuration not found: "+fCmp.getAbsolutePath());}
		logger.info("Using CMP configuration: "+fCmp.getAbsolutePath());
		
		fCmpParent = fCmp.getParentFile();
		
		if(!cmp.isSetSource() || !cmp.getSource().isSetRootXml()){throw new OfxConfigurationException("No source defined in: "+fCmp.getAbsolutePath());}
		fOfxRoot = new File(fCmpParent,cmp.getSource().getRootXml());
		if(!fOfxRoot.exists()){throw new OfxConfigurationException("rootXml does not exist: "+fOfxRoot.getAbsolutePath());}
		logger.info("Using rootXML: "+fOfxRoot.getAbsolutePath());
		
		return cmp;
	}
	
	public File getfCmpParent() {return fCmpParent;}
	public File getfOfxRoot() {return fOfxRoot;}
}