package org.openfuxml.addon.wiki.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class WikiContentIO
{
	static Logger logger = Logger.getLogger(WikiContentIO.class);
	
	public static synchronized void writeTxt(String dirName, String fileName, String content)
	{
		logger.debug("Writing Txt to "+dirName+"/"+fileName);
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dirName+"/"+fileName)));
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized void writeXml(String dirName, String fileName, String content)
	{
		logger.debug("Writing Xml to "+dirName+"/"+fileName);
		try
		{
			Reader sr = new StringReader(content);  
			Document doc = new SAXBuilder().build(sr);
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat() );
			
			File f = new File(dirName+"/"+fileName);
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			
			xmlOut.output( doc, osw );
			osw.close();os.close();
		} 
		catch (JDOMException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public static synchronized String loadTxt(String dirName, String fileName)
	{
		logger.debug("Reading Txt from "+dirName+"/"+fileName);
		StringBuffer sb = new StringBuffer();
		try
		{
			BufferedReader bw = new BufferedReader(new FileReader(new File(dirName+"/"+fileName)));
			while(bw.ready())
			{
				sb.append(bw.readLine());
			}
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
		return sb.toString();
	}
}