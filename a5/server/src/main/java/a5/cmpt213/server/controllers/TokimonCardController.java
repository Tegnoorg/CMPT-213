package a5.cmpt213.server.controllers;

import a5.cmpt213.server.models.Tokimon;
import a5.cmpt213.server.models.TokimonList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
public class TokimonCardController {
    private AtomicInteger nextId;
    private TokimonList tokimonList;


    public int getNextId(){
        return nextId.getAndIncrement();
    }

    @GetMapping("/api/tokimon/all")
    public List<Tokimon> getTokimonList() {
        return tokimonList.getTokiList();
    }

    @GetMapping("/api/tokimon/{id}")
    public Tokimon getTokimon(@PathVariable("id") long id, HttpServletResponse response) {
        for(Tokimon t : tokimonList.getTokiList()) {
            if(t.getId() == id) {
                response.setStatus(HttpServletResponse.SC_OK);
                return t;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    @PostMapping("/api/tokimon/add")
    public Tokimon addTokimon(@RequestBody Tokimon tokimon, HttpServletResponse response) {
        nextId = new AtomicInteger(tokimonList.getTokiList().getLast().getId()+1);
        tokimon.setId(nextId.getAndIncrement());
        tokimonList.getTokiList().add(tokimon);
        response.setStatus(HttpServletResponse.SC_CREATED);
        FileController.updateJson(tokimonList);
        //System.out.println(tokimon.toString());
        return tokimon;
    }

    @PutMapping("/api/tokimon/edit/{id}")
    public Tokimon editTokimon(@PathVariable int id, @RequestBody Tokimon token, HttpServletResponse response) {
        for(Tokimon t : tokimonList.getTokiList()) {
            if(t.getId() == id) {
                t.setType(token.getType());
                t.setName(token.getName());
                t.setRarity(token.getRarity());
                t.setHp(token.getHp());
                t.setURL(token.getURL());
                FileController.updateJson(tokimonList);
                response.setStatus(HttpServletResponse.SC_OK);
                return t;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    @DeleteMapping("/api/tokimon/{id}")
    public Tokimon deleteTokimon(@PathVariable int id, HttpServletResponse response) {
        for(int i = 0; i < tokimonList.getTokiList().size(); i++) {
            if(tokimonList.getTokiList().get(i).getId() == id) {
                Tokimon tokimon = tokimonList.getTokiList().get(i);
                tokimonList.getTokiList().remove(i);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                FileController.updateJson(tokimonList);
                return tokimon;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

    @PostConstruct
    public void init() {
        tokimonList = new TokimonList();
        nextId = new AtomicInteger(0);

        File file = new File("./src/main/resources/static/tokimon.json");
        if (file.exists() && !file.isDirectory()) {
            try {
                Gson gson = new Gson();
                FileReader reader = new FileReader(file);

                // Parse JSON array
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    Tokimon tokimon = gson.fromJson(element, Tokimon.class);
                    tokimonList.getTokiList().add(tokimon);
                }

                int size = tokimonList.getTokiList().size();
                int id = tokimonList.getTokiList().get(size-1).getId();
                nextId.set(id-1);
                System.out.println(nextId);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Populate with default data if file does not exist
            Tokimon t1 = new Tokimon("Lightning", 4, "Tokichu", 100, "https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png");
            t1.setId(nextId.getAndIncrement());
            tokimonList.addTokimon(t1);

            Tokimon t2 = new Tokimon("Fire", 4, "Tokimander", 110, "https://img.pokemondb.net/artwork/large/charmander.jpg");
            t2.setId(nextId.getAndIncrement());
            tokimonList.addTokimon(t2);

            Tokimon t3 = new Tokimon("Water", 4, "Tokisaur", 110, "https://assets.pokemon.com/assets/cms2/img/pokedex/full//001.png");
            t3.setId(nextId.getAndIncrement());
            tokimonList.addTokimon(t3);

            FileController.updateJson(tokimonList);
        }
    }


}
