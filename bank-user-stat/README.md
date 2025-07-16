# Модуль bank-user-stat

## Структура

- **entity**: Bank, User
- **repository**: BankRepository, UserRepository
- **service**: BankUserStatService, BankUserStatServiceImpl
- **controller**: BankUserStatController
- **dto**: UserDto, BankUserStatDto
- **mapper**: UserMapper

## Завдання

1. Реалізувати ендпоінт для отримання статистики користувачів по банках (кількість користувачів у кожному банку, сортування за спаданням).
2. Реалізувати метод для пошуку користувача з найдовшим прізвищем у банку (ефективно, через SQL).
