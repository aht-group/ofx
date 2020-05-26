package org.openfuxml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class OfxLicenceActivator
{
	final static Logger logger = LoggerFactory.getLogger(OfxLicenceActivator.class);
	
	public static String aspose(String resource)
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		com.aspose.words.License license = new com.aspose.words.License();

		try
		{
			license.setLicense(mrl.searchIs(resource));
			
		}
		catch (Exception e) {logger.error(e.getMessage());}
		return "Aspose licence active: ";
	}	
}
