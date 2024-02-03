package creational.builder;

public class Main {

    public static void main(String[] args) {
        User u = User.builder()
                .setName("James")
                .setSurname("Gosling")
                .setDate("19.05.1955")
                .build();

        System.out.println(u);

    }
}
