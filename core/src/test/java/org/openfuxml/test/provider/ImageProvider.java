package org.openfuxml.test.provider;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.factory.xml.editorial.XmlIndexFactory;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Paragraph create(String imageTitle){return create(10, imageTitle);}
	public static Paragraph create(int words, String imageTitle)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
		p.getContent().add(createImage(imageTitle));
		p.getContent().add(li.getWords(words));
    	return p;
	}
	
	private static Image createImage(String imageTitle)
	{
		Image p = new Image();
		p.setTitle(XmlTitleFactory.build(imageTitle));
		p.setMedia(XmlMediaFactory.build("src/folder/img.png", "dst/folder/img.png"));
		return p;
	}

	public static Image createImageOnly(String imageTitle)
	{
		Image p = new Image();
		p.setTitle(XmlTitleFactory.build(imageTitle));
		p.setMedia(XmlMediaFactory.build("src/folder/img.png", "dst/folder/img.png"));
		return p;
	}
}