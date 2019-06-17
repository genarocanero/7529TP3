package map;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {

	public HashMap<String, City> m_Map;
	///usado para referenciar las ciudades en forma numerica
	public HashMap<Integer, City> m_MapById;
	//primara ciudad es la ciudad 0 la primera ciudad neutral es la 2
	public int m_LastCityId;
	public int[] m_EspeciaImperio={0,0,0};
	
	public GameMap(){
		m_LastCityId=0;
		m_Map = new HashMap<String, City>();
		m_MapById = new HashMap<Integer, City>();
		m_Map.clear();
		m_MapById.clear();
	}
	
	public void addCity(City city){
		city.m_CityId=m_LastCityId;
		m_Map.put(city.m_Name, city);
		m_MapById.put(m_LastCityId, city);
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
	
	public void loadOwnerFromFile(String path, int player) throws NumberFormatException, Exception{

    	List<String> lines = Files.readAllLines(Paths.get(path));
    	for (int i=0;i<lines.size();i++){
    		String a= lines.get(i);
    		String[] parts = a.split(",");
    		String city = parts[0];
    		String army = parts[1];
    		m_Map.get(city).m_Owner = player;
    		m_Map.get(city).m_Army = Integer.parseInt(army);
    	}
	}
	
	public void loadEspeciaFromFile(int player, String path) throws IOException{

    	List<String> lines = Files.readAllLines(Paths.get(path));
    	if (player==1 || player==2)
    		m_EspeciaImperio[player] = Integer.parseInt(lines.get(0));
	}
	
	public void saveEspecia(int player, String path) throws IOException{

		FileWriter escritorDeArchivo = new FileWriter(path);
		BufferedWriter buffer = new BufferedWriter(escritorDeArchivo);
		PrintWriter impresion = new PrintWriter(buffer);
		
		impresion.append(Integer.toString(m_EspeciaImperio[player]));
		buffer.newLine();
		
		buffer.close();
		impresion.close();
	}
	
	public void saveImperialArmy(int player, String path) throws IOException{

		FileWriter salidaWriter = new FileWriter(path);
		BufferedWriter buffer = new BufferedWriter(salidaWriter);
		PrintWriter impresion = new PrintWriter(buffer);

    	for (Map.Entry<Integer, City> entry : m_MapById.entrySet()) {
    		if (entry.getValue().m_Owner==player){
    			impresion.append(entry.getValue().m_Name+","+entry.getValue().m_Army);
				buffer.newLine();
    		}
        }
		
		buffer.close();
		impresion.close();
	}
    public static void imprimirDivision(GameMap mapa, String salida1,String salida2) throws IOException{

    }
	
	public void saveMarchingArmy(City origen, City destiny, int amount, PrintWriter printer) throws IOException{

		printer.append(origen.m_Name+","+destiny.m_Name+","+Integer.toString(amount));
	}
}
