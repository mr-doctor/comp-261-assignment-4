
public class LoopNode extends Node {

	private BlockNode block;
	
	public LoopNode(BlockNode block) {
		this.block = block;
	}

	@Override
	public String toString() {
		String s = "loop {\n";
		for (Node n : this.block.getComponents()) {
			System.out.println("adding " + n.toString());
			s += "	" + n.toString();
		}
		s += "}\n";
		return s;
	}

	@Override
	public void execute(Robot r) {
		this.block.execute(r);
	}
	
	public BlockNode getBlock() {
		return this.block;
	}

}
