package cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Visitor {

    private int id;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDateTime created;

    public Visitor() {
    }

    public Visitor(int id, String userName, String password, String email, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Visitor(int id, String userName, String password, String email, String phoneNumber, LocalDateTime created) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visitor visitor = (Visitor) o;
        return id == visitor.id && Objects.equals(email, visitor.email) && Objects.equals(phoneNumber, visitor.phoneNumber) && Objects.equals(created, visitor.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber, created);
    }
}
