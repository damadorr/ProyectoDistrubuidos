
import java.util.*;
public class Principal {

	public static void main(String[] args) {
		
		System.out.println("Elija un tama�o de sudoku: (4 � 9)"); //damos a elegir entre dos tama�os
		Scanner entrada = new Scanner (System.in);
		int n = entrada.nextInt();
		
		while(n!=4 && n!=9){ //si n no es ni 4 ni 9, lo volvemos a solicitar
			System.out.println("Tama�o equivocado, elija entre 4 o 9");
			n = entrada.nextInt();
		}
		
		int [][] m = new int[n] [n]; //creamos el sudoku del tama�o pasado
		SudokuConSolucion s = new SudokuConSolucion(m);
		
		System.out.println("Introduzca la cantidad de n�meros que quiere que tenga el sudoku: "); //escogemos los numeros que queremos que haya en el sudoku
		int c = entrada.nextInt();
		s.crearSudoku(c);
		
		System.out.println("Escoge una opci�n: ");
		System.out.println("  1. Colocar un n�mero ");
		System.out.println("  2. Eliminar un n�mero ");
		System.out.println("  3. Volver al inicio ");
		System.out.println("  4. Resolver el sudoku ");
		System.out.println("  5. Salir ");
		c = entrada.nextInt();
		
		while(c<1 || c>5){ //si la opcion introducida no existe, se pide de nuevo
			System.out.println("No existe opci�n, elija otra");
			c = entrada.nextInt();
		}
		
		do{
			switch(c){
			/*case(0):
				System.out.println("Introduce fila: ");
				int fila = entrada.nextInt()-1;
				System.out.println("Introduce columna: ");
				int columna = entrada.nextInt()-1;
				System.out.println(s.valorCasilla(fila, columna));
				break;*/ //esto servia para comprobar que el numero de la casilla era el que se mostraba
				
			case(1):
				System.out.println("Introduce fila: "); //pedidas fila, columna y color
				int fila = entrada.nextInt()-1;
			    System.out.println("Introduce columna: ");
			    int columna = entrada.nextInt()-1;
			    System.out.println("Introduce n�mero: ");
			    int  num = entrada.nextInt();
			    
			    if(s.correctoA�adir(fila, columna)){ // si es correcto a�adir, la a�ade; si no muestra un mensaje de error
			    	s.a�adirNumeroACasilla(fila, columna, num);
			    	s.mostrarSudoku(n);
					System.out.println("�Correcto? : " + s.gr.esColorValido(fila*s.getNumFilas()+columna+1,num)); //nos dice si despues de a�adir se puede resolver el sudoku
					if(!s.gr.esColorValido(fila*s.getNumFilas()+columna+1,num)){ //y muestra un mesaje si no se puede resolver
			    		System.out.println("El sudoku no tiene soluci�n para el n�mero introducido, borralo e introduce otro");
			    	}
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
				    System.out.println("�Correcto? : " + s.esPosibleResolver());//nos dice si despues de borrar se puede resolver el sudoku
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
				s.resolver(); //resuelve el sudoku
				s.mostrarSudoku(n);
				break;
				
			case (5):
				System.exit(0); //sale del programa
				break;
			
			default:
				System.out.println("N�mero inv�lido, introduzca otro"); //se muestra si el numero introducido es incorrecto
				break;
			}
			
			System.out.println("Escoge una opci�n: ");
			System.out.println("  1. Colocar un n�mero ");
			System.out.println("  2. Eliminar un n�mero ");
			System.out.println("  3. Volver al inicio ");
			System.out.println("  4. Resolver el sudoku ");
			System.out.println("  5. Salir ");
			c = entrada.nextInt();
			
		}while(c==1 || c==2 || c==3 || c==4);
	}
	
}/// OK
