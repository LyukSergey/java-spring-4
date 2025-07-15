package com.lss.l1sbprofilingstartertest.service.impl;

import com.lss.l1sbprofilingstartertest.service.TransactionalTestService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionalTestServiceImpl implements TransactionalTestService {

    @Override
    @Transactional
    public void transactionalTestMethod() {
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Початок основного методу.");
        this.helperMethod(); 
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Кінець основного методу.");
    }

    public void helperMethod() {
        System.out.println(">>> [РЕАЛЬНИЙ ОБ'ЄКТ] Виклик допоміжного методу.");
    }
}
