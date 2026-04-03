package app.services.domain.implementations;

import app.exceptions.NotFoundException;
import app.persistence.daos.domain.interfaces.IUserDAO;
import app.persistence.entities.domain.User;
import app.services.domain.interfaces.IUserService;
import app.utils.Validator;

public class UserService implements IUserService
{
    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @Override
    public User getByUsername(String username)
    {
        Validator.notNullOrBlank(username);
        return userDAO.getByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }
}
