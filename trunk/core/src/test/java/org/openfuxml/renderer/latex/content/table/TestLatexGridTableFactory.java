package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.table.util.TestLatexTabularUtil;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexGridTableFactory extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexGridTableFactory.class);
	
	private static enum Key {table,tableWithHead}
	
	private LatexGridTableRenderer renderer;
	private String dir = "grid";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexGridTableRenderer();
	}
	
	@After public void close(){renderer=null;}
	
	public static Table createTable()
	{
		Specification specification = new Specification();
		specification.setColumns(TestLatexTabularUtil.createColumns());

		Body body = new Body();
		body.getRow().add(TestLatexRowFactory.create());
		body.getRow().add(TestLatexRowFactory.create());
		
		Content content = new Content();
		content.getBody().add(body);
		
		Table table = new Table();
		table.setSpecification(specification);
		table.setContent(content);
		return table;
	}
	
	public static Head createTableHead()
	{
		Head head = new Head();
		head.getRow().add(TestLatexRowFactory.create());
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
    	table.getContent().unsetBody();
    	renderer.render(table);
    }

    @Test
    public void table() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	f = new File(rootDir,dir+"/"+Key.table+".tex");
    	renderer.render(table);
    	renderTest(renderer,f);
    }
    
    @Test
    public void withHead() throws IOException, OfxAuthoringException
    {
    	Table table = createTable();
    	table.getContent().setHead(createTableHead());
    	f = new File(rootDir,dir+"/"+Key.tableWithHead+".tex");
    	renderer.render(table);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexGridTableFactory.initLoremIpsum();
    	TestLatexGridTableFactory test = new TestLatexGridTableFactory();
    	test.setSaveReference(true);
    	
//    	test.initRenderer();test.withoutSpecification();
//    	test.initRenderer();test.table();
    	test.initRenderer();test.withHead();
    }
}