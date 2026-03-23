package app.persistence.entities.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Password will be added when authentication is implemented

    public User(String email, String username)
    {
        this.email = email;
        this.username = username;
    }

    @PrePersist
    protected void onCreate()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.email = this.email.toLowerCase().trim();
        this.username = normalizeName(this.username);
    }

    @PreUpdate
    protected void onUpdate()
    {
        this.updatedAt = LocalDateTime.now();
        this.email = this.email.toLowerCase().trim();
        this.username = normalizeName(this.username);
    }

    private String normalizeName(String name)
    {
        if (name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        return name.trim();
    }
}


