package servidorPartidasSudoku;



import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

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
			System.out.println(in.readLine());	//mensaje de espera
			System.out.println(in.readLine());
			
			is = new ObjectInputStream(cliente.getInputStream());
			SudokuConSolucion s = (SudokuConSolucion) is.readObject();	//recibimos el sudoku
			SudokuConSolucion sResuelto = s.copiaResuelta();
			sResuelto.mostrarSudoku();
			
			Interfaz inter = new Interfaz(s);
			boolean terminado = false;
			while(!terminado && !cliente.isClosed()) {
				inter.mostrar(); //mostramos interfaz al cliente
				if(inter.getSudoku() == null){ //si abandona
					System.out.println("Has abandonado");
					out.println("abandonado");
					out.flush();
					terminado = true;
					break;
				} else if(sResuelto.sonIguales(inter.getSudoku())) { //si es correcto
					System.out.println("Sudoku correcto");
					out.println("correcto");
					out.flush();
					terminado = true;
				} else {
					System.out.println("Sudoku erróneo, inténtelo de nuevo"); //si es erroneo
				}
			}
			String resultado= in.readLine();
			if(resultado!=null) {
				System.out.println(resultado);				
			} else {
				System.out.println("No has ganado... otra vez será");
			}
			
		} catch (IOException e) {
			System.out.println("No has ganado... otra vez será");
		} catch(ClassNotFoundException e) {
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


