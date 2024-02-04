package org.openfuxml.renderer.latex.content.table;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.model.xml.core.table.Body;
import org.openfuxml.model.xml.core.table.Columns;
import org.openfuxml.model.xml.core.table.Content;
import org.openfuxml.model.xml.core.table.Head;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.latex.table.TestLatexRowRenderer;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTableRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTableRenderer.class);
		
	private LatexTableRenderer renderer;
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTableRenderer(cp);
	}
	
	@After public void close(){renderer=null;}
	
	public static Table createTable()
	{
		int[] words = {1,20};
		return createTable(words);
	}
	public static Table createTable(int[] words)
	{
		Columns cols = new Columns();
		cols.getColumn().add(XmlColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		cols.getColumn().add(XmlColumnFactory.flex(10));
//		cols.getColumn().add(OfxColumnFactory.percentage(20));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		Body body = new Body();
		body.getRow().add(TestLatexRowRenderer.create(words));
		body.getRow().add(TestLatexRowRenderer.create(words));
		
		Content content = new Content();
		content.setHead(createTableHead());
		content.getBody().add(body);
		
		Table table = new Table();
		table.setTitle(XmlTitleFactory.build("TestTitle"));
		table.setSpecification(specification);
		table.setContent(content);
		return table;
	}
	
	public static Head createTableHead()
	{
		Head head = new Head();
		head.getRow().add(TestLatexRowRenderer.create());
		return head;
	}
	
    @Test(expected=OfxAuthoringException.class)
    public void withoutSpecification() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.setSpecification(null);
    	renderer.render(table);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void withoutContent() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.setContent(null);
    	renderer.render(table);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void withoutBody() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.getContent().getBody().clear();
    	renderer.render(table);
    }
    
    @Test
    public void noFloat() throws OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	table.getSpecification().setFloat(XmlFloatFactory.build(false));
    	
    	JaxbUtil.trace(table);
    	renderer.render(table);
    	List<String> content = renderer.getContent();
    	OfxContentDebugger.debug(content);
    	testLatex(content);
    }
    
    public static void main(String[] args) throws Exception
    {
    	Configuration config = OfxCoreBootstrap.init();
			
    	TestLatexTableRenderer.initLoremIpsum();
    	TestLatexTableRenderer test = new TestLatexTableRenderer();
    	test.initLatexTestEnvironment(config);
    	test.setEnvironment(true);
    	
//    	test.initRenderer();test.withoutSpecification();
    	test.initRenderer();test.noFloat();

    }
}