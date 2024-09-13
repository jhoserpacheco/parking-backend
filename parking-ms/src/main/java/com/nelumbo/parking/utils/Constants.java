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

    }

    public static class ValidationMessages {
        private ValidationMessages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
        public static final String EMAIL_INVALID = "Email must be valid";
        public static final String EMAIL_REQUIRED = "Email is required";
        public static final String PASSWORD_REQUIRED = "Password is required";
        public static final String PASSWORD_SIZE = "Password must be at least 8 characters long";
        public static final String FULL_NAME_REQUIRED = "Full name is required";
        public static final String VERIFICATION_CODE_SIZE = "Verification code must be exactly 6 digits";
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
