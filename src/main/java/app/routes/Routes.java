package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes
{
    private final LanguageRoute languageRoute;
    private final TraitRoute traitRoute;
    private final RaceRoute raceRoute;
    private final SubraceRoute subraceRoute;

    public Routes(LanguageRoute languageRoute, TraitRoute traitRoute, RaceRoute raceRoute, SubraceRoute subraceRoute)
    {
        this.languageRoute = languageRoute;
        this.traitRoute = traitRoute;
        this.raceRoute = raceRoute;
        this.subraceRoute = subraceRoute;
    }

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/api/v1", () ->
            {
                languageRoute.getRoutes().addEndpoints();
                traitRoute.getRoutes().addEndpoints();
                raceRoute.getRoutes().addEndpoints();
                subraceRoute.getRoutes().addEndpoints();
            });
        };
    }
}
