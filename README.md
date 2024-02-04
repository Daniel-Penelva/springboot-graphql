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

# Entidades

## **Classe Student**

### Visão Geral

A classe `Student` representa um estudante em um sistema educacional e é mapeada para a tabela "student" no banco de dados. Esta classe possui atributos como `id`, `name`, `lastName`, `age` e uma associação muitos-para-um com a entidade `Course`.

```java
package com.api.springbootgraphql.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "last_name")
    private String lastName;
    private Integer age;

    // Muitos estudantes para um curso
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Course.class)
    private Course course;
}

```

### Atributos

1. **`id` (Long):**
   - Identificador único para cada estudante.
   - Anotado com `@Id` para indicar que é a chave primária da entidade.
   - Configurado para ser gerado automaticamente usando a estratégia de identidade (`@GeneratedValue`).

2. **`name` (String):**
   - Nome do estudante.
   - Não pode ser nulo.

3. **`lastName` (String):**
   - Sobrenome do estudante.
   - Mapeado para a coluna "last_name" no banco de dados usando `@Column(name = "last_name")`.

4. **`age` (Integer):**
   - Idade do estudante.
   - Pode ser nulo.

5. **`course` (Course):**
   - Associação muitos-para-um com a entidade `Course`.
   - Indica a relação de que muitos estudantes podem estar associados a um único curso.
   - Configurado para carregamento tardio (`FetchType.LAZY`) para otimização de desempenho.

### Relacionamentos

- **Muitos Estudantes para Um Curso:**
  - Cada instância de `Student` pode estar associada a um único `Course`.
  - A associação é representada pelo atributo `course`.

### Mapeamento de Tabela

- **Nome da Tabela:**
  - A tabela no banco de dados correspondente a esta entidade é chamada "student".

### Uso de Anotações

- **`@Entity`:**
  - Indica que a classe é uma entidade JPA e deve ser mapeada para uma tabela no banco de dados.

- **`@Table(name = "student")`:**
  - Especifica o nome da tabela no banco de dados.

- **`@Data`:**
  - Anotação do Lombok que gera automaticamente métodos como `toString`, `hashCode`, `equals`, `Getter`, `Setter`, etc.

- **`@Id`:**
  - Indica que o campo é a chave primária da entidade.

- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`:**
  - Configura a estratégia de geração de valor automático para a chave primária como identidade.

- **`@Column(name = "last_name")`:**
  - Especifica o nome da coluna no banco de dados para o atributo `lastName`.

- **`@ManyToOne(fetch = FetchType.LAZY, targetEntity = Course.class)`:**
  - Configura a associação muitos-para-um com a entidade `Course`.
  - Usa carregamento tardio para otimizar o desempenho ao recuperar instâncias de `Student`.

## **Classe Course**

### Visão Geral

A classe `Course` representa um curso em um sistema educacional e é mapeada para a tabela "course" no banco de dados. Esta classe possui atributos como `id`, `name`, `category`, `teacher` e uma associação um-para-muitos com a entidade `Student`.

```java
package com.api.springbootgraphql.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String teacher;
    
    // Um curso para muitos estudantes 
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Student.class) 
    private List<Student> studentList;
}
```

### Atributos

1. **`id` (Long):**
   - Identificador único para cada curso.
   - Anotado com `@Id` para indicar que é a chave primária da entidade.
   - Configurado para ser gerado automaticamente usando a estratégia de identidade (`@GeneratedValue`).

2. **`name` (String):**
   - Nome do curso.
   - Não pode ser nulo.

3. **`category` (String):**
   - Categoria do curso.

4. **`teacher` (String):**
   - Nome do professor que ministra o curso.

5. **`studentList` (List<Student>):**
   - Associação um-para-muitos com a entidade `Student`.
   - Indica a relação de que um curso pode ter muitos estudantes associados.
   - Configurado para carregamento tardio (`FetchType.LAZY`) para otimização de desempenho.

### Relacionamentos

- **Um Curso para Muitos Estudantes:**
  - Cada instância de `Course` pode ter uma lista de estudantes associados.
  - A associação é representada pelo atributo `studentList`.

### Mapeamento de Tabela

- **Nome da Tabela:**
  - A tabela no banco de dados correspondente a esta entidade é chamada "course".

### Uso de Anotações

- **`@Entity`:**
  - Indica que a classe é uma entidade JPA e deve ser mapeada para uma tabela no banco de dados.

- **`@Table(name = "course")`:**
  - Especifica o nome da tabela no banco de dados.

- **`@Data`:**
  - Anotação do Lombok que gera automaticamente

 métodos como `toString`, `hashCode`, `equals`, `Getter`, `Setter`, etc.

- **`@Id`:**
  - Indica que o campo é a chave primária da entidade.

- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`:**
  - Configura a estratégia de geração de valor automático para a chave primária como identidade.

- **`@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Student.class)`:**
  - Configura a associação um-para-muitos com a entidade `Student`.
  - Usa carregamento tardio para otimizar o desempenho ao recuperar instâncias de `Course`.

### Observações

- Vale ressaltar que o carregamento tardio (`FetchType.LAZY`) na associação com `Course` pode exigir uma sessão de persistência ativa para acessar os detalhes do curso ao recuperar um estudante.

# Repositórios

## **Interface StudentDao**

### Visão Geral

A interface `StudentDao` é uma interface de acesso a dados que estende `CrudRepository` e fornece métodos para realizar operações de persistência relacionadas à entidade `Student`. Esses métodos incluem operações CRUD (Create, Read, Update, Delete) padrão fornecidas pelo Spring Data.

```java
package com.api.springbootgraphql.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.springbootgraphql.entities.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Long> {

}
```

### Operações Básicas

A interface `CrudRepository` fornece métodos básicos para manipulação de entidades, incluindo:

1. **`save(S entity)`:**
   - Salva uma instância de `Student` no banco de dados. Se a entidade já existir, ela será atualizada.

