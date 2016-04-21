package byCodeGame.game.moudle.arena.binartTree;

import java.util.ArrayList;
import java.util.List;

public class LadderArenaBinartTree {

	private Node root;

	public LadderArenaBinartTree(){
		root = null;
	}

	/**
	 * 查找节点
	 * @param key  roleId
	 * @return
	 */
	public Node find(int key){
		Node cur = root;
		if(cur == null) 
			return null;

		while(cur.getLadderArena().getRoleId() != key){
			if(cur.getLadderArena().getRoleId() < key){
				cur = cur.getLeftChild();
			}else{
				cur = cur.getRightChild();
			}
			if(cur == null){
				return null;
			}
		}
		return cur;
	}

	/**
	 * 插入新节点
	 * @param node
	 */
	public void insert(Node node){
		if(root == null){
			root = node;
			return;
		}
		Node cur = root;
		while(true){
			if(node.getLadderArena().getRoleId() < cur.getLadderArena().getRoleId()){
				if(cur.getLeftChild() == null){
					cur.setLeftChild(node);
					return;
				}
				cur = cur.getLeftChild();
			}else{
				if(cur.getRightChild() == null){
					cur.setRightChild(node);
					return;
				}
				cur = cur.getRightChild();
			}
		}
	}

	/**
	 * 删除节点
	 * @param node
	 * @return
	 */
	public boolean delete(Node node){
		if(root == null)
			return false;
		//记录目标节点是否为父节点的左子节点
		boolean isLeftChild = true;	
		//要删除的节点
		Node cur = root;	
		//要删除节点的父节点
		Node parent = null;

		//确定要删除节点和他的父节点
		while(cur.getLadderArena().getRoleId() != node.getLadderArena().getRoleId()){
			parent = cur;
			//目标节点小于当前节点，跳转左子节点 
			if(node.getLadderArena().getRoleId() < cur.getLadderArena().getRoleId()){
				cur = cur.getLeftChild();
			}else{	//目标节点大于当前节点，跳转右子节点  
				isLeftChild = false;
				cur = cur.getRightChild();
			}
			if(cur == null){
				return false;
			}
		}

		if(cur.getLeftChild() == null && cur.getRightChild() == null){//目标节点为叶子节点（无子节点）
			if(cur == root){	//要删除的为根节点
				root = null;
			}else if(isLeftChild){	
				//要删除的不是根节点，则该节点肯定有父节点，该节点删除后，需要将父节点指向它的引用置空
				parent.setLeftChild(null);
			}else{
				parent.setRightChild(null);
			}
		}else if(cur.getLeftChild() == null){	//只有一个右子节点
			if(cur == root){
				root = cur.getRightChild();
			}else if(isLeftChild){
				parent.setLeftChild(cur.getRightChild());
			}else{
				parent.setRightChild(cur.getRightChild());
			}	
		}else if(cur.getRightChild() == null){	//只有一个左子节点
			if(cur == root){
				root = cur.getLeftChild();
			}else if(isLeftChild){
				parent.setLeftChild(cur.getLeftChild());
			}else{
				parent.setRightChild(cur.getLeftChild());
			}
		}else{	//有两个子节点
			//第一步要找到欲删除节点的后继节点 
			Node successor = cur.getRightChild();
			Node successorParent = null;
			while(successor.getLeftChild() != null){
				successorParent = successor;  
				successor = successor.getLeftChild();
			}
			//欲删除节点的右子节点就是它的后继，证明该后继无左子节点，则将以后继节点为根的子树上移即可 
			if(successorParent == null){
				if(cur == root){	//要删除的为根节点，则将后继设置为根，且根的左子节点设置为欲删除节点的做左子节点 
					root =successor;
					root.setLeftChild(cur.getLeftChild());
				}else if(isLeftChild){
					parent.setLeftChild(successor);
					successor.setLeftChild(cur.getLeftChild());
				}else{
					parent.setRightChild(successor);
					successor.setLeftChild(cur.getLeftChild());
				}
			}else{	//欲删除节点的后继不是它的右子节点  
				successorParent.setLeftChild(successor.getRightChild());
				successor.setRightChild(cur.getRightChild());
				if(cur == root){
					root = successor;
					root.setLeftChild(cur.getLeftChild());
				}else if(isLeftChild){
					parent.setLeftChild(successor);
					successor.setLeftChild(cur.getLeftChild());
				}else{
					parent.setRightChild(successor);
					successor.setLeftChild(cur.getLeftChild());
				}
			}
		}

		return true;
	}

	/** 前序遍历 	 */
	public static final int PREORDER = 1;  
	/** 中序遍历  	  */
	public static final int INORDER = 2;    
	/** 后续序遍历  	 */
	public static final int POSTORDER = 3;  

	/**
	 * 遍历
	 * @param type	1前序 2中序 3后续
	 */
	public void traverse(int type){
		switch (type) {
		case 1:
			preorder(root);  
			break;
		case 2:
			inorder(root);  
			break;
		case 3:
			postorder(root);  
			break;

		default:
			break;
		}
	}

