package org.openfuxml.media.cross;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.CrossMediaManager;
import org.openfuxml.interfaces.CrossMediaTranscoder;
import org.openfuxml.media.transcode.Pdf2PdfTranscoder;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCrossMediaManager implements CrossMediaManager
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	public static String keyOfxLatexImageDir = "ofx.renderer.latex.dir.image";
	
	private File texBase;
	private String imageBaseDir;
	private List<Media> listMedia;
	
	public LatexCrossMediaManager(File texBase, String imageBaseDir)
	{
		this.texBase=texBase;
		this.imageBaseDir=imageBaseDir;
		listMedia = new ArrayList<Media>();
	}
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		listMedia.add(imageMedia);
		StringBuffer sb = new StringBuffer();
		sb.append(imageBaseDir).append(SystemUtils.FILE_SEPARATOR);
		sb.append(imageMedia.getDst());
		sb.append(".pdf");
		return sb.toString();
	}

	@Override
	public void transcode() throws OfxAuthoringException
	{
		logger.info("Transcoding "+listMedia.size());
		
		CrossMediaTranscoder transcoder = null;
		
		for(Media media : listMedia)
		{
			switch(CrossMediaManagerUtil.getFormat(media.getSrc()))
			{
				case PDF:	transcoder = new Pdf2PdfTranscoder(new File(texBase,imageBaseDir));break;
				case SVG:	transcoder = new Svg2PdfTranscoder(new File(texBase,imageBaseDir));break;
			}
			
			transcoder.transcode(media);
		}
	}
}
