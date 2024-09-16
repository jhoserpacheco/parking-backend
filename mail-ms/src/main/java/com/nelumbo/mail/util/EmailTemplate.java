package com.nelumbo.mail.util;

public class EmailTemplate {

    public static String generateBodyEmailParking(String parkingName, String vehiclePlate) {
        return "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Vehicle plate registered " + vehiclePlate + "!</h2>"
                + "<p style=\"font-size: 16px;\">Thank you for choosing us, the best parking lot. Nelumbo Parking</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\"> " + parkingName + ": " + vehiclePlate+ "</h3>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
