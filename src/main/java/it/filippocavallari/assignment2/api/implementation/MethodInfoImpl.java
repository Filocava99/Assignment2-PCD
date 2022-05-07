package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.MethodInfo;

public class MethodInfoImpl implements MethodInfo {

    private final String name;
    private final int srcBeginLine;
    private final int srcEndLine;
    private final ClassReport parent;

    public MethodInfoImpl(String name, int srcBeginLine, int srcEndLine, ClassReport parent) {
        this.name = name;
        this.srcBeginLine = srcBeginLine;
        this.srcEndLine = srcEndLine;
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSrcBeginLine() {
        return srcBeginLine;
    }

    @Override
    public int getSrcEndLine() {
        return srcEndLine;
    }

    @Override
    public ClassReport getParent() {
        return parent;
    }
}
