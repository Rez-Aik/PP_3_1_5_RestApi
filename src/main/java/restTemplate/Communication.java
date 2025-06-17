package restTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import restTemplate.model.User;

import java.util.List;

@Component
public class Communication {

    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionCookie;
    private final RestTemplate restTemplate;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                        });
        this.sessionCookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        return responseEntity.getBody();
    }

    public void saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionCookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(URL, entity, String.class);
        System.out.println("\n User added " + entity);
        System.out.println(responseEntity.getBody());
    }


    public void editUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionCookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println("\n User updated " + entity);
        System.out.println(responseEntity.getBody());

    }

    public void deleteUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionCookie);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.println("\n User deleted " + entity);
        System.out.println(responseEntity.getBody());
    }
}
