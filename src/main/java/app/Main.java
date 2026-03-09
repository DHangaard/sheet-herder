package app;

import app.integrations.DNDClient;
import app.config.HibernateConfig;
import app.integrations.IDNDClient;
import app.services.DNDFetchingService;
import app.services.IDNDFetchingService;
import app.utils.ExecutionTimer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManagerFactory;

import java.net.http.HttpClient;

public class Main
{
    // Singleton
    private final static EntityManagerFactory ENTITY_MANAGER_FACTORY = HibernateConfig.getEntityManagerFactory();
    private final static HttpClient CLIENT = HttpClient.newHttpClient();
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args)
    {
        ExecutionTimer.start();

        IDNDClient dndClient = new DNDClient(CLIENT, OBJECT_MAPPER);
        IDNDFetchingService dndFetchingService = new DNDFetchingService(dndClient);

        dndFetchingService.fetchAllRaces().races().forEach(race -> System.out.println(race.name()));

        System.out.println("Races count: " + dndFetchingService.fetchAllRacesWithDetails().size());         // Should be 9
        System.out.println("Subraces count: " + dndFetchingService.fetchAllSubracesWithDetails().size());   // Should be 4
        System.out.println("Traits count: " + dndFetchingService.fetchAllTraitsWithDetails().size());       // Should be 38
        System.out.println("Languages count: " + dndFetchingService.fetchAllLanguagesWithDetails().size());    // Should be 16

        // dndFetchingService.fetchAllLanguagesWithDetails().forEach(language -> System.out.println(language.typicalSpeakers()));
        dndFetchingService.fetchAllRacesWithDetails().forEach(race -> System.out.println(race.size()));

        ENTITY_MANAGER_FACTORY.close();

        System.out.println(ExecutionTimer.finish());
    }
}