2. **`findById(ID id)`:**
   - Recupera um estudante do banco de dados com base no ID.

3. **`findAll()`:**
   - Recupera todos os estudantes presentes no banco de dados.

4. **`deleteById(ID id)`:**
   - Exclui um estudante do banco de dados com base no ID.

### Uso de Anotações

- **`@Repository`:**
  - Indica que a interface é um componente Spring e que será tratada como um bean gerenciado. Além disso, ela habilita a tradução de exceções específicas do JPA para exceções mais amigáveis do Spring.

## **Interface CourseDao**

### Visão Geral

A interface `CourseDao` é uma interface de acesso a dados que estende `CrudRepository` e fornece métodos para realizar operações de persistência relacionadas à entidade `Course`. Assim como `StudentDao`, ela oferece operações CRUD padrão fornecidas pelo Spring Data.

```java
package com.api.springbootgraphql.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.springbootgraphql.entities.Course;

@Repository
public interface CourseDao extends CrudRepository<Course, Long>{
    
}
```

### Operações Básicas

Os métodos básicos fornecidos pela interface `CrudRepository` para a entidade `Course` são semelhantes aos mencionados anteriormente para `StudentDao`.

### Uso de Anotações

A interface `CourseDao` usa a anotação `@Repository`, indicando que é um componente Spring gerenciado e que será tratado como um bean.

# Serviço

## **Interface IStudentService**

### Visão Geral

A interface `IStudentService` define um conjunto de métodos que encapsulam as operações de serviço relacionadas aos estudantes em um sistema educacional. Ela abrange desde a recuperação de informações específicas do estudante até a associação e desassociação de estudantes de cursos.

```java
package com.api.springbootgraphql.service;

import java.util.List;

import com.api.springbootgraphql.entities.Student;

public interface IStudentService {

    Student findById(Long id);

    List<Student> findAll();

    void createStudent(Student student);

    void deleteById(long id);

    Student updateStudent(Long id, Student updateStudent);

    void createAssociationStudentIncourse(Long idStudent, Long idCourse);

    boolean associateStudentWithCourse(Long idStudent, Long idCourse);

    /* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
    Student createStudentWithoutCourse(Student student);

}
```

### Métodos

1. **`findById(Long id) : Student`:**
   - Retorna um estudante com base no ID fornecido.

2. **`findAll() : List<Student>`:**
   - Retorna uma lista de todos os estudantes no sistema.

3. **`createStudent(Student student) : void`:**
   - Cria um novo estudante no sistema.

4. **`deleteById(long id) : void`:**
   - Exclui um estudante com base no ID fornecido.

5. **`updateStudent(Long id, Student updateStudent) : Student`:**
   - Atualiza as informações de um estudante com base no ID fornecido.

6. **`createAssociationStudentIncourse(Long idStudent, Long idCourse) : void`:**
   - Cria uma associação entre um estudante e um curso com base nos IDs fornecidos.

7. **`associateStudentWithCourse(Long idStudent, Long idCourse) : boolean`:**
   - Associa um estudante a um curso com base nos IDs fornecidos e retorna verdadeiro se a associação for bem-sucedida.

8. **`createStudentWithoutCourse(Student student) : Student`:**
   - Cria um estudante sem associá-lo a um curso específico.

## **Serviço StudentServiceImpl**

### Visão Geral

O `StudentServiceImpl` é uma implementação da interface `IStudentService` e fornece métodos para manipular operações relacionadas aos estudantes em um sistema educacional. Ele inclui funcionalidades como busca, criação, atualização, exclusão e associação/desassociação de estudantes a cursos.

```java
package com.api.springbootgraphql.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.entities.Student;
import com.api.springbootgraphql.persistence.CourseDao;
import com.api.springbootgraphql.persistence.StudentDao;
import com.api.springbootgraphql.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(readOnly = true) // usada para otimizar consultas de leitura, indicando que a transação envolve apenas operações de leitura e não faz modificações no banco de dados.
    public Student findById(Long id) {
        return studentDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
    }

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public List<Student> findAll() {
        return (List<Student>) studentDao.findAll();
    }

    @Override
    @Transactional // A anotação @Transactional sem readOnly assume que a transação pode envolver operações de leitura e escrita.
    public void createStudent(Student student) {
        studentDao.save(student);
    }

    @Override
    @Transactional
    public void deleteById(long id) {

        if (studentDao.existsById(id)) {
            studentDao.deleteById(id);
        } else {
            throw new RuntimeException("Id: " + id + " não encontrado, impossível excluir!");
        }
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student updateStudent) {

        /*
         * Pode fazer assim ou chamando pelo método findById(): 
         * Student existingStudent = studentDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
         */
        Student existingStudent = findById(id);

        // Atualiza os valores do estudante existente com os valores do objeto 'updatedStudent'
        existingStudent.setName(updateStudent.getName());
        existingStudent.setLastName(updateStudent.getLastName());
        existingStudent.setAge(updateStudent.getAge());

        // Salva o estudante existente no banco de dados
        return studentDao.save(updateStudent);
    }

    @Override
    @Transactional
    public void createAssociationStudentIncourse(Long idStudent, Long idCourse) {

        // Busca o estudante e curso pelo ID
        Student existingStudent = findById(idStudent);
        Course existingCourse = courseDao.findById(idCourse)
                .orElseThrow(() -> new RuntimeException("Id: " + idCourse + " não encontrado!"));

        existingStudent.setCourse(existingCourse); // Associa o estudante ao curso

        studentDao.save(existingStudent); // Salva as alterações no banco de dados

    }

    /* Outra forma de fazer utilizando um tipo boolean, fiz somente para adquirir mais aprendizagem: */
    @Override
    @Transactional
    public boolean associateStudentWithCourse(Long idStudent, Long idCourse) {
        try {
            // Busca o estudante pelo ID
            Student existingStudent = findById(idStudent);

            // Busca o curso pelo ID
            Course existingCourse = courseDao.findById(idCourse)
                    .orElseThrow(() -> new RuntimeException("Id: " + idCourse + " não encontrado!"));

            // Associa o estudante ao curso
            existingStudent.setCourse(existingCourse);

            // Salva as alterações no banco de dados
            studentDao.save(existingStudent);

            return true; // Indica que a associação foi bem-sucedida
        } catch (Exception e) {
            e.printStackTrace(); // Lida com a exceção (pode ser melhorar o tratamento dependendo do caso)
            return false; // Indica que a associação falhou
        }
    }

    /* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
    @Override
    @Transactional
    public Student createStudentWithoutCourse(Student student){
        return studentDao.save(student);
    }

}
```

