package com.mariaribeiro.webhookrelay;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebhookRelayApplication {

    public static void main(String[] args) {
        // Carrega variáveis do .env para o ambiente
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(WebhookRelayApplication.class, args);
    }

}
