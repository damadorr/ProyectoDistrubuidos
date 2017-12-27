package servidorPartidasSudoku;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class JuezPartida implements Runnable {
	private Socket c1,c2,c3;
	@Override
	public void run() {
		int [][] m= new int[9] [9];
		SudokuConSolucion sudoku= new SudokuConSolucion(m);
		HiloMediador h1= new HiloMediador(this.c1, sudoku);
		HiloMediador h2= new HiloMediador(this.c2, sudoku);
		HiloMediador h3= new HiloMediador(this.c3, sudoku);
		h1.start();
		h2.start();
		h3.start();
		/*
		 * hay que conseguir que espere hasta que uno de los hilos reciba la solución, en ese momento ese es el ganador y termina.
		 * */
	}
	public JuezPartida(Socket c1, Socket c2, Socket c3){
		this.c1=c1;
		this.c2=c2;
		this.c3=c3;
	}
}
