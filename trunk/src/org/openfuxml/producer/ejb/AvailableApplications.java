package org.openfuxml.producer.ejb;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.communication.cluster.ejb.Host;

@Entity
public class AvailableApplications implements Serializable,EjbObject
{
	static final long serialVersionUID=2;
	
	private int id;
	private Date record;
	private Host host;
	private Collection<Application> applications;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="availableApplications")
	public Collection<Application> getApplications() {return applications;}
	public void setApplications(Collection<Application> applications) {this.applications = applications;}
	
	@ManyToOne
	public Host getHost() {return host;}
	public void setHost(Host host) {this.host = host;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public void addApplication(Application a)
	{
		if(applications==null){applications = new ArrayList<Application>();}
		applications.add(a);
	}
	


}
