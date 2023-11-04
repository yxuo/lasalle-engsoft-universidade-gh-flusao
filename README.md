## Projeto Universidade GH Flusão
*Um trabalho de engenharia de Software do Lasalle*


## Iniciando o projeto

### Requisitos

- VS Code, Eclipse ou Intellij
- [**Extension Pack for Java** - VSCode](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- Maven >= 3.95
- Java JDK 17

### Como rodar

Instalar as dependências:

```
mvn install
```

Criar o banco de dados e aplicar as migrações:

```powershell
mvn exec:java@migration-run
```

Rodar o programa

```powershell
mvn exec:java@main
```

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