### Anotações

- **`@Service`:**
  - Indica que a classe é um componente de serviço Spring, gerenciado pelo contêiner Spring. Esse serviço encapsula a lógica de negócios associada aos estudantes.

### Métodos

1. **`findById(Long id) : Student`:**
   - Recupera um estudante com base no ID fornecido.
   - Utiliza a anotação `@Transactional` para otimizar consultas de leitura, indicando que a transação envolve apenas operações de leitura e não faz modificações no banco de dados.

2. **`findAll() : List<Student>`:**
   - Recupera todos os estudantes no sistema.
   - Usa a anotação `@Transactional` para indicar uma transação somente de leitura.

3. **`createStudent(Student student) : void`:**
   - Cria um novo estudante no sistema.
   - A anotação `@Transactional` assume que a transação pode envolver operações de leitura e escrita.

4. **`deleteById(long id) : void`:**
   - Exclui um estudante com base no ID fornecido.
   - Usa a anotação `@Transactional` para garantir a consistência transacional.

5. **`updateStudent(Long id, Student updateStudent) : Student`:**
   - Atualiza as informações de um estudante com base no ID fornecido.
   - Utiliza a anotação `@Transactional`.

6. **`createAssociationStudentIncourse(Long idStudent, Long idCourse) : void`:**
   - Cria uma associação entre um estudante e um curso com base nos IDs fornecidos.
   - Utiliza a anotação `@Transactional`.

7. **`associateStudentWithCourse(Long idStudent, Long idCourse) : boolean`:**
   - Associa um estudante a um curso com base nos IDs fornecidos e retorna verdadeiro se a associação for bem-sucedida.
   - Utiliza a anotação `@Transactional`.

8. **`createStudentWithoutCourse(Student student) : Student`:**
   - Cria um estudante sem associá-lo a um curso específico.
   - Utiliza a anotação `@Transactional`.


## **Interface ICourseService**

### Visão Geral

A interface `ICourseService` define um conjunto de métodos que encapsulam as operações de serviço relacionadas aos cursos em um sistema educacional. Ela abrange desde a recuperação de informações específicas do curso até a atualização e exclusão de cursos.

```java
public interface ICourseService {

    Course findById(Long id);

    List<Course> findAll();

    void createCourse(Course course);

    void deleteById(long id);

    Course updateCourse(Long id, Course updateCourse);
}
```

### Métodos

1. **`findById(Long id) : Course`:**
   - Retorna um curso com base no ID fornecido.

2. **`findAll() : List<Course>`:**
   - Retorna uma lista de todos os cursos no sistema.

3. **`createCourse(Course course) : void`:**
   - Cria um novo curso no sistema.

4. **`deleteById(long id) : void`:**
   - Exclui um curso com base no ID fornecido.

5. **`updateCourse(Long id, Course updateCourse) : Course`:**
   - Atualiza as informações de um curso com base no ID fornecido.


## **Serviço CourseServiceImpl**

### Visão Geral

O `CourseServiceImpl` é uma implementação da interface `ICourseService` e fornece métodos para manipular operações relacionadas aos cursos em um sistema educacional. Ele abrange funcionalidades como busca, criação, atualização e exclusão de cursos.

```java
package com.api.springbootgraphql.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.persistence.CourseDao;
import com.api.springbootgraphql.service.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public Course findById(Long id) {
        return courseDao.findById(id).orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado!"));
    }

    @Override
    @Transactional(readOnly = true) // transação somente de leitura
    public List<Course> findAll() {
        return (List<Course>) courseDao.findAll();
    }

    @Override
    @Transactional
    public void createCourse(Course course) {
        courseDao.save(course);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        
        if (courseDao.existsById(id)) {
            courseDao.deleteById(id);
        } else {
            throw new RuntimeException("Id: " + id + " não encontrado, impossível excluir!");
        }

    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course updateCourse){

        Course existingCourse = findById(id);

        existingCourse.setName(updateCourse.getName());
        existingCourse.setCategory(updateCourse.getCategory());
        existingCourse.setTeacher(updateCourse.getTeacher());

        return courseDao.save(existingCourse);
    }

}
```

### Anotações

- **`@Service`:**
  - Indica que a classe é um componente de serviço Spring, gerenciado pelo contêiner Spring. Este serviço encapsula a lógica de negócios associada aos cursos.

### Métodos

1. **`findById(Long id) : Course`:**
   - Recupera um curso com base no ID fornecido.
   - Utiliza a anotação `@Transactional` para otimizar consultas de leitura, indicando que a transação envolve apenas operações de leitura e não faz modificações no banco de dados.

2. **`findAll() : List<Course>`:**
   - Recupera todos os cursos no sistema.
   - Usa a anotação `@Transactional` para indicar uma transação somente de leitura.

3. **`createCourse(Course course) : void`:**
   - Cria um novo curso no sistema.
   - A anotação `@Transactional` assume que a transação pode envolver operações de leitura e escrita.

4. **`deleteById(long id) : void`:**
   - Exclui um curso com base no ID fornecido.
   - Utiliza a anotação `@Transactional` para garantir a consistência transacional.

