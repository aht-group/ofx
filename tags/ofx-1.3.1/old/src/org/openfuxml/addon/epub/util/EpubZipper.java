package org.openfuxml.addon.epub.util;

import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.zip.ZipperStream;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

public class EpubZipper
{
	final static Logger logger = LoggerFactory.getLogger(EpubZipper.class);
	
	private File targetDir;
	
	public EpubZipper(File targetDir)
	{
		this.targetDir=targetDir;
	}
	
	public void zip()
	{
		ZipperStream zs = new ZipperStream();
		zs.add("mimetype", "application/epub+zip".getBytes());
		zs.addFile("META-INF/container.xml", targetDir.getAbsolutePath()+"/META-INF/container.xml");
		zs.addFile("inhalt.opf", targetDir.getAbsolutePath()+"/inhalt.opf");
		
		Document docToc = JDomUtil.load(targetDir.getAbsolutePath()+"/inhalt.opf");
//		JDomUtil.debug(docToc);
		try
		{
			XPath xpath = XPath.newInstance("//opf:package/opf:manifest/opf:item");
			xpath.addNamespace(Namespace.getNamespace("opf","http://www.idpf.org/2007/opf"));
			xpath.addNamespace(Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance"));
			xpath.addNamespace(Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/"));
			
			List<?> list = xpath.selectNodes(docToc);
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element item = (Element) iter.next();
				String href = item.getAttribute("href").getValue();
				zs.addFile(href, targetDir.getAbsolutePath()+"/"+href);
			}
		}
		catch (JDOMException e) {logger.error("",e);}

		
		OutputStream os = zs.getZipStream();
		ZipperStream.writeFile((new File(targetDir,"hello.zip")).getAbsolutePath(), os);
	}
	
	public void zipHello()
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
		
		File f = new File("dist");	
			
		EpubZipper epubZipper = new EpubZipper(f);
		epubZipper.zipHello();
	}
}