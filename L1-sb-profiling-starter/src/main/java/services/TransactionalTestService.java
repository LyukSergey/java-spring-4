package services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

public interface TransactionalTestService {
    void transactionalTestMethod();
}