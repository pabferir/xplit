package dev.pabferir.xplit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.pabferir")
public class XplitApplication {

    public static void main(String[] args) {
        SpringApplication.run(XplitApplication.class, args);
    }

}
