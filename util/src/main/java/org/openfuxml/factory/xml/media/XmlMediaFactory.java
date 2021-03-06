package org.openfuxml.factory.xml.media;

import org.openfuxml.content.media.Media;

import net.sf.exlp.xml.io.File;

public class XmlMediaFactory
{
	public static Media dst(String dst)
	{
		return build(null,dst);
	}
	
	public static Media build(String src, String dst)
	{
		Media xml = new Media();
		xml.setSrc(src);
		xml.setDst(dst);
		return xml;
	}
	
	public static Media build(File file)
	{
		Media xml = new Media();
		xml.setFile(file);
		return xml;
	}
}