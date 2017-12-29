package servidorPartidasSudoku;

import java.util.Scanner;

public class Interfaz {
	private SudokuConSolucion s;
	private int n = 9; //para el tamaño, modificar mostrar
	
	public Interfaz(SudokuConSolucion sudoku) {
		this.s = sudoku;
	}
	
	public SudokuConSolucion getSudoku() {
		return this.s;
	}
	//USAR GET O DEVOLVER EL SUDOKU
	//PONER UN TIEMPO PARA QUE SALGA AUTOMÁTICAMENTE SI NO SE HACE NADA
	public SudokuConSolucion mostrar() {
		boolean terminado = false;
		Scanner entrada = new Scanner (System.in);
		
		do{
			System.out.println("Escoge una opción: ");
			System.out.println("  1. Colocar un número ");
			System.out.println("  2. Eliminar un número ");
			System.out.println("  3. Volver al inicio ");
			System.out.println("  4. Sudoku terminado");
			System.out.println("  5. Abandonar partida "); //UN JUGADOR PUEDE ABANDONAR LA PARTIDA ?
			int c = entrada.nextInt();
			
			while(c<1 || c>5){ //si la opcion introducida no existe, se pide de nuevo
				System.out.println("No existe opción, elija otra");
				c = entrada.nextInt();
			}
			
			switch(c){
				
			case(1):
				System.out.println("Introduce fila: "); //pedidas fila, columna y color
				int fila = entrada.nextInt()-1;
			    System.out.println("Introduce columna: ");
			    int columna = entrada.nextInt()-1;
			    System.out.println("Introduce número: ");
			    int  num = entrada.nextInt();
			    
			    if(s.correctoAñadir(fila, columna)){ // si es correcto añadir, la añade; si no muestra un mensaje de error
			    	s.añadirNumeroACasilla(fila, columna, num);
			    	s.mostrarSudoku(n);
					System.out.println("¿Correcto? : " + s.gr.esColorValido(fila*s.getNumFilas()+columna+1,num)); //nos dice si despues de añadir se puede resolver el sudoku
//					if(!s.gr.esColorValido(fila*s.getNumFilas()+columna+1,num)){ //y muestra un mesaje si no se puede resolver
//			    		System.out.println("El sudoku no tiene solución para el número introducido, borralo e introduce otro");
//			    	}
		    	}
		    	else{
		    		System.out.println("Ha introducido una posicion no vacia");
		    	}
				break;
				
			case(2):
				System.out.println("Introduce fila: ");//pedimos fila y columna
		    	fila = entrada.nextInt()-1;
		    	System.out.println("Introduce columna: ");
		    	columna = entrada.nextInt()-1;
		    	
		    	if(s.correctoBorrar(fila, columna)){// si es correcto eliminar, lo elimina; si no muestra un mensaje de error
		    		s.eliminarNumero(fila, columna);
				    s.mostrarSudoku(n);
//				    System.out.println("¿Correcto? : " + s.esPosibleResolver());//nos dice si despues de borrar se puede resolver el sudoku
		    	}
		    	else{
		    		System.out.println("Ha introducido una posicion vacia");
		    	}
				break;
				
			case(3):
				s.volverEstadoInicial(); //vuelve al sudoku dado al inicio (lo resetea)
				s.mostrarSudoku(n);
				break;
				
			case(4):
				terminado = true;
				break;
				
			case (5):
				terminado = true;
				s = null; //para saber que ha abandonado
				break;
			
			default:
				System.out.println("Número inválido, introduzca otro"); //se muestra si el numero introducido es incorrecto
				break;
			}
			
		}while(!terminado);
		
		entrada.close();
		return s;
	}
}
