package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class logic {
	
	private String getNodeValue(Element e, String str){
		Node n = e.getElementsByTagName(str).item(0);
		if(n != null){
			Node ns = n.getFirstChild();
			if(ns != null){
				return ns.getNodeValue();
			}
		}

		return "";
	}
	
	public Object[][] searchxml(String keyword, Object[][] info){
		
		try{
			for(int a = 0; a < 700; a++){
				for(int b = 0; b < 9; b++){
					info[a][b] = "";
				}
			}
			
			if(keyword.isEmpty())
			{
				return info;
			}
			
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	
	        File f = new File("cape.xml");
	
	        Document doc = builder.parse(f);
	         
	        Element root = doc.getDocumentElement();
	        
	        NodeList list = root.getElementsByTagName("capec:Attack_Pattern");
	        
	        int k = 0;
	         
	        for (int i = 0; i < list.getLength(); i++) {
	        	Node nnn = list.item(i);
	            Element n = (Element)nnn;
	            String content = nnn.getTextContent();
	            
	            
	            String [] strlistkeyword = keyword.split(" ");
	            
	            boolean flag = false;
	            int target = 0;
	            
	            for(int index = 0; index < strlistkeyword.length; index++){
	            	if(content.toLowerCase().contains(strlistkeyword[index].toLowerCase())){
	            		target++;
		            }
	            }
	            
	            if(target != strlistkeyword.length){
	            	continue;
	            }
	            
	            /*
	            if(!content.toLowerCase().contains(keyword.toLowerCase())){
	            	continue;
	            }
	            */
	            //ID
	            String id = "<html>";
	            id += n.getAttribute("ID");

	            info[k][0] = id;
	            //Name
	            String name = "<html>";
	            name += n.getAttribute("Name");
	            info[k][1] = name;

	            //Summary
	            String sumoutput = "<html>";
	            Element des = (Element)n.getElementsByTagName("capec:Description").item(0);
	            if(des != null){
	            	Element sum = (Element)des.getElementsByTagName("capec:Summary").item(0);
	            	if(sum != null){
	            		String txt = getNodeValue(sum, "capec:Text");
			            if(txt.isEmpty() == false){
			            	sumoutput += txt;
			            	sumoutput += "<br>";
			            }
			            
			            info[k][2] = sumoutput;
	            	}
		            
	            }
	            
	            // Typical severity
	            String severityoutput = "<html>";
	            Node severity = n.getElementsByTagName("capec:Typical_Severity").item(0);
	            if(severity != null){
	            	severityoutput +=severity.getFirstChild().getNodeValue();
		            info[k][3] = severityoutput;
	            }
	            
	            // Typical likelihood of exploit
	            String likeoutput = "<html>";
	            Element exploit = (Element)n.getElementsByTagName("capec:Typical_Likelihood_of_Exploit").item(0);
	            if(exploit != null){
		            String strlike = getNodeValue(exploit, "capec:Likelihood");
		            if(strlike.isEmpty() == false){
		            	likeoutput += strlike;
		            	likeoutput += "<br>";
		            }
		            String strexplain = getNodeValue(exploit, "capec:Explanation");
		            if(strexplain.isEmpty() == false){
		            	likeoutput += "Explanation: " + strexplain;
		            	likeoutput += "<br>";
		            }
		            
		            info[k][4] = likeoutput;
	            }
	            
	            
	            // Attack skills or knowledge required
	            String knowledgeoutput = "<html>";
	            Element knowledge = (Element)n.getElementsByTagName("capec:Attacker_Skills_or_Knowledge_Required").item(0);
	            if(knowledge != null){
	            	NodeList knowledgelist = knowledge.getElementsByTagName("capec:Attacker_Skill_or_Knowledge_Required");
		            
		            for(int j = 0; j < knowledgelist.getLength(); j++){
		            	Element knowledgesub = (Element) knowledgelist.item(j);
		            	if(knowledgesub != null){
			            	String strknowledgelvl = getNodeValue(knowledgesub, "capec:Skill_or_Knowledge_Level");
			            	if(strknowledgelvl.isEmpty() == false){
			            		knowledgeoutput += "skills or knowledge level: " + strknowledgelvl;
			            		knowledgeoutput += "<br>";
				            }
			            	
			            	Element knowledgetext = (Element)knowledgesub.getElementsByTagName("capec:Skill_or_Knowledge_Type").item(0);
			            	if(knowledgetext != null){
				            	String strknowledgetype = getNodeValue(knowledgetext, "capec:Text");
				            	
				            	if(strknowledgetype.isEmpty() == false){
				            		knowledgeoutput += "skills or knowledge type: " + strknowledgetype;
				            		knowledgeoutput += "<br>";
					            }
			            	}
		            	}
		            }
		            info[k][5] = knowledgeoutput;
	            }
	            
	            
	            // Related weakness
	            String weaknessoutput = "<html>";
	            Element weakness = (Element)n.getElementsByTagName("capec:Related_Weaknesses").item(0);
	            if(weakness != null){
	            	NodeList weaknesslist = weakness.getElementsByTagName("capec:Related_Weakness");
		            
		            for(int j = 0; j < weaknesslist.getLength(); j++){
		            	Element weaknesssub = (Element) weaknesslist.item(j);
		            	if(weaknesssub != null){
			            	String strweaknessID = getNodeValue(weaknesssub, "capec:CWE_ID");
			            	if(strweaknessID.isEmpty() == false){
			            		weaknessoutput += "ID: " + strweaknessID;
			            		weaknessoutput += " ";
				            }
			            	
			            	String strweaknesstype = getNodeValue(weaknesssub, "capec:Weakness_Relationship_Type");
			            	
			            	if(strweaknesstype.isEmpty() == false){
			            		weaknessoutput += "Relation type:  " + strweaknesstype;
			            		weaknessoutput += "<br>";
				            }
		            	}
		            }
		            
		            info[k][6] = weaknessoutput;
	            }
	            
	            
	            // CIA impact
	            String ciaoutput = "<html>";
	            Element impact = (Element)n.getElementsByTagName("capec:CIA_Impact").item(0);
	            if(impact != null){
	            	String strconimp = getNodeValue(impact, "capec:Confidentiality_Impact");
		            if(strconimp.isEmpty() == false){
		            	ciaoutput += "Confidentiality impact: " + strconimp;
		            	ciaoutput += "<br>";
		            }
		            String strintimp = getNodeValue(impact, "capec:Integrity_Impact");
		            if(strintimp.isEmpty() == false){
		            	ciaoutput += "Integrity impact: " + strintimp;
		            	ciaoutput += "<br>";
		            }
		            String stravaimp = getNodeValue(impact, "capec:Availability_Impact");
		            if(stravaimp.isEmpty() == false){
		            	ciaoutput += "Availability impact: " + stravaimp;
		            	ciaoutput += "<br>";
		            }
		            
		            info[k][7] = ciaoutput;
	            }
	            
	            //content which containing keyword
	            String str = "<html>";
	            String [] strlist = content.split("\\s\\s+");
	            
	            for(int index = 0 ; index < strlist.length; index++){
	            	if(strlist[index].toLowerCase().contains(keyword.toLowerCase())){
	            		str += strlist[index];
	            		str = str.replaceAll("(?i)" + keyword, "<nobr style='color:red'>" + keyword + "</nobr>");
	            		str += "<br>";
	            	}
	            }
	            
	            info[k][8] = str;
	            
	            k++;
	        }
	        
		}catch(Exception e){
			
		}
		
		return info;
	}
}
