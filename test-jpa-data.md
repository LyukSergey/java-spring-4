@Query(value = "SELECT * FROM users WHERE bank_id = :bankId ORDER BY LENGTH(surname) DESC LIMIT 1", nativeQuery = true)
Optional<User> findTopUserWithLongestSurnameByBankId(@Param("bankId") Long bankId);

Тут ми використовуємо Native SQL, 
оскільки функція LENGTH() може відрізнятися в різних діалектах SQL. 
Ми сортуємо користувачів за довжиною прізвища у зворотному порядку (DESC) 
і беремо лише перший запис (LIMIT 1).