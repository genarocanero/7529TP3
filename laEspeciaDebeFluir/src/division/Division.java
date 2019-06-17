package division;

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


public class Division {

	public static int EASY =1;
	public static int HARD =2;
	public static int DIFICULT = EASY;

    public static void main(String[] args){

    	String ciudades = args[0];
		String rutas = args[1];
		String seleccion1 = args[2];
		String seleccion2 = args[3];
		GameMap mapa = new GameMap();

		String salida1 = "imperio1.txt";
		String salida2 = "imperio2.txt";
		
		try {
			
			mapa.loadFromFile(ciudades);
			mapa.loadRoadsFromFile(rutas);
			
			repartirCiudades(mapa, seleccion1, seleccion2);
			mapa.saveImperialArmy(1, salida1);
			mapa.saveImperialArmy(2, salida2);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    static public void repartirCiudades(GameMap mapa, String seleccion1,String seleccion2) throws Exception{

    	List<String> lines1 = Files.readAllLines(Paths.get(seleccion1));
    	List<String> lines2 = Files.readAllLines(Paths.get(seleccion2));
    	if (lines1.size()!=lines2.size())
    		throw new Exception("Cantidad de ciudades reclamadas diferentes");
    	
    	for (int i=0;i<lines1.size();i++){
    		String a= lines1.get(i);
    		String b= lines2.get(i);
    		if (!a.equals(b)){
    			if (mapa.m_Map.get(a) == null || mapa.m_Map.get(b) == null)
    				throw new Exception("reclamada ciudad inexistente");
    			
    			//mejora: no se chequea que una ciudad no sea reclamada mas de 1 vez
    			if (mapa.m_Map.get(a).m_Owner==0){
    				mapa.m_Map.get(a).m_Owner=1;
    				mapa.m_Map.get(a).m_Army=1;
    			}
    			if (mapa.m_Map.get(b).m_Owner==0){
    				mapa.m_Map.get(b).m_Owner=2;
    				mapa.m_Map.get(b).m_Army=1;
    			}
    		}
    	}
    }
    

}
