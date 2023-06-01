package javacode.server.springelectronriftstatisticswebapp.controller;


import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatisticswebapp.MailSender.EmailsSender;
import javacode.server.springelectronriftstatisticswebapp.model.Cookie;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import javacode.server.springelectronriftstatisticswebapp.repository.CookieRepository;
import javacode.server.springelectronriftstatisticswebapp.repository.UserRepository;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@RestController
@RequestMapping("/page/")

public class UserController {
    ArrayList<LeagueShard> regions = new ArrayList<>();
    {
        regions.add(LeagueShard.NA1);
        regions.add(LeagueShard.EUN1);
        regions.add(LeagueShard.EUW1);
        regions.add(LeagueShard.KR);
        regions.add(LeagueShard.JP1);
        regions.add(LeagueShard.BR1);
        regions.add(LeagueShard.LA1);
        regions.add(LeagueShard.LA2);
        regions.add(LeagueShard.OC1);
        regions.add(LeagueShard.RU);
        regions.add(LeagueShard.TR1);
    }

    Supplier<FileSystemCacheProvider> fileCache= () -> new FileSystemCacheProvider();

    public UserController() {
        DataCall.setCacheProvider(fileCache.get());
    }


    @Autowired
    UserRepository userRepository;
    @Autowired
    HtmlFactory htmlFactory;
    @Autowired
    private EmailsSender emailsSender;
    @Autowired
    private CookieRepository cookieRepository;

//    @GetMapping("/all")
//    public List<User> list() {
//    }

    // ------------- Ping  ------------- //

    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    // -------------  Account actions  ------------- //