5. **`updateCourse(Long id, Course updateCourse) : Course`:**
   - Atualiza as informações de um curso com base no ID fornecido.
   - Utiliza a anotação `@Transactional`.

### Observações

- Certifique-se de que as dependências e a configuração do Spring estejam corretas para o correto funcionamento do `CourseServiceImpl`.

- A anotação `@Transactional` é usada de maneira apropriada para garantir a integridade transacional e a consistência no banco de dados.

# Dados Inputs

## **Classes de Dados InputStudent e InputCourse**

### Visão Geral

As classes `InputStudent` e `InputCourse` são classes de dados utilizadas para representar informações de entrada (input) em operações relacionadas a estudantes e cursos, respectivamente. Essas classes geralmente são utilizadas em métodos de criação ou atualização, onde os dados são recebidos como entrada.

```java
package com.api.springbootgraphql.graphiql;

import lombok.Data;

@Data
public class InputStudent {

    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private String courseId;
}

```

```java
package com.api.springbootgraphql.graphiql;

import lombok.Data;

@Data
public class InputCourse {
    private String name;
    private String category;
    private String teacher;
}
```

### Anotação

- **`@Data`:**
  - Uma anotação do projeto Lombok que automaticamente gera métodos padrão, como `toString`, `equals`, `hashCode`, e outros métodos úteis, com base nos campos da classe.

### Atributos Comuns (InputStudent)

1. **`id (Long)`:**
   - Identificador único do estudante. Pode ser nulo ao criar um novo estudante, pois geralmente é atribuído automaticamente pelo sistema.

2. **`name (String)`:**
   - Nome do estudante.

3. **`lastName (String)`:**
   - Sobrenome do estudante.

4. **`age (Integer)`:**
   - Idade do estudante.

5. **`courseId (String)`:**
   - Identificador do curso ao qual o estudante está associado. Pode ser nulo ou vazio ao criar um novo estudante.

### Atributos Comuns (InputCourse)

1. **`name (String)`:**
   - Nome do curso.

2. **`category (String)`:**
   - Categoria ou área do curso.

3. **`teacher (String)`:**
   - Nome do professor responsável pelo curso.

### Observações

- A conversão de objetos `InputStudent` ou `InputCourse` para `Student` ou `Course` deve ser realizada em métodos adicionais para garantir a separação de responsabilidades e facilitar a manutenção do código.



# DTO

## **Classe de Registro (Record) StudentRequest**

**DETALHE:** Essa classe de registro (Record) `StudentRequest` foi um exemplo extra para criar uma funcionalidade onde irá criar um estudante que **não terá** associação com o curso (cursoId).

### Visão Geral

A classe `StudentRequest` é um exemplo de classe de registro (record) em Java, uma feature introduzida no Java 16. Ela é utilizada para representar uma requisição ou entrada de dados para a criação de um estudante no sistema educacional. A classe de registro é especialmente útil para criar classes imutáveis de forma concisa.

```java
package com.api.springbootgraphql.dto;

/* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
public record StudentRequest(Long id, String name, String lastName, Integer age) {
    
}

```

### Atributos

1. **`id (Long)`:**
   - Identificador único do estudante. Pode ser nulo ao criar um novo estudante, pois geralmente é atribuído automaticamente pelo sistema.

2. **`name (String)`:**
   - Nome do estudante.

3. **`lastName (String)`:**
   - Sobrenome do estudante.

4. **`age (Integer)`:**
   - Idade do estudante.

### Anotação

- **`record`:**
  - Uma nova feature do Java a partir da versão 16, que permite criar classes imutáveis de forma mais concisa. Automaticamente gera métodos como `toString`, `equals`, e `hashCode` com base nos atributos da classe.

### Observações

- O uso da classe de registro (`record`) simplifica a criação de classes imutáveis, eliminando a necessidade de escrever manualmente métodos como `equals`, `hashCode`, e `toString`.

- A conversão de objetos `StudentRequest` para `Student` deve ser realizada em métodos adicionais para garantir a separação de responsabilidades e facilitar a manutenção do código.


# Graphql

## **Schema GraphQL**

### Visão Geral

O schema GraphQL define a estrutura da API e especifica as operações que os clientes podem realizar. Este schema em particular está relacionado a um sistema educacional, incluindo entidades como `Student` e `Course`. As operações são divididas em consultas (`Query`) para leitura e mutações (`Mutation`) para criação, atualização e exclusão.

```graphqls
# Query - para gerar as consultas de leitura usa-se o type Query
type Query {

    # Student
    findStudentById(studentId: String) : Student,
    findAllStudents : [Student]

    # Course
    findCourseById(courseId: String) : Course,
    findAllCourses : [Course]
}

type Mutation {
    
    # Student
    createStudent(inputStudent: InputStudent) : Student,
    deleteStudentById(studentId: String) : String,
    updateStudent(studentId: String, inputStudent: InputStudent) : Student,
    createStudentAssociationAndCourse(studentId: String!, courseId: String!) : String,

    # Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId)
    createStudentWithoutCourse(studentRequest: StudentRequest) : Student,

    # Course
    createCourse(inputCourse: InputCourse) : Course,
    deleteCourseById(courseId: String) : String,
    updateCourse(courseId: String, inputCourse: InputCourse) : Course

}

# OBS. Por exemplo, age: Int!, o simbolo ! significa que a propriedade é obrigatória, ou seja, não pode deixar null e nem vazio
# Definições de tipos GraphQL para as entidades Student e Course, incluindo os campos e seus tipos associados.
type Student {
    id: ID, 
    name: String,
    lastName: String,
    age: Int,
    course: Course
}

type Course {
    id: ID,
    name: String,
    category: String,
    teacher: String,
    studentList : [Student]
}

# Tipo de entrada de dados de student e course
input InputStudent {
    id: ID,
    name: String,
    lastName: String,
    age: Int,
    courseId: String
}

input InputCourse {
    id: ID,
    name: String,
    category: String,
    teacher: String
}

# Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId)

input StudentRequest{
    id: ID,
    name: String,
    lastName: String,
    age: Int
}
```

