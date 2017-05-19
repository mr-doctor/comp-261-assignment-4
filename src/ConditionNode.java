
public class ConditionNode {

	private String operator;
	public VariableNode v1;
	public VariableNode v2;
	
	public ConditionNode c1;
	public ConditionNode c2;
	
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
	
	public ConditionNode(String op, ConditionNode c1, ConditionNode c2) throws IllegalArgumentException {
		this.operator = op;
		if (c2 == null && !op.equals("not")) {
			throw new IllegalArgumentException("Cannot perform operation on a single condition");
		}
		if (c2 != null && op.equals("not")) {
			throw new IllegalArgumentException("Cannot negate two conditions");
		}
		
		this.c1 = c1;
		this.c2 = c2;
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

	public boolean holds() {
		if (v1 != null) {
			return holdsVariable();
		}
		return holdsConditions();
	}
	
	private boolean holdsVariable() {
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
	
	private boolean holdsConditions() {
		if (c2 != null) {
			switch (operator) {
				case("and"):
					return c1.holds() && c2.holds();
				case("or"):
					return c1.holds() || c2.holds();
			}
		}
		return !c1.holds();
	}

	public void initialise(Robot r) {
		switch (v1.getName()) {
			case ("fuelLeft"):
				v1.setValue(r.getFuel());
				break;
			case ("oppLR"):
				v1.setValue(r.getOpponentLR());
				break;
			case ("oppFB"):
				v1.setValue(r.getOpponentFB());
				break;
			case ("barrelLR"):
				v1.setValue(r.getClosestBarrelLR());
				break;
			case ("barrelFB"):
				v1.setValue(r.getClosestBarrelFB());
				break;
			case ("numBarrels"):
				v1.setValue(r.numBarrels());
				break;
			case ("wallDist"):
				v1.setValue(r.getDistanceToWall());
				break;
		}
	}
}
