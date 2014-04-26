package org.openfuxml.media.cross;

import org.openfuxml.content.media.Image;
import org.openfuxml.interfaces.CrossMediaManager;

public class NoOpCrossMediaManager implements CrossMediaManager
{
	@Override
	public String getImageRef(Image image)
	{
		return "nyi";
	}

}
