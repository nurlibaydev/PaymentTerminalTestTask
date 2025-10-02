# Payment Terminal Test Task

Android-приложение для обработки транзакций с отображением истории платежей.  
Проект выполнен как тестовое задание.

## 📱 Основные возможности
- Ввод суммы и проведение транзакции  
- Отображение результата (успех/ошибка)  
- Сохранение транзакций в локальную базу данных (Room)  
- Просмотр истории транзакций  
- Очистка истории с подтверждением  
- UI с Material Components  

## 🛠️ Технологии
- **Язык**: Java + Kotlin (utils/extensions)  
- **Архитектура**: MVVM (ViewModel + Repository + Room)  
- **База данных**: Room (DAO + Entities)  
- **DI**: Singleton pattern (Repository, Database)  
- **UI**: AndroidX, Material Design, RecyclerView  
