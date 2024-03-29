import java.util.ArrayList;
import java.util.List;

public class IfNode extends Node {

	private BlockNode block;
	private ConditionNode condition;
	public List<IfNode> elses;

	public IfNode(BlockNode block, ConditionNode con) {
		this.block = block;
		this.condition = con;
		this.elses = new ArrayList<>();
	}

	@Override
	public String toString() {
		String s = "";
		if (this.condition == null) {
			s = "else {\n";
		} else {
			s = "if (" + this.condition + ") {\n";
		}
		
		for (RobotProgramNode n : this.block.getComponents()) {
			s += n.toString() + "\n";
		}
		
		if (this.elses.isEmpty()) {
			s += "}\n";
		} else {
			s += "} ";
			for (int i=0; i<this.elses.size(); i++) {
				s += this.elses.get(i).toString();
			}
		}
		return s;
	}

	@Override
	public void execute(Robot r) {
		if (this.condition != null) {
			this.condition.initialise(r);
			if (this.condition.holds()) {
				for (RobotProgramNode n : this.block.getComponents()) {
					n.execute(r);
				}
			} else {
				for (IfNode n : elses) {
					for (RobotProgramNode n2 : n.block.getComponents()) {
						n2.execute(r);
					}
				}
			}
		} else {
			for (RobotProgramNode n : this.block.getComponents()) {
				n.execute(r);
			}
		}
	}

}
