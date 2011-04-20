package org.openfuxml.renderer;

import java.io.File;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.processor.latex.OfxLatexRenderer;
import org.openfuxml.renderer.processor.pre.OfxPreProcessor.DirCode;
import org.openfuxml.renderer.processor.pre.OfxPreProcessor.FileCode;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.openfuxml.xml.renderer.cmp.Pdf;

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
					phaseLatex(pdf, cmpConfigUtil.getFile(cmp.getSource().getDirs(), DirCode.content.toString(), FileCode.target.toString()));
				}
				catch (OfxConfigurationException e){logger.error(e);}
				i++;
			}	
		}
	}
	
	private void phaseLatex(Pdf pdf, File fSrc) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxLatexRenderer renderer = new OfxLatexRenderer(cmp.getTargets().getPdf().get(0));
  		renderer.render(fSrc.getAbsolutePath());
		
		File dstFile = cmpConfigUtil.getFile(pdf.getDirs(), PdfDir.latex.toString(), PdfFile.latex.toString());
		
		logger.debug("pdf."+pdf.getCode()+" dstFile="+dstFile.getAbsolutePath());
		
		ExlpTxtWriter txtWriter = new ExlpTxtWriter();
		txtWriter.add(renderer.getContent());
		txtWriter.add("test");
		txtWriter.writeFile(dstFile);
	}
}