    @GetMapping("users/actions/login/{username}@&@{password}")
    public ResponseEntity<String> get(@PathVariable("username") String username, @PathVariable("password") String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            if (user.get().isVerified()) {
                return new ResponseEntity<>(user.get().getId(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("users/actions/signup/{data}")
    public ResponseEntity<String> signup(@PathVariable("data") String data) {
        String[] userData = data.split("@&@");
        String username = userData[0];
        String email = userData[1];
        String password = userData[2];
        String publicIP = userData[3];
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> user2 = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>("username is already in use", HttpStatus.CONFLICT);
        } else if (user2.isPresent()) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
        } else {
            try {
                User newUser = new User(username, password, email);
                Cookie cookie = new Cookie(newUser.getId(), publicIP);
                userRepository.save(newUser);
                cookieRepository.save(cookie);
                return new ResponseEntity<>(newUser.getId(), HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("users/actions/profileimgupdt/{uid}")
    public ResponseEntity<String> login(@PathVariable("uid") String uid, @RequestBody byte[] profileImg) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            user.get().setAccountimage(profileImg);
            userRepository.save(user.get());
            return new ResponseEntity<>(user.get().getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/verification/{uid}")
    public ResponseEntity<String> verify(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            user.get().setVerified(true);
            userRepository.save(user.get());
            String html = htmlFactory.getVerificationConfirmWeb();
            return new ResponseEntity<>(html, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/passchange/{uid}")
    public ResponseEntity<String> passChangeWeb(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            String html = htmlFactory.getPassChangeWeb(user.get().getId());
            return new ResponseEntity<>(html, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/cookie/verification/{uid}@&@{ipAdress}")
    public ResponseEntity<String> cookieVerify(@PathVariable("uid") String uid, @PathVariable("ipAdress") String ipAdress) {
        Optional<Cookie> cookie = cookieRepository.findByuseridAndIp(uid, ipAdress);
        if (cookie.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/cookie/create/{uid}@&@{ipAdress}")
    public ResponseEntity<String> cookieCreate(@PathVariable("uid") String uid, @PathVariable("ipAdress") String ipAdress) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            Optional<Cookie> cookie = cookieRepository.findByUserid(uid);
            if (cookie.isPresent()) {
                cookie.get().setIp(ipAdress);
                cookieRepository.save(cookie.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Cookie cookie1 = new Cookie(user.get().getId(), ipAdress);
                cookieRepository.save(cookie1);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/change/correctpsw/{uid}@&@{psswd}")
    public ResponseEntity<String> changePassword(@PathVariable("uid") String uid, @PathVariable("psswd") String psswd) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(psswd)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("users/actions/delete/{uid}")
    public ResponseEntity<String> delete(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        try {
            userRepository.delete(user.get());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> user2 = userRepository.findById(uid);
        if (user2.isPresent()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("users/actions/notexist/{uid}")
    public ResponseEntity<String> exists(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("users/actions/update/{uid}/{type}")
    public ResponseEntity<String> update(@PathVariable("uid") String uid, @PathVariable("type") String type,@RequestBody String data) {
        Optional<User> user = userRepository.findById(uid);
        boolean changesMade = false;
        if (user.isPresent()) {
            String[] userData = data.split("@&@");
            String username = null;
            String email = null;
            String psswd = null;
            LocalDate birthdate = null;
            switch (type) {
                case "1" -> username = userData[0];
                case "2" -> email = userData[0];
                case "3" -> birthdate = LocalDate.parse(userData[0]);
                case "4" -> {
                    username = userData[0];
                    email = userData[1];
                }
                case "5" -> {
                    username = userData[0];
                    birthdate = LocalDate.parse(userData[1]);
                }
                case "6" -> {
                    email = userData[0];
                    birthdate = LocalDate.parse(userData[1]);
                }
                case "7" -> {
                    username = userData[0];
                    email = userData[1];
                    birthdate = LocalDate.parse(userData[2]);
                }
                case "8" -> {
                    psswd = userData[0];
                }
            }
            if (username != null) {
                Optional<User> user2 = userRepository.findByUsername(username);
                if (user2.isPresent()) {
                    return new ResponseEntity<>("username is already in use", HttpStatus.CONFLICT);
                }else {
                    user.get().setUsername(username);
                    changesMade = true;
                }
            }
            if (email != null) {
                Optional<User> user2 = userRepository.findByEmail(email);
                if (user2.isPresent()) {
                    return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
                }else {
                    user.get().setEmail(email);
                    changesMade = true;
                }
            }
            if (birthdate != null) {
                user.get().setAccountbirthday(birthdate);
                changesMade = true;
            }
            if (psswd != null) {
                user.get().setPassword(psswd);
                changesMade = true;
            }
            if (changesMade) {
                userRepository.save(user.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("linkAccount/{name}/{lol}@&@{region}")
    public ResponseEntity<Boolean> linkLoLAccount(@PathVariable("name") String name, @PathVariable("lol") String lol, @PathVariable("region") String region) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isPresent()) {
            user.get().setVinculatedlol(lol);
            user.get().setLolregion(region);
            userRepository.save(user.get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("unlinkAccount/{name}/{lol}")
    public ResponseEntity<Boolean> unlinkLoLAccount(@PathVariable("name") String name, @PathVariable("lol") String lol) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isPresent() && user.get().getVinculatedlol().equals(lol)) {
            user.get().setVinculatedlol(null);
            user.get().setLolregion(null);
            userRepository.save(user.get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // -------------  Html petitions  ------------- //

    @GetMapping("/htmlRequests/home/{logged}/{uid}")
    public ResponseEntity<String> login(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), false , user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/home/initialization/{logged}/{uid}")
    public ResponseEntity<String> loginInitialization(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), true , user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.loginPageAction(Boolean.parseBoolean(logged), true);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/championlist/{logged}/{uid}")
    public ResponseEntity<String> championList(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.champList(true, user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.champList(false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/passChange/{uid}")
    public ResponseEntity<String> passChange(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            String html = htmlFactory.PassChange(user.get());
            return new ResponseEntity<>(html, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/htmlRequests/summonerPage/{PUUID}/{region}/{logged}/{uid}")
    public ResponseEntity<String> summonerPage(@PathVariable("logged") String logged, @PathVariable("uid") String uid, @PathVariable("PUUID") String PUUID, @PathVariable("region") String region) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.summonerPage(true, PUUID, region, userRepository, user.get());
                if (html == null) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }else {
                    return new ResponseEntity<>(html, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.summonerPage(false, PUUID, region, userRepository);
            if (html == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else {
                return new ResponseEntity<>(html, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/htmlRequests/itemlist/{logged}/{uid}")
    public ResponseEntity<String> itemList(@PathVariable("logged") String logged, @PathVariable("uid") String uid) {
        if (Boolean.parseBoolean(logged)) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent()) {
                String html = htmlFactory.itemsPage(true, user.get());
                return new ResponseEntity<>(html, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            String html = htmlFactory.itemsPage(false);
            return new ResponseEntity<>(html, HttpStatus.OK);
        }
    }

    @GetMapping("/htmlRequests/accSettings/{uid}")
    public ResponseEntity<String> accSettings(@PathVariable("uid") String uid) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            String html = htmlFactory.accSettings(user.get());
            return new ResponseEntity<>(html, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/htmlRequests/loading")
    public ResponseEntity<String> loading() {
        String html = htmlFactory.loading();
        return new ResponseEntity<>(html, HttpStatus.OK);
    }

    // ------------- General actions  ------------- //

    @GetMapping("/browse/{username}")
    public String browse(@PathVariable("username") String username) {
        boolean finished = false;
        StringBuilder formattedHtmlComplete = new StringBuilder();
        ArrayList<Summoner> summoners = new ArrayList<>();
        for (LeagueShard region : regions) {
            CompletableFuture.supplyAsync(() -> Summoner.byName(region, username)).thenAccept(summoners::add);
        }
        while (!finished) {
            if (summoners.size() == regions.size()) {
                finished = true;
            }
        }
        for (Summoner summoner : summoners) {
            if (summoner != null) {
                String summonerImg = "https://riftstatistics.ddns.net/file/assets/summIcon/" + summoner.getProfileIconId() + ".png";
                Image image = null;
                try {
                    image = ImageIO.read(new URL(summonerImg));
                } catch (IOException e) {
                    System.out.println("Error when loading summoner " + summoner.getName() + " with region " + summoner.getPlatform().getRealmValue().toUpperCase());
                }
                if (image == null) {
                    summonerImg = "https://riftstatistics.ddns.net/file/assets/summIcon/29.png";
                }

                String summonerName = summoner.getName();
                String summonerRegion = summoner.getPlatform().getRealmValue().toUpperCase();

                Map<String, String> values = new HashMap<>();
                values.put("Img", summonerImg);
                values.put("SummName", summonerName);
                values.put("SummReg", summonerRegion);
                values.put("summID", summoner.getPUUID());
                StringSubstitutor sub = new StringSubstitutor(values);

                String htmlFragment = "<li id=\"${summID}\" class=\"browserItem\">" +
                        "<div href=\"\" class=\"browserLink\">" +
                        "<div class=\"cardContainer\">" +
                        "<div class=\"browserCard\">" +
                        "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
                        "</div>" +
                        "<div class=\"cardPhoto\">" +
                        "<img src=\"${Img}\" class=\"cardBackground\" alt=\"${Img}\">" +
                        "</div>" +
                        "</div>" +
                        "<span class=\"browserName\">" +
                        "<span class=\"browserSummName\">${SummName}</span>" +
                        "<span id=\"summoner${summID}Region\"class=\"browserSummRegion\">${SummReg}</span>" +
                        "</span>" +
                        "</div>" +
                        "</li>";

                String formattedHtml = sub.replace(htmlFragment);
                formattedHtmlComplete.append(formattedHtml);
            }
        }
        return formattedHtmlComplete.toString();
    }
}