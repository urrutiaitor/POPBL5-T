package launcher;

import gnu.io.SerialPortEventListener;
import connection.LineaSerie;
import operation.House;
import operation.SerialReader;


public class Main2 {
	final static int NUMSOCKETS = 10;
	House house;
	HiloSockets hilosSockets[];
	LineaSerie bufferSerie;
	SerialReader hiloSerie;
	SerialPortEventListener listener;
	
	public Main2(){
		bufferSerie = new LineaSerie();
		bufferSerie.initialize(listener);
		house = new House(16,bufferSerie);
		hilosSockets = new HiloSockets[NUMSOCKETS];
		for(int i=0;i<NUMSOCKETS;i++ ){
			hilosSockets[i] = new HiloSockets(house);
		}
		hiloSerie = new SerialReader(house);
		for(int i=0;i<NUMSOCKETS;i++ ){
			hilosSockets[i].start();
		}
		hiloSerie.start();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main principal =new Main();
	}

	

}
