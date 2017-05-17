
public class MoveNode extends Node {
	
	int amount;

	public MoveNode(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "move";
	}

	@Override
	public void execute(Robot r) {
		for (int i=0; i<amount; i++) {
			r.move();
		}
	}

}
