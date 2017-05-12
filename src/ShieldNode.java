
public class ShieldNode extends Node {

	private boolean shieldOn;
	
	public ShieldNode(boolean shieldOn) {
		this.shieldOn = shieldOn;
	}

	@Override
	public String toString() {
		if (this.shieldOn) {
			return "shield on";
		}
		return "shield off";
	}

	@Override
	public void execute(Robot r) {
		r.setShield(shieldOn);
	}

}
