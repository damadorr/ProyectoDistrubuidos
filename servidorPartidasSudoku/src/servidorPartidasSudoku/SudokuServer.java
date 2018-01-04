package servidorPartidasSudoku;


import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SudokuServer {

	public static void main(String[] args) {
		
		ServerSocket ss = null;
		ExecutorService pool = Executors.newCachedThreadPool();
		
		try {
			ss= new ServerSocket(6666);
			
			Socket cliente1= null;
			Socket cliente2= null;
			Socket cliente3= null;
			PrintStream ps1= null;
			PrintStream ps2= null;
			PrintStream ps3= null;
			
			while(true){
				try{
					//conexion de 3 clientes de una partida.
					cliente1=ss.accept();
					ps1= new PrintStream(cliente1.getOutputStream());
					ps1.println("Esperando 2 rivales más"); 
					ps1.flush();
					
					cliente2=ss.accept();
					ps2= new PrintStream(cliente2.getOutputStream());
					ps2.println("Esperando 1 rival más");
					ps2.flush();
					
					cliente3=ss.accept();
					ps3= new PrintStream(cliente3.getOutputStream());
					ps3.println("Encontrados 2 rivales");
					ps3.println("Comienza la partida");
					ps3.flush();
					ps2.println("Comienza la partida");
					ps2.flush();
					ps1.println("Comienza la partida");
					ps1.flush();
					
					pool.execute(new JuezPartida(cliente1, cliente2, cliente3));	//lanzamos al juez de la partida de los clientes anteriores
					
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			cerrar(ss);
			pool.shutdown(); 
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
