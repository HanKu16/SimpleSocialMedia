package org.bd.repository;

import org.bd.entity.User;
import java.sql.*;
import java.util.Optional;

public class DbUsersRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbUsersRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Optional<User> findById(String id) {
        String sql = "SELECT user_id, password FROM users WHERE user_id = ?;";
        Optional<User> optionalUser = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("user_id"), rs.getString("password"));
                    optionalUser = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

    public Optional<String> add(User newUser) {
        String sql = "INSERT INTO users (user_id, password) VALUES (?, ?);";
        Optional<String> optionalUserId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, newUser.getUserId());
            stmt.setString(2, newUser.getPassword());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalUserId = Optional.of(
                                rs.getString("user_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUserId;
    }

    public Optional<User> update(String currentUserId, User updatedUser) {
        String sql = "UPDATE users SET user_id = ?, password = ? WHERE id = ?";
        Optional<User> optionalUser = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedUser.getUserId());
            stmt.setString(2, updatedUser.getPassword());
            stmt.setString(3, currentUserId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalUser = Optional.of(updatedUser);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

    public boolean deleteById(String id) {
        String sql = "DELETE FROM users WHERE user_id = ?;";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
