package ganador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import recolectar.Cosechadora;

import map.City;
import map.GameMap;

public class Ganador {

    public static void main(String[] args){

    	String ronda = args[1];
    	String ciudades = args[2];
		String rutas = args[3];
		String imperio1 = args[4];
		String cosecha1 = args[5];
		String imperio2 = args[6];
		String cosecha2 = args[7];
		map.GameMap mapa = new GameMap();
		
		String salida = "ganador.txt";
		
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
			
			boolean fin=false;

			if (mapa.m_EspeciaImperio[1] >= 100 || mapa.m_EspeciaImperio[2] >= 100)
				fin=true;
			
			//si no estan conectados no pueden recolectar
			int recolectado1 = Cosechadora.fordFulkerson(mapa, 1,mapa.m_MapById.size()+1);
			int recolectado2 = Cosechadora.fordFulkerson(mapa, 2,mapa.m_MapById.size()+1);
			if ( recolectado1 == 0 || recolectado2 == 0)
				fin=true;
			
			if (Integer.parseInt(ronda) == 50)
				fin=true;
			
			if (fin){
				if (mapa.m_EspeciaImperio[1]>mapa.m_EspeciaImperio[2])
					impresion.append("1");
				else
					if (mapa.m_EspeciaImperio[2]>mapa.m_EspeciaImperio[1])
						impresion.append("2");
					else{
						int ciudadesCount[]={0,0,0};
						int ejercitos[]={0,0,0};

				        for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
				        	ciudadesCount[origen.getValue().m_Owner]++;
				        	ejercitos[origen.getValue().m_Owner] += origen.getValue().m_Army;
				        }
				        
				        if (ciudadesCount[1]>ciudadesCount[2])
							impresion.append("1");
				        else
					        if (ciudadesCount[2]>ciudadesCount[1])
								impresion.append("2");
					        else
						        if (ejercitos[1]>ejercitos[2])
									impresion.append("1");
						        else
							        if (ejercitos[2]>ejercitos[1])
										impresion.append("2");
				        //si no hay ganador por desempate se deja en blanco el archivo de ganador
				        // la responsabilidad de no segir con las rondas se deja en manos de quien llama
					}
					
					
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
}
