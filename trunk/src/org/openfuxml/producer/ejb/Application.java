package org.openfuxml.producer.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.openfuxml.communication.cluster.ejb.EjbObject;

@Entity
public class Application implements Serializable,EjbObject
{
	static final long serialVersionUID=1;
	
	private int id;
	private AvailableApplications availableApplications;
	private String name;
	private int anzCores;

	public int getAnzCores() {return anzCores;}
	public void setAnzCores(int anzCores) {this.anzCores = anzCores;}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@ManyToOne
	@JoinColumn(name="availableApplications_id", nullable=false)
	public AvailableApplications getAvailableApplications() {return availableApplications;}
	public void setAvailableApplications(AvailableApplications availableApplications) {this.availableApplications = availableApplications;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}	
}
