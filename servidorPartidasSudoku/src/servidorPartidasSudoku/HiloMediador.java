package servidorPartidasSudoku;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class HiloMediador extends Thread{
	
	private Socket socket;
	private Sudoku sudoku;
	public void run(){
		ObjectOutputStream os=null;
		ObjectInputStream is=null;
		PrintStream ps= null;
		try {
			os = new ObjectOutputStream (this.socket.getOutputStream());
			os.writeObject(this.sudoku);
			SudokuConSolucion s=(SudokuConSolucion) is.readObject();//en la clase cliente se debe comprobar que el sudoku está resuelto correctamente antes de enviarlo.
			ps= new PrintStream(this.socket.getOutputStream());
			ps.println("enhorabuena, has ganado");
			cerrar(this.socket);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			cerrar(os);
			cerrar(is);
		}
	}
	public HiloMediador(Socket so, Sudoku su){
		this.socket=so;
		this.sudoku=su;

//		byte[] bytes =  bos.toByteArray(); // devuelve byte[]
//
//		ByteArrayInputStream bis= new ByteArrayInputStream(bytes); // bytes es el byte[]
//		ObjectInputStream is = new ObjectInputStream(bis);
//		Sudoku sudokuSolucion = (Sudoku)is.readObject();
//		is.close();
	}
	
	public static void cerrar(Closeable o){
		try{
			if(o!=null){
				o.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
