package org.openfuxml.renderer.latex.content.listing;

import java.util.Objects;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.ofx.Listing;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListingRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListingRenderer.class);
	
	public LatexListingRenderer(ConfigurationProvider cp)
	{
		super(cp);
        preTxt.add("");
        postTxt.add("");
	}
	
	public void render(Listing listing) throws OfxAuthoringException, OfxConfigurationException
	{
		dsm.apply(listing);
		
        StringBuffer sb = new StringBuffer();
        sb.append("\\lstset{language=XML");
        sb.append(",basicstyle=\\scriptsize");
        sb.append(",tabsize=2");
        sb.append(",frame=none");
        sb.append(",backgroundcolor=\\color{lgrau}");
        sb.append(",showstringspaces=false");
        
        if(Objects.nonNull(listing.isNumbering()) && listing.isNumbering()){sb.append(",numbers=left,numberstyle=\\scriptsize");}
        else{sb.append(",numbers=none");}
        
        if(Objects.nonNull(listing.isLinebreak()) && listing.isLinebreak()){sb.append(",breaklines=true,breakatwhitespace=true,postbreak=\\raisebox{0ex}[0ex][0ex]{\\ensuremath{\\hookrightarrow\\space}}");}
        
        sb.append("}");
		txt.add(sb.toString());
		
		if(Objects.nonNull(listing.getExternal()))
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
