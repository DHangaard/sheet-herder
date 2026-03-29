package app.security.dtos;

public record LoginRequestDTO(
        String email,
        String password
)
{
}
