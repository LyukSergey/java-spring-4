package services;

import services.TransactionalTestService;
import services.TransactionalTestServiceImpl;

public class JdkProxyForMyService implements TransactionalTestService {

    // Проксі тримає посилання на СПРАВЖНІЙ об'єкт вашого сервісу
    private final TransactionalTestServiceImpl realTarget;

    public JdkProxyForMyService(TransactionalTestServiceImpl realTarget) {
        this.realTarget = realTarget;
    }

    @Override
    public void transactionalTestMethod() {
        // --- ЛОГІКА ПРОКСІ (ПОЧАТОК) ---
        System.out.println("✅ [JDK-ПРОКСІ] Перехоплено! Починаю транзакцію...");

        // --- ВИКЛИК ОРИГІНАЛЬНОГО МЕТОДУ ---
        // Проксі викликає метод на справжньому, цільовому об'єкті
        realTarget.transactionalTestMethod();

        // --- ЛОГІКА ПРОКСІ (КІНЕЦЬ) ---
        System.out.println("✅ [JDK-ПРОКСІ] Завершую транзакцію (commit).");
    }

    // УВАГА: Проксі нічого не знає про helperMethod(), бо його немає в інтерфейсі!
}