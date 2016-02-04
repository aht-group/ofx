package org.openfuxml.util.media;

import org.openfuxml.content.media.Image;
import org.openfuxml.factory.xml.media.XmlImageFactory;

public class ImageDimensionRatio
{
	public static Image height(Image image, int height)
	{
		double actualWidth = image.getWidth().getValue();
		double actualHeight = image.getHeight().getValue();
		
		int width = (int)((height * actualWidth) / actualHeight);
		
		return XmlImageFactory.px(width, height);
	}
}