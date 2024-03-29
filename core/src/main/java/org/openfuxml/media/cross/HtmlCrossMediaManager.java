package org.openfuxml.media.cross;

import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlCrossMediaManager implements CrossMediaManager
{
	final static Logger logger = LoggerFactory.getLogger(LatexImageRenderer.class);
	
	private String baseUrl = "https://localhost:8080/image/fr";
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		logger.warn("Only adding the src for "+imageMedia.getId());
		return baseUrl+"/"+imageMedia.getSrc();
	}

	@Override public void transcode() {}

}
