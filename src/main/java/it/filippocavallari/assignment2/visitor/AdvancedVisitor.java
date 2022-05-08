package it.filippocavallari.assignment2.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class AdvancedVisitor extends VoidVisitorAdapter<JsonObject> {

    public void visit(Node node, JsonObject arg){
        if(node instanceof PackageDeclaration){
            visit((PackageDeclaration) node, arg);
        }else if(node instanceof ClassOrInterfaceDeclaration){
            visit((ClassOrInterfaceDeclaration) node, arg);
        }
    }

    @Override
    public void visit(FieldDeclaration n, JsonObject arg) {
        if(!arg.containsKey("fields")){
            arg.put("fields", new JsonArray());
        }
        JsonArray modifiers = new JsonArray();
        n.getModifiers().forEach(modifier -> modifiers.add(new JsonObject().put("name", modifier.getKeyword().name())));
        n.getVariables().forEach(variable -> {
            arg.getJsonArray("fields").add(new JsonObject()
                    .put("name", variable.getNameAsString())
                    .put("type", variable.getType().asString())
                    .put("modifiers", modifiers));
        });
    }

    @Override
    public void visit(MethodDeclaration n, JsonObject arg) {
        if (!arg.containsKey("methods")) {
            arg.put("methods", new JsonArray());
        }
        JsonArray modifiers = new JsonArray();
        n.getModifiers().forEach(modifier -> modifiers.add(new JsonObject().put("name", modifier.getKeyword().name())));
        JsonArray parameters = new JsonArray();
        n.getParameters().forEach(parameter -> parameters.add(new JsonObject().put("name", parameter.getNameAsString()).put("type", parameter.getType().asString())));
        arg.getJsonArray("methods").add(new JsonObject()
                .put("name", n.getNameAsString())
                .put("modifiers", modifiers)
                .put("parameters", parameters));
    }

    @Override
    public void visit(PackageDeclaration n, JsonObject arg) {
        arg.put("type", "package");
        arg.put("name", n.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, JsonObject arg) {
        if (n.isInterface()) {
            arg.put("type", "interface");
            arg.put("name", n.getNameAsString());
        }
        super.visit(n, arg);
    }

}
