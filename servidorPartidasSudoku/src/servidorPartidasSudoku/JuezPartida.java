

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Timer;
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
		sudoku.crearSudoku(25);
		ExecutorService pool = null;
		PrintStream ps1 = null;
		PrintStream ps2 = null;
		PrintStream ps3 = null;
		Timer t= new Timer();
		
		

			//final CyclicBarrier cb = new CyclicBarrier(3+1);
			pool = Executors.newFixedThreadPool(3);
			try {
				ps1 = new PrintStream(this.c1.getOutputStream());
				ps2 = new PrintStream(this.c2.getOutputStream());
				ps3 = new PrintStream(this.c3.getOutputStream());
				
				
				String cadena1 = "";
				String cadena2 = "";
				String cadena3 = "";
				HiloMediador h1 = new HiloMediador(this.c1, sudoku, cadena1);
				HiloMediador h2 = new HiloMediador(this.c2, sudoku, cadena2);
				HiloMediador h3 = new HiloMediador(this.c3, sudoku, cadena3);
				pool.execute(h1);
				pool.execute(h2);
				pool.execute(h3);
				
				long start = System.currentTimeMillis();
				long end = System.currentTimeMillis();
				
					while((cadena1!="correcto" || cadena2!="correcto" || cadena3!="correcto") && (end - start <  1000)) {
						//System.out.println("esperando ganador...");
						end = System.currentTimeMillis();
						if(cadena1=="abandonado" && cadena2=="abandonado" && cadena3=="abandonado") {
							end = start + 900000;
						}
					}
					if(cadena1=="correcto") {
						ps1.println("HAS GANADO!");
						ps2.println("no has ganado...");
						ps3.println("no has ganado...");
					}
					if(cadena2=="correcto") {
						ps2.println("HAS GANADO!");
						ps1.println("no has ganado...");
						ps3.println("no has ganado...");
					}
					if(cadena3=="correcto") {
						ps3.println("HAS GANADO!");
						ps2.println("no has ganado...");
						ps1.println("no has ganado...");
					}
					else {
						
						ps1.println("Se ha acabado el tiempo ! o han abandonado todos"); //NO LO LEE SI ESTA MOSTRANDO INTERFAZ, EXCEPCIONES
						ps2.println("Se ha acabado el tiempo ! o han abandonado todos");
						ps3.println("Se ha acabado el tiempo ! o han abandonado todos");
					}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pool.shutdown();
				cerrar(this.c1);
				cerrar(this.c2);
				cerrar(this.c3);
			}
			
			
			
			/*
			 * hay que conseguir que espere hasta que uno de los hilos reciba la solución, en ese momento ese es el ganador y termina.
			 * */
		
			//cb.await();

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
