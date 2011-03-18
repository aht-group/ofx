package org.openfuxml.renderer.latex.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TxtWriter
{
	static Log logger = LogFactory.getLog(TxtWriter.class);
	
	protected String fs;
	protected String ls;

	protected String dirName,fileName;
	protected DecimalFormat df;
	
	private String encoding;
	
	public TxtWriter(){this("UTF-8",SystemUtils.LINE_SEPARATOR);}
	public TxtWriter(String encoding, String ls)
	{
		this.encoding=encoding;
		this.ls=ls;
		fs = SystemUtils.FILE_SEPARATOR;
		dirName=".";
		fileName="TxtWriter.txt";
	}
	
	public void setFile(String dirName,String fileName)
	{
		this.dirName=dirName;
		this.fileName=fileName;
	}
	
	public void debug(List<String> txt)
	{
		logger.debug("Verzeichnis="+dirName+"\tDatei="+fileName);
		for(String s : txt)
		{
			logger.debug(s);
		}
	}
	
	public void writeFile(List<String> txt)
	{
		File f = new File(dirName+System.getProperty("file.separator")+fileName);
		if(f.exists()){f.delete();}
		logger.info("Writing File: "+f.getAbsolutePath());
		try
		{
			f.createNewFile();
			OutputStream os = new FileOutputStream(f);
			OutputStreamWriter osw = new  OutputStreamWriter(os, encoding); 

			BufferedWriter bw = new BufferedWriter(osw);
			for(String s : txt){bw.write(s+ls);}
			bw.close();osw.close();os.close();
		}
		catch (IOException e){logger.error(e);}  
	}
	
	public void writeStream(OutputStream os, List<String> txt)
	{
		try
		{
			OutputStreamWriter osw = new  OutputStreamWriter(os, encoding); 

			BufferedWriter bw = new BufferedWriter(osw);
			for(String s : txt){bw.write(s+ls);}
			bw.close();osw.close();
		}
		catch (IOException e){logger.error(e);}  
	}
}
