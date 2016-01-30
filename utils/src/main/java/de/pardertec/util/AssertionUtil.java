package de.pardertec.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

public class AssertionUtil {

    public static <T> void assertContainsAll(Collection<T> containingCollection, Collection<? extends T> containedCollection) {
        if (containingCollection == null && containedCollection == null) {
            return;
        }

        if (containingCollection == null ^ containedCollection == null) {
            throw new AssertionError("Expected: " + containingCollection + "\n but was: " + containedCollection);
        }

        if (!containingCollection.containsAll(containedCollection)) {
            String containing = ToStringBuilder.reflectionToString(containingCollection);
            String contained = ToStringBuilder.reflectionToString(containedCollection);
            throw new AssertionError("Expected that  " + containing + "\n would contain all elements of : " + contained);
        }
    }

    public static <T> void assertCollectionEquals(Collection<T> expectedCollection, Collection<T> actualCollection) {
        if (expectedCollection == null && actualCollection == null) {
            return;
        }

        if (expectedCollection == null ^ actualCollection == null) {
            throw new AssertionError("Expected: " + expectedCollection + "\n but was: " + actualCollection);
        }

        if (expectedCollection.size() != actualCollection.size()) {
            throw new AssertionError("Expected size " + expectedCollection.size() + "\n but was: " + actualCollection.size());
        }
        assertContainsAll(expectedCollection, actualCollection);
        assertContainsAll(actualCollection, expectedCollection);
    }
}
