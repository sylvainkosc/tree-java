package lettertree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

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

	public boolean remove(String word) {
		if (!contains(word))
			return false;
		System.out.println("removing " + word);
		Node current = root;
		Stack<Node> stack = new Stack<>();

		for (int i = 0; i < word.length(); i++) {
			char currentChar = word.charAt(i);
			stack.add(current);
			current = current.children.get(currentChar);
		}
		current.terminal = false;

		int index = word.length() - 1;
		while (stack.size() > 0) {
			Node parent = stack.pop();
			if (!current.terminal && current.children.size() == 0) {
				// System.out.println("iteration "+ index );
				parent.children.remove(word.charAt(index));
				current = parent;
				index--;
			} else {
				// System.out.println("break "+ index );
				break;
			}
		}

		return true;
	}

	public boolean removeRec(String word) {
		if (!contains(word))
			return false;
		removeRec(word, 0, root);
		return true;
	}

	private void removeRec(String word, int index, Node current) {
		char currentChar = word.charAt(index);
		Node child = current.children.get(currentChar);

		if (index == word.length() - 1) {
			child.terminal = false;
			return;
		}

		removeRec(word, index + 1, child);

		if (!child.terminal && child.children.isEmpty()) {
			current.children.remove(currentChar);
		}

	}

	public void clear() {
		root.children.clear();
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
		// on suit le meme principe de parcours que countNodes et countWords,
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
			total += countWords(node);
		}
		return total;
	}

	public boolean containsDoubleChar() {
		return containsDoubleChar(root);
	}

	private boolean containsDoubleChar(Node current) {
		// première condition d'arret : si je n'ai plus de fils, je suis en bout de
		// branche et je return false;
		if (current.children.isEmpty())
			return false;

// ecriture équivalent au paragraphe suivant... on itere directement sur une entry qui est le couple clé valeur puisqu'on a besoin des deux
//		for(Entry<Character, Node> childEntry : current.children.entrySet()) {
//			if(childEntry.getValue().children.containsKey(childEntry.getKey()) return true;
//		}

		// seconde condition d'arret : si pour une de mes lettre, j'ai un fils qui
		// contient la meme dans sa "map" children,
		// alors j'ai un double caractère et je retoure vrai en arretant la récursion
		for (Character letter : current.children.keySet()) {
			Node child = current.children.get(letter);
			if (child.children.containsKey(letter))
				return true;
		}

		// dans le cas general ou je ne m'arrete pas, je lance la récursion sur mon
		// fils. Si un de mes fils trouve, alors j'arrete
		// la récursion et je n'itere pas sur les suivants
		for (Node child : current.children.values()) {
			if (containsDoubleChar(child))
				return true;
		}
		// la fin de la boucle, aucun de mes fils n'a donc retourné vrai et n'a trouvé
		// la double lettre, mes conditions
		// d'arret montrent aussi qu'elle n'est pas sur le noeud courant, donc je
		// retourne false
		return false;
	}

	private boolean containsDoubleChar2(Node current) {
		if (current.children.isEmpty())
			return false;

//		for(Entry<Character, Node> childEntry : current.children.entrySet()) {
//			if(childEntry.getValue().children.containsKey(childEntry.getKey()) return true;
//		}

		for (Character letter : current.children.keySet()) {
			Node child = current.children.get(letter);
			if (child.children.containsKey(letter))
				return true;
			if (containsDoubleChar(child))
				return true;
		}
		return false;
	}

	public void load(String filePath) throws Exception { // charge le fichier dont le chemin est donné en paramètre

		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(filePath));
			String s = reader.readLine();
			while (s != null) {
				if (Character.isLowerCase(s.charAt(0)))
					this.add(s.trim());
				s = reader.readLine();
			}
			System.out.println(this.countNodes());
			System.out.println(this.countWords());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Chargement pas bien passé", e);
		} finally {
			if (reader != null)
				reader.close();
		}

	}

	public List<String> moMoMotus(int length, char firstChar, char endChar) {
		if (!root.children.containsKey(firstChar))
			return null;
		List<String> result = new ArrayList<String>();
		moMoMoMotus(result, root.children.get(firstChar), length, endChar, 1, " " + firstChar);
		return result;
	}

	private void moMoMoMotus(List<String> result, Node current, int length, char endChar, int currentDepth,
			String currentWord) {
		if (currentDepth == length-1) {
			Node Child = current.children.get(endChar);
			if (Child != null && Child.terminal) {
				result.add(currentWord + endChar);
			}
			return;
		}
		for (Character c : current.children.keySet()) {
			Node Child = current.children.get(c);

			moMoMoMotus(result, Child, length, endChar, currentDepth + 1, currentWord + c);
		}
	}
}
