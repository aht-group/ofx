package org.openfuxml.model.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.openfuxml.model.jaxb.Sessionpreferences;

@Entity
public class OfxProductionRequest implements Serializable
{
	public static final long serialVersionUID=1;
	public static enum Typ {ENTIITES,PRODUCE}
	
	private int id;
	private Sessionpreferences sessionpreferences;
	private Typ typ;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public Sessionpreferences getSessionpreferences() {return sessionpreferences;}
	public void setSessionpreferences(Sessionpreferences sessionpreferences) {this.sessionpreferences = sessionpreferences;}
	
	public Typ getTyp() {return typ;}
	public void setTyp(Typ typ) {this.typ = typ;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
