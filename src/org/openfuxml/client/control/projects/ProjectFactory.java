package org.openfuxml.client.control.projects;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.model.ejb.OfxProject;

public class ProjectFactory
{
	public ProjectFactory()
	{
		
	}
	
	public List<OfxProject> lProjects()
	{
		ArrayList<OfxProject> result = new ArrayList<OfxProject>();
		OfxProject ofxP = new OfxProject();
			ofxP.setName("Test");
		result.add(ofxP);
		return result;
	}
}
