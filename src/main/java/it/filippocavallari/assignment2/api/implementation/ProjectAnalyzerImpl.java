package it.filippocavallari.assignment2.api.implementation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import it.filippocavallari.assignment2.api.*;
import it.filippocavallari.assignment2.visitor.ClassVisitor;
import it.filippocavallari.assignment2.visitor.PackageVisitor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ProjectAnalyzerImpl implements ProjectAnalyzer {
    private final Vertx vertx = Vertx.vertx();

    @Override
    public Future<ClassReport> getClassReport(String srcClassPath) {
        return parseFile(srcClassPath, new ClassVisitor(), new ClassReportImpl()).map(it -> (ClassReport) it);
    }

    @Override
    public Future<PackageReport> getPackageReport(String srcPackagePath) {
        return vertx.fileSystem().readDir(srcPackagePath).compose(mapper -> {
            List<Future> futures = new ArrayList<>();
            var report = new PackageReportImpl();
            mapper.forEach(file -> {
                if (!Files.isDirectory(Path.of(file))) {
                    futures.add(parseFile(file, new PackageVisitor(), report));
                }
            });
            return CompositeFuture.all(futures).map(report);
        });
    }

    @Override
    public Future<ProjectReport> getProjectReport(String srcProjectFolderPath) {
        return vertx.executeBlocking(future -> {
            List<Future<ClassReport>> futures = parseProject(srcProjectFolderPath);
            var report = new ProjectReportImpl();
            CompositeFuture.all(new ArrayList<>(futures)).onComplete(handler -> {
                System.out.println(handler.succeeded());
                handler.result().list().stream().map(result -> (ClassReport) result).forEach(classReport -> {
                    System.out.println(classReport.getFullClassName());
                    report.getAllClasses().add(classReport);
                    if (classReport.getMethodsInfo().stream().anyMatch(methodInfo -> methodInfo.getName().equalsIgnoreCase("main"))) {
                        report.setMainClass(classReport);
                    }
                });
                future.complete(report);
            });
        });
    }

    @Override
    public void analyzeProject(String srcProjectFolderName, Consumer<ProjectElem> callback) {

    }

    private <T> Future<T> parseFile(String filePath, VoidVisitorAdapter<T> visitor, T arg) {
        return vertx.fileSystem().readFile(filePath).compose(result -> {
            String content = new String(result.getBytes());
            return vertx.executeBlocking(future -> {
                System.out.println(filePath + ": ");
                visitor.visit(StaticJavaParser.parse(content), arg);
                System.out.println("completed");
                future.complete(arg);
            });
        });
    }

    private List<Future<ClassReport>> parseProject(String subDir) {
        List<Future<ClassReport>> classReports = new LinkedList<>();
        Arrays.stream(new File(subDir).listFiles()).forEach(file -> {
            if (file.isDirectory()) {
                classReports.addAll(parseProject(file.getPath()));
            } else {
                classReports.add(parseFile(file.getPath(), new ClassVisitor(), new ClassReportImpl()).map(it -> it));
            }
        });
        return classReports;
    }

    public void stopVertex() {
        vertx.close();
    }

}
