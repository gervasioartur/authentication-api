package domain.entities.ouputs;

public class UserOutput {
    private String id;
    private String name;
    private String email;

    public UserOutput() {
    }

    public UserOutput(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }

    public UserOutput(String id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
