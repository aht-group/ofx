package org.openfuxml.util.media;

import org.openfuxml.interfaces.CrossMediaManager;

public class MediaFileExtensionUtil
{
	public static CrossMediaManager.Format getFormat(String fileName)
	{
		if(fileName.endsWith(".pdf")){return CrossMediaManager.Format.PDF;}
		if(fileName.endsWith(".svg")){return CrossMediaManager.Format.SVG;}
		return null;
	}
}
