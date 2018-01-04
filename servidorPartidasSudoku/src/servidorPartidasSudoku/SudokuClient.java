package servidorPartidasSudoku;



import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

//COMO SABER SI EL SOCKET ESTA CERRADO (interrumpir metodo mostrar)
//COMPROBAR QUE ES VALIDO


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
			while(!terminado && !cliente.isClosed()) {
				inter.mostrar(); //Si alguno de los otros clientes termina el sudoku, deberá terminar
				if(inter.getSudoku() == null/*s.equals(null)*/){ //XQ
					System.out.println("Has abandonado");
					out.println("abandonado");
					out.flush();
					terminado = true;
					break;
				} else if(sResuelto.sonIguales(inter.getSudoku())) { //Redefinido 
					System.out.println("Sudoku correcto");
					out.println("correcto");
					out.flush();
					terminado = true;
				} else {
					System.out.println("Sudoku erróneo, inténtelo de nuevo");
				}
			}
			String resultado= in.readLine();
			if(resultado!=null) {
				System.out.println(resultado);				
			} else {
				System.out.println("no has ganado... otra vez será");
			}
			
		} catch (IOException e) {
			System.out.println("no has ganado... otra vez será");
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


