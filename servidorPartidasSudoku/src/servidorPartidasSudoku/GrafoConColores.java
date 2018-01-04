package servidorPartidasSudoku;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class GrafoConColores extends Grafo implements Serializable{
	
	private Map<Integer, Integer> tablaColores;
	
	public GrafoConColores(){ //constructor
		super();
		this.tablaColores = new Hashtable<Integer, Integer>();
	}
	
	public void mostrarGrafo(){ //mostramos el map: parejas vertice - color
		ArrayList<Integer> listaC  = new ArrayList<Integer>(this.tablaColores.keySet());
		ArrayList<Integer> listaV  = new ArrayList<Integer>(this.tablaColores.values());
		for(int i = 0; i<listaC.size();i++){
			System.out.println( "Vertice: " + listaC.get(i) + " - Color : " + listaV.get(i));
		}
	}
	
	public boolean setColor(int v, int color){ //devuelve verdadero si da color a un vertice
		if(this.esColorValido(v, color)){ //comprobamos que el color sea valido
			this.tablaColores.put(v,color);
			return true;
		}
		return false;
	}
	
	public boolean eliminarColorVertice(int v){ //devuelve verdadero si borra un vertice y su color asociado
			if(this.tablaColores.remove(v) != null){ //comprobamos que remove devuelve el vertice eliminado
				return true;
			}
			return false;	
	} 
	
	public int informarColorVertice(int v){ //devuelve el color del vertice que pasamos como parámetro
		if(this.tablaColores.containsKey(v)){ //comprobamos que el vertice esta en tablaColores
			return this.tablaColores.get(v);
		}
		return 0;
	}
	
	public void borrarColores(){ //elimina todo el contenido del map (vertices y colores)
		this.tablaColores.clear();
		/*for(Integer v: this.tablaColores.keySet()){
			this.eliminarColorVertice(v);
		}*/	//otra forma 
	}
	
	public ArrayList<Integer> listaVerticesConColor(){ //devuelve un array con los vertices que tienen un color asociado
		/*ArrayList<Integer> lista  = new ArrayList<Integer>(this.tablaColores.keySet());
		 for(Integer i: lista){
		 	if(this.tablaColores.get(lista.get(i)) == 0){
		 		lista.remove(i);
		 	}
		 }
		 return lista;*/
		
		return new ArrayList<Integer>(this.tablaColores.keySet());
	}
	
	public boolean esColorValido(int v, int color){ //devuelve verdadero si pasado un color y un vertice, el color es valido para el vertice
		List<Integer> lista = this.listaVerticesAdyacentes(v);
		for(int i = 0; i<lista.size(); i++){ //recorremos los vertices adyacentes
			if(this.informarColorVertice(lista.get(i)) == color){ //y si el color de uno, es igual al pasado; el color no es válido
				return false;
			}
		}
		return true;
	}
	
	public boolean colorear(int numColores){
		List<Integer> listaVertices;
		// lista auxiliar en la que colocaré todos los vértices
		/* Para poder aplicar el algoritmo de coloración de un grafo necesito tener los vértices almacenados en orden. En primer lugar colocaré los vértices que tienen ya un color asignado (este color no podrá modificarse). A continuación colocaré en la lista el resto de vértices, a los que el algoritmo de coloración irá asignando diferentes colores hasta dar con una combinación correcta.
		*/
		List<Integer> listaVerticesColoreados=this.listaVerticesConColor( );
		List<Integer> listaVerticesNoColoreados= super.listaVertices( ); //todos
		listaVerticesNoColoreados.removeAll(listaVerticesColoreados); //quito los
		//coloreados
		//Compruebo que los colores que ya están asignados son correctos
		for(int v:listaVerticesColoreados){
			if (!this.esColorValido(v, this.informarColorVertice(v)))
				return false;
		}
		// vuelco los vértices en la nueva lista, en el orden correcto
		listaVertices=new ArrayList<Integer>( );
		listaVertices.addAll(listaVerticesColoreados);
		listaVertices.addAll(listaVerticesNoColoreados);
		int k=listaVerticesColoreados.size( );
		boolean b=this.coloreoConRetroceso(listaVertices, k, numColores);
		if (b== false) {
		// no se ha podido colorear el grafo
		// vuelvo a la situación inicial
			for (int i = 0; i < listaVerticesNoColoreados.size( ); i++) {
				this.tablaColores.remove(listaVerticesNoColoreados.get(i));
			}
		}
		return b;
	}
	
	private boolean aceptable(List<Integer> listaVertices, int color, int posicion){
		/*
		devuelve true si al vértice que ocupa la posición k en listaVertices
		puedo asignarle el color k de modo que no haya ningún vértice en las
		posiciones anteriores que sea adyacente y que tenga el mismo color asignado.
		*/
		boolean acept=true;
		for (int i=0; i<posicion && acept; i++){
			if (super.sonAdyacentes(listaVertices.get(i), listaVertices.get(posicion)) && this.informarColorVertice(listaVertices.get(i))== color)
				acept=false;
		}
		return acept;
	}
		
	private boolean coloreoConRetroceso(List<Integer> listaVertices, int k, int numColores){
		/*
		Supongo que a los vértices situados en las posiciones 0..k-1
		de listaVertices ya les he asignado color.
		Busco un color para el vértice en la posición k que sea compatible
		con los anteriores.
		*/
		if (k==listaVertices.size( ))
			return true;
		else {
			for (int c=1; c<=numColores; c++){
				if (this.aceptable(listaVertices,c, k)) {
					this.tablaColores.put(listaVertices.get(k), c);
					boolean b=coloreoConRetroceso(listaVertices,k + 1, numColores);
					if (b)
						return b;
				}
			}
		}
		// he recorrido todas las combinaciones y ninguna es válida, devuelvo falso.
		return false;
	}
			
}

/// OK
