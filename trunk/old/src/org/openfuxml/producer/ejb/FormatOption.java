package org.openfuxml.producer.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jdom2.Element;

@Entity
public class FormatOption implements Serializable
{
	static final long serialVersionUID=1;
	
	private int id;
	private String displayname,name,type;
	private boolean value;
	private String description;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getDisplayname() {return displayname;}
	public void setDisplayname(String displayname) {this.displayname = displayname;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public boolean isValue() {return value;}
	public void setValue(boolean value) {this.value = value;}
	
	public void init(Element option)
	{
		name=option.getAttributeValue("name");
		type=option.getAttributeValue("type");
		value=new Boolean(option.getAttributeValue("value"));
		displayname=option.getAttributeValue("displayname");
		Element elDescription = option.getChild("description");
		if(elDescription!=null){description=elDescription.getText();}
	}
	
	public Element toXML()
	{
		Element option = new Element("option");
		option.setAttribute("name",name);
		if(type!=null){option.setAttribute("type",type);}
		option.setAttribute("value",""+value);
		if(displayname!=null){option.setAttribute("displayname",displayname);}
		
		if(description!=null)
		{
			Element elDescription = new Element("description");
			elDescription.addContent(description);
			option.addContent(elDescription);
		}
		return option;
		
	}
}
