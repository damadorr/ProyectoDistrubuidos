

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HiloMediador extends Thread{
	
	private Socket socket;
	private Sudoku sudoku;
	private CyclicBarrier cb;
	
	public HiloMediador(Socket so, Sudoku su, CyclicBarrier cb){
		this.socket=so;
		this.sudoku=su;
		this.cb = cb;                       

//		byte[] bytes =  bos.toByteArray(); // devuelve byte[]
//
//		ByteArrayInputStream bis= new ByteArrayInputStream(bytes); // bytes es el byte[]
//		ObjectInputStream is = new ObjectInputStream(bis);
//		Sudoku sudokuSolucion = (Sudoku)is.readObject();
//		is.close();
	}
	
	public void run(){
		ObjectOutputStream os = null;
		ObjectInputStream is = null;
		PrintStream ps = null;
		try {
			os = new ObjectOutputStream (this.socket.getOutputStream()); //envía el sudoku al cliente
			os.writeObject(this.sudoku);
			
			
//			is = new ObjectInputStream(this.socket.getInputStream());
//			SudokuConSolucion s=(SudokuConSolucion) is.readObject();//en la clase cliente se debe comprobar que el sudoku está resuelto correctamente antes de enviarlo.
			ps= new PrintStream(this.socket.getOutputStream());
			ps.println("enhorabuena, has ganado");
			cerrar(this.socket);
			
			cb.await(); //COMO SABER CUAL HA GANADO, Y MOSTRARSELO A LOS DEMÁS (?)
			
		} catch (IOException | InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		} finally{
			cerrar(os);
			cerrar(is);
		}
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
