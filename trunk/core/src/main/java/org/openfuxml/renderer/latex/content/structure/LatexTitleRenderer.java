package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTitleRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTitleRenderer.class);
	
	public LatexTitleRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public void render(Title title,Section section,int lvl,LatexPreamble latexPreamble)
	{
		logger.trace("Render title");
		if(title.isSetNumbering()){logger.warn("Ignoring numbring");}
		
		txt.add("");

		StringBuffer sb = new StringBuffer();
		sb.append("\\").append(latexPreamble.getSectionHeaderName(lvl));
        sb.append("{").append(TexSpecialChars.replace(title.getValue())).append("}");
		txt.add(sb.toString());

        if(section.isSetId())
        {
            txt.add("\\label{"+section.getId()+"}");
        }
	}
	
	public void render(Table table)
	{
		txt.add("\\caption{"+table.getTitle().getValue()+"}");
	}
	
	public void render(Image image)
	{
		txt.add("  \\caption{"+image.getTitle().getValue()+"}");
	}
}