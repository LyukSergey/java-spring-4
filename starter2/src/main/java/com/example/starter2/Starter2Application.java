
package com.example.starter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Starter2Application {

	public static void main(String[] args) {
		SpringApplication.run(Starter2Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		System.out.println("====================================================");
		System.out.println("Демо-додаток запущено! Доступні ендпоінти:");
		System.out.println("http://localhost:8080/do - виконати просту задачу");
		System.out.println("http://localhost:8080/do/process/hello/5 - обробити текст");
		System.out.println("====================================================");
    }

}