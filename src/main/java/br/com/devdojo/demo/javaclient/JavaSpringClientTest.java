package br.com.devdojo.demo.javaclient;

import br.com.devdojo.demo.model.Student;

public class JavaSpringClientTest {

    public static void main(String[] args) {
        JavaClientDAO dao = new JavaClientDAO();

        Student student = new Student();
        student.setName("John Wick");
        student.setEmail("john@email.com");
        student.setId(12L);

//        System.out.println("===========FIND BY ID==============");
//        System.out.println(dao.findById(5));
//        System.out.println("===========LIST ALL================");
//        System.out.println(dao.listAll());
//        System.out.println("==============SAVE=================");
//        System.out.println(dao.save(student));
//        dao.update(student);
//        dao.delete(student.getId());
    }

}
