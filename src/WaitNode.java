
public class WaitNode extends Node {

	private int millis;

	public WaitNode() {

	}

	public WaitNode(int millis) {
		this.millis = millis;
	}

	@Override
	public String toString() {
		if (millis != 0) {
			return "wait for " + millis;
		}
		return "wait";
	}

	@Override
	public void execute(Robot r) {
		for (int i=0; i<millis; i++) {
			r.idleWait();
		}
	}

}
