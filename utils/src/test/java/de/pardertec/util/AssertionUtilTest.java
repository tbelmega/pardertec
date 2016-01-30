package de.pardertec.util;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class AssertionUtilTest {

    @Test(expectedExceptions = {AssertionError.class})
    public void testThatCollectionDoesNotEqualCollection() {
        //arrange
        Collection<Integer> coll1 = new LinkedList<>();
        coll1.add(5);
        Collection<Integer> coll2 = new HashSet<>();
        coll2.add(6);

        //act
        AssertionUtil.assertCollectionEquals(coll2, coll1);

        //assert
    }

    @Test
    public void testThatCollectionEqualsCollection() {
        //arrange
        Collection<Integer> coll1 = new LinkedList<>();
        coll1.add(5);
        Collection<Integer> coll2 = new HashSet<>();
        coll2.add(5);

        //act
        AssertionUtil.assertCollectionEquals(coll2, coll1);

        //assert
    }

    @Test
    public void testThatListEqualsSet() {
        //arrange
        LinkedList<Integer> coll1 = new LinkedList<>();
        coll1.add(5);
        HashSet<Integer> coll2 = new HashSet<>();
        coll2.add(5);

        //act
        AssertionUtil.assertCollectionEquals(coll2, coll1);

        //assert
    }

}
