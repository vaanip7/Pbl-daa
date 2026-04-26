

import java.util.*;

@Component
public class HashMapService {

    // ── HASHMAP 1 — Fast submission lookup ──
    // Instead of looping through all submissions (O(n))
    // HashMap gives O(1) instant lookup
    //
    // Key   = studentName_assignmentTitle
    // Value = Submission object

    public Map<String, Submission> buildSubmissionMap(List<Submission> submissions) {
        Map<String, Submission> map = new HashMap<>();

        for (Submission s : submissions) {
            String key = s.getStudentName() + "_" + s.getAssignmentTitle();
            map.put(key, s);
        }

        return map;
    }

    // Check if student already submitted
    public boolean alreadySubmitted(Map<String, Submission> map,
                                     String studentName,
                                     String assignmentTitle) {
        String key = studentName + "_" + assignmentTitle;
        return map.containsKey(key);
    }

    // ── HASHMAP 2 — Group submissions by assignment ──
    // Key   = assignment title
    // Value = list of all submissions for that assignment

    public Map<String, List<Submission>> groupByAssignment(List<Submission> submissions) {
        Map<String, List<Submission>> map = new HashMap<>();

        for (Submission s : submissions) {
            map.computeIfAbsent(s.getAssignmentTitle(), k -> new ArrayList<>())
               .add(s);
        }

        return map;
    }

    // ── HASHMAP 3 — Count submissions per student ──
    // Key   = student name
    // Value = number of assignments submitted

    public Map<String, Integer> countSubmissionsPerStudent(List<Submission> submissions) {
        Map<String, Integer> map = new HashMap<>();

        for (Submission s : submissions) {
            map.put(s.getStudentName(),
                    map.getOrDefault(s.getStudentName(), 0) + 1);
        }

        return map;
    }

    // ── HASHMAP 4 — Subject wise assignment count ──
    public Map<String, Integer> countAssignmentsBySubject(List<Assignment> assignments) {
        Map<String, Integer> map = new HashMap<>();

        for (Assignment a : assignments) {
            map.put(a.getSubject(),
                    map.getOrDefault(a.getSubject(), 0) + 1);
        }

        return map;
    }

    // ── PRIORITY QUEUE — Urgent deadlines ──
    // Always returns nearest deadline first
    // Time complexity: O(n log n)

    public List<Assignment> getUrgentDeadlines(List<Assignment> assignments) {
        PriorityQueue<Assignment> pq = new PriorityQueue<>(
            Comparator.comparing(Assignment::getDeadline)
        );

        pq.addAll(assignments);

        List<Assignment> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }

        return result;
    }
}