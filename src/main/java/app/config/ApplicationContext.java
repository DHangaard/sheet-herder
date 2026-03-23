package app.config;

import app.controllers.*;
import app.dtos.dnd.DNDLanguageDetailDTO;
import app.dtos.dnd.DNDRaceDetailDTO;
import app.dtos.dnd.DNDSubraceDetailDTO;
import app.dtos.dnd.DNDTraitDetailDTO;
import app.dtos.reference.LanguageDTO;
import app.dtos.reference.RaceDTO;
import app.dtos.reference.SubraceDTO;
import app.dtos.reference.TraitDTO;
import app.integrations.DNDClient;
import app.integrations.IDNDClient;
import app.persistence.daos.implementations.LanguageDAO;
import app.persistence.daos.implementations.RaceDAO;
import app.persistence.daos.implementations.SubraceDAO;
import app.persistence.daos.implementations.TraitDAO;
import app.persistence.daos.interfaces.IReferenceDAO;
import app.persistence.entities.reference.Language;
import app.persistence.entities.reference.Race;
import app.persistence.entities.reference.Subrace;
import app.persistence.entities.reference.Trait;
import app.services.reference.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;

import java.net.http.HttpClient;

public final class ApplicationContext
{
    private static ApplicationContext instance;
    private final EntityManagerFactory entityManagerFactory;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final IDNDClient dndClient;
    private final IDNDFetchingService dndFetchingService;

    private final IReferenceDAO<Language> languageDAO;
    private final IReferenceDAO<Trait> traitDAO;
    private final IReferenceDAO<Race> raceDAO;
    private final IReferenceDAO<Subrace> subraceDAO;

    private final IReferenceDataService<DNDLanguageDetailDTO, LanguageDTO> languageService;
    private final IReferenceDataService<DNDTraitDetailDTO, TraitDTO> traitService;
    private final IReferenceDataService<DNDRaceDetailDTO, RaceDTO> raceService;
    private final IReferenceDataService<DNDSubraceDetailDTO, SubraceDTO> subraceService;

    @Getter
    private final IReferenceController languageController;

    @Getter
    private final IReferenceController traitController;

    @Getter
    private final IReferenceController raceController;

    @Getter
    private final IReferenceController subraceController;


    public ApplicationContext(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;

        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        this.dndClient = new DNDClient(httpClient, objectMapper);
        this.dndFetchingService = new DNDFetchingService(dndClient);

        this.languageDAO = new LanguageDAO(entityManagerFactory);
        this.traitDAO = new TraitDAO(entityManagerFactory);
        this.raceDAO = new RaceDAO(entityManagerFactory);
        this.subraceDAO = new SubraceDAO(entityManagerFactory);

        this.languageService = new LanguageService(languageDAO);
        this.traitService = new TraitService(traitDAO);
        this.raceService = new RaceService(raceDAO, languageDAO, traitDAO);
        this.subraceService = new SubraceService(subraceDAO, raceDAO, traitDAO);

        this.languageController = new LanguageController(languageService);
        this.traitController = new TraitController(traitService);
        this.raceController = new RaceController(raceService);
        this.subraceController = new SubraceController(subraceService);
    }

    public static ApplicationContext getInstance()
    {
        if (instance == null)
        {
            instance = new ApplicationContext(HibernateConfig.getEntityManagerFactory());
        }
        return instance;
    }

    public static ApplicationContext getTestInstance(EntityManagerFactory entityManagerFactory)
    {
        instance = new ApplicationContext(entityManagerFactory);
        return instance;
    }
}
