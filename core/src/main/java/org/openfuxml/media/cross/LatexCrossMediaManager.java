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
import org.openfuxml.media.transcode.Png2PngTranscoder;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCrossMediaManager implements CrossMediaManager
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	public static String keyOfxLatexImageDir = "ofx.renderer.latex.dir.image";
	
	private File texBase;
	private String imageBaseDir;
	private List<Media> listMedia;
	
	private MediaSourceModificationTracker msmt;
	
	public LatexCrossMediaManager(File texBase, String imageBaseDir)
	{
		this(texBase,imageBaseDir,null);
	}
	public LatexCrossMediaManager(File texBase, String imageBaseDir,MediaSourceModificationTracker msmt)
	{
		this.texBase=texBase;
		this.imageBaseDir=imageBaseDir;
		this.msmt=msmt;
		listMedia = new ArrayList<Media>();
	}
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		listMedia.add(imageMedia);
		StringBuffer sb = new StringBuffer();
		sb.append(imageBaseDir).append(SystemUtils.FILE_SEPARATOR);
		sb.append(imageMedia.getDst());
//		sb.append(".pdf");
		return sb.toString();
	}

	@Override
	public void transcode() throws OfxAuthoringException
	{
		logger.info("Transcoding "+listMedia.size());
		
		File fImage = new File(texBase,imageBaseDir);
		CrossMediaTranscoder transcoder = null;
		
		for(Media media : listMedia)
		{
			CrossMediaManager.Format format = CrossMediaFileUtil.getFormat(media.getSrc());
			switch(format)
			{
				case PDF:	transcoder = new Pdf2PdfTranscoder(fImage);break;
				case SVG:	transcoder = new Svg2PdfTranscoder(fImage);break;
				case PNG:	transcoder = new Png2PngTranscoder(fImage);break;
				default:	logger.warn("Format "+format+" Not Implemented");break;
			}
			if(isSourceChanged(media) || !transcoder.isTargetExisting(media))
			{
				transcoder.transcode(media);
			}
		}
		if(msmt!=null){msmt.persist();}
	}
	
	private boolean isSourceChanged(Media media)
	{
		if(msmt==null){return true;}
		else {return msmt.isChanged(media);}
	}
}
