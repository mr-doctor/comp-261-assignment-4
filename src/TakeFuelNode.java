
public class TakeFuelNode extends Node {

	public TakeFuelNode() {

	}

	@Override
	public String toString() {
		return "take fuel";
	}

	@Override
	public void execute(Robot r) {
		r.takeFuel();
	}

}
