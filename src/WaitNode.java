
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
		try {
			if (millis != 0) {
				r.wait(millis);
			} else {
				r.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
