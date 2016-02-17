package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.*;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TableProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Section build()
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(create());
		return xml;
	}

	public static Table create(){return create(4);}
	private static Table create(int words)
	{
		Table xml = OfxTableFactory.build(createHead(),createBody(words));
		xml.setId(li.getWords(words-2,words+2));
		return xml;
	}

	private static List<String> createHead()
	{
		List<String> h = new ArrayList<String>();
		h.add("First col");
		h.add("Second col");
		return h;
	}

	private static List<Object[]> createBody(int words)
	{
		List<Object[]> b = new ArrayList<Object[]>();
		String [] data = {li.getWords(words),li.getWords(words)};
		b.add(data);

		return b;
	}
}