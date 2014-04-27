package org.openfuxml.interfaces;

import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;

public interface CrossMediaTranscoder
{
	void transcode(Media media) throws OfxAuthoringException;
}
