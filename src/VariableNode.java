
public class VariableNode {
	
	private String name;
	private String valueString;
	private String valueInt;
	private boolean robotVariable = false;
	
	private String operator;
	private VariableNode v1;
	private VariableNode v2;
	
	public VariableNode(String name, String value) {
		this.name = name;
		this.valueString = value;
	}
	
	public VariableNode(String name, int value, boolean rVar) {
		this.name = name;
		this.valueInt = value + "";
		this.robotVariable = rVar;
	}
	
	public VariableNode(String op, VariableNode v1, VariableNode v2) {
		this.operator = op;
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public String toString() {
		if (this.v1 != null) {
			switch (this.operator) {
	            case ("add"):
	                return v1.toString() + " + " + v2.toString();
	            case ("sub"):
	                return v1.toString() + " - " + v2.toString();
	            case ("mul"):
	                return v1.toString() + " * " + v2.toString();
	            case ("div"):
	                return v1.toString() + " / " + v2.toString();
	        }
		}
		return name;
	}

	public boolean isString() {
		return this.valueString != null;
	}
	
	public boolean isRobotVariable() {
		return this.robotVariable;
	}
	
	public Object getValue() throws NullPointerException {
		if (this.v1 != null) {
			return this.evaluate();
		}
		if (this.valueString == null) {
			return Integer.parseInt(this.valueInt);
		} else if (this.valueInt == null) {
			return this.valueString.toString();
		}
		throw new NullPointerException("Variable has no value");
	}
	
	public void setValue(String s) throws IllegalArgumentException {
		if (valueString == null && valueInt != null) {
			throw new IllegalArgumentException("Invalid variable type: expected Integer");
		}
		this.valueString = s;
	}
	
	public void setValue(int i) throws IllegalArgumentException {
		if (valueString != null && valueInt == null) {
			throw new IllegalArgumentException("Invalid variable type: expected String");
		}
		this.valueInt = i + "";
	}

	public String getName() {
		return this.name;
	}
	
	private int evaluate() {
		switch(this.operator) {
			case("add"):
				return (Integer) (v1.getValue()) + (Integer) (v2.getValue());
			case("sub"):
				return (Integer) (v1.getValue()) - (Integer) (v2.getValue());
			case("mul"):
				return (Integer) (v1.getValue()) * (Integer) (v2.getValue());
			case("div"):
				return (Integer) (v1.getValue()) / (Integer) (v2.getValue());
		}
		return 0;
	}
	
}
