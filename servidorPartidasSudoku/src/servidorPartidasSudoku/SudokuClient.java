package servidorPartidasSudoku;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SudokuClient {
	public static void main(String[] args) {
		
		Socket cliente = null;
		ObjectInputStream is = null;
		PrintStream out = null;
		try {
			cliente = new Socket("localhost", 6666);
			is = new ObjectInputStream(cliente.getInputStream());
			out = new PrintStream(cliente.getOutputStream());
			SudokuConSolucion s = (SudokuConSolucion) is.readObject();
			s.resolver();
			
			Interfaz inter = new Interfaz(s);
			boolean terminado = false;
			while(!terminado) {
				SudokuConSolucion sClient = inter.mostrar(); //Si alguno de los otros clientes termina el sudoku, deberá terminar
				if(s.equals(sClient)) { //Redefinir equals, para que compruebe casilla a casilla (?)
					System.out.println("Sudoku correcto");
					out.print("Su sudoku es correcto");
					terminado = true;
				}else if(sClient.equals(null)){
					System.out.println("Has abandonado");
					out.print("Ha abandonado");
					terminado = true;
				} else {
					System.out.println("Sudoku erróneo, inténtelo de nuevo");
					//inter.mostrar(); //Crear una nueva con sClient (?) //HAY QUE REPETIR LAS COMPROBACIONES
				}
			}
			
			

			
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			cerrar(cliente);
		}
	}
	
	public static void cerrar(Closeable o){
		try {
			if(o!=null){
				o.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


