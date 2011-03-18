package org.openfuxml.addon.wiki.util;

import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class WikiBotFactory
{
	static Log logger = LogFactory.getLog(WikiBotFactory.class);
	
	private URL url;
	private String httpUsername,httpPassword;
	private String wikiUsername,wikiPassword;
	
	public WikiBotFactory()
	{
		setUrl("http://de.wikipedia.org/w/");
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
	
	public MediaWikiBot createBot()
	{
		MediaWikiBot bot = null;
		try
		{
			bot = new MediaWikiBot(url);
			if(httpUsername!=null && httpPassword!=null){bot.setConnection(createActionClient());}
			if(wikiUsername!=null && wikiPassword!=null){bot.login(wikiUsername, wikiPassword);}
		}
		catch (MalformedURLException e) {logger.error(e);}
		catch (ActionException e) {logger.error(e);}
		return bot;
	}
	
	private HttpActionClient createActionClient() throws MalformedURLException
	{		
		
		AuthScope scope = new AuthScope(url.getHost(), url.getDefaultPort());
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(httpUsername, httpPassword);
		AbstractHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(scope, credentials);
		
		HttpActionClient actionClient = new HttpActionClient(httpclient, url);
		return actionClient;
	}
}