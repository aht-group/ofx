package org.openfuxml.renderer;

import java.io.File;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.renderer.data.jaxb.Pdf;
import org.openfuxml.renderer.processor.latex.OfxLatexRenderer;
import org.openfuxml.renderer.util.OfxRenderConfiguration;

public class OfxTargetRenderer
{
	public static enum PdfDir {latex};
	public static enum PdfFile {latex};
	
	static Log logger = LogFactory.getLog(OfxTargetRenderer.class);
		
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge,containerMerge,externalMerge,phaseTemplate,mergeTemplate};
	
	private OfxRenderConfiguration cmpConfigUtil;
	private Cmp cmp;
	
	public OfxTargetRenderer(OfxRenderConfiguration cmpConfigUtil)
	{
		this.cmpConfigUtil=cmpConfigUtil;
		cmp = cmpConfigUtil.getCmp();
	}
	
	public void renderTargets() throws OfxAuthoringException
	{
		if(cmp.getTargets().isSetPdf())
		{
			int i=0;
			for(Pdf pdf : cmp.getTargets().getPdf())
			{
				try
				{
					if(!pdf.isSetCode()){pdf.setCode(""+i);}
					phaseLatex(pdf, Phase.mergeTemplate);
				}
				catch (OfxConfigurationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}	
		}
	}
	
	private void phaseLatex(Pdf pdf,Phase phaseLoad) throws OfxAuthoringException, OfxConfigurationException
	{
		
		OfxLatexRenderer renderer = new OfxLatexRenderer(cmp.getTargets().getPdf().get(0));
//		renderer.render(new File(tmpDir,getPhaseXmlFileName(phaseLoad)).getAbsolutePath());
		
		File dstFile = cmpConfigUtil.getFile(pdf.getDirs(), PdfDir.latex.toString(), PdfFile.latex.toString());
		
		logger.debug("pdf."+pdf.getCode()+" dstFile="+dstFile.getAbsolutePath());
		
		ExlpTxtWriter txtWriter = new ExlpTxtWriter();
		txtWriter.add(renderer.getContent());
		txtWriter.add("test");
		txtWriter.writeFile(dstFile);
	}
}