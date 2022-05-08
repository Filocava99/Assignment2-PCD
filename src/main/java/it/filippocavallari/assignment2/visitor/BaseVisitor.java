package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import it.filippocavallari.assignment2.api.InterfaceReport;
import it.filippocavallari.assignment2.api.implementation.FieldInfoImpl;
import it.filippocavallari.assignment2.api.implementation.MethodInfoImpl;
import it.filippocavallari.assignment2.api.ClassReport;
import it.filippocavallari.assignment2.api.FieldInfo;
import it.filippocavallari.assignment2.api.MethodInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public interface BaseVisitor {
    default MethodInfo parseMethod(MethodDeclaration methodDeclaration, InterfaceReport parentClass) {
        String name = methodDeclaration.getNameAsString();
        AtomicInteger beginLine = new AtomicInteger();
        AtomicInteger endLine = new AtomicInteger();
        methodDeclaration.getRange().ifPresent(range -> {
            beginLine.set(range.begin.line);
            endLine.set(range.end.line);
        });
        List<String> modifiers = methodDeclaration.getModifiers().stream().map(modifier -> modifier.getKeyword().asString()).collect(Collectors.toList());
        return new MethodInfoImpl(name, beginLine.get(), endLine.get(), modifiers, parentClass);
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
