package app.security.routes;

import app.security.controllers.ISecurityController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class SecurityRoutes
{
    private final ISecurityController securityController;

    public SecurityRoutes(ISecurityController securityController)
    {
        this.securityController = securityController;
    }

    public EndpointGroup getRoutes()
    {
        return () -> path("/security", () ->
        {

        });
    }
}
