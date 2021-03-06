package servidorPartidasSudoku;



import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloMediador implements Runnable {
	
	private Socket socket;
	private String cadena;
	private Sudoku sudoku;
	
	public HiloMediador(Socket so, Sudoku su){
		this.socket = so;
		this.sudoku = su;
		this.cadena = "";                  
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		ObjectOutputStream os = null;
		DataInputStream in = null;
		
		try {
			os = new ObjectOutputStream (this.socket.getOutputStream()); //env�a el sudoku al cliente
			os.writeObject(this.sudoku);
			os.flush();
			
			in = new DataInputStream(this.socket.getInputStream());
			this.cadena = in.readLine();
			
			
		} catch (IOException e) {
			this.cadena="abandonado";
		}
	}
	
	public String getCadena() {
		return this.cadena;
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
