package org.openfuxml.producer.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.jdom.Element;
import org.openfuxml.communication.cluster.ejb.EjbObject;

@Entity
public class SyncLocation implements Serializable,EjbObject
{
	static final long serialVersionUID=1;
	public static enum Typ {REPOSITORY,OUTPUT};
	
	private int id;
	private ProductionRequest productionRequest;
	private Typ typ;
	private String url;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@ManyToOne
	public ProductionRequest getProductionRequest() {return productionRequest;}
	public void setProductionRequest(ProductionRequest productionRequest) {this.productionRequest = productionRequest;}
	
	public Typ getTyp() {return typ;}
	public void setTyp(Typ typ) {this.typ = typ;}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}		
	
	public void init(Element elTyp)
	{
		typ=Typ.valueOf(elTyp.getAttributeValue("typ"));
		url=elTyp.getText();
	}
	
	public Element toXML()
	{
		Element elUrl = new Element("url");
		elUrl.setAttribute("typ",typ.name());
		elUrl.setText(url);
				
		
		return elUrl;
	}
}
