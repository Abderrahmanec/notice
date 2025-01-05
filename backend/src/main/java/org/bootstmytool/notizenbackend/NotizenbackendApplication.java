package org.bootstmytool.notizenbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "org.bootstmytool.notizenbackend")
public class NotizenbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotizenbackendApplication.class, args);
    }

}
