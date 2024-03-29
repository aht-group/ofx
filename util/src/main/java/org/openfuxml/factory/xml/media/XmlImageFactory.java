package org.openfuxml.factory.xml.media;

import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlHeightFactory;
import org.openfuxml.factory.xml.layout.XmlWidthFactory;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.media.Media;

public class XmlImageFactory
{
	public static Image build() {return new Image();}
	
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
	
	public static Image px(int width, int height)
	{		
		Image image = new Image();
		image.setWidth(XmlWidthFactory.px(width));
		image.setHeight(XmlHeightFactory.px(height));
		return image;
	}
	
	public static Image size(double width, double height)
	{		
		Image image = new Image();
		image.setWidth(XmlWidthFactory.size(width));
		image.setHeight(XmlHeightFactory.size(height));
		return image;
	}
	
	public static Image centerPercent(long id, int precentage)
	{		
		Image image = new Image();
		image.setId(Long.valueOf(id).toString());
		image.setWidth(XmlWidthFactory.percentage(Integer.valueOf(precentage).doubleValue()));
		image.setAlignment(XmlAlignmentFactory.buildHorizontal(XmlAlignmentFactory.Horizontal.center));
		return image;
	}
	
	public static Image idHeight(long id, int height)
	{		
		Image image = new Image();
		image.setId(""+id);
		image.setHeight(XmlHeightFactory.px(height));
		return image;
	}
}