package org.openfuxml.renderer.util;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.Dirs;
import net.sf.exlp.xml.xpath.IoXpath;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.xml.renderer.cmp.Cmp;

public class OfxRenderConfiguration
{
	static Log logger = LogFactory.getLog(OfxRenderConfiguration.class);
	
	private Cmp cmp;
	
	private File fCmpParent;

	public OfxRenderConfiguration()
	{
		
	}
	
	public Cmp readCmp(String fileCmp) throws OfxConfigurationException
	{
		File fCmp = new File(fileCmp);
		try
		{
			cmp = (Cmp)JaxbUtil.loadJAXB(fCmp.getAbsolutePath(), Cmp.class);
//			JaxbUtil.debug(cmp, new OfxNsPrefixMapper());
		}
		catch (FileNotFoundException e){throw new OfxConfigurationException("CMP configuration not found: "+fCmp.getAbsolutePath());}
		logger.info("Using CMP configuration: "+fCmp.getAbsolutePath());
		fCmpParent = fCmp.getParentFile();
		
		if(!cmp.isSetSource()){throw new OfxConfigurationException("No <source/> defined in "+fCmp.getAbsolutePath());}
		if(!cmp.getSource().isSetDirs()){throw new OfxConfigurationException("No <source><dirs/></source> defined in "+fCmp.getAbsolutePath());}
		
		return cmp;
	}
	
	public File getDir(Dirs dirs, String dirCode) throws OfxConfigurationException
	{
		File f = null;
		Dir dir = null;
		try
		{
			dir = IoXpath.getDir(dirs, dirCode);
			if(!dir.isSetName()){throw new OfxConfigurationException("No @name definined for <dir code=\""+dirCode+"\"/>");}
			f = new File(FilenameUtils.normalize(fCmpParent.getAbsolutePath()+SystemUtils.FILE_SEPARATOR+dir.getName()));
		}
		catch (ExlpXpathNotFoundException e){throw new OfxConfigurationException("Directory not configured for code="+dirCode+" ("+e.getMessage()+")");}
		catch (ExlpXpathNotUniqueException e){throw new OfxConfigurationException("Directory not configured for code="+dirCode+" ("+e.getMessage()+")");}
		
		if(!f.exists())
		{
			if(dir.isSetAllowCreate() && dir.isAllowCreate())
			{
				f.mkdirs();
			}
			else
			{
				logger.info("You may set allowCreate=\"true\" in <dir code=\""+dirCode+"\"/>");
				throw new OfxConfigurationException("Directory <dir code=\""+dirCode+"\"/> not available: "+f.getAbsolutePath());
			}
		}
		if(!f.isDirectory()){throw new OfxConfigurationException("Something exists for <dir code=\""+dirCode+"\"/>, but it's not a directory: "+f.getAbsolutePath());}
		
		return f;
	}
	
	public File getFile(Dirs dirs, String dirCode, String fileCode) throws OfxConfigurationException
	{
		File f = null;
		try
		{
			net.sf.exlp.xml.io.File file = IoXpath.getFile(dirs, fileCode);
			
			File dir = getDir(dirs, dirCode);
			f = new File(FilenameUtils.normalize(dir.getAbsolutePath()+SystemUtils.FILE_SEPARATOR+file.getName()));
			
			if(!f.exists()){throw new OfxConfigurationException("File (code="+fileCode+"does not exist: "+f.getAbsolutePath());}
		}
		catch (ExlpXpathNotFoundException e){throw new OfxConfigurationException("Directory not configured for code="+dirCode+" ("+e.getMessage()+")");}
		catch (ExlpXpathNotUniqueException e){throw new OfxConfigurationException("Directory not configured for code="+dirCode+" ("+e.getMessage()+")");}
		return f;
	}
	
	public Cmp getCmp() {return cmp;}
	public File getfCmpParent() {return fCmpParent;}
}