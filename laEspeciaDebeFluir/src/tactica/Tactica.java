package tactica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import map.City;
import map.GameMap;

public class Tactica {

	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;

    public static void main(String[] args){

    	String player = args[1];
    	String ciudades = args[2];
		String rutas = args[3];
		String imperio1 = args[4];
		String cosecha1 = args[5];
		String imperio2 = args[6];
		String cosecha2 = args[7];
		map.GameMap mapa = new GameMap();
		
		String salida = "ataque"+player+".txt";
		
		try {
			FileWriter escritorDeArchivo = new FileWriter(salida);
			BufferedWriter buffer = new BufferedWriter(escritorDeArchivo);
			PrintWriter impresion = new PrintWriter(buffer);
			
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			mapa.loadOwnerFromFile(imperio1,1);
			mapa.loadOwnerFromFile(imperio2,2);
			mapa.loadEspeciaFromFile(1, cosecha1);
			mapa.loadEspeciaFromFile(2, cosecha2);

			if (DIFICULT == EASY){
				moverEjercitos(mapa, Integer.parseInt(player), impresion);
			}
			if (DIFICULT == HARD){
				throw new Exception("Dificultad no implementada");
			}
			
			buffer.close();
			impresion.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    static public void moverEjercitos(map.GameMap mapa, int jugador, PrintWriter impresion) throws IOException{

        for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
        	if (origen.getValue().m_Owner == jugador){
            	int contactos=0;//cantidad de concciones con ciudades enemigas
	        	for (Map.Entry<Integer, City> destino : origen.getValue().m_RoadsDestination.entrySet()){

	        		if (destino.getValue().m_Owner != jugador){
	        			contactos++;
	        		}
	        	}
        		if (origen.getValue().m_Army > contactos+1){
    	        	for (Map.Entry<Integer, City> destino : origen.getValue().m_RoadsDestination.entrySet()){

	        			//si la ciudad tiene mas ejercito que el enemigo mandar la mitad
		        		if (destino.getValue().m_Owner != jugador){
		        			if (origen.getValue().m_Army > destino.getValue().m_Army){
		        				int marchar = origen.getValue().m_Army/2;
		        				if (marchar >0){
		        					mapa.saveMarchingArmy(origen.getValue(),destino.getValue(),marchar, impresion);
			        				origen.getValue().m_Army -= marchar;
		        				}
		        			}
		        		}
    	        	}
        		}
	        }
    	}
  
    }
}
