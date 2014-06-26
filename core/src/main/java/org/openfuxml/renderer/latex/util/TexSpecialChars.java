package org.openfuxml.renderer.latex.util;

import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexSpecialChars extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TexSpecialChars.class);
	
	public static String replace(String s)
	{		
//		s=s.replaceAll("\\\\", "/");
		s=s.replaceAll("#", "\\\\#");
		s=s.replaceAll("∞","\\$ \\\\inf \\$");
		s=s.replaceAll("&", "\\\\&");
        s=s.replaceAll("_", "\\\\_");
		return s;

	}
}
