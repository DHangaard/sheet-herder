package app.routes;

import app.controllers.IReferenceController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;

public class RaceRoute
{
    private final IReferenceController raceController;

    public RaceRoute(IReferenceController raceController)
    {
        this.raceController = raceController;
    }

    protected EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/races", () ->
            {
                get(raceController::getAll);
                get("/id/{id}", raceController::getById);
                get("/name/{name}", raceController::getByName);
            });
        };
    }
}
