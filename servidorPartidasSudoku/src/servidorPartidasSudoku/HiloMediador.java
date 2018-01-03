

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloMediador implements Runnable{
	
	private Socket socket;
	private String cadena;
	private Sudoku sudoku;
	//private CyclicBarrier cb;
	
	public HiloMediador(Socket so, Sudoku su, String cadena){
		this.socket = so;
		this.sudoku = su;
		this.cadena = cadena;
		//this.cb = cb;                       
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		ObjectOutputStream os = null;
		ObjectInputStream is = null;
		DataInputStream in = null;
		
		try {
			os = new ObjectOutputStream (this.socket.getOutputStream()); //envía el sudoku al cliente
			os.writeObject(this.sudoku);
			
			in = new DataInputStream(this.socket.getInputStream());
			this.cadena = in.readLine();

			/*ps= new PrintStream(this.socket.getOutputStream());
			ps.println("enhorabuena, has ganado");
			cerrar(this.socket);*/
			
			//cb.await(); //COMO SABER CUAL HA GANADO, Y MOSTRARSELO A LOS DEMÁS (?)
			
		} catch (IOException e) {
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
