
public class ConditionNode extends Node {

	private String operator;
	private VariableNode v1;
	private VariableNode v2;
	
	public ConditionNode(String op, VariableNode v1, VariableNode v2) throws IllegalArgumentException {
		this.operator = op;
		if (!(op.equals("lt") || op.equals("gt") || op.equals("eq"))) {
			throw new IllegalArgumentException("Invalid operator");
		}
		if (v1.isString() != v2.isString()) {
			throw new IllegalArgumentException("Inconsistent variable type: cannot compare String and Integer");
		}
		if (v1.isString() && (op.equals("lt") || op.equals("gt"))) {
			throw new IllegalArgumentException("Invalid operator for String");
		}
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public String toString() {
		if (operator.equals("lt")) {
			return v1 + " < " + v2;
		} else if (operator.equals("gt")) {
			return v1 + " > " + v2;
		} else if (operator.equals("eq")) {
			return v1 + " == " + v2;
		}
		return null;
	}

	@Override
	public void execute(Robot r) {
	}
	
	public boolean holds() {
		if (v1.isString()) {
			return v1.getValue().equals(v2.getValue());
		}
		if (this.operator.equals("lt")) {
			return (int) (v1.getValue()) < (int) (v2.getValue());
		} else if (this.operator.equals("gt")) {
			return (int) (v1.getValue()) > (int) (v2.getValue());
		} else if (this.operator.equals("eq")) {
			return (int) (v1.getValue()) == (int) (v2.getValue());
		}
		return false;
	}

}
