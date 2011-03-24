package org.openfuxml.addon.wiki.processor.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WikiContentIO
{
	static Log logger = LogFactory.getLog(WikiContentIO.class);
	
	public static synchronized void writeTxt(String dirName, String fileName, String content){writeTxt(new File(dirName), fileName, content);}
	public static synchronized void writeTxt(File fDir, String fileName, String content){writeTxt(new File(fDir,fileName), content);}
	public static synchronized void writeTxt(File f, String content)
	{
		logger.debug("Writing Txt to "+f.getAbsolutePath());
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized String loadTxt(File fDir, String fileName){return loadTxt(new File(fDir,fileName));}
	public static synchronized String loadTxt(File f)
	{
		logger.debug("Reading Txt from "+f.getAbsolutePath());
		StringBuffer sb = new StringBuffer();
		try
		{
			BufferedReader bw = new BufferedReader(new FileReader(f));
			while(bw.ready())
			{
				sb.append(bw.readLine());
				sb.append(SystemUtils.LINE_SEPARATOR);
			}
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
		return sb.toString();
	}
	
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