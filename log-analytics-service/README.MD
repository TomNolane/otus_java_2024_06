## Универсальный микросервис для аналитики логов

Это сервис, который принимает логи приложений, обрабатывает их и предоставляет аналитику.

### *Технологии:*
- Netty для обработки входящих логов
- Redis для временного хранения данных
- Spring WebFlux для реактивного REST API
- Docker для развертывания.

### *Проект состоит из следующих модулей:*

- model — данные и сущности,
- mapper — преобразования моделей,
- repository — взаимодействие с БД,
- service — бизнес-логика,
- controller — REST API,
- websocket — обработка WebSocket,
- udp — реализация UDP-сервера с Netty.

### *Принцип работы:* 

Потоковая обработка логов и их отображение в реальном времени, анализ ошибок, отправка оповещений.

### API
### Создать лог:

`curl -X POST "http://localhost:8080/logs?source=service1&level=ERROR&message=Something_went_wrong"`

### Получить все логи:

`curl -X GET "http://localhost:8080/logs"`

### Получить логи по уровню:

`curl -X GET "http://localhost:8080/logs/level/ERROR"`

### Отрпавить по UDP через PowerShell:

`$endpoint = New-Object System.Net.IPEndPoint ([System.Net.IPAddress]::Parse("127.0.0.1"), 9999)
$udpClient = New-Object System.Net.Sockets.UdpClient
$message = [System.Text.Encoding]::ASCII.GetBytes("service1-udp,ERROR,Something went wrong")
$bytesSent = $udpClient.Send($message, $message.Length, $endpoint)
$udpClient.Close()
Write-Host "Sent $bytesSent bytes to $($endpoint.Address):$($endpoint.Port)"`

### Тест отправки логов в rabbitMQ
POST `http://localhost:8080/api/logs/send?source=service1&level=ERROR&message=Something_went_wrong`

http://localhost:8080/logs/view — список логов

http://localhost:8080/logs/metrics — диаграмма с метриками.