package org.openfuxml.renderer.processor.latex.content.table.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.layout.Alignment;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class LatexTabluar extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexTabluar.class);
	
	public static String getTableAlignment(String sAlignment)
	{
		Alignment alignment = Alignment.valueOf(sAlignment.toUpperCase());
		switch(alignment)
		{
			case LEFT: return "l";
			case RIGHT: return "r";
			case CENTER: return "c";
		}
		
		return "l";
	}
}
