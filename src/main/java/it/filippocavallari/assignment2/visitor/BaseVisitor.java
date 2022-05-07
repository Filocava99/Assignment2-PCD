package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import it.filippocavallari.assignment2.api.implementation.FieldInfoImpl;
import it.filippocavallari.assignment2.api.implementation.MethodInfoImpl;
import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.FieldInfo;
import it.filippocavallari.assignment2.api.MethodInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface BaseVisitor {
    default MethodInfo parseMethod(MethodDeclaration methodDeclaration, ClassReport parentClass) {
        String name = methodDeclaration.getNameAsString();
        AtomicInteger beginLine = new AtomicInteger();
        AtomicInteger endLine = new AtomicInteger();
        methodDeclaration.getRange().ifPresent(range -> {
            beginLine.set(range.begin.line);
            endLine.set(range.end.line);
        });
        return new MethodInfoImpl(name, beginLine.get(), endLine.get(), parentClass);
    }

    default List<FieldInfo> parseField(FieldDeclaration fieldDeclaration, ClassReport parentClass) {
        List<FieldInfo> list = new LinkedList<>();
        String type = fieldDeclaration.getElementType().asString();
        fieldDeclaration.getVariables().forEach(it -> {
            list.add(new FieldInfoImpl(it.getName().asString(), type, parentClass));
        });
        return list;
    }
}
