
public class WhileNode extends Node implements RobotProgramNode {

	private BlockNode block;
	private ConditionNode condition;
	
	public WhileNode(BlockNode block, ConditionNode con) {
		this.block = block;
		this.condition = con;
	}
	
	@Override
	public String toString() {
		String s = "while (" + this.condition + ") {\n";
		for (RobotProgramNode n : this.block.getComponents()) {
			s += n.toString() + "\n";
		}
		s += "}\n";
		return s;
	}
	
	@Override
	public void execute(Robot r) {
		this.condition.initialise(r);
		while (this.condition.holds()) {
			this.condition.initialise(r);
			for (RobotProgramNode n : this.block.getComponents()) {
				n.execute(r);
			}
		}
	}

}
