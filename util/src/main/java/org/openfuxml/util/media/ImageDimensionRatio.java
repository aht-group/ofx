package org.openfuxml.util.media;

import org.openfuxml.factory.xml.media.XmlImageFactory;
import org.openfuxml.model.xml.core.media.Image;

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