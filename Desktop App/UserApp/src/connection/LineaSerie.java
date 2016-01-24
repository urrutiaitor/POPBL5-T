package connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import graphicInterface.Window;
import operation.BuzonSincrono;
import operation.House;

public class LineaSerie implements SerialPortEventListener {
	
	SerialPort serialPort;
	private InputStream input;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 115200;
	final int NEW_LINE_ASCII = 10;
	
	House house;
	Window window;
	SocketConnection socketConn;


	public LineaSerie(House house, Window window, SocketConnection socketConn) {
		house = this.house;
		window = this.window;
		socketConn = this.socketConn;
	}


	public void initialize(SerialPortEventListener listener) {
		CommPortIdentifier portId = null;
	    Enumeration puertos = CommPortIdentifier.getPortIdentifiers();
	    
		if(puertos.hasMoreElements()){
			while(puertos.hasMoreElements()){
				portId = (CommPortIdentifier) puertos.nextElement();
				break;
			}
		}else{
			System.out.println("no hay puertos com");
		}

	    try {
	    	// abrir puerta
	        serialPort = (SerialPort) portId.open(this.getClass().getName(),
	                TIME_OUT);
	        System.out.println("\nPuerta abierta... \n");
	        
	        //configura la linea serie
	        serialPort.setSerialPortParams(DATA_RATE,
	                SerialPort.DATABITS_8,
	                SerialPort.STOPBITS_1,
	                SerialPort.PARITY_NONE);
	        System.out.println("Linea configurada... \n");
	        
	        //crea los objetos para lectura y escritura de bytes, asociados a la puerta
	        input = serialPort.getInputStream();
	        
	        System.out.println("Objetos de lectura creados... \n");
	        
	        //asigna un gestor de eventos a la linea serie
	        serialPort.addEventListener(listener);
	        serialPort.notifyOnDataAvailable(true);
	        System.out.println("NotifyOnDataAvailable(true)... \n");
	      
	    } catch (Exception e) {
	        System.err.println(e.toString());
	    }
	}


	public synchronized void close() {
	    if (serialPort != null) {
	        serialPort.removeEventListener();
	        serialPort.close();
	    }
	}

	public byte[] leer(){
		
		byte b[] = null;;
		
		int available = 0 ;
		try {
			available = input.available();
			b = new byte[available];
			input.read(b, 0, input.available());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Available: " + available + "String: " + new String(b));
		
		return b;
	}


	@Override
	public void serialEvent(SerialPortEvent arg0) {
		if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			byte[] infor = leer();
			short[] data = house.interpretarInformacion((short)infor[0]);
			String str = "REQUEST%" + data[0] + "%" + data[1] + "\n";
			System.out.println(str);
			
			BuzonSincrono buzon = new BuzonSincrono();
			window.getTab2().setMessage(data[0], data[1], buzon);
			String str1 = (String) buzon.receive();
			if (str1.equals("ACCEPTED")) {
				System.out.println("to send serial");
				socketConn.tryWrite(str);
			}
			if (str1.equals("DENIED")) {
				System.out.println("Request denied");
			}
		}
	}

	
}



