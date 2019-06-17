package seleccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import map.GameMap;

public class Seleccion {
	
	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;


    public static void main(String[] args){

    	String player = args[0];
    	String ciudades = args[1];
		String rutas = args[2];
		map.GameMap mapa = new GameMap();
		
		String archivo = "seleccion"+player+".txt";
		
		try {
			FileWriter escritorDeArchivo = new FileWriter(archivo);
			BufferedWriter buffer = new BufferedWriter(escritorDeArchivo);
			PrintWriter impresion = new PrintWriter(buffer);
			
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			if (DIFICULT == EASY){
				Random rand = new Random();
				ArrayList<Integer> ciudadesRand = new ArrayList<Integer>();
				//creo una lista de ciudades
				for (int i=0;i<mapa.m_MapById.size()-2;i++ )
					ciudadesRand.add(i+2);
				while (ciudadesRand.size()>0){
					int random = rand.nextInt(ciudadesRand.size());
					String nombreCiudad = mapa.m_MapById.get(ciudadesRand.get(random)).m_Name;
					ciudadesRand.remove(random);
					impresion.append(nombreCiudad);
					buffer.newLine();
				}
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
    
    
}
