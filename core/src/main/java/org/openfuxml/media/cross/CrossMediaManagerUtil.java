package org.openfuxml.media.cross;

import org.openfuxml.interfaces.CrossMediaManager;

public class CrossMediaManagerUtil
{
	public static CrossMediaManager.Format getFormat(String fileName)
	{
		if(fileName.endsWith(".pdf")){return CrossMediaManager.Format.PDF;}
		if(fileName.endsWith(".svg")){return CrossMediaManager.Format.SVG;}
		return null;
	}
}
