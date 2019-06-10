package map;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Map {

	public HashMap<String, City> m_Map;
	///usado para referenciar las ciudades en forma numerica
	public HashMap<Integer, City> m_MapById;
	//primara ciudad es la ciudad 0 la primera ciudad neutral es la 2
	public int m_LastCityId;
	
	public Map(){
		m_LastCityId=0;
		m_Map.clear();
	}
	
	public void addCity(City city){
		city.m_CityId=m_LastCityId;
		m_Map.put(city.m_Name, city);
		m_LastCityId++;
	}
	
	public void loadFromFile(String path) throws IOException{

    	List<String> lines = Files.readAllLines(Paths.get(path));
    	for (int i=0;i<lines.size();i++){
    		String a= lines.get(i);
    		String[] parts = a.split(",");
    		String name = parts[0];
    		String production = parts[1];
    		if (i==0)
    			addCity(new City(name,1,Integer.parseInt(production)));
    		if (i==1)
    			addCity(new City(name,2,Integer.parseInt(production)));
    		if (i>1)
    			addCity(new City(name,0,Integer.parseInt(production)));
    	}
	}
	public void loadRoadsFromFile(String path) throws NumberFormatException, Exception{

    	List<String> lines = Files.readAllLines(Paths.get(path));
    	for (int i=0;i<lines.size();i++){
    		String a= lines.get(i);
    		String[] parts = a.split(",");
    		String city1 = parts[0];
    		String city2 = parts[1];
    		String capacity = parts[2];
    		m_Map.get(city1).addRoad(m_Map.get(city2), Integer.parseInt(capacity));
    	}
	}
	public void loadOwnerFromFile(String path) throws NumberFormatException, Exception{

    	List<String> lines = Files.readAllLines(Paths.get(path));
    	for (int i=0;i<lines.size();i++){
    		String a= lines.get(i);
    		String[] parts = a.split(",");
    		String city = parts[0];
    		String owner = parts[2];
    		m_Map.get(city).m_Owner=Integer.parseInt(owner);
    	}
	}
}
