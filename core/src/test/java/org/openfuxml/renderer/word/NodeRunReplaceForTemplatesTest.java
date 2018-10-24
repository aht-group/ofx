package org.openfuxml.renderer.word;

import java.util.HashMap;
import java.util.Map;

import org.openfuxml.renderer.word.util.NodeRunReplace;

import com.aspose.words.Document;

public class NodeRunReplaceForTemplatesTest 
{
	public static void main(String[] args) throws Exception 
	{
		Document doc = new Document("src/test/resources/data/docxtemplate/momtemplate.docx");
		
		Map<String, String> replacementTags = new HashMap<String, String>();

		replacementTags.put("cNHinRtXt0", "Test Hallo BlaBlaBla");
		replacementTags.put("cNHinRtXt1", "BlaBlaBla BlaBlaBla BlaBlaBla");
		replacementTags.put("cNHinRtXt2", "Hallo Hallo Test Test");
		
		NodeRunReplace nRR = new NodeRunReplace(doc);
		nRR.replace(replacementTags);
		
		doc.save("target/docxtemplatetest.docx");
	}
}