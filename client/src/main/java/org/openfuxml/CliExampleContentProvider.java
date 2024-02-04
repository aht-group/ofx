package org.openfuxml;

import java.util.ArrayList;
import java.util.List;

import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.interfaces.xml.OfxXmlExampleProvider;
import org.openfuxml.model.xml.core.ofx.Document;

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
		OfxClientBootstrap.init();
		CliExampleContentProvider cli = new CliExampleContentProvider();
//		cli.updateExamples();
		cli.output();
    }
}