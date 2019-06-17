package contienda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import map.GameMap;

public class Contienda {

    public static void main(String[] args){

    	String ciudades = args[1];
		String rutas = args[2];
		String imperio1 = args[3];
		String imperio2 = args[4];
		String ataque1 = args[5];
		String ataque2 = args[6];
		map.GameMap mapa = new GameMap();

		
		try {
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			mapa.loadOwnerFromFile(imperio1,1);
			mapa.loadOwnerFromFile(imperio2,2);

			moverTropas(mapa, ataque1);
			moverTropas(mapa, ataque2);
			
			mapa.saveImperialArmy(1, imperio1);
			mapa.saveImperialArmy(1, imperio2);
			
			
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
}
