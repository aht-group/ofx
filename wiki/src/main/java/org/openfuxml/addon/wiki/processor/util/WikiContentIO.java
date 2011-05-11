package org.openfuxml.addon.wiki.processor.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WikiContentIO
{
	static Log logger = LogFactory.getLog(WikiContentIO.class);
	
	public static synchronized String getFileFromSource(String ofxSource)
	{
		int indexFrom = ofxSource.lastIndexOf("/");
		int indexTo = ofxSource.lastIndexOf(".xml");
		
		StringBuffer sb = new StringBuffer();
		sb.append(ofxSource.substring(indexFrom+1,indexTo));;
		return sb.toString();
	}
	
	public static synchronized String getFileFromSource(String ofxSource, String suffix)
	{
		int indexFrom = ofxSource.lastIndexOf("/");
		int indexTo = ofxSource.lastIndexOf(".xml");
		
		StringBuffer sb = new StringBuffer();
		sb.append(ofxSource.substring(indexFrom+1,indexTo));
		sb.append(".").append(suffix);
		return sb.toString();
	}
}