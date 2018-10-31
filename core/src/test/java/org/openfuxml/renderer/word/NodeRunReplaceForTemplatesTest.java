package org.openfuxml.renderer.word;

import java.util.HashMap;
import java.util.Map;

import org.openfuxml.renderer.word.util.NodeRunReplace;

import com.aspose.words.Document;

public class NodeRunReplaceForTemplatesTest 
{
	public static void main(String[] args) throws Exception 
	{
		Document doc = new Document("src/test/resources/data/docxtemplate/AHTCVKfW.docx");
		
		Map<String, String> replacementTags = new HashMap<String, String>();

		replacementTags.put("CNHINRTXT10", "Chef for Word in Java");
		replacementTags.put("CNHINRTXT11", "Petersen");
		replacementTags.put("CNHINRTXT12", "Lasse");
		replacementTags.put("CNHINRTXT13", "08/04/1980");
		replacementTags.put("CNHINRTXT14", "German");
		replacementTags.put("CNHINRTXT15", "Single");
		replacementTags.put("CNHINRTXT16", "BFZ");
		replacementTags.put("CNHINRTXT17", "2012 - 2014");
		replacementTags.put("CNHINRTXT18", "Automatisierungstechniker");
		replacementTags.put("CNHINRTXT19", " ");
		replacementTags.put("CNHINRTXT20", "ComCave College");
		replacementTags.put("CNHINRTXT21", "2016-2018");
		replacementTags.put("CNHINRTXT22", "IT Anwendungsentwickler");
		replacementTags.put("CNHINRTXT23", " ");
		replacementTags.put("CNHINRTXT24", "-");
		replacementTags.put("CNHINRTXT25", "MT");
		replacementTags.put("CNHINRTXT26", "MT");
		replacementTags.put("CNHINRTXT27", "MT");
		replacementTags.put("CNHINRTXT28", "5");
		replacementTags.put("CNHINRTXT29", "4");
		replacementTags.put("CNHINRTXT30", "4");
		replacementTags.put("CNHINRTXT31", "0");
		replacementTags.put("CNHINRTXT32", "0");
		replacementTags.put("CNHINRTXT33", "0");
		replacementTags.put("CNHINRTXT34", "-");
		replacementTags.put("CNHINRTXT35", "-");
		replacementTags.put("CNHINRTXT36", "-");
		replacementTags.put("CNHINRTXT37", "-");
		replacementTags.put("CNHINRTXT38", "-");
		replacementTags.put("CNHINRTXT39", "-");
		replacementTags.put("CNHINRTXT40", "Anwendungsentwickler");
		replacementTags.put("CNHINRTXT41", "1 Year");
		replacementTags.put("CNHINRTXT42", "02/2018");
		replacementTags.put("CNHINRTXT43", "dev");
		replacementTags.put("CNHINRTXT44", "Lasse");
		replacementTags.put("CNHINRTXT45", "Lasse");
	
		
		NodeRunReplace nRR = new NodeRunReplace(doc);
		nRR.replace(replacementTags);
		
		doc.save("target/AHTCVKfWtest.docx");
	}
}