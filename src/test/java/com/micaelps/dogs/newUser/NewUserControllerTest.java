package com.micaelps.dogs.newUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micaelps.dogs.user.NewUserSystemRequest;
import com.micaelps.dogs.user.UserSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class NewUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("Shold create new user and return status 200")
    void create_new_user() throws Exception {
        NewUserSystemRequest newUserRequest = new NewUserSystemRequest("micael@email.com","123456");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(newUserRequest)))
                .andExpect(status().isOk());

        List<UserSystem> users = entityManager.createQuery("from UserSystem", UserSystem.class).getResultList();
        UserSystem user = users.get(2);

        assertEquals(users.size(), 3);
        assertEquals("micael@email.com", user.getEmail());
    }

    @Test
    @DisplayName("Shouldn't create a user with the same email")
    void create_user_with_the_same_email() throws Exception {
        NewUserSystemRequest newUserRequest = new NewUserSystemRequest("m@email.com","123456");
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(newUserRequest))).andExpect(status().isBadRequest());
    }

    private String toJson(NewUserSystemRequest newUserRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(newUserRequest);
    }
}
