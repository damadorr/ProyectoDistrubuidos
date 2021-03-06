package servidorPartidasSudoku;



import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JuezPartida extends TimerTask implements Runnable{
	private Socket c1,c2,c3;

	public JuezPartida(Socket c1, Socket c2, Socket c3){
		this.c1=c1;
		this.c2=c2;
		this.c3=c3;
	}
	
	public void run() {
		int [][] m= new int[9] [9];
		SudokuConSolucion sudoku= new SudokuConSolucion(m);
		sudoku.crearSudoku(17);
		ExecutorService pool = null;
		PrintStream ps1 = null;
		PrintStream ps2 = null;
		PrintStream ps3 = null;
		
		pool = Executors.newFixedThreadPool(3);
		try {
			ps1 = new PrintStream(this.c1.getOutputStream());	
			ps2 = new PrintStream(this.c2.getOutputStream());
			ps3 = new PrintStream(this.c3.getOutputStream());
			HiloMediador h1 = new HiloMediador(this.c1, sudoku);	//creamos un hilo por cliente
			HiloMediador h2 = new HiloMediador(this.c2, sudoku);
			HiloMediador h3 = new HiloMediador(this.c3, sudoku);
			pool.execute(h1);
			pool.execute(h2);
			pool.execute(h3);
			
			long start = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			
				while(!h1.getCadena().equalsIgnoreCase("correcto") && !h2.getCadena().equalsIgnoreCase("correcto") && !h3.getCadena().equalsIgnoreCase("correcto") && (end - start <  900000)) { //comprobamos que no haya pasado mas de 15 min
					end = System.currentTimeMillis();
					if(h1.getCadena().equalsIgnoreCase("abandonado") && h2.getCadena().equalsIgnoreCase("abandonado") && h3.getCadena().equalsIgnoreCase("abandonado")) { //si abandonan los tres termina el juego
						end = start + 900000;
					}
				}
				if(h1.getCadena().equalsIgnoreCase("correcto")) { //depende del cliente que haya acertado el sudoku
					ps1.println("HAS GANADO!");
					ps1.flush();
				} else if(h2.getCadena().equalsIgnoreCase("correcto")) {
						ps2.println("HAS GANADO!");
						ps2.flush();
					} else if(h3.getCadena().equalsIgnoreCase("correcto")) {
						ps3.println("HAS GANADO!");
						ps3.flush();
					} else {
						ps1.println("no hay ganador"); //si han abandonado todos o han pasado mas de 15min
						ps2.println("no hay ganador");
						ps3.println("no hay ganador");
					}
				
				//solo envio mensaje al ganador si lo hay
				
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
			cerrar(this.c1);
			cerrar(this.c2);
			cerrar(this.c3);
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
