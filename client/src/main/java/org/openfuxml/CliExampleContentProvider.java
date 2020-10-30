package org.openfuxml;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.interfaces.xml.OfxXmlExampleProvider;

import net.sf.exlp.util.xml.JaxbUtil;

public class CliExampleContentProvider
{	
	public CliExampleContentProvider()
	{
		
	}
	
	public void output()
	{
		Document doc = DemoContentProvider.build();
    	JaxbUtil.info(doc);
	}
	
	public void updateExamples()
	{
		List<OfxXmlExampleProvider> providers = new ArrayList<>();
		providers.add(new ImageProvider());
		
		for(OfxXmlExampleProvider provider : providers)
		{
			provider.updateXmlInResourceFolder();
		}
	}
	
	public static void main(String[] args) throws Exception
    {
		OfxCoreBootstrap.init();
		CliExampleContentProvider cli = new CliExampleContentProvider();
//		cli.updateExamples();
		cli.output();
    }
}