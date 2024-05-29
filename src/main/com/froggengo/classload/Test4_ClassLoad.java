package com.froggengo.classload;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author fly
 * @create 2024-05-29-15:02
 **/
public class Test4_ClassLoad {
    /**
     * 模块化系统（JPMS）：在Java 9及以上版本中，Java使用模块化系统，类可能位于模块中，而不是单个JAR文件中。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Map<String, String> env = new HashMap<>();
        try (FileSystem fileSystem = FileSystems.newFileSystem(URI.create("jrt:/"), env);) {
            Path path = fileSystem.getPath("/modules");
            try (Stream<Path> modulePaths = Files.list(path)) {
                modulePaths.forEach(System.out::println);
            }
        }

    }

    @Test
    public void test33() {
        Path path = FileSystems.getFileSystem(URI.create("jrt:/")).getPath("modules/java.sql");
        try (Stream<Path> entries = Files.list(path)) {
            entries.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test48() {
        ModuleFinder finder = ModuleFinder.ofSystem();
        Set<ModuleReference> modules = finder.findAll();
        int count = 0;
        for (ModuleReference moduleRef : modules) {
            System.out.println("Module: " + moduleRef.descriptor().name());
            count++;
        }
        // jdk11 有72个
        System.out.println("count:" + count);
    }
}
