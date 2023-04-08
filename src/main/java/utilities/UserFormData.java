package utilities;

import com.github.javafaker.Faker;

public class UserFormData {

    private final String fullName;
    private final String email;
    private final String friendEmail;
    private final String message;
    private final String firstName;
    private final String lastName;
    private final String oldPassword;
    private final String newPassword;
    private final String address;
    private final String city;
    private final String postalCode;
    private final String phoneNumber;

    public UserFormData() {
        Faker faker = new Faker();
        fullName = faker.name().fullName();
        email = faker.internet().safeEmailAddress();
        friendEmail = faker.internet().safeEmailAddress();
        message = faker.gameOfThrones().quote();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        oldPassword = faker.number().digits(8);
        newPassword = faker.number().digits(9);
        address = faker.address().streetAddress();
        city = faker.address().city();
        postalCode = faker.number().digits(5);
        phoneNumber = faker.phoneNumber().cellPhone();
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }

    public String getEmail() {
        return email;
    }
    public String getFriendEmail() {
        return friendEmail;
    }
    public String getMessage() {
        return message;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }
    public String getPostalCode()
    {
        return postalCode;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

}
