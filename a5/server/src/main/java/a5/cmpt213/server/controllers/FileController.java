package a5.cmpt213.server.controllers;

import a5.cmpt213.server.models.TokimonList;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RestController
public class FileController {
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        Files.copy(file.getInputStream(), Paths.get(Objects.requireNonNull(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
        return "OK";
    }

    public static void updateJson(TokimonList tokimonList) {
        try (FileWriter writer = new FileWriter("./src/main/resources/static/tokimon.json", false)){
            Gson gson = new Gson();
            gson.toJson(tokimonList.getTokiList(), writer);
            writer.flush();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