### Tipos GraphQL

#### Tipo Query

1. **`findStudentById(studentId: String) : Student`**
   - Retorna um estudante com base no ID fornecido.

2. **`findAllStudents : [Student]`**
   - Retorna uma lista de todos os estudantes no sistema.

3. **`findCourseById(courseId: String) : Course`**
   - Retorna um curso com base no ID fornecido.

4. **`findAllCourses : [Course]`**
   - Retorna uma lista de todos os cursos no sistema.

#### Tipo Mutation

1. **`createStudent(inputStudent: InputStudent) : Student`**
   - Cria um novo estudante com base nos dados fornecidos.

2. **`deleteStudentById(studentId: String) : String`**
   - Exclui um estudante com base no ID fornecido.

3. **`updateStudent(studentId: String, inputStudent: InputStudent) : Student`**
   - Atualiza as informações de um estudante com base no ID fornecido.

4. **`createStudentAssociationAndCourse(studentId: String!, courseId: String!) : String`**
   - Associa um estudante a um curso com base nos IDs fornecidos.

5. **`createStudentWithoutCourse(studentRequest: StudentRequest) : Student`**
   - (Exemplo-Extra) Cria um estudante sem associá-lo a um curso, utilizando os dados fornecidos.

6. **`createCourse(inputCourse: InputCourse) : Course`**
   - Cria um novo curso com base nos dados fornecidos.

7. **`deleteCourseById(courseId: String) : String`**
   - Exclui um curso com base no ID fornecido.

8. **`updateCourse(courseId: String, inputCourse: InputCourse) : Course`**
   - Atualiza as informações de um curso com base no ID fornecido.

#### Tipos de Dados GraphQL

1. **Tipo `Student`**
   - Representa um estudante no sistema.
   - Campos: `id`, `name`, `lastName`, `age`, `course`.

2. **Tipo `Course`**
   - Representa um curso no sistema.
   - Campos: `id`, `name`, `category`, `teacher`, `studentList`.

3. **Tipos de Entrada `InputStudent` e `InputCourse`**
   - Utilizados como entrada para criar ou atualizar estudantes e cursos, respectivamente.

4. **Tipo de Entrada `StudentRequest`**
   - (Exemplo-Extra) Utilizado como entrada para criar um estudante sem associá-lo a um curso.

### Observações

- A notação `!` após o tipo de dado (`Int!`, `String!`) indica que o campo é obrigatório, ou seja, não pode ser nulo.

- A estrutura do schema GraphQL fornece uma documentação clara das operações suportadas pela API, facilitando o entendimento e uso por parte dos desenvolvedores e consumidores da API.

# Controladores

## **Classe GraphqlStudentController**

### Visão Geral

O `GraphqlStudentController` é um controlador Spring responsável por gerenciar requisições GraphQL relacionadas a estudantes e cursos em um sistema educacional. Ele utiliza a biblioteca Spring GraphQL para processar consultas e mutações GraphQL.

```java
package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.dto.StudentRequest;
import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.entities.Student;
import com.api.springbootgraphql.graphiql.InputStudent;
import com.api.springbootgraphql.service.ICourseService;
import com.api.springbootgraphql.service.IStudentService;

@Controller
public class GraphqlStudentController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ICourseService iCourseService;

    /* Acessar a interface gráfica do Graphiql: http://localhost:8080/graphiql */
    @QueryMapping(name = "findStudentById")
    public Student findById(@Argument(name = "studentId") String id) {
        Long studentId = Long.parseLong(id);
        return iStudentService.findById(studentId);
    }

    @QueryMapping(name = "findAllStudents")
    public List<Student> findAll() {
        return iStudentService.findAll();
    }

    /*  // Neste método, o Student é criado já associado ao Course (courseId).
    @MutationMapping
    public Student createStudent(@Argument InputStudent inputStudent) {
        
        Course course = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(course);

        iStudentService.createStudent(student);
        return student;
    }
    */

    // Refatorando CreateStudent, neste método o Student pode ser criado associando ao Course ou não associando ao Course (courseId).
    @MutationMapping
    public Student createStudent(@Argument InputStudent inputStudent) {

        Student student = new Student();
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());

        // Verifica se courseId não é nulo antes de associar o curso
        if (inputStudent.getCourseId() != null) {
            Course course = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));
            student.setCourse(course);
        }

        iStudentService.createStudent(student);
        return student;
    }

    // Cria a associação entre Student e Course
    @MutationMapping
    public String createStudentAssociationAndCourse(@Argument String studentId, @Argument String courseId){

        // Convertendo Student String para Long 
        Long student = Long.parseLong(studentId);

        // Convertendo Course String para Long 
        Long course = Long.parseLong(courseId);

        try {
            iStudentService.createAssociationStudentIncourse(student, course);
            // ou pode chamar esse método também: // iStudentService.associateStudentWithCourse(student, course);
            return "Student associated with Course successfully!";
        } catch (Exception e) {
            return "Failed to associate Student with Course. Reason: " + e.getMessage();
        }
        
    }

    @MutationMapping(name = "deleteStudentById")
    public String deleteById(@Argument(name = "studentId") String id) {
        iStudentService.deleteById(Long.parseLong(id));
        return "student with id " + id + " successfully deleted!";
    }

    @MutationMapping
    public Student updateStudent(@Argument(name = "studentId") String id,
            @Argument(name = "inputStudent") InputStudent inputStudent) {

        // Convertendo o id de String para Long do Student
        Long studentId = Long.parseLong(id);

        // Convertendo o id de String para Long do Course
        Course courseId = iCourseService.findById(Long.parseLong(inputStudent.getCourseId()));

        // Convertendo o InputString para Student
        Student student = new Student();
        student.setId(studentId);
        student.setName(inputStudent.getName());
        student.setLastName(inputStudent.getLastName());
        student.setAge(inputStudent.getAge());
        student.setCourse(courseId);

        // Chama o serviço para realizar a atualização do estudante
        Student updateStudent = iStudentService.updateStudent(studentId, student);

        // Retorna o estudante atualizado
        return updateStudent;
    }


    /* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
    @MutationMapping
    public Student createStudentWithoutCourse(@Argument StudentRequest studentRequest){

        Student student = new Student();
        student.setName(studentRequest.name());
        student.setLastName(studentRequest.lastName());
        student.setAge(studentRequest.age());

        iStudentService.createStudentWithoutCourse(student);

        return student;
    }

}
```

