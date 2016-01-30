package de.pardertec.util;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


@SuppressWarnings("ALL")
public class SubsetUtilTest {

    List<String> superList = Arrays.asList("1", "2", "4", "3");
    Set<String> superSet = new HashSet<>(superList);


    @Test
    public void testThatSubsetUtilReturnsAllSubSetsOfSize4() {
        //arrange

        //act
        Set<Set<String>> subSets = SubsetUtil.getAllSubsetsOfSize(superSet, 4);

        //assert
        assertTrue(subSets.contains(superSet));
    }

    @Test
    public void testThatSubsetUtilReturnsAllSubSetsOfSize3() {
        //arrange
        List<String> subList1 = Arrays.asList("1", "2", "4");
        List<String> subList2 = Arrays.asList("1", "2", "3");
        List<String> subList3 = Arrays.asList("1", "4", "3");
        List<String> subList4 = Arrays.asList("3", "2", "4");

        //act
        Set<Set<String>> subSets = SubsetUtil.getAllSubsetsOfSize(superSet, 3);

        //assert
        assertTrue(subSets.contains(new HashSet(subList1)));
        assertTrue(subSets.contains(new HashSet(subList2)));
        assertTrue(subSets.contains(new HashSet(subList3)));
        assertTrue(subSets.contains(new HashSet(subList4)));
    }

    @Test
    public void testThatSubsetUtilReturnsAllSubSetsOfSize2() {
        //arrange

        //act
        Set<Set<String>> subSets = SubsetUtil.getAllSubsetsOfSize(superSet, 2);

        //assert
        assertEquals(6, subSets.size());

    }

    @Test
    public void testThatSubsetUtilReturnsAllSubSetsOfSize1() {
        //arrange
        List<String> subList1 = Arrays.asList("1");
        List<String> subList2 = Arrays.asList("2");
        List<String> subList3 = Arrays.asList("3");
        List<String> subList4 = Arrays.asList("4");

        //act
        Set<Set<String>> subSets = SubsetUtil.getAllSubsetsOfSize(superSet, 1);

        //assert
        assertTrue(subSets.contains(new HashSet(subList1)));
        assertTrue(subSets.contains(new HashSet(subList2)));
        assertTrue(subSets.contains(new HashSet(subList3)));
        assertTrue(subSets.contains(new HashSet(subList4)));
    }


}
