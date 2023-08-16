package sg.edu.nus.iss.csf36_giphy_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.csf36_giphy_backend.services.GiphyService;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins="*")
public class GiphyController {

    GiphyService giphySvc;

    public GiphyController(GiphyService giphySvc) {
        this.giphySvc = giphySvc;
    }
    
    @GetMapping(path = "/get")
    public List<String> getGiphy(@RequestParam String name) {
        return giphySvc.getGiphyByName(name);
    }
}
