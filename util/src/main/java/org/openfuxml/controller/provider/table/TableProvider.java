package org.openfuxml.controller.provider.table;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.layout.Width;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.controller.provider.DemoContentProvider;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.openfuxml.model.xml.core.table.Columns;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TableProvider.class);
	
	public static Section build()
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(create());
		return xml;
	}

	public static Section build(int col, int row)
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(create(col, row));
		return xml;
	}

	public static Section buildWithoutSpecification()
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(createSimple());
		return xml;
	}

	public static Section buildWithoutSpecification(int col, int row)
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(createSimple(col, row));
		return xml;
	}

	public static Table createSimple(){return createSimple(4,2,1);}
	public static Table createSimple(int col, int row){return createSimple(4,col,row);}
	private static Table createSimple(int words, int col, int row)
	{
		Table xml = XmlTableFactory.build(createHead(col),createBody(words,col,row));
		xml.setTitle(title());
		xml.setId(DemoContentProvider.li.getWords(words-3,words+2));
		return xml;
	}
	public static Table create(){return create(4,2,1);}
	public static Table create(int col, int row){return create(4,col,row);}
	private static Table create(int words, int col, int row)
	{
		Table xml = XmlTableFactory.build(createHead(col),createBody(words,col,row));
		xml.setTitle(title());
		xml.setSpecification(specifications());
		xml.setId(DemoContentProvider.li.getWords(words-3,words+2));
		return xml;
	}

	private static List<String> createHead(int col)
	{
		List<String> h = new ArrayList<String>();
		while(col > 0)
			h.add("Column" + col--);
		return h;
	}

	private static List<Object[]> createBody(int words, int col, int row)
	{
		List<Object[]> b = new ArrayList<Object[]>();
		while(row > 0){
			int c = col;
			String [] data = new String [col];
			while(c > 0){
				data [--c] = DemoContentProvider.li.getWords(words);
			}
			b.add(data);
			row--;
		}
		return b;
	}

	private static Specification specifications()
	{
		Columns c = new Columns();
		XmlColumnFactory.add(c,XmlAlignmentFactory.Horizontal.center);
		c.getColumn().add(XmlColumnFactory.percentage(55d));
		XmlColumnFactory.add(c,XmlAlignmentFactory.Horizontal.right);
		Width w = new Width(); w.setValue(30d);w.setUnit("percentage");
		c.getColumn().get(2).setWidth(w);
		Specification spezi = new Specification();
		spezi.setColumns(c);
		return spezi;
	}

	private static Title title()
	{
		Title t = new Title();
		t.getContent().add(DemoContentProvider.li.getWords(3));
		return t;
	}
}