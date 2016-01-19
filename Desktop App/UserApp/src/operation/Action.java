package operation;

public class Action {

	public final static int NUM_ACTIONS = 16;
	public final static int BLIND_UP = 1;
	public final static int BLIND_DOWN = 2;
	public final static int HEATING_START = 3;
	public final static int HEATING_STOP = 4;
	public final static int DOOR_OPEN = 5;
	public final static int DOOR_CLOSE = 6;
	public final static int ALARM_START = 7;
	public final static int ALARM_STOP = 8;
	public final static int LIGHT1_ON = 9;
	public final static int LIGTH1_OFF = 10;
	public final static int LIGHT2_ON = 11;
	public final static int LIGHT2_OFF = 12;
	public final static int LIGHT3_ON = 13;
	public final static int LIGHT3_OFF = 14;
	public final static int LIGHT4_ON = 15;
	public final static int LIGHT4_OFF = 16;

	int action;
	int user;
	long time;

	public Action(int action, int user, long time) {
		super();
		this.action = action;
		this.user = user;
		this.time = time;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static int getNumActions() {
		return NUM_ACTIONS;
	}

}
