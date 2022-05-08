package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import it.filippocavallari.assignment2.api.ProjectElem;

import java.util.function.Consumer;

public class AdvancedVisitor extends VoidVisitorAdapter<ProjectElem> {

    Consumer<ProjectElem> consumer;

    public AdvancedVisitor(Consumer<ProjectElem> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, ProjectElem arg) {

    }
}
