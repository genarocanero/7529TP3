package recolectar;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import map.City;
import map.GameMap;

public class Recolectar {

    public static void main(String[] args){

    	String player = args[1];
    	String ciudades = args[2];
		String rutas = args[3];
		String imperio = args[4];
		map.GameMap mapa = new GameMap();
		
		String salida = "cosecha"+player+".txt";
		
		try {
			
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			mapa.loadOwnerFromFile(imperio,Integer.parseInt(player));
			try{
				mapa.loadEspeciaFromFile(Integer.parseInt(player), salida);
			}catch (IOException e) {
				//si el archivo no existe entonces no hay cosecha anterior (1er turno)
				mapa.m_EspeciaImperio[Integer.parseInt(player)] = 0;
			}

			int recolectado = Cosechadora.fordFulkerson(mapa, Integer.parseInt(player),mapa.m_MapById.size()+1);
			mapa.m_EspeciaImperio[Integer.parseInt(player)] += recolectado; 
			
			mapa.saveEspecia(Integer.parseInt(player), salida);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
