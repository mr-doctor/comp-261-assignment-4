
public class IfNode extends Node {

	private BlockNode block;

	public IfNode(BlockNode block) {
		this.block = block;
	}

	@Override
	public String toString() {
		String s = "if {\n";
		for (RobotProgramNode n : this.block.getComponents()) {
			s += "	" + n.toString() + "\n";
		}
		s += "}\n";
		return s;
	}

	@Override
	public void execute(Robot r) {
		
	}

}
