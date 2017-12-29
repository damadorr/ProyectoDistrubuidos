package servidorPartidasSudoku;

import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JuezPartida implements Runnable {
	private Socket c1,c2,c3;

	public JuezPartida(Socket c1, Socket c2, Socket c3){
		this.c1=c1;
		this.c2=c2;
		this.c3=c3;
	}
	
	public void run() {
		int [][] m= new int[9] [9];
		SudokuConSolucion sudoku= new SudokuConSolucion(m);
		sudoku.crearSudoku(10);
		try {
			
			final CyclicBarrier cb = new CyclicBarrier(3+1);
			ExecutorService pool = Executors.newFixedThreadPool(3);
			pool.execute(new HiloMediador(this.c1, sudoku, cb));
			pool.execute(new HiloMediador(this.c2, sudoku, cb));
			pool.execute(new HiloMediador(this.c3, sudoku, cb));
			
			/*HiloMediador h1= new HiloMediador(this.c1, sudoku, cb);
			HiloMediador h2= new HiloMediador(this.c2, sudoku, cb);
			HiloMediador h3= new HiloMediador(this.c3, sudoku, cb);
			h1.start();
			h2.start();
			h3.start();*/
			
			/*
			 * hay que conseguir que espere hasta que uno de los hilos reciba la solución, en ese momento ese es el ganador y termina.
			 * */
		
			cb.await();
			
			
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		} 
	}
}
