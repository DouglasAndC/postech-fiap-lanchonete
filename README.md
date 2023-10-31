# postech-fiap-lanchonete
# Lanchonete - Versão 1.0.0

Bem-vindo ao projeto **Lanchonete**! Esta é a versão 1.0.0, e abaixo você encontrará todas as instruções necessárias para começar a utilizar Docker e Docker Compose para rodar a aplicação.

## Pré-Requisitos

Antes de prosseguir, assegure-se de que você tem o Docker e o Docker Compose instalados em seu sistema. Caso ainda não os tenha instalado, visite a [documentação oficial do Docker](https://docs.docker.com/get-docker/) para obter instruções de instalação.

## Estrutura do Projeto

O projeto tem a seguinte estrutura de diretórios:

```plaintext
lanchonete/
│   docker-compose.yml
│   Dockerfile
│   ...
```


- `Dockerfile`: Arquivo com instruções para a criação da imagem Docker.
- `docker-compose.yml`: Arquivo de configuração para rodar a aplicação com o Docker Compose, definindo serviços, volumes, portas, entre outros.

## Construindo a Imagem Docker

Para construir a imagem Docker do projeto, abra um terminal, vá até a raiz do projeto e execute o seguinte comando:

`docker build -t lanchonete:1.0.0`

## Executando a Imagem Docker com `postgres:13.3`:

`docker-compose up`
