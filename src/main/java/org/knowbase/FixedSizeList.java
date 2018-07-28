package org.knowbase;

import java.util.ArrayList;

/**
 * Created on 7/21/18 by alexiy.
 * Doesn't allow duplicate elements. When a new element is added, first one is removed
 */
public class FixedSizeList<T> extends ArrayList<T> {

    private final int maxSize;

    public FixedSizeList(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T t) {
        if(size()==maxSize)
            remove(0);
        if(!contains(t))
            return super.add(t);
        return false;
    }
}
