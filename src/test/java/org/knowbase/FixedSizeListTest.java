package org.knowbase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 7/21/18 by alexiy.
 */
public class FixedSizeListTest {

    @Test
    public void add() {
        FixedSizeList<String> fixedSizeList=new FixedSizeList<>(6);
        fixedSizeList.add("1");
        fixedSizeList.add("2");
        fixedSizeList.add("3");
        fixedSizeList.add("4");
        fixedSizeList.add("5");
        fixedSizeList.add("6");
        fixedSizeList.add("7");
        assertEquals(fixedSizeList.get(5),"7");
    }
}