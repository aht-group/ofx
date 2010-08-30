package org.openfuxml.addon.epub.util;

import java.io.OutputStream;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.zip.ZipperStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EpubZipper
{
	static Log logger = LogFactory.getLog(EpubZipper.class);
	
	public EpubZipper()
	{
		
	}
	
	public void zip()
	{
		ZipperStream zs = new ZipperStream();
		zs.add("mimetype", "application/epub+zip".getBytes());
		zs.addFile("META-INF/container.xml", "resources/data/epub/container.xml");
		zs.addFile("hello.ncx", "resources/data/epub/hello.ncx");
		zs.addFile("hello.opf", "resources/data/epub/hello.opf");
		zs.addFile("hello.xhtml", "resources/data/epub/hello.xhtml");
		OutputStream os = zs.getZipStream();
		ZipperStream.writeFile("dist/hello.epub", os);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		EpubZipper epubZipper = new EpubZipper();
		epubZipper.zip();
	}
}
