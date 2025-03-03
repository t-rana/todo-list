package org.tushar.todolist.service.jwt;

import org.jetbrains.annotations.NotNull;
import org.tushar.todolist.exceptions.ServiceException;

import java.util.Date;

public interface JwtService {
    String generateToken(String userName);

    String extractUserName(@NotNull String token);

    Date extractExpirationDate(@NotNull String token);

    boolean isTokenValid(@NotNull String token, @NotNull String userName);

    String refreshToken(@NotNull String token) throws ServiceException;
}
