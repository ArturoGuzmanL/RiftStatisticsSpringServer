package javacode.server.springelectronriftstatisticswebapp.controller;


import javacode.server.springelectronriftstatisticswebapp.MailSender.EmailsSender;
import javacode.server.springelectronriftstatisticswebapp.model.Cookie;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import javacode.server.springelectronriftstatisticswebapp.repository.CookieRepository;
import javacode.server.springelectronriftstatisticswebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/file/")
public class FilesController {

    @Autowired
    private EmailsSender emailsSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CookieRepository cookieRepository;

    // ------------- CSS ------------- //

    @GetMapping("css/style")
    public ResponseEntity<String> getDefaultStyle() {
        try (FileReader reader = new FileReader("src/main/resources/static/css/styles.css")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("css/loader")
    public ResponseEntity<String> getLoaderStyle() {
        try (FileReader reader = new FileReader("src/main/resources/static/css/loader.css")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("css/accSett")
    public ResponseEntity<String> getAccSettStyle() {
        try (FileReader reader = new FileReader("src/main/resources/static/css/AccountSettings.css")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- JAVASCRIPT ------------- //

    @GetMapping("js/Utilities")
    public ResponseEntity<String> getUtilitiesJS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/javascript"));
        try (FileReader reader = new FileReader("src/main/resources/static/javascript/UtilitiesScripts.js")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("js/Jquery")
    public ResponseEntity<String> getJqueryJS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/javascript"));
        try (FileReader reader = new FileReader("src/main/resources/static/javascript/jquery-3.6.4.min.js")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("js/AccSett")
    public ResponseEntity<String> getAccSettJS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/javascript"));
        try (FileReader reader = new FileReader("src/main/resources/static/javascript/AccountSettings.js")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("js/PassPage")
    public ResponseEntity<String> getPassPageJS() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/javascript"));
        try (FileReader reader = new FileReader("src/main/resources/static/javascript/PassPage.js")){
            String data = getDataFromFile(reader);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- COOKIE ------------- //

    @GetMapping("cookie/request/{id}")
    public ResponseEntity<String> getCookie(@PathVariable String id) {
        try {
            Optional<Cookie> cookie = cookieRepository.findByUserid(id);
            if (cookie.isPresent()) {
                return new ResponseEntity<>(cookie.get().getIp(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "cookie/create/{ip}@&@{id}")
    public ResponseEntity<String> createCookie(@PathVariable String ip, @PathVariable String id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                Cookie cookie = new Cookie();
                cookie.setUserid(id);
                cookie.setIp(ip);
                cookieRepository.save(cookie);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- ASSETS ------------- //

    @GetMapping("assets/logo/{name}")
    public ResponseEntity<byte[]> getLogo(@PathVariable String name) {
        try {
            File file = new File("src/main/resources/assets/Logo/" + name);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/championsqrrond/{id}")
    public ResponseEntity<byte[]> getChampionSqrRond(@PathVariable String id) {
        try {
            File file = new File("src/main/resources/assets/champion_squares_rounded/" + id);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/championIcon/{id}")
    public ResponseEntity<byte[]> getChampionIcon(@PathVariable String id) {
        try {
            File file = new File("src/main/resources/assets/championIcons/" + id);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/summIcon/{id}")
    public ResponseEntity<byte[]> getSummIcon(@PathVariable String id) {
        try {
            File file = new File("src/main/resources/assets/summIcon/" + id);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/eloIcon/{elo}")
    public ResponseEntity<byte[]> getEloIcon(@PathVariable String elo) {
        try {
            File file = new File("src/main/resources/assets/elo/" + elo);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/itemIcon/{id}")
    public ResponseEntity<byte[]> getItemIcon(@PathVariable String id) {
        try {
            File file = new File("src/main/resources/assets/itemIcons/" + id);
            if (!file.exists()) {
                file = new File("src/main/resources/assets/itemIcons/29.png");
            }
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("assets/statIcon/{name}")
    public ResponseEntity<byte[]> getStatIcon(@PathVariable String name) {
        try {
            File file = new File("src/main/resources/assets/statIcons/" + name);
            return getImageEntity(file);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- Mail ------------- //

    @GetMapping("mail/verify/{username}")
    public ResponseEntity<Boolean> register(@PathVariable String username) {
        try {
            Optional<User> userInfo = userRepository.findByUsername(username);
            if (userInfo.isPresent()) {
                emailsSender.sendConfirmationEmail(userInfo.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("mail/passchange/{username}")
    public ResponseEntity<Boolean> passChange(@PathVariable String username) {
        try {
            Optional<User> userInfo = userRepository.findByUsername(username);
            if (userInfo.isPresent()) {
                emailsSender.sendPassChangeEmail(userInfo.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private ResponseEntity<byte[]> getImageEntity (File file) throws IOException {
        if (file.exists()) {
            Path filePath = Paths.get(file.getAbsolutePath());
            byte[] fileContent = Files.readAllBytes(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getDataFromFile (FileReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[5120];
        int c;
        while ((c = reader.read(buffer)) > 0) {
            stringBuilder.append(buffer, 0, c);
        }
        return stringBuilder.toString();
    }
}
