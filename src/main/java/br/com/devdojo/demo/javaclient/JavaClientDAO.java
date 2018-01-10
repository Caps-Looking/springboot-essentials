package br.com.devdojo.demo.javaclient;

import br.com.devdojo.demo.handler.RestResponseExceptionHandler;
import br.com.devdojo.demo.model.PageableResponse;
import br.com.devdojo.demo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {

    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthorization("goku", "teste")
            .errorHandler(new RestResponseExceptionHandler()).build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthorization("goku", "teste")
            .errorHandler(new RestResponseExceptionHandler()).build();

    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
    }

    public List<Student> listAll() {
        return restTemplate.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {
                }).getBody().getContent();
    }

    public Student save(Student student) {
        return restTemplateAdmin.exchange("/", HttpMethod.POST,
                new HttpEntity<>(student, createJSONHeader()),
                Student.class).getBody();
    }

    public void update(Student student) {
        restTemplateAdmin.put("/", student);
    }

    public void delete(long id) {
        restTemplateAdmin.delete("/{id}", id);
    }

}
