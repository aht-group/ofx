package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.openfuxml.renderer.latex.preamble.packages.ReportLatexPackages;
import org.openfuxml.renderer.latex.preamble.packages.SimpleLatexPackages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLatexDocument extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLatexDocument.class);
	
	public AbstractLatexDocument(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void simplePackages()
	{
		SimpleLatexPackages p = new SimpleLatexPackages(cp);
		p.render();
		renderer.add(p);
	}
	
	public void reportPackages()
	{
		ReportLatexPackages p = new ReportLatexPackages(cp);
		p.render();
		renderer.add(p);
	}
	
	public void graphicsPath(String... path)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\\graphicspath{");
		for(String p : path)
		{
			sb.append("{").append(p).append("/}");
		}
		sb.append("}");
		
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Graphics Path"));
		r.line(sb.toString());

		renderer.add(r);
	}
	
	public void chapterSectionMarks()
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Chapter and Section makrs (Impact of command needs to be documented)"));
		r.line("\\renewcommand{\\chaptermark}[1]{\\markboth{\\thechapter.\\ #1}{}}");
		r.line("\\renewcommand{\\sectionmark}[1]{\\markright{\\thesection\\ #1}}");
		renderer.add(r);
	}
	
	public void fancyHeader(String left, String right)
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Fancy Header"));
		r.line("\\fancyhead[LE]{"+left+"}");
		r.line("\\fancyhead[LO]{\\bfseries \\nouppercase{\\rightmark}}");
		r.line("\\fancyhead[RE]{\\bfseries \\nouppercase{\\leftmark}}");
		r.line("\\fancyhead[RO]{"+right+"}");
		renderer.add(r);
	}
	
	public void fancyFooter(String text)
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Fancy Footer"));
		r.line("\\fancyfoot{}");
		r.line("\\fancyfoot[LE,RO]{\\bfseries \\thepage}");
		r.line("\\fancyfoot[RE]{\\nouppercase{"+text+"}}");
		r.line("\\fancyfoot[LO]{\\nouppercase{\\today}}");
		r.line("\\fancypagestyle{plain}{}");
		renderer.add(r);
	}
	
	public void draft(boolean value)
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Draft true/false"));
		r.line("\\newboolean{draft}\\setboolean{draft}{"+value+"}");
		renderer.add(r);
	}
	
	public void hyphenation()
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line(LatexCommentRenderer.line("Hyphenation"));
		r.line("\\hyphenation{}");
		renderer.add(r);
	}
	
	public void beginDocument()
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line("\\begin{document}");
		renderer.add(r);
	}
	
	public void endDocument()
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line("\\end{document}");
		renderer.add(r);
	}
	
	public void include(String f)
	{
		StringRenderer r = new StringRenderer();
		r.line("");
		r.line("\\include{"+f+"}");
		renderer.add(r);
	}
	
	public String getDefaultSectionHeaderName(int lvl)
	{
		if      (lvl==0){return "chapter";}
		else if (lvl==1){return "section";}
		else if (lvl==2){return "subsection";}
		else if (lvl==3){return "subsubsection";}
		else if (lvl==4){return "paragraph";}
		else if (lvl==5){return "subparagraph";}
		
		logger.warn("Level "+lvl+" not supported by "+LatexArticle.class.getSimpleName());
		return "section";
	}
}