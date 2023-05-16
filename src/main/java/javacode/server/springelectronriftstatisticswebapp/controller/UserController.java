package javacode.server.springelectronriftstatisticswebapp.controller;


import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatisticswebapp.MailSender.EmailsSender;
import javacode.server.springelectronriftstatisticswebapp.SecretFile;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import javacode.server.springelectronriftstatisticswebapp.repository.UserRepository;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.utils.SummonerCrawler;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.raw.DDragonAPI;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    final R4J r4J = new R4J(SecretFile.CREDS);
    DDragonAPI api = r4J.getDDragonAPI();
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

//    @GetMapping("/all")
//    public List<User> list() {
//    }

    // ------------- Ping  ------------- //

    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    // ------------- Account actions  ------------- //

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
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> user2 = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>("username is already in use", HttpStatus.CONFLICT);
        } else if (user2.isPresent()) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
        } else {
            try {
                User newUser = new User(username, password, email);
                userRepository.save(newUser);
                return new ResponseEntity<>(newUser.getId(), HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("users/actions/profileimgupdt/{uid}")
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
            String html = htmlFactory.getVerificationConfirm();
            return new ResponseEntity<>(html, HttpStatus.OK);
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

    // ------------- Html petitions  ------------- //

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

    // ------------- General actions  ------------- //

    @GetMapping("/browse/{username}")
    public String browse(@PathVariable("username") String username) {
        StringBuilder formattedHtmlComplete = new StringBuilder();
        ArrayList<Summoner> summoners = new ArrayList<>();
        for (LeagueShard region : regions) {
            Summoner sum = Summoner.byName(region, username);
            if (sum != null) {
                summoners.add(sum);
            }
        }

        for (Summoner summoner: summoners) {
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
        return formattedHtmlComplete.toString();
    }
}