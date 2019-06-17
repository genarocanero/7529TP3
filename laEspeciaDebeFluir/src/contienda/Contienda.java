package contienda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import map.City;
import map.GameMap;

public class Contienda {

    public static void main(String[] args){

    	String ciudades = args[0];
		String rutas = args[1];
		String imperio1 = args[2];
		String imperio2 = args[3];
		String ataque1 = args[4];
		String ataque2 = args[5];
		map.GameMap mapa = new GameMap();

		
		try {
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			mapa.loadOwnerFromFile(imperio1,1);
			mapa.loadOwnerFromFile(imperio2,2);

			moverTropas(mapa, ataque1);
			moverTropas(mapa, ataque2);
			resolverEnfrentamientos(mapa);
			
			mapa.saveImperialArmy(1, imperio1);
			mapa.saveImperialArmy(2, imperio2);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    static public void moverTropas(map.GameMap mapa, String ataque) throws IOException{

    	List<String> lines = Files.readAllLines(Paths.get(ataque));
    	for (int i=0;i<lines.size();i++){
    		String a= lines.get(i);
    		String[] parts = a.split(",");
    		String origen = parts[0];
    		String destino = parts[1];
    		String cantidad = parts[2];
    		mapa.m_Map.get(origen).m_Army -=  Integer.parseInt(cantidad);
    		mapa.m_Map.get(destino).m_IncomingArmy[mapa.m_Map.get(origen).m_Owner] 
    				+=  Integer.parseInt(cantidad);
    	}
	}
    
    static public void resolverEnfrentamientos(map.GameMap mapa){

        for (Map.Entry<Integer, City> ciudad : mapa.m_MapById.entrySet()) {
        	if (ciudad.getValue().m_CityId>1){
	        	ciudad.getValue().m_IncomingArmy[ciudad.getValue().m_Owner] += ciudad.getValue().m_Army;
	        	int ejercitoResultante = ciudad.getValue().m_IncomingArmy[1] - ciudad.getValue().m_IncomingArmy[2];
	        	
	        	if (ejercitoResultante > 0){
	        		ciudad.getValue().m_Owner = 1;
	        		ciudad.getValue().m_Army = ejercitoResultante;
	        	}
	        	if (ejercitoResultante < 0){
	        		ciudad.getValue().m_Owner = 2;
	        		ciudad.getValue().m_Army = -ejercitoResultante;
	        	}
	        	if (ejercitoResultante == 0){
	        		ciudad.getValue().m_Owner = 0;
	        		ciudad.getValue().m_Army = ejercitoResultante;
	        	}
	        }
        }
    }
}
