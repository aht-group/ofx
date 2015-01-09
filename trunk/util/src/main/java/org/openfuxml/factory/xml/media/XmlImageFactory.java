package org.openfuxml.factory.xml.media;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.media.Media;
import org.openfuxml.factory.xml.layout.XmlHeightFactory;

public class XmlImageFactory
{
	public static Image em(String pathPrefix,String imageName, int em)
	{
		int index = imageName.lastIndexOf(".");
		String dst = imageName.substring(0,index);
				
		StringBuffer src = new StringBuffer();
		if(pathPrefix!=null){src.append(pathPrefix).append("/");}
		src.append(imageName);
		
		Media media = new Media();
		media.setSrc(src.toString());
		media.setDst(dst);
		
		Image image = new Image();
		image.setMedia(media);
		image.setHeight(XmlHeightFactory.em(em));
		return image;
	}
}
