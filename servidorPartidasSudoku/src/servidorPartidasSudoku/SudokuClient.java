

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

//COMO HACER UNA COPIA DEL SUDOKU PARA RESOLVERLO
//COMO NOTIFICAR AL RESTO DE LOS CLIENTES QUE UNO A TERMINADO

public class SudokuClient {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Socket cliente = null;
		ObjectInputStream is = null;
		PrintStream out = null;
		DataInputStream in = null;
		
		try {
			cliente = new Socket("localhost", 6666);
			in = new DataInputStream(cliente.getInputStream());
			out = new PrintStream(cliente.getOutputStream());
			System.out.println(in.readLine());
			System.out.println(in.readLine());
			
			is = new ObjectInputStream(cliente.getInputStream());
			SudokuConSolucion s = (SudokuConSolucion) is.readObject();
			SudokuConSolucion sResuelto = s.copiaResuelta();
			sResuelto.mostrarSudoku(9);
			
			Interfaz inter = new Interfaz(s);
			boolean terminado = false;
			while(!terminado) {
				inter.mostrar(); //Si alguno de los otros clientes termina el sudoku, deberá terminar
				if(s.equals(sResuelto)) { //Redefinido 
					System.out.println("Sudoku correcto");
					out.print("Su sudoku es correcto");
					terminado = true;
				} else {
					System.out.println("Sudoku erróneo, inténtelo de nuevo");
				}
				if(s == null){ //XQ
					System.out.println("Has abandonado");
					out.print("Ha abandonado");
					terminado = true;
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


