package org.openfuxml.trancoder.html;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.list.Type;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(HtmlListTranscoder.class);
	
	public HtmlListTranscoder()
	{
		
	}
	
	public List transcode(Node list)
	{
		Type type = new Type();
		type.setOrdering("ordered");
		
		List ofxList = new List();
		ofxList.setType(type);
		for(Node node : list.childNodes())
		{
			Element e = (Element)node;
			Paragraph p = new Paragraph();
			
//			logger.info("nbsc"+e.text().contains("\u00a0"));
			String text = e.text();
			text = text.replaceAll("\u00a0", "");
			
			p.getContent().add(text);
			
			Item item = new Item();
			item.getContent().add(p);
			
			ofxList.getItem().add(item);
		}
		return ofxList;
	}

}
