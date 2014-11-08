package org.openfuxml.renderer.latex.content.listing;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListingRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListingRenderer.class);
	
	public LatexListingRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
        preTxt.add("");
        postTxt.add("");
	}
	
	public void render(Listing listing) throws OfxAuthoringException
	{

        StringBuffer sb = new StringBuffer();
        sb.append("\\lstset{language=XML");
        sb.append(",basicstyle=\\scriptsize");
        sb.append(",tabsize=2");
        sb.append(",frame=none");
        sb.append(",backgroundcolor=\\color{lgrau}");
        sb.append(",numbers=none}");
		txt.add(sb.toString());
		
		if(listing.isSetExternal())
		{
			StringBuffer sbInput = new StringBuffer();
			sbInput.append("\\lstinputlisting{");
			sbInput.append(listing.getExternal());
			sbInput.append("}");
			txt.add(sbInput.toString());
		}
		else
		{
			txt.add("\\begin{lstlisting}");
	        txt.add(listing.getRaw().getValue());
	        txt.add("\\end{lstlisting}");
		}
	}
}
