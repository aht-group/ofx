package org.openfuxml.media.cross;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.content.media.Media;
import org.openfuxml.interfaces.CrossMediaManager;

public class LatexCrossMediaManager implements CrossMediaManager
{
	private String imageBaseDir;
	
	public LatexCrossMediaManager(String imageBaseDir)
	{
		this.imageBaseDir=imageBaseDir;
	}
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(imageBaseDir).append(SystemUtils.FILE_SEPARATOR);
		sb.append(imageMedia.getDst());
		return sb.toString();
	}

}
