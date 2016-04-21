package byCodeGame.game.moudle.arena.binartTree;

import byCodeGame.game.entity.bo.LadderArena;

public class Node {

	private LadderArena ladderArena;
	private Node leftChild;
	private Node rightChild;
	
	public Node(LadderArena ladderArena){
		this.setLadderArena(ladderArena);
	}

	public LadderArena getLadderArena() {
		return ladderArena;
	}

	public void setLadderArena(LadderArena ladderArena) {
		this.ladderArena = ladderArena;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
}
