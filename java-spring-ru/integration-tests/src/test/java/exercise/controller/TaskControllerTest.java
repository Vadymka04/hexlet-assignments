package exercise.controller;

import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    @Test
    public void testSpecificTask() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .generate(Select.field(Task::getId), gen -> gen.longs().range(1L, 111L))
                .supply(Select.field(Task::getTitle), () -> faker.job().title())
                .supply(Select.field(Task::getDescription), () -> faker.job().keySkills())
                .create();
        taskRepository.save(task);

        var request = get("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON);
        var jsonResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var omResult = om.readValue(jsonResult, Task.class);

        assertAll("Verify task fields",
                () -> assertEquals(task.getId(), omResult.getId()),
                () -> assertEquals(task.getTitle(), omResult.getTitle()),
                () -> assertEquals(task.getDescription(), omResult.getDescription()),
                () -> assertNotNull(omResult.getCreatedAt()),
                () -> assertNotNull(omResult.getUpdatedAt())
        );
    }

    @Test
    public void createTask() throws Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.job().title())
                .supply(Select.field(Task::getDescription), () -> faker.job().keySkills())
                .create();

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task));

        var jsonResult = mockMvc.perform(request).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var omResult = om.readValue(jsonResult, Task.class);

        var actualResult = taskRepository.findById(omResult.getId());

        assertAll("Verify task fields",
                () -> assertNotNull(actualResult.orElseThrow().getId()),
                () -> assertEquals(task.getTitle(), actualResult.orElseThrow().getTitle()),
                () -> assertEquals(task.getDescription(), actualResult.orElseThrow().getDescription()),
                () -> assertNotNull(actualResult.orElseThrow().getCreatedAt()),
                () -> assertNotNull(actualResult.orElseThrow().getUpdatedAt())
        );
    }


    @Test
    public void updateTask() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .generate(Select.field(Task::getId), gen -> gen.longs().range(1L, 111L))
                .supply(Select.field(Task::getTitle), () -> faker.job().title())
                .supply(Select.field(Task::getDescription), () -> faker.job().keySkills())
                .create();
        taskRepository.save(task);

        var updateTask = Instancio.of(Task.class)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.job().title())
                .supply(Select.field(Task::getDescription), () -> faker.job().keySkills())
                .create();

        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updateTask));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var actualResult = taskRepository.findById(task.getId());

        assertAll("Verify task fields",
                () -> assertEquals(updateTask.getTitle(), actualResult.orElseThrow().getTitle()),
                () -> assertEquals(updateTask.getDescription(), actualResult.orElseThrow().getDescription()),
                () -> assertNotNull(actualResult.orElseThrow().getCreatedAt()),
                () -> assertNotNull(actualResult.orElseThrow().getUpdatedAt())
        );
    }

    @Test
    public void deleteTask() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .generate(Select.field(Task::getId), gen -> gen.longs().range(1L, 111L))
                .supply(Select.field(Task::getTitle), () -> faker.job().title())
                .supply(Select.field(Task::getDescription), () -> faker.job().keySkills())
                .create();
        taskRepository.save(task);

        var request = delete("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());

        assertEquals(0, taskRepository.findById(task.getId()).stream().count());
    }
}
