package org.openfuxml.addon.epub.generator.content;

import java.io.File;
import java.io.Serializable;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.producer.preprocessors.ExternalMerger;

public class ContentGenerator
{
	static Log logger = LogFactory.getLog(ExternalMerger.class);
	
	private File targetDir;
	private Namespace nsXhtml;
	private PartXhtmlFactory partFactory;
	
	public ContentGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		nsXhtml = Namespace.getNamespace("http://www.w3.org/1999/xhtml");
		partFactory = new PartXhtmlFactory(nsXhtml);
	}
	
	public void create(Ofxdoc ofxDoc)
	{
		int partNr=1;
		for(Serializable s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				File f = new File(targetDir,"part-"+partNr+".xhtml");
				Document doc = new Document();
			
				Element rootElement = partFactory.createPart(section);
				doc.setRootElement(rootElement);
			
				JDomUtil.save(doc, f, Format.getPrettyFormat());
				partNr++;
			}
		}
	}

}