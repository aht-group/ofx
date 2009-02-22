package org.openfuxml.model.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.openfuxml.model.jaxb.Productionresult;

@Entity
public class OfxProductionResult implements Serializable
{
	public static final long serialVersionUID=1;
	
	private int id;
	private Productionresult productionresult;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public Productionresult getProductionresult() {return productionresult;}
	public void setProductionresult(Productionresult productionresult) {this.productionresult = productionresult;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
