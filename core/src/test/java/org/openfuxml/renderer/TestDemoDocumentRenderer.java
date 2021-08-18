package org.openfuxml.renderer;

import java.io.IOException;
import java.io.StringWriter;

import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.text.OfxTextRenderer;

public class TestDemoDocumentRenderer
{	

	public void text() throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		StringWriter writer = new StringWriter();
		OfxTextRenderer ofx = new OfxTextRenderer();
		ofx.render(DemoContentProvider.build(), writer);
		System.out.println(writer.toString());
	}
	
	public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();	
    	TestDemoDocumentRenderer cli = new TestDemoDocumentRenderer();
    	
    	cli.text();
    }
}