import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String name;
    private String job;
    private Integer id; // jsonplaceholder trả id dạng số
    private String createdAt;
    private String updatedAt;

    public UserResponse() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}