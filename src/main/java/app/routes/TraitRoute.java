package app.routes;

import app.controllers.IReferenceController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class TraitRoute
{
    private final IReferenceController traitController;

    public TraitRoute(IReferenceController traitController)
    {
        this.traitController = traitController;
    }

    protected EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/traits", () ->
            {
                get(traitController::getAll);
                get("/id/{id}", traitController::getById);
                get("/name/{name}", traitController::getByName);
            });
        };
    }
}
