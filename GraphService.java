package com.unishare.dsa;

import com.unishare.model.Submission;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphService {

    // ── GRAPH + BFS — Find plagiarism groups ──
    //
    // How the graph works:
    // Each student = a NODE
    // If two students submitted same/similar file = EDGE between them
    //
    // BFS (Breadth First Search):
    // Start from one flagged student
    // Visit all connected students level by level
    // This finds the entire cheating group
    public Map<String, Object> detectPlagiarismGroups(List<Submission> allSubmissions) {

        // Step 1 — Build adjacency list (the graph)
        Map<String, List<String>> graph      = new HashMap<>();
        List<Map<String, Object>> flaggedList = new ArrayList<>();

        for (int i = 0; i < allSubmissions.size(); i++) {
            for (int j = i + 1; j < allSubmissions.size(); j++) {
                Submission a = allSubmissions.get(i);
                Submission b = allSubmissions.get(j);

                // Only compare same assignment
                if (!a.getAssignmentTitle().equals(b.getAssignmentTitle())) continue;

                // Calculate similarity
                int similarity = calculateSimilarity(a.getFileName(), b.getFileName());

                if (similarity >= 60) {
                    // Add edge: a ↔ b
                    graph.computeIfAbsent(a.getStudentName(), k -> new ArrayList<>())
                         .add(b.getStudentName());
                    graph.computeIfAbsent(b.getStudentName(), k -> new ArrayList<>())
                         .add(a.getStudentName());

                    // Record the flag
                    Map<String, Object> flag = new HashMap<>();
                    flag.put("student1",    a.getStudentName());
                    flag.put("student2",    b.getStudentName());
                    flag.put("assignment",  a.getAssignmentTitle());
                    flag.put("similarity",  similarity);
                    flaggedList.add(flag);
                }
            }
        }

        // Step 2 — BFS to find all connected groups
        List<List<String>> groups  = new ArrayList<>();
        Set<String>        visited = new HashSet<>();

        for (String student : graph.keySet()) {
            if (!visited.contains(student)) {
                List<String>   group = new ArrayList<>();
                Queue<String>  queue = new LinkedList<>();

                queue.add(student);

                // BFS loop
                while (!queue.isEmpty()) {
                    String current = queue.poll();
                    if (visited.contains(current)) continue;

                    visited.add(current);
                    group.add(current);

                    // Add all neighbours to queue
                    for (String neighbour :
                            graph.getOrDefault(current, new ArrayList<>())) {
                        if (!visited.contains(neighbour)) {
                            queue.add(neighbour);
                        }
                    }
                }

                if (group.size() > 1) groups.add(group);
            }
        }

        // Step 3 — DFS to find indirect connections
        List<List<String>> dfsGroups = new ArrayList<>();
        Set<String> dfsVisited = new HashSet<>();

        for (String student : graph.keySet()) {
            if (!dfsVisited.contains(student)) {
                List<String> dfsGroup = new ArrayList<>();
                dfsHelper(student, graph, dfsVisited, dfsGroup);
                if (dfsGroup.size() > 1) dfsGroups.add(dfsGroup);
            }
        }

        // Build final result
        Map<String, Object> result = new HashMap<>();
        result.put("flagged",   flaggedList);
        result.put("groups",    groups);
        result.put("dfsGroups", dfsGroups);
        result.put("graphEdges", buildEdgeList(graph));
        return result;
    }

    // ── DFS Helper — recursive ──
    private void dfsHelper(String student,
                            Map<String, List<String>> graph,
                            Set<String> visited,
                            List<String> group) {
        visited.add(student);
        group.add(student);

        for (String neighbour : graph.getOrDefault(student, new ArrayList<>())) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, graph, visited, group);
            }
        }
    }

    // ── Build edge list for frontend visualization ──
    private List<Map<String, String>> buildEdgeList(Map<String, List<String>> graph) {
        List<Map<String, String>> edges   = new ArrayList<>();
        Set<String>               seen    = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            for (String neighbour : entry.getValue()) {
                String edgeKey = entry.getKey() + "_" + neighbour;
                String reverseKey = neighbour + "_" + entry.getKey();

                if (!seen.contains(edgeKey) && !seen.contains(reverseKey)) {
                    Map<String, String> edge = new HashMap<>();
                    edge.put("from", entry.getKey());
                    edge.put("to",   neighbour);
                    edges.add(edge);
                    seen.add(edgeKey);
                }
            }
        }
        return edges;
    }

    // ── Similarity calculation ──
    // Compares two file names — replace with real text comparison later
    private int calculateSimilarity(String file1, String file2) {
        if (file1 == null || file2 == null) return 0;
        if (file1.equalsIgnoreCase(file2))  return 95;  // exact same file name

        // Count matching characters
        int matches = 0;
        int minLen  = Math.min(file1.length(), file2.length());

        for (int i = 0; i < minLen; i++) {
            if (Character.toLowerCase(file1.charAt(i)) ==
                Character.toLowerCase(file2.charAt(i))) {
                matches++;
            }
        }

        return (int)((double) matches / Math.max(file1.length(), file2.length()) * 100);
    }
}