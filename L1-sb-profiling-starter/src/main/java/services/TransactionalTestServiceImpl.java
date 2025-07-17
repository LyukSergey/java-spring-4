package services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionalTestServiceImpl implements TransactionalTestService {

    @Override
    @Transactional // Spring бачить цю анотацію
    public void transactionalTestMethod() {
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Початок основного методу.");
        // Це і є "самовиклик" (self-invocation)
        this.helperMethod();
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Кінець основного методу.");
    }

    // Другий метод цього ж класу
    public void helperMethod() {
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Виклик допоміжного методу.");
    }
}
