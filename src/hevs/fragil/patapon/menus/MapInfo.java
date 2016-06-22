package hevs.fragil.patapon.menus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MapInfo {
	private String name, loopUrl; 
	
	public String getName(){
		return "coucou";
	}
	public void load(int mapIndex){
		HashMap<String, Boolean> hashmap = new HashMap<String, Boolean>();
		File file = new File("maps/test.map");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String l;
			while((l = br.readLine()) != null)
			{
				String[] args = l.split("[,]", 2);
				if(args.length != 2)continue;
				String p = args[0].replaceAll(" ", "");
				String b = args[1].replaceAll(" ", "");
				if(b.equalsIgnoreCase("true"))hashmap.put(p, true);
				else hashmap.put(p, false);
			}
			br.close();
		}
		catch (IOException e){
			System.out.println("file not found.");
		}
	}
	public void save(){
		HashMap<String,Boolean> hashmap = new HashMap<String, Boolean>();
		File file = new File("maps/1.map");
		try{
		   BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		   for(String p:hashmap.keySet())
		   {
		      bw.write(p + "," + hashmap.get(p));
		      bw.newLine();
		   }
		   bw.flush();
		   bw.close();
		}
		catch (IOException e){
            System.out.println("file not found.");
        }
	}
}
