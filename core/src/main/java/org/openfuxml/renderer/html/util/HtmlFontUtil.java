package org.openfuxml.renderer.html.util;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.layout.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlFontUtil
{
	final static Logger logger = LoggerFactory.getLogger(HtmlFontUtil.class);

	public static String fontSize(Font font)
	{
		String fontsize = "font-size:";
		if(font.isSetRelativeSize())
		{
			switch(font.getRelativeSize())
			{
				case -4: fontsize +="50%";
					break;
				case -3: fontsize +="63%";
					break;
				case -2: fontsize +="75%";
					break;
				case -1: fontsize +="88%";
					break;
				case 0: fontsize +="100%";
					break;
				case 1: fontsize +="120%";
					break;
				case 2: fontsize +="140%";
					break;
				case 3: fontsize +="160%";
					break;
				case 4: fontsize +="180%";
					break;
				case 5: fontsize +="200%";
					break;
			}
		}
		return fontsize;
	}
}
