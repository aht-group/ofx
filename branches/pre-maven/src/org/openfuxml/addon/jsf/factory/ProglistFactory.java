package org.openfuxml.addon.jsf.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsf.JsfTagTransformator;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.content.ofx.Title;

public class ProglistFactory
{
	static Log logger = LogFactory.getLog(ProglistFactory.class);
	
	private File fDocBase;
	private String logMsg;
	
	public ProglistFactory(File fDocBase)
	{
		this.fDocBase=fDocBase;
	}
	
	public org.openfuxml.content.ofx.Listing createProglist(org.openfuxml.addon.jsf.data.jaxb.Listing jsfListing)
	{
		logger.debug("Creating listing .."+jsfListing.getFile());
		org.openfuxml.content.ofx.Listing ofxListing = new org.openfuxml.content.ofx.Listing();
		Title title = new Title();
		title.setValue(jsfListing.getTitle());
		title.setNumbering(true);
		ofxListing.setTitle(title);
		ofxListing.setRaw(getRawListing(jsfListing.getFile()));
		ofxListing.setId(jsfListing.getId());
		return ofxListing;
	}
	
	@SuppressWarnings("unchecked")
	private Raw getRawListing(String fileName)
	{
		String result = "";
		File f = new File(fDocBase,fileName);
		logger.debug(f.getAbsoluteFile());
		try
		{
			List<String> lLines = IOUtils.readLines(new FileInputStream(f), "UTF8");
			for(String s : lLines)
			{
				s=s.replaceAll("\t", "   ");
				result=result+s+"\n";
			}

		}
		catch (FileNotFoundException e)
		{
			logMsg=e.getMessage();
			if(JsfTagTransformator.useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		}
		catch (IOException e)
		{
			logMsg=e.getMessage();
			if(JsfTagTransformator.useLog4j){logger.debug(logMsg);}else{System.out.println(logMsg);}
		}
		Raw raw = new Raw();
		raw.setValue(result);
		return raw;
	}
}
