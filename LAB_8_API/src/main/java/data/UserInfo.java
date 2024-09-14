package data;

public class UserInfo {
    private String name;
    private String gender;
    private int age;
    private String job;

    public UserInfo() {}

    public UserInfo(String name, String gender, int age, String job) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.job = job;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
}