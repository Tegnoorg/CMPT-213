package a5.cmpt213.server.controllers;

import a5.cmpt213.server.models.Tokimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokimonCardControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokimonCardController tokiListController;

    @BeforeAll
    static void setUp() {
        System.out.println("Runs before each test");
    }

    //start with this first. make sure the tokimon.json file is deleted
    @Test
    void contextLoads() {
        assertThat(tokiListController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void testAddAndGetTokimon() throws Exception {
        Tokimon p1 = new Tokimon("Water", 1, "Das", 110,"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png" );
        p1.setId(tokiListController.getNextId());
        Tokimon p2 = new Tokimon("Earth", 4, "Sad", 140,"https://archives.bulbagarden.net/media/upload/thumb/9/97/0074Geodude.png/250px-0074Geodude.png");
        p2.setId(tokiListController.getNextId());
        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(new ObjectMapper().writeValueAsString(p1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"type\":\"Water\",\"rarity\":1,\"name\":\"Das\",\"id\":3,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png\"}")
                );
        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(new ObjectMapper().writeValueAsString(p2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"type\":\"Earth\",\"rarity\":4,\"name\":\"Sad\",\"id\":4,\"hp\":140,\"url\":\"https://archives.bulbagarden.net/media/upload/thumb/9/97/0074Geodude.png/250px-0074Geodude.png\"}")
                );

        Tokimon p3 = new Tokimon("Water", 1, "Das", 100,"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png" );
        this.mockMvc.perform(
                        put("/api/tokimon/edit/{id}", 3)
                                .content(new ObjectMapper().writeValueAsString(p3))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"type\":\"Water\",\"rarity\":1,\"name\":\"Das\",\"id\":3,\"hp\":100,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png\"}")
                );

        Tokimon p4 = new Tokimon("Water", 1, "Das", 110,"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png" );
        this.mockMvc.perform(
                        put("/api/tokimon/edit/{id}", 3)
                                .content(new ObjectMapper().writeValueAsString(p4))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"type\":\"Water\",\"rarity\":1,\"name\":\"Das\",\"id\":3,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png\"}")
                );

        this.mockMvc.perform(
                        put("/api/tokimon/edit/{id}", 9)
                                .content(new ObjectMapper().writeValueAsString(p4))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()
                );
        this.mockMvc.perform(
                        get("/api/tokimon/{id}",0))
                .andExpect(status().isOk()
                );
        this.mockMvc.perform(
                        get("/api/tokimon/{id}",1))
                .andExpect(status().isOk()
                );
        this.mockMvc.perform(
                        get("/api/tokimon/{id}",2))
                .andExpect(status().isOk()
                );
        this.mockMvc.perform(
                        get("/api/tokimon/{id}",3))
                .andExpect(status().isOk()
                );
        this.mockMvc.perform(
                        get("/api/tokimon/{id}",5))
                .andExpect(status().isNotFound()
                );

        this.mockMvc.perform(
                        get("/api/tokimon/all"))
                .andExpect(status().isOk())  // Ensure the status code is 200 OK
                .andExpect(content().json("[{\"type\":\"Lightning\",\"rarity\":4,\"name\":\"Tokichu\",\"id\":0,\"hp\":100,\"url\":\"https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png\"},"
                        + "{\"type\":\"Fire\",\"rarity\":4,\"name\":\"Tokimander\",\"id\":1,\"hp\":110,\"url\":\"https://img.pokemondb.net/artwork/large/charmander.jpg\"},"
                        + "{\"type\":\"Water\",\"rarity\":4,\"name\":\"Tokisaur\",\"id\":2,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//001.png\"},"
                        + "{\"type\":\"Water\",\"rarity\":1,\"name\":\"Das\",\"id\":3,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png\"},"
                        + "{\"type\":\"Earth\",\"rarity\":4,\"name\":\"Sad\",\"id\":4,\"hp\":140,\"url\":\"https://archives.bulbagarden.net/media/upload/thumb/9/97/0074Geodude.png/250px-0074Geodude.png\"}]")
                );

        this.mockMvc.perform(
                delete("/api/tokimon/{id}", 4))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(
                        get("/api/tokimon/all"))
                .andExpect(status().isOk())  // Ensure the status code is 200 OK
                .andExpect(content().json("[{\"type\":\"Lightning\",\"rarity\":4,\"name\":\"Tokichu\",\"id\":0,\"hp\":100,\"url\":\"https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png\"},"
                        + "{\"type\":\"Fire\",\"rarity\":4,\"name\":\"Tokimander\",\"id\":1,\"hp\":110,\"url\":\"https://img.pokemondb.net/artwork/large/charmander.jpg\"},"
                        + "{\"type\":\"Water\",\"rarity\":4,\"name\":\"Tokisaur\",\"id\":2,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//001.png\"},"
                        + "{\"type\":\"Water\",\"rarity\":1,\"name\":\"Das\",\"id\":3,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//007.png\"}]")
                );

        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 3))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(
                        get("/api/tokimon/all"))
                .andExpect(status().isOk())  // Ensure the status code is 200 OK
                .andExpect(content().json("[{\"type\":\"Lightning\",\"rarity\":4,\"name\":\"Tokichu\",\"id\":0,\"hp\":100,\"url\":\"https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png\"},"
                        + "{\"type\":\"Fire\",\"rarity\":4,\"name\":\"Tokimander\",\"id\":1,\"hp\":110,\"url\":\"https://img.pokemondb.net/artwork/large/charmander.jpg\"},"
                        + "{\"type\":\"Water\",\"rarity\":4,\"name\":\"Tokisaur\",\"id\":2,\"hp\":110,\"url\":\"https://assets.pokemon.com/assets/cms2/img/pokedex/full//001.png\"}]")
                );

        this.mockMvc.perform(
                        delete("/api/tokimon/{id}", 3))
                .andExpect(status().isNotFound());
    }
}
