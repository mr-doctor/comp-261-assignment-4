
public class IfNode extends Node {

	private BlockNode block;
	private ConditionNode condition;

	public IfNode(BlockNode block, ConditionNode con) {
		this.block = block;
		this.condition = con;
	}

	@Override
	public String toString() {
		String s = "if (" + this.condition + ") {\n";
		for (RobotProgramNode n : this.block.getComponents()) {
			s += "	" + n.toString() + "\n";
		}
		s += "}\n";
		return s;
	}

	@Override
	public void execute(Robot r) {
		if (this.condition.holds()) {
			for (RobotProgramNode n : this.block.getComponents()) {
				n.execute(r);
			}
		}
	}

}
