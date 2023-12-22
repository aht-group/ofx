package org.openfuxml.factory.xml.media;

import org.exlp.model.xml.io.File;
import org.openfuxml.model.xml.core.media.Media;

public class XmlMediaFactory
{
	public static Media build() {return new Media();}
	
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