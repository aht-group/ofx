package org.openfuxml.interfaces.media;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.media.Media;

public interface CrossMediaManager
{
	enum Format {SVG,PDF,PNG}
	
	String getImageRef(Media imageMedia);
	
	void transcode() throws OfxAuthoringException;

}
