package org.openfuxml.addon.wiki.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.exlp.util.io.StringBufferOutputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openfuxml.model.xml.addon.wiki.Wikiinjection;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiContentIO
{
	final static Logger logger = LoggerFactory.getLogger(WikiContentIO.class);
	
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
		catch (JDOMException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
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
		catch (IOException e) {logger.error("",e);}
		return sb.toString();
	}
	
	public synchronized static StringBuffer toString(Wikiinjection injection)
	{
		StringBufferOutputStream sbos = new StringBufferOutputStream();
		try
		{
			Element element = toElement(injection, Wikiinjection.class);
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat() );
			xmlOut.output(element, sbos);
		}
		catch (IOException e) {logger.error("",e);}
		return sbos.getStringBuffer();
	}
	
	public static synchronized Element toElement(Object o, Class<?> c)
	{
		Element result = null;
		try
		{
			StringBufferOutputStream sbos = new StringBufferOutputStream();
			JAXBContext context = JAXBContext.newInstance(c);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE); 
			m.marshal(o, sbos);
			
			Reader sr = new StringReader(sbos.getStringBuffer().toString());  
			Document doc = new SAXBuilder().build(sr);
			
			result = unsetNameSpace(doc.getRootElement());
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (JDOMException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return result;
	}
	
	private synchronized static Element unsetNameSpace(Element e)
	{
		e.setNamespace(null);
		for(Object o : e.getChildren())
		{
			Element eChild = (Element)o;
			eChild=unsetNameSpace(eChild);
		}
		return e;
	}
	
	public synchronized static void toFile(Wikiinjection injection,File baseDir)
	{
		File f = new File(baseDir,injection.getId()+"-"+injection.getOfxtag()+".xml");
		if(f.exists() && f.isFile()){f.delete();}
		try
		{
			JAXBContext context = JAXBContext.newInstance(Wikiinjection.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty("com.sun.xml.bind.namespacePrefixMapper",new OfxNsPrefixMapper());
			m.marshal(injection, f);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
}