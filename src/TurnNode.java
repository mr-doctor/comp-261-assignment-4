
public class TurnNode extends Node {
	
	private boolean turnsRight;
	
	public TurnNode(boolean turnsRight) {
		this.turnsRight = turnsRight;
	}

	@Override
	public String toString() {
		if (turnsRight) {
			return "turn right";
		}
		return "turn left";
	}

	@Override
	public void execute(Robot r) {
		if (turnsRight) {
			r.turnRight();
		} else {
			r.turnLeft();
		}
	}

}
