package services;

// Цей клас генерується Spring "в пам'яті" і наслідує ваш
public class CglibProxyForMyService extends TransactionalTestServiceImpl {

    @Override // Перевизначення основного методу
    public void transactionalTestMethod() {
        // --- ЛОГІКА ПРОКСІ (ПОЧАТОК) ---
        System.out.println("✅ [CGLIB-ПРОКСІ] Перехоплено! Починаю транзакцію...");

        // --- ВИКЛИК ОРИГІНАЛЬНОГО МЕТОДУ ---
        // Викликаємо реалізацію з батьківського класу (вашого класу)
        super.transactionalTestMethod();

        // --- ЛОГІКА ПРОКСІ (КІНЕЦЬ) ---
        System.out.println("✅ [CGLIB-ПРОКСІ] Завершую транзакцію (commit).");
    }

    @Override // CGLIB може перевизначити і публічні методи, навіть якщо їх немає в інтерфейсі
    public void helperMethod() {
        // Якби на цьому методі була анотація, її логіка була б тут
        System.out.println("✅ [CGLIB-ПРОКСІ] Перехопив виклик helperMethod!");
        super.helperMethod();
    }
}