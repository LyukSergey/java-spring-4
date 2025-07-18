* **Сутності**:
    * `Department` (`id`, `name`)
    * `Employee` (`id`, `firstName`, `lastName`, `email`, `position`, `salary`)
* **Зв'язки**: `@OneToMany` у `Department`, `@ManyToOne` у `Employee`.
* **DTO**:
    * `DepartmentDto(Long id, String name)`
    * `EmployeeDto(Long id, String fullName, String position, BigDecimal salary, String departmentName)`
* **Початковий стан**: Існують пусті 
* `Controller`, `Service`, `Repository` класи. База даних наповнена тестовими даними.

---
// GET http://localhost:8080/employees/search/by-position?position=Java Developer
#### **Завдання по `Employee` (Робота з однією сутністю)**

1.  **Завдання 1**: Створити ендпоінт `GET /employees`, який повертає список `List<EmployeeDto>` **всіх** співробітників.
2.  **Завдання 2**: Створити ендпоінт `GET /employees/{id}`, який знаходить і повертає `EmployeeDto` за його унікальним `id`.
3.  **Завдання 3**: Створити ендпоінт `GET /employees/search/by-email`, який приймає рядок `email` як параметр запиту і повертає `Optional<EmployeeDto>` співробітника з таким email.
4.  **Завдання 4**: Створити ендпоінт `GET /employees/search/by-lastname`, який приймає рядок `lastName` і повертає `List<EmployeeDto>` всіх співробітників із таким прізвищем.
5.  **Завдання 5**: Створити ендпоінт `GET /employees/search/by-position`, який приймає рядок `position` і повертає `List<EmployeeDto>` всіх співробітників на цій посаді.
6.  **Завдання 6**: Створити ендпоінт `GET /employees/salary-greater-than`, який приймає `BigDecimal` `minSalary` і повертає `List<EmployeeDto>` співробітників, чия зарплата **вища** за вказану.
7.  **Завдання 7**: Створити ендпоінт `GET /employees/salary-less-than`, який приймає `BigDecimal` `maxSalary` і повертає `List<EmployeeDto>` співробітників, чия зарплата **нижча** за вказану.
8.  **Завдання 8**: Створити ендпоінт `GET /employees/search/lastname-starts-with`, який за параметром `prefix` повертає `List<EmployeeDto>` співробітників, прізвище яких починається на цей префікс.
9.  **Завдання 9**: Створити ендпоінт `GET /employees/search/by-position-and-salary-greater`, який приймає `position` та `minSalary` і повертає список співробітників на цій посаді із зарплатою, вищою за `minSalary`.
10. **Завдання 10**: Створити ендпоінт `POST /employees`, який приймає `EmployeeCreateDto` (з полями `firstName`, `lastName`, `email`, `position`, `salary`, `departmentId`) і створює нового співробітника.
11. **Завдання 11**: Створити ендпоінт `DELETE /employees/{id}`, який видаляє співробітника за його `id`.
12. **Завдання 12**: Створити ендпоінт `GET /employees/sorted/by-lastname-asc`, який повертає всіх співробітників, відсортованих за прізвищем в алфавітному порядку.
13. **Завдання 13**: Створити ендпоінт `GET /employees/search/top-3-by-salary`, який повертає `List<EmployeeDto>` з трьох найбільш високооплачуваних співробітників.
14. **Завдання 14**: Створити ендпоінт `GET /employees/search/email-containing`, який за параметром `text` знаходить `List<EmployeeDto>` співробітників, email яких містить цей текст.
15. **Завдання 15**: Створити ендпоінт `PUT /employees/{id}/position`, який приймає в тілі запиту нову посаду (`String position`) і оновлює її для співробітника з вказаним `id`.

#### **Завдання по `Department` та зв'язках (Робота з двома сутностями)**

16. **Завдання 16**: Створити ендпоінт `GET /departments/{id}/employees`, який повертає `List<EmployeeDto>` всіх співробітників, що працюють у відділі із заданим `id`.
17. **Завдання 17**: Створити ендпоінт `GET /departments`, який повертає `List<DepartmentDto>` всіх відділів.
18. **Завдання 18**: Створити ендпоінт `POST /departments`, який приймає `DepartmentCreateDto` (`name`) і створює новий відділ.
19. **Завдання 19**: Створити ендпоінт `GET /departments/{id}/employees/count`, який повертає `long` — кількість співробітників у конкретному відділі.
20. **Завдання 20**: Створити ендпоінт `GET /departments/search/by-name`, який знаходить відділ за точною назвою і повертає `Optional<DepartmentDto>`.
21. **Завдання 21**: Створити ендпоінт `GET /departments/sorted/by-name-desc`, який повертає всі відділи, відсортовані за назвою у зворотному алфавітному порядку.
22. **Завдання 22**: Створити ендпоінт `GET /departments/{id}/employees/by-position`, який приймає параметр `position` і повертає `List<EmployeeDto>` співробітників на даній посаді **тільки** з конкретного відділу.
23. **Завдання 23**: Створити ендпоінт `GET /departments/by-employee-lastname`, який за параметром `lastName` знаходить і повертає `DepartmentDto` відділу, в якому працює співробітник з таким прізвищем.
24. **Завдання 24**: Створити ендпоінт `GET /departments/without-employees`, який знаходить і повертає `List<DepartmentDto>` всіх відділів, у яких немає жодного співробітника.
25. **Завдання 25**: Створити ендпоінт `PUT /employees/{employeeId}/department/{departmentId}`, який переводить існуючого співробітника до нового відділу.