package com.nelumbo.user.utils;

public class Constants {
    private static final String UTILITY_CLASS = "Utility class";
    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
    public static class Message{
        private Message() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
        /* users */
        public static final String USER_NOT_FOUND  = "User not found";
        public static final String USER_CHANGE_ROL_SUCCESS  = "User change role success";
        public static final String IS_ADMIN_DEFAULT = "The default admin role cannot be changed.";

        /* auth */
        public static final String ACCOUNT_ALREADY_VERIFIED  = "Account is already verified";
        public static final String ACCOUNT_NOT_VERIFIED = "Account not verified. Please verify your account.";
        public static final String ACCOUNT_VERIFIED = "Account verified successfully";
        public static final String RESEND_CODE = "Verification code sent";
        public static final String CODE_EXPIRED = "Verification code has expired";
        public static final String CODE_INVALID = "Invalid verification code";
        public static final String TOKEN_INVALID = "Invalid token";
    }

    public static class ValidationMessages {
        private ValidationMessages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
        public static final String EMAIL_INVALID = "Email must be valid";
        public static final String EMAIL_REQUIRED = "Email is required";
        public static final String PASSWORD_REQUIRED = "Password is required";
        public static final String ID_ROL_REQUIRED = "Id rol is required";
        public static final String PASSWORD_SIZE = "Password must be at least 8 characters long";
        public static final String FULL_NAME_REQUIRED = "Full name is required";
        public static final String VERIFICATION_CODE_SIZE = "Verification code must be exactly 6 digits";
        public static final String NO_EXIST = "This is not the web page you are looking for.";
        public static final String NUMBER_NEGATIVE = "The number must be a positive number.";

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
