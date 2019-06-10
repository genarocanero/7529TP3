package map;

import java.util.HashMap;

public class City {

	public String m_Name;
	public int m_CityId; //usado para los mapas
	
	public int m_Owner; //0 nadie, 1 jugador 1, 2 jugador 2
	public int m_Army;
	
	public int m_Production;
	
	public HashMap <Integer, Integer> m_RoadsCapasity;
	public HashMap <Integer, City> m_RoadsDestination;
	
	public City(String name, int owner, int production){
		m_Name = name;
		m_Owner = owner;
		m_Production = production;
		m_RoadsCapasity.clear();
		m_RoadsDestination.clear();
	}
	
	public void addRoad(City city, int capasity) throws Exception{
		if (city.m_CityId == -1)
			throw new Exception("Intento de agregar ruta a ciudad no inicializada");
		m_RoadsCapasity.put(city.m_CityId,capasity);
		m_RoadsDestination.put(city.m_CityId, city);
	}
	
	///resuleve el ejercito entrante, sea por combate o movimiento de tropas
	/// return true cuando cambia de propietario
	public boolean incomingTroops(int owner, int army){
		if (m_Owner == owner)
			m_Army += army;
		else
			m_Army -= army;
		
		if (m_Army > 0)
			return false;
		else if (m_Army == 0)
			m_Owner=0;
		else{
			m_Army = m_Army*(-1);
			m_Owner=owner;
		}
		return true;
	}
}
