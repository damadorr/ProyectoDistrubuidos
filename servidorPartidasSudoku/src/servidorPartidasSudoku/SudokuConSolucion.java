package servidorPartidasSudoku;

import java.io.Serializable;
import java.util.Random;

public class SudokuConSolucion extends Sudoku implements Serializable {

	public GrafoConColores gr; //declarado public para poder usarlo en el main
	/// DEBERÍA SER PRIVADO Y DESDE EL MAIN USAR SOLO LOS MÉTODOS DE SudokuConSolucion
	private final int tamaño = matriz.length;
	
	public SudokuConSolucion(int [][] matriz){ //constructor
		super(matriz);
		gr = new GrafoConColores();
		this.construirGrafoInicial();
		
	}
	
	public void resolver(){ //resuelve un sudoku
		boolean aux = this.gr.colorear(this.getNumFilas()); //coloreamos el grafo
		if(aux){ //si se puede colorear
			for(int i = 0; i<this.getNumFilas(); i++){ //vamos añadiendo colores a la matriz
				for(int j = 0; j<this.getNumFilas(); j++){
					super.añadirNumeroACasilla(i, j, this.gr.informarColorVertice(i*this.getNumFilas()+j+1));
				}
			}
		}
	}
	
	public boolean esPosibleResolver(){ //devuelve verdadero si el sudoku se puede resolver
		int [][] aux = new int [matriz.length][matriz.length]; //creamos una copia de la matriz
		for(int i=0; i<matriz.length; i++){
			for(int j=0; j<matriz.length;j++){
				aux [i][j] = matriz [i][j];
			}
		}
		SudokuConSolucion auxSudoku = new SudokuConSolucion(aux); //se la pasamos al constructor de sudoku
		return auxSudoku.gr.colorear(auxSudoku.getNumFilas()); //y devuelve verdadero si lo puede colorear y falso en caso contrario
	}
	
	//Reescritos
	public boolean añadirNumeroACasilla(int fila, int columna, int num){  //devuelve true si añade un numero a una casilla
			super.añadirNumeroACasilla(fila, columna, num); //llama al de padre
			if(matriz [fila] [columna] != 0){ //entonces si la posición ya no es cero, añade el color en el grafo
				this.gr.setColor((fila*this.getNumFilas()+columna+1), num); 
				return true;
			}
			return false;
	}
	public boolean eliminarNumero(int fila, int columna){ //devuelve true si borra un numero de una casilla
		super.eliminarNumero(fila, columna); //llama al de padre
		if(matriz [fila] [columna] == 0){ //entonces si la posición es cero despues de llamar al padre, elimina el color en el grafo
			this.gr.eliminarColorVertice(fila*this.getNumFilas()+columna+1);
			return true;
		}
		return false;
	}
	public void volverEstadoInicial(){ //devuelve el valor inicial que tenia una casilla
		super.volverEstadoInicial(); //llama al de padre
		/// MAL, FALTA MODIFICAR EL GRAFO CON COLORES
	}
	
	//Reescrito y creado por mi para comprobaciones
	public boolean correctoAñadir(int fila, int columna){ //devuelve verdadero si es correcto añadir en una posicion
		if((this.matriz [fila] [columna] == 0) && (this.esPosibleResolver())){ /// MAL, HABRÍA QUE COMPROBAR SI ES POSIBLE RESOLVER DESPUÉS DE AÑADIRLO
			return true;
		}
		return false;
	}
	public boolean correctoBorrar(int fila, int columna){ //devuelve verdadero si es correcto eliminar en una posicion
		if((this.matriz [fila] [columna] != 0) && (this.esPosibleResolver())){
			return true;
		}
		return false;
	}
	
	public void crearSudoku(int numeros){ //crea un sudoku con una cantidad de numeros pasada como parámetro
		if(numeros<=tamaño*tamaño){
			Random r = new Random(); //creamos con random los numeros
			int fila = r.nextInt(tamaño);
			int columna = r.nextInt(tamaño);
			int color = r.nextInt(tamaño)+1;
			
			while(this.gr.listaVerticesConColor().size()<numeros){ //mientras que haya menos numeros que los pedidos entra aqui
				this.añadirNumeroACasilla(fila, columna, color); //lo añade
				if(!gr.esColorValido(fila*getNumFilas()+columna+1,color)|| !this.esPosibleResolver()){
					this.eliminarNumero(fila, columna); //y si no es posible resolver lo borra
				}
				
				fila = r.nextInt(tamaño); //coge otros numeros aleatorios
				columna = r.nextInt(tamaño);
				color = r.nextInt(tamaño)+1;
			}
			
			this.mostrarSudoku(tamaño); //mostramos el sudoku
			//gr.mostrarGrafo(); //mostramos el grafo asociado para ver si los colores son correctos
		}
	}
	
	private void construirGrafoInicial( ){
		int numFilas=this.getNumFilas( );
		int numVertices=numFilas*numFilas;
		for (int v=1; v<=numVertices; v++)
			this.gr.anadirVertice(v);
		//gr es el nombre del atributo de Sudoku que contiene el grafo con colores
		//Añado aristas para todas las parejas de vértices que están en la misma fila
		for (int i = 0; i < numFilas ; i++) {
			for (int j = 0; j < numFilas; j++) {
					for (int k = j + 1; k < numFilas ; k++) {
						int v1=numFilas*i + j+1;
						int v2=numFilas*i + k+1;
						this.gr.anadirArista(v1,v2);
					}
			}
		}
		//Añado aristas para todas las parejas de vértices que están en la misma
		// columna
		for (int j = 0; j < numFilas; j++) {
			for (int i = 0; i < numFilas ; i++) {
				for (int k = i + 1; k < numFilas ; k++) {
					int v1=numFilas*i + j+1;
					int v2=numFilas*k + j+1;
					this.gr.anadirArista(v1,v2);
				}
			}
		}
		//Añado aristas para todas las parejas de vértices que están en la misma región
		int n = (int)Math.sqrt(numFilas);
		for (int i = 0; i < n ; i++) {
			for (int j = 0; j < n; j++) {
				int i0 = i * n;
				int j0 = j * n;
		// (i0,j0) es la esquina superior izquierda de la región
				for (int i1 = i0; i1 < i0 + n; i1++) {
					for (int j1 = j0; j1 < j0 + n; j1++) {
						for (int i2 =i0; i2<i0+n; i2++){
							for (int j2 = j0; j2 < j0 + n; j2++) {
								int v1 = numFilas * i1 + j1 + 1;
								int v2 = numFilas * i2 + j2 + 1;
								if ((v2 != v1) && !this.gr.sonAdyacentes(v1, v2))
									this.gr.anadirArista(v1, v2);
							}
						}
					}
				}
			}
		}
		// Por último añado los colores a los vértices correspondientes a los
		// valores iniciales del sudoku
		for (int i=0; i<numFilas; i++){
			for (int j=0; j<numFilas; j++){
				if (this.valorInicial(i,j)!=0)
					this.gr.setColor(i*numFilas+j+1,this.valorInicial(i,j));
			}
		}
	}
	
	
	
	
}
