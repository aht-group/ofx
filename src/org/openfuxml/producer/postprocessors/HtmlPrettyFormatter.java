package org.openfuxml.producer.postprocessors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CleanerTransformations;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.JDomSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagTransformation;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.util.OfxApp;

import de.kisner.util.LoggerInit;

public class HtmlPrettyFormatter extends DirectoryWalker
{
	static Logger logger = Logger.getLogger(OfxApp.class);
	
	private CleanerTransformations ct;
	
	public HtmlPrettyFormatter(String suffix)
	{
		super(HiddenFileFilter.VISIBLE,FileFilterUtils.suffixFileFilter("."+suffix),10);
		initCT();
	}
	
	private void initCT()
	{
		ct =  new CleanerTransformations();
		TagTransformation tt = new TagTransformation("div", "div", true);
			tt.addAttributeTransformation("xmlns:saxon");
			tt.addAttributeTransformation("xmlns:xs");
		ct.addTransformation(tt);
	}
	
	@SuppressWarnings("unchecked")
	protected void handleFile(File file, int depth, Collection results)
	{
		File f = new File(FilenameUtils.normalize(file.getAbsolutePath()));
		logger.debug(f.getAbsoluteFile());
		try
		{
			HtmlCleaner cleaner = new HtmlCleaner();
			cleaner.setTransformations(ct);
			
			CleanerProperties props = cleaner.getProperties();
			TagNode node = cleaner.clean(f);
			
			TagNode tnBody = node.getAllElements(false)[1];
			List l = tnBody.getChildren();
			if(l!=null && l.size()>0)
			{	//This is a hack to remove the <?xml in the beginning of body
				tnBody.removeChild(l.get(0));
			}			
			
			Document myJDom = new JDomSerializer(props, true).createJDom(node);
			
			Format format = Format.getPrettyFormat();
			format.setEncoding("iso-8859-1");
			XMLOutputter outputter = new XMLOutputter(format);
			
			OutputStream os = new FileOutputStream(f);
			
			outputter.output(myJDom,os);
//			sbResult.append(outputter.outputString(myJDom));
			results.add(f.getAbsoluteFile());
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public void start(File startDir, Collection results)
	{
		try{walk(startDir,results);}
		catch (IOException e) {logger.error(e);}
		logger.debug("Processed files: "+results.size());
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		File f = new File("/Users/thorsten/Documents/workspace/openfuxml/output/fuxml/jWan/html/jWAN");
		Collection<String> c = new ArrayList<String>();
			
		HtmlPrettyFormatter hpf = new HtmlPrettyFormatter("html"); 
		hpf.start(f, c);
		logger.debug("Processed files: "+c.size());
	}
}
