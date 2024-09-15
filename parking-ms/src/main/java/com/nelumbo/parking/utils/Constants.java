package com.nelumbo.parking.utils;

public class Constants {
    private static final String UTILITY_CLASS = "Utility class";
    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public enum rol {
        SOCIO,
        ADMIN,
        USER
    }

    public static class Message{
        private Message() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
        /* Vehicle */
        public static final String VEHICLE_NOT_FOUND = "Vehicle not found";

        /* User */
        public static final String USER_NOT_FOUND = "User not found";
        public static final String USER_NO_REQUIRED_ROLE = "User does not have the required role";

        /* Parking */
        public static final String PARKING_NOT_FOUND = "Parking not found";
        public static final String PARKING_MAX_CAPACITY_FAILED = "The maximum capacity cannot be less than the number of active vehicles";
        public static final String PARKING_DELETE_FAILED = "The parking could not be deleted because there are cars in the parking lot";
        public static final String VEHICLE_NO_REGISTER_PARKING = "Vehicle does not have a registered parking";
        public static final String REGISTER_ENTRY = "Registration entry";
        public static final String REGISTER_EXIT = "Registration exit";
        public static final String REGISTER_ENTRY_FAILED = "This vehicle is already registered in another parking. Please exit first.";
        public static final String PARKING_UNAUTHORIZED = "Unauthorized";
    }

    public static class ValidationMessages {
        private ValidationMessages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
        public static final String NAME_REQUIRED = "The name is required.";
        public static final String MAX_CAPACITY_REQUIRED = "The maximum capacity is required.";
        public static final String MAX_CAPACITY_POSITIVE = "The maximum capacity must be a positive number.";
        public static final String COST_HOUR_REQUIRED = "The cost per hour is required.";
        public static final String COST_HOUR_POSITIVE = "The cost per hour must be a positive value.";
        public static final String DIRECTION_REQUIRED = "The direction is required.";
        public static final String EMAIL_REQUIRED = "The email of the user is required.";
        public static final String EMAIL_FORMAT_INVALID = "The email format is invalid.";
        public static final String VEHICLE_PLATE_REQUIRED = "Vehicle plate is required.";
        public static final String MODEL_REQUIRED = "Model is required.";
    }

    public static class EmailTemplate{

        public static String generateVerificationEmail(String fullName, String verificationCode) {
            return "<html>"
                    + "<body style=\"font-family: Arial, sans-serif;\">"
                    + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                    + "<h2 style=\"color: #333;\">Welcome " + fullName + "!</h2>"
                    + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                    + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                    + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                    + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
        }

        public enum StatusEmail {
            PROCESSING,
            SENT,
            ERROR;
        }
    }
}
