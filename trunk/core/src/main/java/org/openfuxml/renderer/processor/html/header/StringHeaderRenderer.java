package org.openfuxml.renderer.processor.html.header;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;

import org.jdom.Content;
import org.jdom.Text;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.xml.xpath.content.SectionXpath;

public class StringHeaderRenderer implements OfxHeaderRenderer
{
	public Content render(Section section)
	{
		Title title = null;
		try {
			title = SectionXpath.getTitle(section);
		} catch (ExlpXpathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Text(title.getValue());
	}
}