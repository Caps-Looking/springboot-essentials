package br.com.devdojo.demo;

import br.com.devdojo.demo.model.Student;
import br.com.devdojo.demo.repository.StudentRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
//Descomente a linha abaixo para buscar dados no banco
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Philipe", "philipe@email.com.br");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Philipe");
        assertThat(student.getEmail()).isEqualTo("philipe@email.com.br");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Philipe", "philipe@email.com.br");
        this.studentRepository.save(student);
        this.studentRepository.delete(student);
        assertThat(this.studentRepository.findOne(student.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        Student student = new Student("Philipe", "philipe@email.com.br");
        this.studentRepository.save(student);
        student.setName("Philipe222");
        student.setEmail("philipe222@email.com.br");
        student = this.studentRepository.save(student);
        assertThat(student.getName()).isEqualTo("Philipe222");
        assertThat(student.getEmail()).isEqualTo("philipe222@email.com.br");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("Philipe", "philipe@email.com.br");
        Student student2 = new Student("philipe", "philipe123@email.com.br");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> studentList = this.studentRepository.findByNameIgnoreCaseContaining("philipe");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldFindContaining() {
        Student student = new Student("Philipe", "philipe@email.com.br");
        this.studentRepository.save(student);
        List<Student> studentList = this.studentRepository.findByNameIgnoreCaseContaining("h");
        assertThat(studentList.size()).isEqualTo(1);
        assertThat(studentList.get(0).getName()).isEqualTo("Philipe");
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo nome do estudante é obrigatório");
        Student student = new Student();
        student.setEmail("teste@email.com");
        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("teste");
        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo email do estudante não é válido");
        Student student = new Student();
        student.setName("teste");
        student.setEmail("teste");
        this.studentRepository.save(student);
    }

}
