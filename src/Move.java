
public class Move extends Action {

	public Move() {
		// TODO Auto-generated constructor stub
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
