# Currency-giphy integration project
## Описание проекта
Сервис, который обращается к сервису курсов валют (по умолчанию рассматривается пара USD-RUB), и отдает gif в ответ:
если курс по отношению к рублю за сегодня стал выше вчерашнего, то отдаем рандомную - [отсюда](https://giphy.com/search/rich),
если ниже - [отсюда](https://giphy.com/search/broke).
Запросы приходят на HTTP endpoint (http://localhost:8090/giphy). Для взаимодействия с внешними сервисами используется Feign. Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки. 

Ссылки:
* REST API [курсов валют](https://docs.openexchangerates.org/)
* REST API [гифок](https://developers.giphy.com/docs/api#quick-start-guide)

## Используемые технологии
* Java 11
* Spring boot 2.4.3
* Feign client
* Jupiter, Mockito
* Docker

## Сборка и запуск
Сборка и запуск проекта производится с помощью Docker.
Для запуска в корневой директории выполнить команду: 

```docker-compose up -d```