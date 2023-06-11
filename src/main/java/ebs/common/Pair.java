package ebs.common;

import java.io.Serializable;

public class Pair<U, V> implements Serializable {
    public U first;
    public V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }
}