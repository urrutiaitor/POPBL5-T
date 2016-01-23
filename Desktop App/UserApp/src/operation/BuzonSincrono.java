package operation;

import java.util.concurrent.Semaphore;

public class BuzonSincrono {

	Object message;
	
	Object sending;
	Object receiving;
	
	Semaphore sent;
	Semaphore received;
	
	public BuzonSincrono() {
		
		message = new Object();
		
		sending = new Object();
		receiving = new Object();
		
		sent = new Semaphore(0);
		received = new Semaphore(0);
	}
	
	public void send(Object message) {
		synchronized (sending) {
			this.message = message;
			sent.release();
			try {
				received.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Object receive() {
		Object receivedMessage = null;
		
		synchronized (receiving) {
			try {
				sent.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			receivedMessage = message;
			received.release();
		}
		return receivedMessage;
	}
}
