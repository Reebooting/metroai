package com.metroai.metroai_backend.ticket.util;

import java.io.ByteArrayOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeGenerator {

    private QrCodeGenerator() {
    }

    public static byte[] generateQrCode(
            String content
    ) {

        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            var bitMatrix =
                    qrCodeWriter.encode(
                            content,
                            BarcodeFormat.QR_CODE,
                            300,
                            300
                    );

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream
            );

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to generate QR Code",
                    ex
            );
        }
    }
}