package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import graphicInterface.Window;
import operation.BuzonSincrono;
import operation.House;

public class LineaSerie implements SerialPortEventListener {
	
	SerialPort serialPort;

	House house;
	Window window;
	SocketConnection socketConn;

	int data = 0;
	private Enumeration<?> puertos = null; // para guardar los puertos no
											// encontrados

	private HashMap<String, CommPortIdentifier> mapaPuertos = new HashMap<String, CommPortIdentifier>(); // mapea
																											// el
	// contiene los puertos abiertos
		private CommPortIdentifier identificadorPuertos = null;
		private SerialPort serial = null;

		// input y output para trueque de informacion
		private InputStream input = null;
		private OutputStream output = null;

		// para saber si esta conectado a algun puerto o no
		private boolean conectado = false;

		final static int TIEMPOESPERA = 2000; // el tiempo para conectarse al puerto

		// valores ascii
		final int SPACE_ASCII = 32;
		final int DASH_ASCII = 45;
		final int NEW_LINE_ASCII = 10;

		String logText = ""; // guarda el string para el log

			

	public LineaSerie(House house, Window window, SocketConnection socketConn) {
		this.window = window;
		this.socketConn = socketConn;
		
		buscarPuertos();

		connect("COM3");
		initIOStream();
		initListener();
		while (getConnected()) {
			if (getData() != 0) {
				System.out.print(getData() + " - ");
			}
		}
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			byte[] infor = leer();
			short[] data = interpretarInformacion((short)infor[0]);
			String str = "REQUEST%" + data[0] + "%" + data[1] + "\n";
			System.out.println(str);
			
			BuzonSincrono buzon = new BuzonSincrono();
			window.getTab2().setMessage(data[0], data[1], buzon);
			System.out.println("str badabil");
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
																								// identificadores

	public short[] interpretarInformacion(short inforInt) {
		// TODO Auto-generated method stub
		boolean bits[] = new boolean[8];
		short divisor = 128;
		short multiplicador = 1;
		short accion = 0, usuario = 0;
		short datos[] = new short[2];
		
		
		for (int i = 0; i < 8; i++){
			if (inforInt/divisor > 0){
				inforInt = (short) (inforInt - divisor);
				divisor = (short)(divisor/2);
				bits[i] = true;
			} else {
				divisor = (short) (divisor/2);
				bits[i] = false;
			}
		}
				
		for (int i = 0; i < 5; i++){
			if (bits[7 - i]){
				accion = (short) (accion + multiplicador);
			}
			multiplicador = (short) (multiplicador*2);
		}
		
		multiplicador = 1;
		for (int i = 0; i < 3; i++){
			if (bits[2 - i]){
				usuario = (short) (usuario + multiplicador);
			}
			multiplicador = (short) (multiplicador*2);
		}
		datos[1] = accion;
		datos[0] = usuario;
	      
		return datos;
	}

	public void buscarPuertos() {
		puertos = CommPortIdentifier.getPortIdentifiers();
		while (puertos.hasMoreElements()) {

			CommPortIdentifier curPort = (CommPortIdentifier) puertos.nextElement();
			// solo guardar los seriales
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				mapaPuertos.put(curPort.getName(), curPort);
			}
		}
	}

	// se conecta al puerto elegido en el combobox
	// el puerto comm conectado se guarda en puertoComm, sino salta una
	// excepcion
	public void connect(String puertoSeleccionado) {

		identificadorPuertos = (CommPortIdentifier) mapaPuertos.get(puertoSeleccionado);
		CommPort puertoComm = null;

		try {
			// devuelve un objeto de tipo CommPort
			puertoComm = identificadorPuertos.open("TigerControlPanel", TIEMPOESPERA);
			// casting a SerialPort
			serial = (SerialPort) puertoComm;

			// si se conecta al puerto serial
			setConnected(true);

			// logging
			logText = puertoSeleccionado + " se abrió correctamente.";

			// se deberia de ajusta el baud rate, pero los xbee ya estan
			// configurados correctamente
		} catch (PortInUseException e) {

			logText = puertoSeleccionado + " está en uso. (" + e.toString() + ")";

		} catch (Exception e) {

			logText = "Fallo al abrir " + puertoSeleccionado + "(" + e.toString() + ")";
		}
	}

	// abrir input y output streams
	public boolean initIOStream() {
		// devuelve un boolean para saber si la comunicacion se ha efectuado

		try {
			//
			input = serial.getInputStream();
			output = serial.getOutputStream();

			return true;

		} catch (IOException e) {

			logText = "I/O Streams fallo al abrir. (" + e.toString() + ")";
			return false;
		}
	}

	// se inicializa un event listener para saber cuando esta listo para leer
	// informacion, cuando se ha recibio informacion
	public void initListener() {
		try {

			serial.addEventListener(this);
			serial.notifyOnDataAvailable(true);

		} catch (TooManyListenersException e) {

			logText = "Demasiados listeners. (" + e.toString() + ")";
		}
	}

	// desconectar el puerto serial
	public void disconnect() {
		try {
			
			serial.removeEventListener();
			serial.close();
			input.close();
			output.close();
			conectado = false;

			logText = "Desconectado.";

		} catch (Exception e) {

			logText = "Fallo al cerrar " + serial.getName() + "(" + e.toString() + ")";
		}
	}

	final public boolean getConnected() {
		return conectado;
	}

	public void setConnected(boolean conectado) {
		this.conectado = conectado;
	}

	// action performed de serial, cuando recibe algo entra aqui

	
	public int getData() {
		return data;
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
	
}



