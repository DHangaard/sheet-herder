package app.integrations;

import app.dtos.DNDRaceDTO;
import app.dtos.DNDRaceResultDTO;
import app.exceptions.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DNDClient
{
    private HttpClient client;
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "https://www.dnd5eapi.co/api/2014%s";
    private static final String ALL_RACES_URL = String.format(BASE_URL, "/races");


    public DNDClient(HttpClient client, ObjectMapper objectMapper)
    {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public DNDRaceResultDTO getAllRaces()
    {
        HttpRequest request = buildRequest(ALL_RACES_URL);
        try
        {
            String response = getResponse(request);
            DNDRaceResultDTO dndRaceResultDTO = objectMapper.readValue(response, DNDRaceResultDTO.class);
            return dndRaceResultDTO;
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    // TODO Remove or refactor
    private DNDRaceDTO mapRaceDto(String json)
    {
        DNDRaceDTO dndRaceDTO = null;

        try
        {
            dndRaceDTO = objectMapper.readValue(json, DNDRaceDTO.class);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
        return dndRaceDTO;
    }

    private String getResponse(HttpRequest request) throws IOException, InterruptedException
    {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
        {
            throw new ApiException(response.statusCode(), "dnd5eapi returned error code: " + response.statusCode());
        }
        return response.body();
    }

    private HttpRequest buildRequest(String url)
    {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "applicatio/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }
}
