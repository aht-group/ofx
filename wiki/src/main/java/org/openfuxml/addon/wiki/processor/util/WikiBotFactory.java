package org.openfuxml.addon.wiki.processor.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openfuxml.addon.wiki.data.jaxb.Server;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WikiBotFactory
{
	final static Logger logger = LoggerFactory.getLogger(WikiBotFactory.class);
	
	private URL url;
	
	@SuppressWarnings("unused")
	private String httpUsername,httpPassword;
	
	@SuppressWarnings("unused")
	private String wikiUsername,wikiPassword;
	
	private MediaWikiBot bot;

	public WikiBotFactory()
	{
		setUrl("http://de.wikipedia.org/w/");
	}
	
	public WikiBotFactory(Servers wikiServers)
	{
		Server wikiServer = wikiServers.getServer().get(0); 
		try{url = new URL(wikiServer.getUrl());}
		catch (MalformedURLException e) {}
	}
	
	public void setUrl(String wikiURL)
	{
		try{url = new URL(wikiURL);}
		catch (MalformedURLException e) {}
	}
	
	public void setHttpDigestAuth(String httpUsername, String httpPassword)
	{
		this.httpUsername=httpUsername;
		this.httpPassword=httpPassword;
	}
	
	public void setWikiAuth(String wikiUsername, String wikiPassword)
	{
		this.wikiUsername=wikiUsername;
		this.wikiPassword=wikiPassword;
	}
	
	public MediaWikiBot getBot()
	{
		if(bot==null)
		{
			logger.debug("Creating MediaWikiBot");
			bot = createBot();
		}
		return bot;
	}
	
	public MediaWikiBot createBot()
	{
		MediaWikiBot bot = null;
		try
		{
			bot = new MediaWikiBot(url);
			logger.info("Password deactivated");
//			if(httpUsername!=null && httpPassword!=null){bot.setConnection(createActionClient());}
//			if(wikiUsername!=null && wikiPassword!=null){bot.login(wikiUsername, wikiPassword);}
		}
//		catch (MalformedURLException e) {logger.error("",e);}
		catch (ActionException e) {logger.error("",e);}
		return bot;
	}
	
//	private HttpActionClient createActionClient() throws MalformedURLException
//	{		
//		
//		AuthScope scope = new AuthScope(url.getHost(), url.getDefaultPort());
//		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(httpUsername, httpPassword);
//		AbstractHttpClient httpclient = new DefaultHttpClient();
//		httpclient.getCredentialsProvider().setCredentials(scope, credentials);
//		
//		HttpActionClient actionClient = new HttpActionClient(httpclient, url);
//		return actionClient;
//	}
}