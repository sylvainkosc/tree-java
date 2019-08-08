package lettertree;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

public class LetterTree {

	private Node root = new Node();

	public void add(String word) {
		Node current = root;
		char[] chars = word.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!current.children.containsKey(chars[i])) {
				Node n = new Node();
				current.children.put(chars[i], n);
			}
			current = current.children.get(chars[i]);
		}
		current.terminal = true;
	}

	public void remove(String word, int currentIndex, Node currentNode) {
		if (contains(word) == false) {
			return;}
		for (Character c : currentNode.children.keySet()) {
			currentNode.children.get(c);
		}
			if (currentNode.children.isEmpty());{
			
		}

	}

	public void display() {
		System.out.println("----------------------------");
		display(root, new char[100], 0);
	}

	private void display(Node currentNode, String s) {
		if (currentNode.terminal)
			System.out.println(s);
		for (Character c : currentNode.children.keySet()) {
			display(currentNode.children.get(c), s + c);
		}

	}

	private void display(Node currentNode, char[] stack, int length) {
		if (currentNode.terminal)
			System.out.println(new String(stack, 0, length));
		for (Character c : currentNode.children.keySet()) {
			stack[length] = c;
			display(currentNode.children.get(c), stack, length + 1);
		}

	}

	public boolean contains(String word) {
		// solution 1 itérative: similaire au add...

		Node current = root;
		char[] chars = word.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!current.children.containsKey(chars[i])) {
				return false;
			}
			current = current.children.get(chars[i]);
		}
		return current.terminal;
	}

	public boolean containsRecursive(String word) {
		return containsRecursive(word, 0, root);
	}

	private boolean containsRecursive(String word, int currentIndex, Node currentNode) {
		if (currentIndex == word.length())
			return currentNode.terminal;
		char currentChar = word.charAt(currentIndex);
		Node child = currentNode.children.get(currentChar);
		if (child == null)
			return false;
		return containsRecursive(word, currentIndex + 1, child);
	}

	public int countNodes() { // retourne le nombre de noeud de l'arbre
		return countNodes(root);
	}

	private int countNodes(Node currentNode) {
		int total = 1;
		for (Node node : currentNode.children.values()) {
			total += countNodes(node);
		}
		return total;
	}

	public int maxDepth() { // retourne la profondeur maximum de l'arbre
		return maxDepth(root);
	}

	private int maxDepth(Node currentNode) { // retourne la profondeur maximum de l'arbre
		// on suite le meme principe de parcours que countNodes et countWords,
		// la seule différence est que ON RECHERCHE LA MAX DES FILS DANS LA RECURSION

		// petit rappel :il est parfois plus facile de commencer par la condition
		// d'arret:
		// c'est à dire penser en premier à ce que fait le noeud feuille,
		// ensuite réfléchir à ce que doit faire sont parent, puis le parent du parent
		// et vérifier que ca fonctionne dans le cas général
		if (currentNode.children.isEmpty())
			return 1;
		int max = -1;
		for (Node node : currentNode.children.values()) {
			int tmpDepth = maxDepth(node);
			if (tmpDepth > max)
				max = tmpDepth;
		}
		return max + 1;
	}

	public int countWords() {// retourne le nombre de mots stockés dans l'arbre
		return countWords(root);
	}

	private int countWords(Node currentNode) {
		// la seule chose qui change est la condition d'etre terminal pour ajouter 1 au
		// total
		int total = currentNode.terminal ? 1 : 0;
		// le reste est indentique à countNodes
		for (Node node : currentNode.children.values()) {
			total += countNodes(node);
		}
		return total;
	}

	public void load(String filePath) { // charge le fichier dont le chemin est donné en paramètre
		// TODO
	}

	public List<String> moMoMotus(int length, char firstChar, char endChar) {
		if (root.children.containsKey(firstChar));
		List<String> result = new ArrayList<String>();
		moMoMoMotus(result,root.children.get(firstChar),length,endChar,1," "+firstChar);
		return result;}

		private void moMoMoMotus(List<String> result, Node node,int length, char endChar, int currentDepth, String currentWord){
			if(currentDepth==length) {
				Node Child = node.children.get(endChar);
				if (Child!=null && Child.terminal) {
					result.add(currentWord);
				}
				return;
			}
	
	}

	
	}


