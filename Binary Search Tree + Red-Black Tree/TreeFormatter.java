import java.util.*;

class TreeFormatter {

    int padding = 2; // minimum number of horizontal spaces between two node data

    private int indent(List<String> lines, int margin) {
        // If negative, prefix all lines with spaces and return 0
        if (margin >= 0) return margin;
        String spaces = " ".repeat(-margin);
        int i = 0;
        for (var line : lines) {
            lines.set(i++, spaces + line);
        }
        return 0;
    }

    private List<String> merge(List<String> left, List<String> right) {
        // Merge two arrays, where the right strings are indented so there is no overlap
        int minSize = Math.min(left.size(), right.size());
        int offset = 0;
        for (int i = 0; i < minSize; i++) {
            offset = Math.max(offset, left.get(i).length() + padding - right.get(i).replaceAll("\\S.*", "").length());
        }
        indent(right, -indent(left, offset));
        for (int i = 0; i < minSize; i++) {
            left.set(i, left.get(i) + right.get(i).substring(left.get(i).length()));
        }
        if (right.size() > minSize) {
            left.addAll(right.subList(minSize, right.size()));
        }
        return left;
    }

    private List<String> buildLines(Node node, RedBlackTree tree) {
        List<String> lines = new ArrayList<>();

        if (node == null || node == tree.getNill()) {
            return lines; // Retorna uma lista vazia se o nó for nulo ou o nó nill
        }

        List<String> leftLines = buildLines(node.left, tree);
        List<String> rightLines = buildLines(node.right, tree);

        lines = merge(leftLines, rightLines);
        int half = String.valueOf(node.data).length() / 2;
        int i = half;

        if (!lines.isEmpty()) {
            String line;
            i = lines.get(0).indexOf("*");

            if (node.right == null || node.right == tree.getNill()) {
                line = " ".repeat(i) + "┌─┘";
                i += 2;
            } else if (node.left == null || node.left == tree.getNill()) {
                int indentation = indent(lines, i - 2);
                line = " ".repeat(indentation) + "└─┐";
                i = indentation;
            } else {
                int dist = lines.get(0).length() - 1 - i;
                line = String.format("%s┌%s┴%s┐", " ".repeat(i), "─".repeat(dist / 2 - 1), "─".repeat((dist - 1) / 2));
                i += dist / 2;
            }
            lines.set(0, line);
        }

        lines.add(0, " ".repeat(indent(lines, i - half)) + node.data);
        lines.add(0, " ".repeat(i + Math.max(0, half - i)) + "*");

        return lines;
    }

    public String topDownBinarySearchTree(Node root) {
        List<String> lines = buildLines(root, null);
        return String.join("\n", lines.subList(1, lines.size()));
    }

    public String topDownRedBlackTree(Node root, RedBlackTree tree) {
        List<String> lines = buildLines(root, tree);
        return String.join("\n", lines.subList(1, lines.size()));
    }


}
