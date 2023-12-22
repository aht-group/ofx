package org.openfuxml.renderer.latex.content.structure;

import java.util.Objects;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.LatexSectionHeaderNameFactory;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTitleRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTitleRenderer.class);
	
	public LatexTitleRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Title title, Section section, int lvl, LatexSectionHeaderNameFactory latexPreamble)
	{
		logger.trace("Render title");
		if(Objects.nonNull(title.isNumbering())) {logger.warn("Ignoring numbring");}
		
		txt.add("");

		StringBuffer sb = new StringBuffer();
		sb.append("\\").append(latexPreamble.getSectionHeaderName(lvl));
        sb.append("{").append(TexSpecialChars.replace(TxtTitleFactory.build(title))).append("}");
		txt.add(sb.toString());

        if(Objects.nonNull(section.getId()))
        {
            txt.add("\\label{"+section.getId()+"}");
        }
	}
	
	public void render(Table table)
	{
		txt.add("\\caption{"+TexSpecialChars.replace(TxtTitleFactory.build(table.getTitle()))+"}");
	}
	
	public void render(Image image)
	{
		txt.add("  \\caption{"+TexSpecialChars.replace(TxtTitleFactory.build(image.getTitle()))+"}");
	}
}