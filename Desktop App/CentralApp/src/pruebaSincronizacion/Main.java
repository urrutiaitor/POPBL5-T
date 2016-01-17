package pruebaSincronizacion;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;




public class Main {
//	short data[] = new short[2];
//	short[] datos = new short[2];
//	char caracter;
	final static int NUMSOCKETS = 10;
	House house;
	HiloSockets hilosSockets[];
	LineaSerie bufferSerie;
//	SerialReader hiloSerie;
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
		
//		hiloSerie.start();
		
//		data[0] = 8; //user
//		data[1] = 17; //action
//		System.out.println("Datos del principio" + data[0] + " "+ data[1]);
//		caracter = generarInformacion(data);
//		System.out.println("Caracter: " + caracter);
//		datos = interpretarInformacion((short)caracter);
//		System.out.println("Datos al final" + datos[0] + " "+ datos[1]);
////		Calendar calendar = Calendar.getInstance();
//		long time =  calendar.getTimeInMillis();
//		Date date = new Date(time);
//		System.out.println(date);
//		
	//	Calendar calendar = Calendar.getInstance();
	//	Date myDate= new Date(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DAY_OF_MONTH), 
	//			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	//	System.out.println(myDate.getDate() + "/" + myDate.getMonth() + "/" + myDate.getYear() + " " +myDate.getHours() + ":" + myDate.getMinutes() + ":" + myDate.getSeconds());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main principal =new Main();
	}

//	private short[] interpretarInformacion(short inforInt) {
//		// TODO Auto-generated method stub
//		boolean bits[] = new boolean[8];
//		short divisor = 128;
//		short multiplicador = 1;
//		short accion = 0, usuario = 0;
//		short datos[] = new short[2];
//		
//		for (int i = 0; i < 8; i++){
//			if (inforInt/divisor > 0){
//				inforInt = (short) (inforInt - divisor);
//				divisor = (short)(divisor/2);
//				bits[i] = true;
//			} else {
//				divisor = (short) (divisor/2);
//				bits[i] = false;
//			}
//		}
//				
//		for (int i = 0; i < 5; i++){
//			if (bits[7 - i]){
//				accion = (short) (accion + multiplicador);
//			}
//			multiplicador = (short) (multiplicador*2);
//		}
//		
//		multiplicador = 1;
//		for (int i = 0; i < 3; i++){
//			if (bits[2 - i]){
//				usuario = (short) (usuario + multiplicador);
//			}
//			multiplicador = (short) (multiplicador*2);
//		}
//		datos[1] = accion;
//		datos[0] = usuario;
//	      
//		return datos;
//	}
//	private char generarInformacion(short[] data) {
//		// TODO Auto-generated method stub
//		boolean bits[] = new boolean [8];
//		short multiplicador;
//		short resultado = 0; 
//		short divisor = 4;
//		char caracter;
//		
//		for( int i= 0; i<3;i++){
//		
//			if((data[0]/divisor)>0){
//				data[0] = (short) (data[0] - divisor);
//				bits[i] = true;
//			}else{
//				bits[i] = false;
//			}
//			divisor = (short) (divisor/2);
//			
//		}
//		divisor = 16;
//		for(int i= 3; i<8;i++){
//			
//			if((data[1]/divisor)>0){
//				data[1] = (short) (data[1] - divisor);
//				bits[i] = true;
//			}else{
//				bits[i] = false;
//			}
//			divisor = (short) (divisor/2);
//			
//		}
//		
//		multiplicador = 1;
//		for(int i=7;i>=0;i--){
//			if(bits[i]){
//				resultado = (short) (resultado + multiplicador);
//			}
//			multiplicador = (short) (multiplicador*2);
//		}
//		caracter = (char) resultado;      
//		return caracter;
//	}
}
