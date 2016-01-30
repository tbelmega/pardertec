package de.pardertec.util;

import java.util.*;

/**
 * Created by Thiemo on 20.12.2015.
 */
public class SubsetUtil {
    public static <T> Set<Set<T>> getAllSubsetsOfSize(Set<T> superSet, int size) {
        List<T> superList = new ArrayList<>(superSet);
        Set<Set<T>> setOfSubSets = new HashSet<>();

        for (int i = 0; i < superList.size(); i++) {
            T currentElement = superList.get(i);

            if (size == 1) {
                List<T> newSubList = Arrays.asList(currentElement);
                setOfSubSets.add(new HashSet<>(newSubList));
            } else {
                setOfSubSets.addAll(createAllSubSetsWithCurrentElementRecursively(size, superList, currentElement));
            }
        }

        return setOfSubSets;
    }

    /**
     * Recursive method.
     * <p>
     * Example:
     * If current element is "1" and superList is "1","2","3","4" and we are looking for sub sets of size 2;
     * <p>
     * - The method gets all sub sets of size 1 of the list "2","3","4", which are the sub sets {"2"}, {"3"} and {"4"}
     * - Then the method adds the current element to each of these sub sets, which results
     * in the sets {"1","2"},{"1","3"} and {"1","4"}
     * - The method returns a set of all these created sub sets
     */
    private static <T> Set<Set<T>> createAllSubSetsWithCurrentElementRecursively(int size, List<T> superList, T currentElement) {
        Set<Set<T>> setOfSubSetsWithoutCurrentElement = getAllSmallerSubsetsWithoutCurrentElement(size, superList, currentElement);
        Set<Set<T>> resultSetOfSubSets = new HashSet<>();

        for (Set<T> subSetWithoutCurrentElement : setOfSubSetsWithoutCurrentElement) {
            subSetWithoutCurrentElement.add(currentElement);
            resultSetOfSubSets.add(subSetWithoutCurrentElement);
        }

        return resultSetOfSubSets;
    }

    private static <T> Set<Set<T>> getAllSmallerSubsetsWithoutCurrentElement(int size, List<T> superList, T currentElement) {
        Set<T> setWithoutCurrentElement = new HashSet<>(superList);
        setWithoutCurrentElement.remove(currentElement);
        return getAllSubsetsOfSize(setWithoutCurrentElement, size - 1);
    }


}
