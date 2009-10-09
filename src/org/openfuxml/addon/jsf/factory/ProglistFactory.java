package org.openfuxml.addon.jsf.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openfuxml.addon.jsf.JsfTagTransformator;
import org.openfuxml.addon.jsf.data.jaxb.Listing;

public class ProglistFactory
{
	private static Logger logger = Logger.getLogger(ProglistFactory.class);
	
	private File fDocBase;
	private String logMsg;
	
	public ProglistFactory(File fDocBase)
	{
		this.fDocBase=fDocBase;
	}
	
	public Element createProglist(Listing listing)
	{
		logger.debug("Creating listing .."+listing.getFile());
		Element eRoot = new Element("proglist");
		
		Element eTitle = new Element("zwischentitel");
		eTitle.setText(listing.getTitle());
		eRoot.addContent(eTitle);

		Element eBuch = new Element("buchstaeblich");
		Element eSchreib = new Element("schreibmaschine");
		eSchreib.setText(getListing(listing.getFile()));
		
		eBuch.addContent(eSchreib);
		eRoot.addContent(eBuch);
		
		return eRoot;
	}
	
	@SuppressWarnings("unchecked")
	private String getListing(String fileName)
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
		return result;
	}
}
