package org.openfuxml.media.cross;

import org.openfuxml.content.media.Media;
import org.openfuxml.interfaces.CrossMediaManager;

public class NoOpCrossMediaManager implements CrossMediaManager
{
	@Override
	public String getImageRef(Media imageMedia)
	{
		return imageMedia.getSrc();
	}

}
