package org.openfuxml.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class Spawn
{
	static Logger logger = Logger.getLogger(Spawn.class);
	
	public static void exec(String cmd)
	{
		exec(cmd,null);
	}
	
	public static void exec(String cmd,String charSet)
	{
		try
		{
			logger.debug("Spawn: "+cmd);
			Process myProcess=Runtime.getRuntime().exec(cmd);
			InputStreamReader isr;
			if(charSet==null) {isr = new InputStreamReader(myProcess.getInputStream());}
			else {isr = new InputStreamReader(myProcess.getInputStream(),charSet);}
			BufferedReader in = new BufferedReader(isr);
			String Zeile;
			while ((Zeile = in.readLine()) != null)
			{
				logger.debug("Input:\t"+Zeile);
			}
			in.close();
			
			if(charSet==null) {isr = new InputStreamReader(myProcess.getErrorStream());}
			else {isr = new InputStreamReader(myProcess.getErrorStream(),charSet);}
			in = new BufferedReader(isr);
			while ((Zeile = in.readLine()) != null)
			{
				logger.debug("Error:\t"+Zeile);
			}
			in.close();
		}
		catch (IOException e) {logger.error("Fehler beim ausführen von: "+cmd,e);}
	}
	
}
