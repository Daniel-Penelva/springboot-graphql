# Operações de Leitura e Escrita em Bancos de Dados

#### 1. Introdução

Operações em bancos de dados podem ser categorizadas em operações de leitura e operações de escrita. No contexto do Hibernate/JPA, essas operações têm significados específicos.

#### 2. Operações de Leitura

- **Definição:**
  - Operações que leem dados do banco de dados, mas não realizam modificações. 

- **Exemplos:**
  - `findById`: Recupera um registro com base em um ID. Ou seja, São operações de leitura, pois está consultando dados existentes, mas não os modificando.
  - `findBy...`: Consultas que recuperam registros com base em critérios específicos.
  - Operações de leitura de dados existentes sem alterá-los. Ou seja, São operações de leitura, pois está consultando dados existentes, mas não os modificando.

#### 3. Operações de Escrita

- **Definição:**
  - Operações que modificam dados no banco de dados.

- **Exemplos:**
  - `save`: Insere um novo registro no banco de dados. Ou seja, é considerada uma operação de escrita, pois está inserindo novos dados no banco de dados.
  - `update`: Modifica dados existentes no banco de dados. Ou seja, também é uma operação de escrita, pois está modificando dados existentes no banco de dados.
  - `delete`: Remove registros do banco de dados. Ou seja, é uma operação de escrita, pois está removendo dados do banco de dados.

#### 4. Anotação `@Transactional`

- **Definição:**
  - A anotação `@Transactional` é usada para controlar transações em métodos de serviço.

- **Uso:**
  - `@Transactional` **sem** `readOnly`: Indica que a transação pode envolver operações de leitura e escrita.
  - `@Transactional(readOnly = true)`: Indica que a transação envolve apenas operações de leitura, otimizando o desempenho.

A anotação `@Transactional(readOnly = true)` é geralmente usada para otimizar consultas de leitura, indicando que a transação envolve apenas 
operações de leitura e não faz modificações no banco de dados. No entanto, listar uma lista ou buscar um valor são geralmente considerados 
operações de leitura, não de escrita. Isso pode variar um pouco dependendo do contexto e das implementações específicas, mas a distinção geral 
entre leitura e escrita permanece.

Portanto, a anotação `@Transactional` **sem** `readOnly` assume que a transação pode envolver **operações de leitura e escrita**. Já a anotação `@Transaction` **com** o `"readOnly=true"` é usada para **otimizar consultas de leitura**, indicando que a transação envolve apenas operações de leitura e não faz modificações no banco de dados.

#### 5. Considerações Finais

- Operações de leitura e escrita são conceitos importantes ao trabalhar com bancos de dados.
- A anotação `@Transactional` pode ser usada para controlar transações, garantindo atomicidade e consistência.

### Exemplo Prático

Vamos considerar um método de serviço fictício para ilustrar a distinção:

```java
@Service
public class ExemploService {

    @Autowired
    private ExemploRepository exemploRepository;

    @Transactional(readOnly = true)
    public List<Exemplo> listarExemplos() {
        return exemploRepository.findAll(); // Operação de leitura
    }

    @Transactional
    public void criarExemplo(Exemplo exemplo) {
        exemploRepository.save(exemplo); // Operação de escrita
    }
}
```

Esse é apenas um exemplo simples, e a aplicação prática pode variar com base nas necessidades específicas do projeto. 

# Autor 
## Feito por: `Daniel Penelva de Andrade`