package org.openfuxml.renderer.latex.document;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LatexDocument
{
	static Log logger = LogFactory.getLog(LatexDocument.class);
	
	private List<String> txt;
	
	public LatexDocument()
	{
		txt = new ArrayList<String>();
	}
	
	public List<String> render()
	{
		txt.add("\\begin{document}");
		txt.add("\\maketitle ");
		txt.add("\\end{document}");
		return txt;
	}
	
}
