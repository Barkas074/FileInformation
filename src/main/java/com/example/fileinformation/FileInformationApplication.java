package com.example.fileinformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.fileinformation.modules.Module;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Scanner;

@SpringBootApplication
public class FileInformationApplication {
    
    private final Collection<Module> modules;
    
    @Autowired
    public FileInformationApplication(Collection<Module> modules) {
        this.modules = modules;
    }
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FileInformationApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите путь к файлу: ");
            String path = scanner.nextLine().replace("\"", "");
            File file = new File(path);
            for (Module module : modules) {
                if (module.isSuitableExtension(file) && file.exists()) {
                    applyModule(module, file);
                }
            }
        };
    }
    
    public void applyModule(Module module, File file) {
        System.out.println("Список команд: ");
        System.out.println(module.getDescription());
        System.out.println("Введите команду: ");
        Scanner scanner = new Scanner(System.in);
        module.executeCommand(scanner.nextLine(), file);
    }
    
}
