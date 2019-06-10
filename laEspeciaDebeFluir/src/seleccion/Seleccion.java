package seleccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import map.Map;

public class Seleccion {
	
	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;


    public static void main(String[] args){

    	String player = args[1];
		String path1 = args[2];
		String path2 = args[3];
		Map mapa = new Map();
		
		String archivo = "seleccion"+player+".txt";
		
		try {
			FileWriter escritorDeArchivo = new FileWriter(archivo);
			BufferedWriter buffer = new BufferedWriter(escritorDeArchivo);
			PrintWriter impresion = new PrintWriter(buffer);
			
			mapa.loadFromFile(path1);
			mapa.loadRoadsFromFile(path2);
			if (DIFICULT == EASY){
				Random rand = new Random();
				while (mapa.m_MapById.size()>2){
					int random = rand.nextInt(mapa.m_MapById.size()-2);
					String nombreCiudad = mapa.m_MapById.get(random+2).m_Name;
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
