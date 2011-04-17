package org.openfuxml.renderer.data.exception;

import java.io.Serializable;

@Deprecated //Avoid dependencies from wiki->core
public class OfxAuthoringException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxAuthoringException() 
	{ 
	} 
 
	public OfxAuthoringException(String s) 
	{ 
		super(s); 
	} 
}
