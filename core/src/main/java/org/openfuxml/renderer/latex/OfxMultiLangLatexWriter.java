package org.openfuxml.renderer.latex;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

import org.openfuxml.content.editorial.Acronyms;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.latex.content.editorial.LatexAcronymRenderer;
import org.openfuxml.renderer.latex.content.editorial.LatexGlossaryRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;

public class OfxMultiLangLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(OfxMultiLangLatexWriter.class);
	
	private String[] keys;
	
	private File baseLatex;
	private RelativePathFactory rpf;
	
	private ConfigurationProvider cp;
	
	public OfxMultiLangLatexWriter(File baseLatex, String[] keys, ConfigurationProvider cp)
	{
		this.baseLatex=baseLatex;
		this.keys=keys;
		this.cp=cp;
		
		dirTable = "table";
		logger.info("Base Directory for "+OfxMultiLangLatexWriter.class.getSimpleName()+": "+baseLatex.getAbsolutePath());
		rpf = new RelativePathFactory(baseLatex);
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
			
			LatexTableRenderer tableRenderer = new LatexTableRenderer(cp);
			tableRenderer.setPreBlankLine(false);
			tableRenderer.render(omf.filterLang(table));
			
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
			
			LatexSectionRenderer sectionRenderer = new LatexSectionRenderer(cp,sectionLevel,new LatexPreamble(cp));
			Section sectionFiltered = omf.filterLang(section);
			sectionRenderer.render(sectionFiltered);

			StringWriter sw = new StringWriter();
			sectionRenderer.write(sw);
			
			StringBuffer sb = new StringBuffer();
			sb.append("Writing ").append(Section.class.getSimpleName());
			if(Objects.nonNull(section.getId()))
			{
				sb.append(" (").append(section.getId()).append(")");
			}
			sb.append(" to ").append(rpf.relativate(f));
			
			logger.debug(sb.toString());
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
	public void glossary(Glossary glossary) throws OfxAuthoringException, IOException
	{
		for(String lang : keys)
		{
			File f = buildFile(lang+"/editorial/glossary");
			logger.info(f.getAbsolutePath());
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			LatexGlossaryRenderer r = new LatexGlossaryRenderer(cp);
			r.render(omf.filterLang(glossary));

			StringWriter sw = new StringWriter();
			r.write(sw);
			logger.trace("Writing to : "+f.getAbsolutePath());
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
	public void acronym(Acronyms acronyms) throws OfxAuthoringException, IOException
	{
		for(String lang : keys)
		{
			File f = buildFile(lang+"/editorial/acronyms");
			logger.info(f.getAbsolutePath());
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			LatexAcronymRenderer r = new LatexAcronymRenderer(cp);
			r.render(omf.filterLang(acronyms));

			StringWriter sw = new StringWriter();
			r.write(sw);
			logger.trace("Writing to : "+f.getAbsolutePath());
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
	private File buildFile(String fileName)
	{
		if(!fileName.endsWith(".tex")){fileName = fileName+".tex";}
		return new File(baseLatex,fileName);
	}
}