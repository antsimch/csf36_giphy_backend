package sg.edu.nus.iss.csf36_giphy_backend.services;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;

@Service
public class GiphyService {

    @Value("${giphy.api.key}")
    private String key;

    private String apiUrl = "https://api.giphy.com/v1/gifs/search";

    public List<String> getGiphyByName(String name) {

        System.out.println("\n\n" + ">>>> key: " + key + "\n\n");
        System.out.println("\n\n" + ">>>> name: " + name + "\n\n");

        String url = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("api_key", key)
                .queryParam("q", name)
                .toUriString();

        System.out.println("\n\n" + ">>>> api_url: " + url + "\n\n");
        
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();

        try {
            ResponseEntity<String> resp = 
                    template.exchange(req, String.class);

            JsonArray arr = Json.createReader(new StringReader(resp.getBody()))
                    .readObject()
                    .getJsonArray("data");

            return arr.stream()
                    .map(data -> data.asJsonObject())
                    .map(data -> data.getJsonObject("images")
                            .getJsonObject("fixed_height")
                            .getString("url"))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
        }

        return null;
    }
}
