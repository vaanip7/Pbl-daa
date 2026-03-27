package com.unishare.dsa;

import com.unishare.model.Assignment;
import com.unishare.model.Material;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SearchingService {

    // ── BINARY SEARCH for Assignments ──
    // Time complexity: O(log n)
    

    public List<Assignment> binarySearchAssignment(List<Assignment> assignments,
                                                    String keyword) {
        
        List<Assignment> sorted = new ArrayList<>(assignments);
        sorted.sort(Comparator.comparing(a -> a.getTitle().toLowerCase()));

        List<Assignment> results = new ArrayList<>();

       
        int low = 0, high = sorted.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midTitle = sorted.get(mid).getTitle().toLowerCase();
            String key      = keyword.toLowerCase();

            if (midTitle.contains(key)) {
                
                results.add(sorted.get(mid));

              
                int left = mid - 1;
                while (left >= 0 &&
                       sorted.get(left).getTitle().toLowerCase().contains(key)) {
                    results.add(sorted.get(left--));
                }

                
                int right = mid + 1;
                while (right < sorted.size() &&
                       sorted.get(right).getTitle().toLowerCase().contains(key)) {
                    results.add(sorted.get(right++));
                }
                break;

            } else if (midTitle.compareTo(key) < 0) {
                low = mid + 1;   // go right
            } else {
                high = mid - 1;  // go left
            }
        }

        return results;
    }

    // ── BINARY SEARCH for Materials by title ──
    public List<Material> binarySearchMaterial(List<Material> materials,
                                                String keyword) {
        List<Material> sorted = new ArrayList<>(materials);
        sorted.sort(Comparator.comparing(m -> m.getTitle().toLowerCase()));

        List<Material> results = new ArrayList<>();
        int low = 0, high = sorted.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midTitle = sorted.get(mid).getTitle().toLowerCase();
            String key      = keyword.toLowerCase();

            if (midTitle.contains(key)) {
                results.add(sorted.get(mid));

                int left = mid - 1;
                while (left >= 0 &&
                       sorted.get(left).getTitle().toLowerCase().contains(key)) {
                    results.add(sorted.get(left--));
                }

                int right = mid + 1;
                while (right < sorted.size() &&
                       sorted.get(right).getTitle().toLowerCase().contains(key)) {
                    results.add(sorted.get(right++));
                }
                break;

            } else if (midTitle.compareTo(key) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return results;
    }
}