### Endpoints GraphQL

#### Consultas (Queries)

1. **`findStudentById`**
   - Endpoint para encontrar um estudante por ID.
   - **Parâmetro:**
     - `studentId (String)`: ID do estudante a ser encontrado.
   - **Retorno:**
     - Objeto `Student`.

2. **`findAllStudents`**
   - Endpoint para listar todos os estudantes no sistema.
   - **Retorno:**
     - Lista de objetos `Student`.

3. **`findCourseById`**
   - Endpoint para encontrar um curso por ID.
   - **Parâmetro:**
     - `courseId (String)`: ID do curso a ser encontrado.
   - **Retorno:**
     - Objeto `Course`.

4. **`findAllCourses`**
   - Endpoint para listar todos os cursos no sistema.
   - **Retorno:**
     - Lista de objetos `Course`.

#### Mutações

5. **`createStudent`**
   - Endpoint para criar um novo estudante.
   - **Parâmetro:**
     - `inputStudent (InputStudent)`: Dados do estudante a ser criado.
   - **Retorno:**
     - Objeto `Student` recém-criado.

6. **`createStudentAssociationAndCourse`**
   - Endpoint para associar um estudante a um curso.
   - **Parâmetros:**
     - `studentId (String)`: ID do estudante.
     - `courseId (String)`: ID do curso.
   - **Retorno:**
     - Mensagem de sucesso ou falha.

7. **`deleteStudentById`**
   - Endpoint para excluir um estudante por ID.
   - **Parâmetro:**
     - `studentId (String)`: ID do estudante a ser excluído.
   - **Retorno:**
     - Mensagem de confirmação.

8. **`updateStudent`**
   - Endpoint para atualizar os dados de um estudante.
   - **Parâmetros:**
     - `studentId (String)`: ID do estudante a ser atualizado.
     - `inputStudent (InputStudent)`: Novos dados do estudante.
   - **Retorno:**
     - Objeto `Student` atualizado.

9. **`createStudentWithoutCourse`**
   - (Exemplo-Extra) Endpoint para criar um estudante sem associá-lo a um curso.
   - **Parâmetro:**
     - `studentRequest (StudentRequest)`: Dados do estudante a ser criado.
   - **Retorno:**
     - Objeto `Student` recém-criado.

### Observações

- O controlador utiliza injeção de dependência (`@Autowired`) para acessar serviços (`IStudentService` e `ICourseService`) responsáveis por realizar as operações.

- Os métodos no controlador são mapeados para as operações GraphQL por meio de anotações específicas, como `@QueryMapping` e `@MutationMapping`.

- Para garantir a flexibilidade na criação de estudantes (associados ou não a cursos), o método `createStudent` foi refatorado para aceitar um parâmetro opcional `courseId`. Se fornecido, associa o estudante ao curso correspondente.

- O controlador oferece endpoints GraphQL para realizar operações CRUD (Create, Read, Update, Delete) em estudantes e cursos, proporcionando uma interface eficiente e intuitiva para interações com o sistema educacional.


## **Classe GraphqlCourseController**

### Visão Geral

O `GraphqlCourseController` é um controlador Spring que gerencia requisições GraphQL relacionadas a cursos em um sistema educacional. Ele utiliza a biblioteca Spring GraphQL para processar consultas e mutações GraphQL.

```java
package com.api.springbootgraphql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.api.springbootgraphql.entities.Course;
import com.api.springbootgraphql.graphiql.InputCourse;
import com.api.springbootgraphql.service.ICourseService;

@Controller
public class GraphqlCourseController {

    @Autowired
    private ICourseService iCourseService;

    /* Acessar a interface gráfica do Graphiql: http://localhost:8080/graphiql */
    @QueryMapping(name = "findCourseById")
    public Course findById(@Argument(name = "courseId") String id) {
        Long courseId = Long.parseLong(id);
        return iCourseService.findById(courseId);
    }

    @QueryMapping(name = "findAllCourses")
    public List<Course> findall() {
        return iCourseService.findAll();
    }

    @MutationMapping
    public Course createCourse(@Argument InputCourse inputCourse) {

        Course course = new Course();
        course.setName(inputCourse.getName());
        course.setCategory(inputCourse.getCategory());
        course.setTeacher(inputCourse.getTeacher());

        iCourseService.createCourse(course);
        return course;

    }

    @MutationMapping(name = "deleteCourseById")
    public String deleteById(@Argument(name = "courseId") String id) {

        iCourseService.deleteById(Long.parseLong(id));
        return "course with id " + id + " successfully deleted!";
    }

    @MutationMapping
    public Course updateCourse(@Argument(name = "courseId") String id, @Argument InputCourse inputCourse) {

        // Converter o id String para Long
        Long courseId = Long.parseLong(id);

        // Convertendo InputCourse para Course
        Course course = new Course();
        course.setId(courseId);
        course.setName(inputCourse.getName());
        course.setCategory(inputCourse.getCategory());
        course.setTeacher(inputCourse.getTeacher());

        iCourseService.createCourse(course);

        return course;
    }
}
```

### Endpoints GraphQL

#### Consultas (Queries)

1. **`findCourseById`**
   - Endpoint para encontrar um curso por ID.
   - **Parâmetro:**
     - `courseId (String)`: ID do curso a ser encontrado.
   - **Retorno:**
     - Objeto `Course`.

