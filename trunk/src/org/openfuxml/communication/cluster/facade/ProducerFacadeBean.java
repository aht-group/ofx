package org.openfuxml.communication.cluster.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.producer.ejb.Application;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.Format;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;

@Stateful
@Remote(ProducerFacade.class)
public class ProducerFacadeBean implements ProducerFacade, Serializable
{
	static Logger logger = Logger.getLogger(ProducerFacadeBean.class);
	static final long serialVersionUID=11;
	
	public ProducerFacadeBean()
	{
		logger.info("public Constructor. Version="+serialVersionUID);
	}
	
	@PersistenceContext (unitName="openfuxml")
	private EntityManager manager;
	
	public Object newObject(Object o) {manager.persist(o);return o;}
	public int newEjbObject(EjbObject ejbo) {manager.persist(ejbo);return ejbo.getId();}
	
	//neue Methoden
	public AvailableApplications getAvailableApplications() throws ProductionHandlerException
	{
		Hashtable<String,Application> htAvailableApplication = new Hashtable<String,Application>();
		try
		{
			Context ctx = new InitialContext();
			OpenFuxmlFacade fO = (OpenFuxmlFacade) ctx.lookup(OpenFuxmlFacadeBean.class.getSimpleName()+"/remote");
			for(Host h : fO.findLastHosts())
			{
				AvailableApplications aas = fO.findLastAvailableApplicationsforHost(h);
				for(Application a : aas.getApplications())
				{
					if(htAvailableApplication.containsKey(a.getName()))
					{
						int coresFound=htAvailableApplication.get(a.getName()).getAnzCores();
						int newCores=a.getAnzCores();
						a.setAnzCores(coresFound+newCores);
					}
					else{htAvailableApplication.put(a.getName(), a);}
				}
			}
			fO.close();
		}
		catch (NamingException e) {e.printStackTrace();}
		AvailableApplications aas = new AvailableApplications();
		for(Application a : htAvailableApplication.values()){aas.addApplication(a);}
		aas.setRecord(new Date());
		return aas;
	}
	
	public AvailableFormats getAvailableFormats(String application) throws  ProductionHandlerException
	{
		Hashtable<String,Format> htAvailableFormats = new Hashtable<String,Format>();
		try
		{
			Context ctx = new InitialContext();
			OpenFuxmlFacade fO = (OpenFuxmlFacade) ctx.lookup(OpenFuxmlFacadeBean.class.getSimpleName()+"/remote");
			for(Host h : fO.findLastHosts())
			{
				AvailableFormats afs = fO.findLastAvailableFormatsforHost(h);
				for(Format f : afs.getFormats())
				{
					if(htAvailableFormats.containsKey(f.getFormatId()))
					{
						int coresFound=htAvailableFormats.get(f.getFormatId()).getAnzCores();
						int newCores=f.getAnzCores();
						f.setAnzCores(coresFound+newCores);
					}
					else{htAvailableFormats.put(f.getFormatId(), f);}
				}
			}
			fO.close();
		}
		catch (NamingException e) {e.printStackTrace();}
		AvailableFormats afs = new AvailableFormats();
		for(Format f : htAvailableFormats.values()){afs.addFormat(f);}
		afs.setRecord(new Date());
		return afs;
	}
	public ProducedEntities invoke(ProductionRequest request) throws ProductionHandlerException
	{
		logger.error("invoke(org.openfuxml.producer.ejb.ProductionRequest request) noch nicht implementiert");
		return null;
	}
		
	@Remove
	public void close()
	{
		logger.info("close()");
	}
}