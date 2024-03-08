import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class TreeNode {
    private final String projectName;
    private final List<TreeNode> dependencies;

    public TreeNode(final String projectName) {
        this.projectName = projectName;
        dependencies = new ArrayList<TreeNode>();
    }

    public String getProjectName() {
        return projectName;
    }

    public List<TreeNode> getDependencies() {
        return dependencies;
    }

    public void addDependency(final TreeNode dependency) {
        dependencies.add(dependency);
    }

    public static void displayAllDependencies(final TreeNode node) {
        final Set<String> visited = new HashSet<String>();
        displayAllDependenciesHelper(node, visited);
    }

    private static void displayAllDependenciesHelper(final TreeNode node, final Set<String> visited) {
        if (!visited.contains(node.getProjectName())) {
            visited.add(node.getProjectName());
            System.out.println(node.getProjectName());
            for (final TreeNode dependency : node.getDependencies()) {
                displayAllDependenciesHelper(dependency, visited);
            }
        }
    }

    private static void typedependence(final int level) {
        if (level == 1) {
            System.out.print("Arbres : ");
        }
        if (level > 1) {
            System.out.print("Branche : ");
        }
    }

    public static void displayTree(final TreeNode node, final int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        typedependence(level);

        System.out.println(node.getProjectName());

        for (final TreeNode dependency : node.getDependencies()) {
            displayTree(dependency, level + 1);
        }
    }

    public static void displayTreeLarge(final TreeNode node, int level) {
        final Queue<TreeNode> queue = new LinkedList<TreeNode>();
        final Set<String> visited = new HashSet<String>();

        queue.offer(node);

        while (!queue.isEmpty()) {
            final int size = queue.size();

            for (int i = 0; i < size; i++) {
                final TreeNode current = queue.poll();

                if (!visited.contains(current.getProjectName())) {
                    for (int j = 0; j < level; j++) {
                        System.out.print("  ");
                    }
                    System.out.println(current.getProjectName());
                    visited.add(current.getProjectName());

                    for (final TreeNode dependency : current.getDependencies()) {
                        queue.offer(dependency);
                    }
                }
            }
            level++;
        }
    }


}

class TreeNodeClone {
    private final String projectName;
    private final List<TreeNodeClone> dependencies;

    public TreeNodeClone(final String projectName) {
        this.projectName = projectName;
        dependencies = new ArrayList<TreeNodeClone>();
    }

    public String getProjectName() {
        return projectName;
    }

    public List<TreeNodeClone> getDependencies() {
        return dependencies;
    }

    public void addDependency(final TreeNodeClone dependency) {
        dependencies.add(dependency);
    }

    public static TreeNodeClone cloneWithUniqueDependencies(final TreeNode node) {
        final TreeNodeClone cloneRoot = new TreeNodeClone(node.getProjectName());
        final Set<TreeNode> visitedNodes = new HashSet<TreeNode>();
        final Map<TreeNode, TreeNodeClone> clonedNodesMap = new HashMap<TreeNode, TreeNodeClone>();

        final Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(node);
        visitedNodes.add(node);
        clonedNodesMap.put(node, cloneRoot);

        while (!queue.isEmpty()) {
            final TreeNode current = queue.poll();
            final TreeNodeClone cloneCurrent = clonedNodesMap.get(current);

            for (final TreeNode dependency : current.getDependencies()) {
                if (!visitedNodes.contains(dependency)) {
                    visitedNodes.add(dependency);
                    queue.offer(dependency);

                    final TreeNodeClone clonedDependency = new TreeNodeClone(dependency.getProjectName());
                    cloneCurrent.addDependency(clonedDependency);
                    clonedNodesMap.put(dependency, clonedDependency);
                }
            }
        }

        return cloneRoot;
    }


    private static void typedependence(final int level) {
        if (level == 1) {
            System.out.print("Arbres : ");
        }
        if (level > 1) {
            System.out.print("Branche : ");
        }
    }

    public static void displayTree(final TreeNodeClone node, final int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        typedependence(level);

        System.out.println(node.getProjectName());

        for (final TreeNodeClone dependency : node.getDependencies()) {
            displayTree(dependency, level + 1);
        }
    }
}

public class TreeApplicationLargeur {
    public static void main(final String[] args) {
        final TreeNode root = new TreeNode("Terre");

        final TreeNode A = new TreeNode("A");
        final TreeNode B = new TreeNode("B");
        final TreeNode C = new TreeNode("C");
        final TreeNode D = new TreeNode("D");
        final TreeNode E = new TreeNode("E");
        final TreeNode F = new TreeNode("F");
        final TreeNode G = new TreeNode("G");
        final TreeNode H = new TreeNode("H");
        final TreeNode I = new TreeNode("I");
        final TreeNode J = new TreeNode("J");
        final TreeNode K = new TreeNode("K");
        final TreeNode L = new TreeNode("L");

        root.addDependency(A);
        root.addDependency(H);
        A.addDependency(C);
        C.addDependency(D);
        C.addDependency(F);
        C.addDependency(G);
        D.addDependency(E);
        H.addDependency(I);
        H.addDependency(J);
        I.addDependency(B);
        I.addDependency(K);
        I.addDependency(L);
        D.addDependency(K);
        D.addDependency(L);

        System.out.println("Liste de toutes les branches en profondeur:");
        TreeNode.displayAllDependencies(root);

        System.out.println("\nArbre complet en profondeur (recursif):");
        TreeNode.displayTree(root, 0);

        System.out.println("\nArbre parcouru en largeur (!recursif):");
        TreeNode.displayTreeLarge(root, 0);

        System.out.println("\nClone de l'arbre avec dépendances uniques (clone !recursif; affichage recursif) :");
        final TreeNodeClone clonedRoot = TreeNodeClone.cloneWithUniqueDependencies(root);
        TreeNodeClone.displayTree(clonedRoot, 0);
    }
}