	/**
	 * 前序遍历
	 * @param currentRoot
	 */
	public void preorder(Node currentRoot){  
		if(currentRoot != null){
//			System.out.println(currentRoot.getLadderArena().getRoleId());
			preorder(currentRoot.getLeftChild());
			preorder(currentRoot.getRightChild());
		}
	}

	/**
	 * 中序遍历
	 * @param currentRoot
	 */
	public void inorder(Node currentRoot){  
		if(currentRoot != null){
			inorder(currentRoot.getLeftChild());
//			System.out.println(currentRoot.getLadderArena().getRoleId());
			inorder(currentRoot.getRightChild());
		}
	}
	
	/**
	 * 后续遍历
	 * @param currentRoot
	 */
	public void postorder(Node currentRoot){
		if(currentRoot != null){
			postorder(currentRoot.getLeftChild());
			postorder(currentRoot.getRightChild());
//			System.out.println(currentRoot.getLadderArena().getRoleId());
		}
	}
	
	/**
	 * 私有方法，用迭代方法来获取左子树和右子树的最大深度，返回两者最大值
	 * @param currentNode
	 * @param initDeep
	 * @return
	 */
	private int getDepth(Node currentNode,int initDeep){
		int deep = initDeep;
		int leftDeep = initDeep;
		int rightDeep = initDeep;
		if(currentNode.getLeftChild() != null){		//计算当前节点左子树的最大深度  
			leftDeep = getDepth(currentNode.getLeftChild(), deep + 1);
		}
		if(currentNode.getRightChild() != null){	//计算当前节点右子树的最大深度 
			rightDeep = getDepth(currentNode.getRightChild(), deep + 1);
		}
		return Math.max(leftDeep, rightDeep);
	}
	
	/**
	 * 获取树的深度
	 * @return
	 */
	public int getTreeDepth(){
		if(root == null)
			return 0;
		return getDepth(root, 1);
	}
	
	/**
	 * 返回关键值最大的节点 
	 * @return
	 */
	public Node getMax(){
		if(isEmpty())
			return null;
		Node cur = root;
		while(cur.getRightChild() != null){
			cur = cur.getRightChild();
		}
		return cur;
	}
	
	/**
	 * 返回关键值最小的节点
	 * @return
	 */
	public Node getMin(){
		if(isEmpty())
			return null;
		Node cur = root;
		while(cur.getLeftChild() != null){
			cur = cur.getLeftChild();
		}
		return cur;
	}
	
	/**
	 * 以树的形式打印出该树 
	 */
	public void displayTree(){
		int depth = getTreeDepth();
		ArrayList<Node> currentLayerNodes = new ArrayList<Node>();
		currentLayerNodes.add(root);
		int layerIndex = 1;
		while(layerIndex <= depth){
			int NodeBlankNum = (int)Math.pow(2, depth-layerIndex)-1;
			for (int i = 0; i < currentLayerNodes.size(); i++) {
				Node node = currentLayerNodes.get(i);
				printBlank(NodeBlankNum);
				
				if(node == null){
//					System.out.print("*\t");  //如果该节点为null，用空位代替 
				}else{
//					System.out.print("*  "+node.getLadderArena().getRoleId()+"\t");  //打印该节点  
				}
				
				printBlank(NodeBlankNum);  //打印节点之后的空位  
//				System.out.print("*\t");   //补齐空位  
			}
//			System.out.println();  
			layerIndex++; 
			currentLayerNodes = getAllNodeOfThisLayer(currentLayerNodes);  //获取下一层所有的节点  
		}
	}
	
	/**
	 * 获取指定节点集合的所有子节点  
	 * @param parentNodes
	 * @return
	 */
	private ArrayList<Node> getAllNodeOfThisLayer(List<Node> parentNodes){  
		ArrayList<Node> list = new ArrayList<Node>();  
		Node parentNode;  
		for (int i = 0; i < parentNodes.size(); i++) {
			parentNode = (Node)parentNodes.get(i);
			if(parentNode != null){
				if(parentNode.getLeftChild() != null){	//如果上层的父节点存在左子节点，加入集合  
					list.add(parentNode.getLeftChild());
				}else{		//如果上层的父节点不存在左子节点，用null代替，一样加入集合
					list.add(null);
				}
				if(parentNode.getRightChild() != null){
					list.add(parentNode.getRightChild());
				}else{
					list.add(null);
				}
			}else{		//如果上层父节点不存在，用两个null占位，代表左右子节点  
				list.add(null);
				list.add(null);
			}
		}
		return list;
	}
	
	/**
	 * 打印指定个数的空位 
	 * @param num
	 */
	private void printBlank(int num){
		for (int i = 0; i < num; i++) {
//			System.out.println("*\t");
		}
	}
	
	/**
	 * 判空
	 * @return
	 */
	public boolean isEmpty(){
		return (root == null);
	}
	
	/**
	 * 判断是否为叶子节点 
	 * @param node
	 * @return
	 */
	public boolean isLeaf(Node node){
		return (node.getLeftChild() !=null || node.getRightChild() !=null);
	}
	
	/**
	 * 获取根节点  
	 * @return
	 */
	public Node getRoot(){
		return root;
	}
}
