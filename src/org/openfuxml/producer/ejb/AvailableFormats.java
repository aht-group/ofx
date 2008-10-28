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
public class AvailableFormats implements Serializable,EjbObject
{
	static final long serialVersionUID=1;
	
	private int id;
	private Date record;
	private Host host;
	private Collection<Format> formats;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="availableFormats")
	public Collection<Format> getFormats() {return formats;}
	public void setFormats(Collection<Format> formats) {this.formats = formats;}	
	
	@ManyToOne
	public Host getHost() {return host;}
	public void setHost(Host host) {this.host = host;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}

	public void addFormat(Format f)
	{
		if(formats==null){formats = new ArrayList<Format>();}
		formats.add(f);
	}

}