2. **`findAllCourses`**
   - Endpoint para listar todos os cursos no sistema.
   - **Retorno:**
     - Lista de objetos `Course`.

#### Mutações

3. **`createCourse`**
   - Endpoint para criar um novo curso.
   - **Parâmetro:**
     - `inputCourse (InputCourse)`: Dados do curso a ser criado.
   - **Retorno:**
     - Objeto `Course` recém-criado.

4. **`deleteCourseById`**
   - Endpoint para excluir um curso por ID.
   - **Parâmetro:**
     - `courseId (String)`: ID do curso a ser excluído.
   - **Retorno:**
     - Mensagem de confirmação.

5. **`updateCourse`**
   - Endpoint para atualizar os dados de um curso.
   - **Parâmetros:**
     - `courseId (String)`: ID do curso a ser atualizado.
     - `inputCourse (InputCourse)`: Novos dados do curso.
   - **Retorno:**
     - Objeto `Course` atualizado.

### Observações

- O controlador utiliza injeção de dependência (`@Autowired`) para acessar o serviço (`ICourseService`) responsável por realizar as operações relacionadas a cursos.

- Os métodos no controlador são mapeados para as operações GraphQL por meio de anotações específicas, como `@QueryMapping` e `@MutationMapping`.

- O método `createCourse` recebe um parâmetro `inputCourse` do tipo `InputCourse`, permitindo a criação de cursos com base nos dados fornecidos.

- O controlador oferece endpoints GraphQL para realizar operações CRUD (Create, Read, Update, Delete) em cursos, proporcionando uma interface eficiente e intuitiva para interações com o sistema educacional.


# Documentação da Configuração do Banco de Dados e GraphQL

## **application.properties**

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/spring_graphQL_db
spring.datasource.username = root
spring.datasource.password = root

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto = create-drop
spring.jpa.properties.hibernate.show_sql = true

