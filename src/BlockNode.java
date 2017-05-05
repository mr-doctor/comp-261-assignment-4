import java.util.List;

public class BlockNode extends Node {

	private List<Node> components;
	
	public BlockNode(List<Node> block) {
		this.components = block;
	}

	@Override
	public String toString() {
		String s = "";
		for (Node n : this.components) {
			s = s + n.toString() + "\n";
		}
		return s;
	}

	@Override
	public void execute(Robot r) {
		for (Node n : this.components) {
			n.execute(r);
		}
	}
	
	public List<Node> getComponents() {
		return this.components;
	}

}
