package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;

public class LineaSerie {
	
	SerialPort serialPort;
	private InputStream input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 115200;

	public void initialize(SerialPortEventListener listener) {
		CommPortIdentifier portId = null;
	    Enumeration puertos = CommPortIdentifier.getPortIdentifiers();
		if(puertos.hasMoreElements()){
			while(puertos.hasMoreElements()){
				portId = (CommPortIdentifier) puertos.nextElement();
				
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
	        output = serialPort.getOutputStream();
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

	public void escribir (String comando){
		byte[] command;
		try {
			command=comando.getBytes();
			output.write(command);
		} catch (IOException e) {
			System.out.println("Error al enviar comando");
		}
	}


	public String leer(){
		byte[] readBuffer=new byte[50];
		int numBytes =0;

		try {
	    	while (input.available() > 0){	
	    		numBytes = input.read(readBuffer);
	    	} 
	    
			String valor = new String(readBuffer,0,numBytes);
			return valor;
	    } catch (IOException e){
	    	System.out.print("Error de evento \n");
	    	return "ERROR al leer";
	    }
	}
}

