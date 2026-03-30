import java.util.*;

public class PlagiarismGraphModule {

    // MAIN FUNCTION
    public Map<String, Object> analyze(List<SubmissionNode> submissions) {

        Map<String, List<String>> graph = buildGraph(submissions);

        List<List<String>> bfsGroups = findGroupsBFS(graph);
        List<List<String>> dfsGroups = findGroupsDFS(graph);

        Map<String, Object> result = new HashMap<>();
        result.put("graph", graph);
        result.put("bfsGroups", bfsGroups);
        result.put("dfsGroups", dfsGroups);

        return result;
    }

    // ─────────────────────────────────────────────
    // 🔷 STEP 1: BUILD GRAPH
    private Map<String, List<String>> buildGraph(List<SubmissionNode> list) {

        Map<String, List<String>> graph = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {

                SubmissionNode a = list.get(i);
                SubmissionNode b = list.get(j);

                if (!a.getAssignment().equals(b.getAssignment())) continue;

                int similarity = similarity(a.getContent(), b.getContent());

                if (similarity >= 60) {
                    graph.computeIfAbsent(a.getStudent(), k -> new ArrayList<>()).add(b.getStudent());
                    graph.computeIfAbsent(b.getStudent(), k -> new ArrayList<>()).add(a.getStudent());
                }
            }
        }

        return graph;
    }

    // ─────────────────────────────────────────────
    // 🔷 STEP 2: BFS (Connected Components)
    private List<List<String>> findGroupsBFS(Map<String, List<String>> graph) {

        List<List<String>> groups = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {

            if (!visited.contains(node)) {

                List<String> group = new ArrayList<>();
                Queue<String> queue = new LinkedList<>();

                queue.add(node);

                while (!queue.isEmpty()) {
                    String curr = queue.poll();

                    if (visited.contains(curr)) continue;

                    visited.add(curr);
                    group.add(curr);

                    for (String neigh : graph.getOrDefault(curr, new ArrayList<>())) {
                        if (!visited.contains(neigh)) {
                            queue.add(neigh);
                        }
                    }
                }

                if (group.size() > 1) groups.add(group);
            }
        }

        return groups;
    }

    // ─────────────────────────────────────────────
    // 🔷 STEP 3: DFS (Recursive)
    private List<List<String>> findGroupsDFS(Map<String, List<String>> graph) {

        List<List<String>> groups = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {

            if (!visited.contains(node)) {

                List<String> group = new ArrayList<>();
                dfs(node, graph, visited, group);

                if (group.size() > 1) groups.add(group);
            }
        }

        return groups;
    }

    private void dfs(String node,
                     Map<String, List<String>> graph,
                     Set<String> visited,
                     List<String> group) {

        visited.add(node);
        group.add(node);

        for (String neigh : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neigh)) {
                dfs(neigh, graph, visited, group);
            }
        }
    }

    // ─────────────────────────────────────────────
    // 🔷 STEP 4: SIMILARITY FUNCTION
    private int similarity(String a, String b) {

        if (a.equalsIgnoreCase(b)) return 95;

        int match = 0;
        int min = Math.min(a.length(), b.length());

        for (int i = 0; i < min; i++) {
            if (Character.toLowerCase(a.charAt(i)) ==
                Character.toLowerCase(b.charAt(i))) {
                match++;
            }
        }

        return (int)((double) match / Math.max(a.length(), b.length()) * 100);
    }
}
