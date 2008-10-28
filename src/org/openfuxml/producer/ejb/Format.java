package org.openfuxml.producer.ejb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.jdom.Document;
import org.jdom.Element;

import de.kisner.util.xml.XmlElementNotFoundException;
import de.kisner.util.xml.XmlObject;

@Entity
public class Format implements Serializable
{
	static final long serialVersionUID=1;
	
	private int id;
	private AvailableFormats availableFormats;
	private Date record;
	private int anzCores;
	private String formatId,outputformat,title,type;
	private String buildFile,description,author;
	private ArrayList<String> alRequirements;
	private Collection<FormatOption> formatOptions;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@ManyToOne
	@JoinColumn(name="availableFormats_id", nullable=false)
	public AvailableFormats getAvailableFormats() {return availableFormats;}
	public void setAvailableFormats(AvailableFormats availableFormats) {this.availableFormats = availableFormats;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="availableFormat")
	public Collection<FormatOption> getFormatOptions() {return formatOptions;}
	public void setFormatOptions(Collection<FormatOption> formatOptions) {this.formatOptions = formatOptions;}
	
	public ArrayList<String> getAlRequirements() {return alRequirements;}
	public void setAlRequirements(ArrayList<String> alRequirements) {this.alRequirements = alRequirements;}
	
	public String getBuildFile() {return buildFile;}
	public void setBuildFile(String buildFile) {this.buildFile = buildFile;}
	
	public String getFormatId() {return formatId;}
	public void setFormatId(String formatId) {this.formatId = formatId;}
	
	public String getOutputformat() {return outputformat;}
	public void setOutputformat(String outputformat) {this.outputformat = outputformat;}
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public String getAuthor() {return author;}
	public void setAuthor(String author) {this.author = author;}
	
	public int getAnzCores() {return anzCores;}
	public void setAnzCores(int anzCores) {this.anzCores = anzCores;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public void loadXML(File xmlFile) throws XmlElementNotFoundException
	{
		XmlObject xO = new XmlObject();
		xO.load(xmlFile);
		formatId=xO.getAttribute("", "id");
		title=xO.getAttribute("", "title");
		outputformat=xO.getAttribute("", "outputformat");
		type=xO.getAttribute("", "type");
		buildFile=xO.getAttribute("buildfile", "fileref");
		alRequirements = xO.getTexts("requires/requirement");
		description = xO.getText("description");
		author = xO.getText("author");
		formatOptions = new ArrayList<FormatOption>();
		for(Element option : xO.getElements("options/option"))
		{
			FormatOption afo = new FormatOption();
			afo.init(option);
			afo.setAvailableFormat(this);
			formatOptions.add(afo);
		}
	}
	
	public Document toXmlDoc()
	{
		Document xmlDoc = new Document(toXmlElement());
		return xmlDoc;
	}
	
	public Element toXmlElement()
	{
		Element elFormat = new Element("format");
		elFormat.setAttribute("id", formatId);
		elFormat.setAttribute("title", title);
		elFormat.setAttribute("outputformat", outputformat);
		elFormat.setAttribute("type", type);
		
		Element elBuildfile = new Element("buildfile");
		elBuildfile.setAttribute("fileref",buildFile);
		elFormat.addContent(elBuildfile);
		
		if(alRequirements!=null && alRequirements.size()>0)
		{
			Element requires=new Element("requires");
			for(String s : alRequirements)
			{
				Element requirement = new Element("requirement");
				requirement.setText(s);
				requires.addContent(requirement);
			}
			elFormat.addContent(requires);
		}
		
		Element elDescription = new Element("description");
		elDescription.setText(description);
		elFormat.addContent(elDescription);
		
		Element elAuthor = new Element("author");
		elAuthor.setText(author);
		elFormat.addContent(elAuthor);
		
		if(formatOptions!=null && formatOptions.size()>0)
		{
			Element options = new Element("options");
			for(FormatOption afo : formatOptions)
			{
				options.addContent(afo.toXML());
			}
			elFormat.addContent(options);
		}
		return elFormat;
	}

	public static Document getAvailableFormatsDoc(ArrayList<Format> alAfs)
	{
		Element elAvailableFormats =new Element("availableFormats");
		for(Format af : alAfs){elAvailableFormats.addContent(af.toXmlElement());}
		Document xmlDoc = new Document(elAvailableFormats);
		return xmlDoc;
	}	
}
