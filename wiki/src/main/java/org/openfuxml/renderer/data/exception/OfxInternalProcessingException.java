package org.openfuxml.renderer.data.exception;

import java.io.Serializable;

@Deprecated //Avoid dependencies from wiki->core
public class OfxInternalProcessingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxInternalProcessingException() 
	{ 
	} 
 
	public OfxInternalProcessingException(String s) 
	{ 
		super(s); 
	} 
}
