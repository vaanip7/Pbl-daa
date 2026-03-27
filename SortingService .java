package com.unishare.dsa;

import com.unishare.model.Assignment;
import com.unishare.model.Submission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortingService {

    // ── MERGE SORT — Sort assignments by deadline ──
    // Time complexity: O(n log n)
    

    public List<Assignment> mergeSortByDeadline(List<Assignment> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;

        List<Assignment> left  = mergeSortByDeadline(new ArrayList<>(list.subList(0, mid)));
        List<Assignment> right = mergeSortByDeadline(new ArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private List<Assignment> merge(List<Assignment> left, List<Assignment> right) {
        List<Assignment> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            // Compare deadlines as strings 
            if (left.get(i).getDeadline()
                    .compareTo(right.get(j).getDeadline()) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

      
        while (i < left.size())  result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    // ── QUICK SORT — Sort submissions by similarity score ──
    // Time complexity: O(n log n) average
    

    public List<Submission> quickSortBySimilarity(List<Submission> list) {
        if (list.size() <= 1) return list;

        
        int pivotIndex = list.size() / 2;
        int pivotValue = list.get(pivotIndex).getSimilarityScore();

        List<Submission> left   = new ArrayList<>();
        List<Submission> middle = new ArrayList<>();
        List<Submission> right  = new ArrayList<>();

        for (Submission s : list) {
            if (s.getSimilarityScore() > pivotValue) {
                left.add(s);    // higher similarity first
            } else if (s.getSimilarityScore() < pivotValue) {
                right.add(s);
            } else {
                middle.add(s);
            }
        }

        List<Submission> sorted = new ArrayList<>();
        sorted.addAll(quickSortBySimilarity(left));
        sorted.addAll(middle);
        sorted.addAll(quickSortBySimilarity(right));
        return sorted;
    }

    // ── INSERTION SORT — Sort materials by title ──
    // Time complexity: O(n²) but good for small lists
   
    public List<Assignment> insertionSortByTitle(List<Assignment> list) {
        List<Assignment> sorted = new ArrayList<>(list);

        for (int i = 1; i < sorted.size(); i++) {
            Assignment key = sorted.get(i);
            int j = i - 1;

            while (j >= 0 && sorted.get(j).getTitle()
                                   .compareToIgnoreCase(key.getTitle()) > 0) {
                sorted.set(j + 1, sorted.get(j));
                j--;
            }
            sorted.set(j + 1, key);
        }

        return sorted;
    }
}