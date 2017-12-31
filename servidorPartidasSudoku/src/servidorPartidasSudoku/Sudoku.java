

import java.io.Serializable;
import java.util.*;

public class Sudoku implements Serializable{

	protected int [] [] matriz;
	private static int [] [] matrizInicial;
	
	public Sudoku(int [][] matriza){ //constructor
		int [][] aux = new int [matriza.length][matriza.length]; //por defecto el valor inicial de un entero es cero
		for(int i=0; i<matriza.length; i++){
			for(int j=0; j<matriza.length;j++){
				aux [i][j] = matriza [i][j];
			}
		}
		this.matriz = matriza;
		matrizInicial = aux; 
	}
	
	public int getNumFilas(){ //devuelve el numero de filas de la matriz
		return this.matriz.length;
	}
	
	public boolean añadirNumeroACasilla(int fila, int columna, int num){ //devuelve true si añade un numero a una casilla
		if(this.matriz [fila] [columna] == 0){ 
			this.matriz[fila][columna] = num; //añade si la casilla vale 0
			return true;
		}
		return false;
	}
	
	public boolean eliminarNumero(int fila, int columna){ //devuelve true si borra un numero de una casilla
		if(this.matriz [fila] [columna] != 0){
			this.matriz[fila][columna] = 0; //elimina si la casilla vale distinto de 0; es decir tiene un valor
			return true;
		}
		return false;
	}
	
	public void volverEstadoInicial(){ //vuelve al estado inicial de la matriz
		int [][] aux = new int [matriz.length][matriz.length]; //crea una copia de matrizInicial
		for(int i=0; i<matrizInicial.length; i++){
			for(int j=0; j<matrizInicial.length;j++){
				aux [i][j] = matrizInicial [i][j];
			}
		}
		this.matriz = aux; //Le da el valor a matriz
		/// PODRÍAS PONER LOS DATOS DIRECTAMENTE EN LA MATRIZ SIN CREAR OTRA MATRIZ PERO OK		
	}
	
	public int valorInicial(int fila, int columna){ //devuelve el valor inicial que tenia una casilla
		int num = matrizInicial [fila][columna]; //devuelve el valor de la matrizInicial
		return num;
	}
	
	public void mostrarSudoku(int tamaño){ //muestra un sudoku de 4x4 o 9x9 dependiendo del tamaño pasado
		/// SE PODRÍA HACER PARA TAMAÑO GENERAL
		if(tamaño == 4){
			int contadorlinea = 0; //usamos contadores para saber cuando escribir por pantalla
			int contadorfila = -1;
			System.out.println("-------------");
			for(int i = 0; i < this.getNumFilas(); i++){ //para las filas
				contadorfila++;
				if(contadorfila == 2){
					System.out.println("|-----------|");
					contadorfila = 0;
				}
				System.out.print("| ");
				for(int j = 0; j < this.getNumFilas(); j++){ //para las columnas
					System.out.print(this.matriz[i][j] + " "); 
					contadorlinea++;
					if(contadorlinea == 2){
						System.out.print("| ");
						contadorlinea = 0;
					}
				}
				System.out.println(" ");
			}
			System.out.println("-------------");
		}
		
		if(tamaño == 9){ //igual que el de 4, pero para tamaño 9
			int contadorlinea = 0;
			int contadorfila = -1;
			System.out.println("-------------------------");
			for(int i = 0; i < this.getNumFilas(); i++){
				contadorfila++;
				if(contadorfila == 3){
					System.out.println("|-----------------------|");
					contadorfila = 0;
				}
				System.out.print("| ");
				for(int j = 0; j < this.getNumFilas(); j++){
					System.out.print(this.matriz[i][j] + " ");
					contadorlinea++;
					if(contadorlinea == 3){
						System.out.print("| ");
						contadorlinea = 0;
					}
				}
				System.out.println(" ");
			}
			System.out.println("-------------------------");
		}
		
	}  
	
	//metodos creados por mi para comprobar ciertos errores
	public boolean correctoAñadir(int fila, int columna){ //devuelve verdadero si es correcto añadir en una posicion
		if(this.matriz [fila] [columna] == 0){
			return true;
		}
		return false;
	}
	public boolean correctoBorrar(int fila, int columna){ //devuelve verdadero si es correcto eliminar en una posicion
		if(this.matriz [fila] [columna] != 0){
			return true;
		}
		return false;
	}
	public int valorCasilla (int fila, int columna){ //devuelve el valor de una casilla
		int j = this.matriz[fila][columna];
		return j;
	}

	
}/// OK
