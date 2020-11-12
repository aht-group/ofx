package org.openfuxml.factory.txt;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtTitleFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtTitleFactory.class);
	
	public static String build(Title title)
	{
		StringBuffer sb = new StringBuffer();
		if(title!=null)
		{
			for(Object s : title.getContent())
			{
				if     (s instanceof String){sb.append(((String)s).trim());}
				else if(s instanceof Text) {sb.append(((Text)s).getValue().trim());}
				else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
			}
		}
		return sb.toString();
	}
}
