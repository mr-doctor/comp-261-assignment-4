
public class MoveNode extends Node {

	public MoveNode() {
		
	}

	@Override
	public String toString() {
		return "move";
	}

	@Override
	public void execute(Robot r) {
		r.move();
	}

}