# Acessar a interface gráfica do Graphiql: http://localhost:8080/graphiql
spring.graphql.graphiql.enabled = true
```

### Configuração do Banco de Dados

A configuração abaixo é responsável por definir as propriedades do banco de dados utilizado pela aplicação Spring Boot.

- **URL do Banco de Dados:**
  ```
  spring.datasource.url = jdbc:mysql://localhost:3306/spring_graphQL_db
  ```
  - Define a URL do banco de dados MySQL, indicando o nome do banco de dados (`spring_graphQL_db`), o host (`localhost`) e a porta (`3306`).

- **Credenciais de Acesso ao Banco de Dados:**
  ```
  spring.datasource.username = root
  spring.datasource.password = root
  ```
  - Define o nome de usuário (`root`) e senha (`root`) para autenticação no banco de dados.

### Configuração do Hibernate

O Hibernate é uma ferramenta ORM (Object-Relational Mapping) que mapeia objetos Java para tabelas de banco de dados relacionais. As propriedades abaixo são específicas do Hibernate.

- **Dialeto SQL:**
  ```
  spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
  ```
  - Define o dialeto SQL utilizado pelo Hibernate para gerar consultas SQL otimizadas para o banco de dados MySQL.

- **Driver do Banco de Dados:**
  ```
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  ```
  - Define a classe do driver JDBC a ser utilizada para se conectar ao banco de dados MySQL.

- **DDL Automático do Hibernate:**
  ```
  spring.jpa.hibernate.ddl-auto = create-drop
  ```
  - Define o comportamento do Hibernate em relação à criação e atualização do esquema do banco de dados. Neste caso, `create-drop` indica que o Hibernate irá criar e, posteriormente, excluir o esquema ao iniciar e encerrar a aplicação, útil para ambientes de desenvolvimento.

- **Exibição de SQL:**
  ```
  spring.jpa.properties.hibernate.show_sql = true
  ```
  - Habilita a exibição das instruções SQL geradas pelo Hibernate no console para fins de depuração.

### Configuração do GraphiQL

- **Habilitar/Desabilitar o GraphiQL:**
  ```
  spring.graphql.graphiql.enabled = true
  ```
  - Configura se a interface gráfica do GraphiQL estará habilitada ou desabilitada. Se definido como `true`, o GraphiQL será acessível ao acessar o ponto de extremidade GraphQL da aplicação em um navegador. Isso proporciona uma interface interativa para explorar e executar consultas GraphQL.

### Observações

- As configurações apresentadas são essenciais para o funcionamento adequado da aplicação Spring Boot, garantindo a conexão com o banco de dados e a correta utilização do Hibernate para operações ORM.

- A opção `spring.jpa.hibernate.ddl-auto` define uma estratégia específica para o ambiente de desenvolvimento. Em ambientes de produção, essa opção pode ser ajustada para garantir a integridade do banco de dados.

- A habilitação do GraphiQL (`spring.graphql.graphiql.enabled = true`) facilita a interação com a API GraphQL durante o desenvolvimento, permitindo testes e consultas diretas no navegador. Deve-se considerar desativar esta opção em ambientes de produção por questões de segurança.


# **Documentação das Requisições GraphQL**

Abaixo estão documentadas as principais operações GraphQL realizadas por meio de consultas (`query`) e mutações (`mutation`). Cada exemplo é acompanhado de uma descrição do seu propósito e estrutura.

### Consulta - Encontrar Estudante por ID

**Objetivo:** Encontrar informações detalhadas de um estudante específico pelo ID.

**Exemplo:**
```graphql
query {
  findStudentById(studentId: "2") {
    id,
    name,
    lastName,
    age
  }
}
```

### Consulta - Encontrar Estudante com Detalhes do Curso

**Objetivo:** Encontrar informações detalhadas de um estudante, incluindo informações do curso associado.

**Exemplo:**
```graphql
query {
  findStudentById(studentId: "2") {
    id
    name
    lastName
    age
    course {
      id,
      category,
      name,
      teacher
    }
  }
}
```

### Consulta - Listar Todos os Estudantes

**Objetivo:** Recuperar informações básicas de todos os estudantes.

**Exemplo:**
```graphql
query {
  findAllStudents{
    id,
    name, 
    lastName,
    age
  }
}
```

### Consulta - Listar Todos os Estudantes com Detalhes do Curso (Alias)

**Objetivo:** Listar todos os estudantes com detalhes do curso associado usando um alias.

**Exemplo:**
```graphql
query {
  estudantes: findAllStudents{
    id,
    name, 
    lastName,
    age,
    course {
      id,
      category,
      name,
      teacher
    }
  }
}
```

### Consulta - Encontrar Curso por ID

**Objetivo:** Recuperar informações detalhadas de um curso específico pelo ID.

**Exemplo:**
```graphql
query{
  findCourseById(courseId:"3"){
    id,
    name,
    category,
    teacher
  }
}
```

### Consulta - Encontrar Curso com Lista de Estudantes

**Objetivo:** Recuperar informações detalhadas de um curso, incluindo a lista de estudantes associados.

**Exemplo:**
```graphql
query{
  findCourseById(courseId:"3"){
    id,
    name,
    category,
    teacher,
    studentList{
      id,
      name,
      lastName,
      age
    }
  }
}
```

### Consulta - Listar Todos os Cursos

**Objetivo:** Recuperar informações básicas de todos os cursos.

**Exemplo:**
```graphql
query{
  findAllCourses{
    id,
    name,
    category,
    teacher
  }
}
```

### Consulta - Listar Todos os Cursos com Lista de Estudantes

**Objetivo:** Recuperar informações detalhadas de todos os cursos, incluindo a lista de estudantes associados.

**Exemplo:**
```graphql
query{
  findAllCourses{
    id,
    name,
    category,
    teacher,
    studentList{
      id,
      name,
      lastName,
      age
    }
  }
}
```

### Mutação - Criar Novo Estudante com Associação a um Curso

**Objetivo:** Criar um novo estudante associado a um curso específico.

**Exemplo:**
```graphql
mutation{
  createStudent(inputStudent: {
    name: "Daniel",
    lastName: "Penelva",
    age: 34,
    courseId: "3"
  }){
    name,
    lastName, 
    age,
    course{
      id,
      name, 
      category,
      teacher
    }
  }
}
```

### Mutação - Atualizar Informações de um Estudante

**Objetivo:** Atualizar informações de um estudante, incluindo a associação a um novo curso.

**Exemplo:**
```graphql
mutation{
  updateStudent(studentId: "3", inputStudent:{
    id: "3",
    name: "Daniel",
    lastName: "Penelva de Andrade",
    age: 34,
    courseId: "1"
  }){
    id,
    name,
    lastName,
    age, 
    course{
      name,
      category,
      teacher
    }
  }
}
```

### Mutação - Criar Novo Curso

**Objetivo:** Criar um novo curso.

**Exemplo:**
```graphql
mutation{
  createCourse(inputCourse:{
    name: "Programação Java",
    category: "Tecnologia",
    teacher: "João da Silva"
  }){
    id,
    name,
    category,
    teacher
  }
}
```

### Mutação - Excluir Curso

 por ID

**Objetivo:** Excluir um curso específico pelo ID.

**Exemplo:**
```graphql
mutation{
  deleteCourseById(courseId: "2")
}
```

### Mutação - Atualizar Informações de um Curso

**Objetivo:** Atualizar informações de um curso específico.

**Exemplo:**
```graphql
mutation{
  updateCourse(courseId:"3", inputCourse:{
    id: "3",
    name: "Tecnologia da Informação",
    category: "TI",
    teacher: "João Damacedo"
  }){
    id, 
    name, 
    category,
    teacher
  }
}
```

### Mutação - Criar Novo Estudante sem Associação a um Curso

**Objetivo:** Criar um novo estudante sem associá-lo a um curso.

**Exemplo:**
```graphql
mutation{
  createStudent(inputStudent: {
    name: "Daniel",
    lastName: "Penelva",
    age: 34,
    courseId: null
  }){
    name,
    lastName, 
    age,
    course{
      id,
      name, 
      category,
      teacher
    }
  }
}
```

### Mutação - Criar Associação entre Estudante e Curso

**Objetivo:** Criar uma associação direta entre um estudante e um curso específico.

**Exemplo:**
```graphql
mutation {
  createStudentAssociationAndCourse(studentId:"5", courseId:"2")
}
```

### Mutação - Exemplo Extra: Criar Estudante sem Associação a um Curso (Usando Record)

**Objetivo:** Criar um novo estudante sem associá-lo a um curso, utilizando uma estrutura Record.

**Exemplo:**
```graphql
mutation{
  createStudentWithoutCourse(studentRequest:{
    name: "Daniel",
    lastName: "Penelva de Andrade",
    age: 34
  }){
    id,
    name, 
    lastName, 
    age
  }
}
```

### Observações

- As consultas e mutações fornecem uma ampla gama de operações para interagir com o sistema de estudantes e cursos por meio da API GraphQL.

- A flexibilidade do GraphQL permite realizar operações complexas com uma única solicitação, otimizando a eficiência da comunicação entre o cliente e o servidor.

- Certifique-se de ajustar os IDs e outros parâmetros conforme necessário ao realizar testes com sua aplicação.

### Acessando o GraphiQL para Operações GraphQL

Para realizar operações GraphQL e interagir com o sistema de estudantes e cursos, é necessário acessar o GraphiQL, uma interface gráfica interativa que facilita a execução de consultas e mutações. Siga os passos abaixo para acessar o GraphiQL:

**Link de Acesso:** Abra o navegador de sua escolha e digite o seguinte link na barra de endereços: `http://localhost:8080/graphiql`.

---

# Autor 
## Feito por: `Daniel Penelva de Andrade`