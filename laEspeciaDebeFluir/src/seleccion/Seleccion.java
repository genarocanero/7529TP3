package seleccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import map.GameMap;

public class Seleccion {
	
	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;


    public static void main(String[] args){

    	String player = args[1];
    	String ciudades = args[2];
		String rutas = args[3];
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
				while (mapa.m_MapById.size()>2){
					int random = rand.nextInt(mapa.m_MapById.size()-2);
					String nombreCiudad = mapa.m_MapById.get(random+2).m_Name;
					mapa.m_MapById.remove(random+2);
					impresion.append(nombreCiudad+","+player);
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
