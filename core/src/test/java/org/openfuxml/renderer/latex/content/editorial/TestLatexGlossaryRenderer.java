package org.openfuxml.renderer.latex.content.editorial;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.editorial.XmlGlossaryFactory;
import org.openfuxml.factory.xml.editorial.XmlTermFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexGlossaryRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private LatexGlossaryRenderer renderer;
	private String dir = "editorial/glossary";
	
	@BeforeEach public void init()
	{
		renderer = new LatexGlossaryRenderer(cp);
	}
	@AfterEach public void close()
	{
		renderer=null;
	}
	
	public static Glossary create()
	{
		Glossary g = new Glossary();
    	g.getTerm().add(XmlTermFactory.buildC("c1", XmlGlossaryFactory.Classifier.name, "Code-1", "Description-1"));
    	g.getTerm().add(XmlTermFactory.buildC("c2", XmlGlossaryFactory.Classifier.text, "Code-2", "Description-2"));
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		g.setComment(comment);
    	
    	return g;
	}
	
	private static Paragraph paragraph()
	{
		Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(1)+" ");
    	p.getContent().add(XmlGlossaryFactory.build("c1"));
    	p.getContent().add(" "+li.getWords(3));
    	return p;
	}
	
    @Test public void glossaryList() throws IOException, OfxAuthoringException
    {    	
    	referenceFile = new File(rootDir,dir+"/glossary.txt");
    	renderer.render(create());
    	renderTest(renderer);
    }
    
    @Test public void glossary() throws IOException, OfxAuthoringException
    {    	
    	referenceFile = new File(rootDir,dir+"/glossary-1.txt");
    	LatexParagraphRenderer renderer = new LatexParagraphRenderer(cp,true);
    	renderer.render(paragraph());
    	renderTest(renderer);
    }
    
    @Test public void glossaryEmphasis() throws IOException, OfxAuthoringException
    {    	
//    	Emphasis e = OfxEmphasisFactory.italic("test");
    	
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(1)+" ");
    	p.getContent().add(XmlGlossaryFactory.build("c1"));
    	p.getContent().add(" "+li.getWords(3));
    	
    	referenceFile = new File(rootDir,dir+"/glossary-2.txt");
    	LatexParagraphRenderer renderer = new LatexParagraphRenderer(cp,true);
    	renderer.render(paragraph());
    	renderTest(renderer);
    }
    
    @Test
    public void termWithoutCode() throws OfxAuthoringException
    {
        Assertions.assertThrows(OfxAuthoringException.class, () -> {
    	Glossary g = create();
    	g.getTerm().add(XmlTermFactory.build(null, null, "name", "description"));
    	renderer.render(g);
        });
    }
    
    @Test
    public void termWithoutText() throws OfxAuthoringException
    {
        Assertions.assertThrows(OfxAuthoringException.class, () -> {
    	Glossary g = create();
    	g.getTerm().add(XmlTermFactory.build("code", null, null, "description"));
    	renderer.render(g);
        });
    }
    
    @Test
    public void termWithoutParagraph() throws OfxAuthoringException
    {
        Assertions.assertThrows(OfxAuthoringException.class, () -> {
    	Glossary g = create();
    	g.getTerm().add(XmlTermFactory.build("code", null, "name", null));
    	renderer.render(g);
        });
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
			
    	TestLatexGlossaryRenderer.initLoremIpsum();
    	TestLatexGlossaryRenderer test = new TestLatexGlossaryRenderer();
    	test.init();
    	test.setEnvironment(true);
//    	test.glossaryList();
//    	test.glossary();
    	test.glossaryEmphasis();
    }
}