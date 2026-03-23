package app.routes;

import app.controllers.IReferenceController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class LanguageRoute
{
    private final IReferenceController languageController;

    public LanguageRoute(IReferenceController languageController)
    {
        this.languageController = languageController;
    }

    protected EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/languages", () ->
            {
                get(languageController::getAll);
                get("/id/{id}", languageController::getById);
                get("/name/{name}", languageController::getByName);
            });
        };
    }
}
