package it.filippocavallari.assignment2.api.implementation;

import it.filippocavallari.assignment2.api.*;

import java.util.LinkedList;
import java.util.List;

public class MethodInfoImpl implements MethodInfo, ProjectElem {

    private final String name;
    private final int srcBeginLine;
    private final int srcEndLine;
    private final InterfaceReport parent;
    private final List<String> modifiers;

    public MethodInfoImpl(String name, int srcBeginLine, int srcEndLine, List<String> modifiers, InterfaceReport parent) {
        this.name = name;
        this.srcBeginLine = srcBeginLine;
        this.srcEndLine = srcEndLine;
        this.modifiers = modifiers;
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
    public InterfaceReport getParent() {
        return parent;
    }

    public List<String> getModifiers() {
        return modifiers;
    }
}
