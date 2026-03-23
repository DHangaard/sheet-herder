package app.routes;

import app.controllers.IReferenceController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class SubraceRoute
{
    private final IReferenceController subraceController;

    public SubraceRoute(IReferenceController subraceController)
    {
        this.subraceController = subraceController;
    }

    protected EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/subraces", () ->
            {
                get(subraceController::getAll);
                get("/id/{id}", subraceController::getById);
                get("/name/{name}", subraceController::getByName);
            });
        };
    }
}
