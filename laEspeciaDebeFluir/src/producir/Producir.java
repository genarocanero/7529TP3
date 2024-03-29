package producir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import map.City;
import map.GameMap;

public class Producir {

	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;

    public static void main(String[] args){

    	String player = args[0];
    	String ciudades = args[1];
		String rutas = args[2];
		String imperio1 = args[3];
		String cosecha1 = args[4];
		String imperio2 = args[5];
		String cosecha2 = args[6];
		map.GameMap mapa = new GameMap();
		
		String salida = "cosecha"+player+"_temp.txt";
		String salida2 = "imperio"+player+"_temp.txt";
		
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
				int especia;
				especia = decidirGastoEasy(mapa, Integer.parseInt(player));
				reclutarEasy(mapa, Integer.parseInt(player), especia);
			}
			if (DIFICULT == HARD){
				int especia;
				especia = decidirGastoHard(mapa, Integer.parseInt(player));
				reclutarHard(mapa, Integer.parseInt(player), especia);
			}
			
			mapa.saveEspecia(Integer.parseInt(player), salida);
			mapa.saveImperialArmy(Integer.parseInt(player), salida2);
			
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
    
    static public int decidirGastoEasy(map.GameMap mapa, int jugador){
    	if (mapa.m_EspeciaImperio[jugador] >= 100)
    		return 0;
    	int ejercitos =0;
    	//por cada ciudad propia cuento el ejercito en total
        for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
        	if (origen.getValue().m_Owner == jugador)
        		//uno de los ejercitos no se puede mover o perdes la ciudad
        		ejercitos += (origen.getValue().m_Army -1);
        }
        //se busca tener la misma cantidad de especia en ejercito que en reserva
        return (mapa.m_EspeciaImperio[jugador]-(ejercitos*2))/2;
    }

    static public void reclutarEasy(map.GameMap mapa, int jugador, int especia){

    	if (especia <=1)
    		return;
    	//cada ejercito cuesta 2 de especia
    	int ejercitosAColocar = especia/2;
    	int ejercitosColocados = 0;
    	int contactos=0;//cantidad de concciones con ciudades enemigas
    	int limites=0;//cantidad de ciudades limitrofes propias
    	
    	while (ejercitosColocados < ejercitosAColocar){
	        for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
	        	if (origen.getValue().m_Owner == jugador){
		        	for (Map.Entry<Integer, Integer> destino : origen.getValue().m_RoadsCapasity.entrySet()){
		        		boolean limite=false;
		        		if (mapa.m_MapById.get(destino.getKey()).m_Owner != jugador){
		        			contactos++;
		        			if (!limite){
		        				limite=true;
		        				limites++;
		        			}
		        			//si puedo, agrego el ejercito, y disminuyo en 2 los recursos disponibles
		        			if (ejercitosColocados < ejercitosAColocar){
		        				origen.getValue().m_Army++;
		        				ejercitosColocados++;
		        				mapa.m_EspeciaImperio[jugador]-=2;
		        			}
		        				
		        		}
		        	}
		        }
	        }
    	}
  
    }
    
    static public int decidirGastoHard(map.GameMap mapa, int jugador){
    	if (mapa.m_EspeciaImperio[jugador] >= 100)
    		return 0;
        return mapa.m_EspeciaImperio[jugador];
    }

    static public void reclutarHard(map.GameMap mapa, int jugador, int especia){

    	if (especia <=1)
    		return;
    	//cada ejercito cuesta 2 de especia
    	int ejercitosAColocar = especia/2;
    	int ejercitosColocados = 0;

		ArrayList<Integer> ciudadesRand = new ArrayList<Integer>();
		//creo una lista de ciudades con contactos a enemigos, 
		//mientras mas contacots mas chances de obtener un ejercito
    	for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
	    	if (origen.getValue().m_Owner == jugador){
	        	for (Map.Entry<Integer, Integer> destino : origen.getValue().m_RoadsCapasity.entrySet()){
	        		if (mapa.m_MapById.get(destino.getKey()).m_Owner != jugador){
	        			ciudadesRand.add(origen.getValue().m_CityId);
	        		}
	        	}
	    	}
    	}

		Random rand = new Random();
    	while (ejercitosColocados < ejercitosAColocar){
			int random = rand.nextInt(ciudadesRand.size());
			mapa.m_MapById.get(ciudadesRand.get(random)).m_Army++;
			ejercitosColocados++;
    	}
  
    }
}
