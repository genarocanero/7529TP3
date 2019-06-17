package recolectar;

import java.util.LinkedList;
import java.util.Map;

import map.City;

public class Cosechadora {

    /**
     * codigo obtenido de:
     *  https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
     *  adaptado al problema y agregado de comentarios en castellano
     *  la fuente siempre es la ultima ciudad
     */
    static public int fordFulkerson(map.GameMap mapa, int jugador, int ciudades) 
    { 
        int u, v; 
        // crea el grafo recidual con las capacidades del grafo original
  
        // el grfo recidual indica la capacidad de 'a' a 'b' 
        // o 0 en caso de no existir la coneccion
        int rGraph[][] = new int[ciudades][ciudades];
        
  
        for (Map.Entry<Integer, City> origen : mapa.m_MapById.entrySet()) {
        	if (origen.getValue().m_Owner == jugador){
	        	for (Map.Entry<Integer, Integer> destino : origen.getValue().m_RoadsCapasity.entrySet()){
	        		if (mapa.m_MapById.get(destino.getKey()).m_Owner == jugador)
	        			//por cada ciudad origen y destino del jugador agergo el camino
	        			rGraph[origen.getValue().m_CityId][destino.getKey()] = destino.getValue(); 
	        	}
	        	//agrego un camino desde la fuente hacia las ciudades que procuden
	        	if (origen.getValue().m_Production > 0)
	        		rGraph[ciudades][origen.getValue().m_CityId] = origen.getValue().m_Production;
	        }
        }
  
        // se guarda el camino allado por el bfs 
        int parent[] = new int[ciudades]; 
  
        int max_flow = 0;  // flujo que se obtiene
  
        // mientras halla capacidad de flujo (bfs encontro un camino) agrega flujo
        while (bfs(rGraph, ciudades, jugador, parent,ciudades)) 
        { 
            // obtiene el flujo de corte del camino encontrado
            int path_flow = Integer.MAX_VALUE; 
            for (v=jugador; v!=ciudades; v=parent[v]) 
            { 
                u = parent[v]; 
                path_flow = Math.min(path_flow, rGraph[u][v]); 
            } 
  
            // actualiza las capacidades del grafo recidual 
            for (v=jugador; v != ciudades; v=parent[v]) 
            { 
                u = parent[v]; 
                rGraph[u][v] -= path_flow; 
                rGraph[v][u] += path_flow; 
            } 
  
            // cuenta el flujo agregado
            max_flow += path_flow; 
        } 
  
        return max_flow; 
    }
    
    /**
     * codigo obtenido de:
     *  https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
     *  adaptado al problema y agregado de comentarios en castellano
     */
	  static boolean bfs(int rGraph[][], int s, int t, int parent[], int ciudades) 
	  { 
	      // marca todos los nodos como no visitados
		  boolean visited[] = new boolean[ciudades]; 
		  for(int i=0; i<ciudades; ++i) 
		      visited[i]=false; 
		
		  // crea una cola, marca a s como raiz y visitado
		  LinkedList<Integer> queue = new LinkedList<Integer>(); 
		  queue.add(s); 
		  visited[s] = true; 
		  parent[s]=-1; 
		
		  // Standard BFS Loop 
		  while (queue.size()!=0) 
		  { 
		      int u = queue.poll(); 
		
		      for (int v=0; v<ciudades; v++) 
		      { 
		          if (visited[v]==false && rGraph[u][v] > 0) 
		          { 
		              queue.add(v); 
		              parent[v] = u; 
		              visited[v] = true; 
		          } 
		      } 
		  } 
		
		  // si llego al destino encontro un camino sino no
		      return (visited[t] == true); 
	  } 
}
