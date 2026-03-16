package app.config;

import app.entities.Campaign;
import app.entities.CampaignMembership;
import app.entities.CharacterSheet;
import app.entities.User;
import app.entities.reference.Language;
import app.entities.reference.Race;
import app.entities.reference.Subrace;
import app.entities.reference.Trait;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(CharacterSheet.class);
        configuration.addAnnotatedClass(Campaign.class);
        configuration.addAnnotatedClass(CampaignMembership.class);
        configuration.addAnnotatedClass(Race.class);
        configuration.addAnnotatedClass(Subrace.class);
        configuration.addAnnotatedClass(Language.class);
        configuration.addAnnotatedClass(Trait.class);
    }
}