package creational.builder;

public class User {

    private String name;
    private String surname;
    private String fatherName;
    private String date;
    private int age;

    private User() {

    }

    private User(String name, String surname, String fatherName, String date, int age) {
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.date = date;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getDate() {
        return date;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nSurname: " + surname + "\nFather name: " + fatherName + "\nDate: " + date + "\nAge: " + age;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private User u = null;

        private UserBuilder() {
            u = new User();
        }

        public User build() {
            return new User(u.name, u.surname, u.fatherName, u.date, u.age);
        }

        public UserBuilder setName(String name) {
            u.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            u.surname = surname;
            return this;
        }

        public UserBuilder setFatherName(String fatherName) {
            u.fatherName = fatherName;
            return this;
        }

        public UserBuilder setDate(String date) {
            u.date = date;
            return this;
        }

        public UserBuilder setAge(int age) {
            u.age = age;
            return this;
        }
    }
}
