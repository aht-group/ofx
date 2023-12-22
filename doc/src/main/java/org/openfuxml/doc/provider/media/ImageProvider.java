package org.openfuxml.doc.provider.media;

import java.io.File;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.doc.provider.AbstractXmlExampleProvider;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.interfaces.xml.OfxXmlExampleProvider;
import org.openfuxml.model.xml.core.layout.Height;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.model.xml.core.media.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageProvider extends AbstractXmlExampleProvider implements OfxXmlExampleProvider
{	
	final static Logger logger = LoggerFactory.getLogger(ImageProvider.class);
	
	public enum Code{inline}
	
	
	
	public ImageProvider()
	{
		super();
		filePath = File.separator+"media"+File.separator+"image"+File.separator;
		resourcePath = "/media/image/";
		for(Code c : Code.values())
		{
			codes.add(c.toString());
			mapResources.put(c.toString(),resourceBase+resourcePath+c+".xml");
		}
	}
	

	
	@Override public void updateXmlInResourceFolder()
	{
		for(Code c : Code.values())
		{
			switch (c)
			{
				case inline: super.save(c,ImageProvider.figure("XX"));break;
			}
		}
	}
	
	public static Image figure(String imageTitle)
	{
		Image p = new Image();
		p.setTitle(XmlTitleFactory.build(imageTitle));
		p.setId("imageID");
		p.setMedia(XmlMediaFactory.build("src/folder/img.png", "dst/folder/img.png"));
		return p;
	}
	
	public static Paragraph inline(String imageTitle)
	{
		Image i = new Image();
		i.setTitle(XmlTitleFactory.build(imageTitle));
		i.setMedia(XmlMediaFactory.build("src/folder/img.png", "dst/folder/img.png"));
		
    	Paragraph p = new Paragraph();
    	p.getContent().add(DemoContentProvider.li.getWords(10));
		p.getContent().add(i);
		p.getContent().add(DemoContentProvider.li.getWords(10));
    	return p;
	}
	private static Width setWidth()
	{
		Width w = new Width();
		w.setUnit("percentage");
		w.setValue(75d);
		return w;
	}
	private static Height setHeight()
	{
		Height h = new Height();
		h.setUnit("percentage");
		h.setValue(75d);
		return h;
	}
}