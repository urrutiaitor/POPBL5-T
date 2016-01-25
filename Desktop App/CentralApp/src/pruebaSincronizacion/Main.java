package pruebaSincronizacion;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;




public class Main {

	final static int NUMSOCKETS = 10;
	House house;
	HiloSockets hilosSockets[];
	LineaSerie bufferSerie;
	SerialPortEventListener listener;
	
	public Main(){
		listener = new SerialPortEventListener() {
			
			@Override
			public void serialEvent(SerialPortEvent arg0) {
				if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE){
					house.saveAction();
//					house.releaseSerialReader();
//					System.out.println("evento entrada: "+arg0.getEventType());
				}
			}
		};
		bufferSerie = new LineaSerie();
		bufferSerie.initialize(listener);
		house = new House(16,bufferSerie);
		hilosSockets = new HiloSockets[NUMSOCKETS];
		for(int i=0;i<NUMSOCKETS;i++ ){
			hilosSockets[i] = new HiloSockets(house);
		}
//		hiloSerie = new SerialReader(house);
		for(int i=0;i<NUMSOCKETS;i++ ){
			hilosSockets[i].start();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main principal =new Main();
	}

}
