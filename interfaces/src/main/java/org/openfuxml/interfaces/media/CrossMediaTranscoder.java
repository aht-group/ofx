package org.openfuxml.interfaces.media;

import java.io.File;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.media.Media;

public interface CrossMediaTranscoder
{
	void transcode(Media media) throws OfxAuthoringException;
	File buildTarget(Media media);
	boolean isTargetExisting(Media media);
}
