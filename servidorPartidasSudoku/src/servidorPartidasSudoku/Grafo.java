package servidorPartidasSudoku;



import java.io.Serializable;
import java.util.*;

public class Grafo implements Serializable{
	
	private Map<Integer, List<Integer>> tabla;
	
	public Grafo(){ //constructor
		this.tabla = new Hashtable<Integer, List<Integer>>();
	}
	
	public boolean anadirVertice(int v){ //devuelve verdadero si añade el vertice dado
		if(!this.tabla.containsKey(v)){ //comprueba que tabla no lo contenga todavia
			this.tabla.put(v, new ArrayList<Integer>());
			return true;
		}
		return false;
	}
	
	public boolean anadirArista(int v1, int v2){//devuelve verdadero si añade v1 a la lista de v2, y viceversa
		if(this.tabla.containsKey(v1) && this.tabla.containsKey(v2)){ //comprueba que ambos vertices existen
			this.tabla.get(v1).add(v2);
			this.tabla.get(v2).add(v1);
			return true;
		}
		return false;
	}
	
	public int numVertices(){ //devuelve el numero de vertices que hay
		return (this.tabla.size());
	}
	
	public boolean sonAdyacentes(int v1, int v2){ //devuelve verdadero si los dos vertices pasados son adyacentes
		return (this.tabla.get(v1).contains(v2));
	}
	
	public boolean contieneVertice(int v){ //devuelve verdadero  si el vertice pasado esta en el grafo
		return (this.tabla.containsKey(v));
	}
	
	public List<Integer> listaVertices(){ //devuelve una lista con los vertices que tiene el grafo
		/*Una forma es -> List<Integer> l = new ArrayList<>();
			for(Integer x: tabla.keySet()){
				l.add(x);
			}
			return (l);	*/
		//ó sustituyendo al for se puede hacer -> l.addAll(tabla.keySet())
			
		return (new ArrayList<Integer>(this.tabla.keySet()));
	}
	
	public List<Integer> listaVerticesAdyacentes(int v){ //devuelve una lista con los vertices adyacentes a uno pasado
		return (new ArrayList<Integer>(this.tabla.get(v)));
		
	}
}
/// OK