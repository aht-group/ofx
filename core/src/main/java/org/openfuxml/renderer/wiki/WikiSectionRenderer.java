package org.openfuxml.renderer.wiki;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.ofx.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yannkruger
 *
 */
public class WikiSectionRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer {
	
	//TODO REMOVE
	public WikiSectionRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	final static Logger logger = LoggerFactory.getLogger(WikiSectionRenderer.class);
	
	int lvl;
	
	//TODO FIX
//	public WikiSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl) {
//		super(cmm, dsm);
//		this.lvl = lvl;
//	}
	
	public void render(Section section) throws OfxAuthoringException
	{
		
		//Rekursiver Aufruf des Sectionrenderers, um verschiedene Ebenen (z.B. Untertitel) anzuzeigen
		for (Object o : section.getContent())
		{
			if(o instanceof Title)
			{
				renderTitle((Title)o,lvl);
			}
		}
		
		//Prüfung auf Inhalt
		for (Object o : section.getContent()){
			if(o instanceof Section){renderSection((Section)o);}
			else if(o instanceof String){txt.add((String)o);}
			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o);}
			else if(o instanceof List)
			{
				//TODO ListRenderer
			}
			
		}
	}
	
	//TODO FIX
	private void renderSection(Section section) throws OfxAuthoringException
	{
//		WikiSectionRenderer wsr = new WikiSectionRenderer(cmm, dsm, lvl++);
//		wsr.render(section);
//		renderer.add(wsr);
	}
	
	//TODO FIX
	private void renderTitle(Title title, int lvl) throws OfxAuthoringException
	{
//		WikiTitleRenderer wtr = new WikiTitleRenderer(cmm, dsm);
//		wtr.render(title, lvl);
//		renderer.add(wtr);
	}

}

