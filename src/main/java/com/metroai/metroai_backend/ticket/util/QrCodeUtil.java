package com.metroai.metroai_backend.ticket.util;

public class QrCodeUtil {

    private QrCodeUtil() {
    }

    public static String generateQrContent(
            Long ticketId
    ) {

        return "METROAI-TICKET-" + ticketId;
    }
}