package org.openfuxml.renderer.wiki;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.wiki.OfxWikiRenderer;

/**
 * 
 * @author yannkruger
 *
 */
public class WikiTitleRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer  {

	public WikiTitleRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	//TODO FIX
//	public WikiTitleRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm) {
//		super(cmm, dsm);
//	}
//	
	public void render(Title title, int lvl) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		
		//Wikisyntax erlaubt erst ab H2 "=="
		if(lvl>1){
			for(int i=0; i<=lvl; i++)
			{
				sb.append("=");
			}
			
				sb.append(title.getContent().toString());
			
			for(int i=0; i<=lvl; i++)
			{
				sb.append("=");
			}
		}else{
			sb.append(title.getContent().toString());
		}
		
		txt.add(sb.toString());
	}

}
