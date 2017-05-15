
public class VariableNode extends Node {
	
	private final String name;
	private String valueString;
	private String valueInt;
	
	public VariableNode(String name, String value) {
		this.name = name;
		this.valueString = value;
	}
	
	public  VariableNode(String name, int value) {
		this.name = name;
		this.valueInt = value + "";
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void execute(Robot r) {
	}
	
	public boolean isString() {
		return this.valueString != null;
	}
	
	public Object getValue() throws NullPointerException {
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
	
}
