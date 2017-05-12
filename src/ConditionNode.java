
public class ConditionNode extends Node {

	private String operator;
	
	public ConditionNode(String op) throws IllegalArgumentException {
		this.operator = op;
		if (!(op.equals("lt") || op.equals("gt") || op.equals("eq"))) {
			throw new IllegalArgumentException("Invalid condition");
		}
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void execute(Robot r) {
	}
	
	public boolean holds() {
		return false;
	}

}
