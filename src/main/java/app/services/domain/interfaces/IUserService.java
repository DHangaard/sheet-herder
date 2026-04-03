package app.services.domain.interfaces;

import app.persistence.entities.domain.User;

public interface IUserService
{
    User getByUsername(String username);
}
