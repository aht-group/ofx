package org.openfuxml.renderer.html;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;
import org.openfuxml.content.editorial.Acronyms;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class OfxMultiLangHTMLWriter
{
	final static Logger logger = LoggerFactory.getLogger(OfxMultiLangHTMLWriter.class);

	private String[] keys;

	private File baseHTML;
	private RelativePathFactory rpf;

	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;

	public OfxMultiLangHTMLWriter(File baseHTML, String[] keys, CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		this.baseHTML=baseHTML;
		this.keys=keys;
		this.cmm=cmm;
		this.dsm=dsm;
		
		dirTable = "table";
		logger.info("Base Directory for "+OfxMultiLangHTMLWriter.class.getSimpleName()+": "+baseHTML.getAbsolutePath());
		rpf = new RelativePathFactory(baseHTML);
	}
	
	private String dirTable;
	public String getDirTable() {return dirTable;}
	public void setDirTable(String dirTable) {this.dirTable = dirTable;}

	public void table(String fileName, Table table) throws OfxAuthoringException, IOException {table(fileName,table,dirTable);}
	public void table(String fileName, Table table, String myTableDir) throws OfxAuthoringException, IOException
	{
		for(String lang : keys)
		{
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			HtmlTableRenderer tableRenderer = new HtmlTableRenderer(cmm,dsm);
			tableRenderer.render(null,omf.filterLang(table));
			
			File f = buildFile(lang+"/"+myTableDir+"/"+fileName);
			logger.trace(f.getAbsolutePath());
			StringWriter sw = new StringWriter();
			tableRenderer.write(sw);
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
	public void section(int sectionLevel,String fileName,Section section) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		for(String lang : keys)
		{
			File f = buildFile(lang+"/section/"+fileName);
			logger.trace(f.getAbsolutePath());
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			HtmlSectionRenderer sectionRenderer = new HtmlSectionRenderer(cmm,dsm,sectionLevel);
			Section sectionFiltered = omf.filterLang(section);
			sectionRenderer.render(null, sectionFiltered);

			StringWriter sw = new StringWriter();
			sectionRenderer.write(sw);
			
			StringBuffer sb = new StringBuffer();
			sb.append("Writing ").append(Section.class.getSimpleName());
			if(section.isSetId())
			{
				sb.append(" (").append(section.getId()).append(")");
			}
			sb.append(" to ").append(rpf.relativate(f));
			
			logger.debug(sb.toString());
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
//	public void glossary(Glossary glossary) throws OfxAuthoringException, IOException
//	{
//		for(String lang : keys)
//		{
//			File f = buildFile(lang+"/editorial/glossary");
//			logger.info(f.getAbsolutePath());
//			OfxLangFilter omf = new OfxLangFilter(lang);
//
//			LatexGlossaryRenderer r = new LatexGlossaryRenderer(cmm,dsm);
//			r.render(omf.filterLang(glossary));
//
//			StringWriter sw = new StringWriter();
//			r.write(sw);
//			logger.trace("Writing to : "+f.getAbsolutePath());
//			StringIO.writeTxt(f, sw.toString());
//		}
//	}
//
//	public void acronym(Acronyms acronyms) throws OfxAuthoringException, IOException
//	{
//		for(String lang : keys)
//		{
//			File f = buildFile(lang+"/editorial/acronyms");
//			logger.info(f.getAbsolutePath());
//			OfxLangFilter omf = new OfxLangFilter(lang);
//
//			LatexAcronymRenderer r = new LatexAcronymRenderer(cmm,dsm);
//			r.render(omf.filterLang(acronyms));
//
//			StringWriter sw = new StringWriter();
//			r.write(sw);
//			logger.trace("Writing to : "+f.getAbsolutePath());
//			StringIO.writeTxt(f, sw.toString());
//		}
//	}
	
	private File buildFile(String fileName)
	{
		if(!fileName.endsWith(".html")){fileName = fileName+".html";}
		return new File(baseHTML,fileName);
	}
}