import java.util.List;

public class BlockNode extends Node {

	private List<RobotProgramNode> components;
	
	public BlockNode(List<RobotProgramNode> block) {
		this.components = block;
		for (int i=0; i<this.components.size(); i++) {
			if (this.components.get(i) == null) {
				this.components.remove(i);
			}
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (RobotProgramNode n : this.components) {
			s = s + n.toString() + "\n";
		}
		return s;
	}

	@Override
	public void execute(Robot r) {
		for (RobotProgramNode n : this.components) {
			n.execute(r);
		}
	}
	
	public List<RobotProgramNode> getComponents() {
		return this.components;
	}

}
