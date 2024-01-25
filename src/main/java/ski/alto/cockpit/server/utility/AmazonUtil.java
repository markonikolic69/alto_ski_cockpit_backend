package ski.alto.cockpit.server.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AmazonUtil {

    public static String getReportFileName(String resortName, LocalDate from, LocalDate to) {

        String resortFolderName = resortName.toLowerCase().replaceAll(" ", "-");

        StringBuffer fileNameBuffer = new StringBuffer();
        fileNameBuffer.append(from.format(DateTimeFormatter.ISO_DATE));
        fileNameBuffer.append("-");
        fileNameBuffer.append(to.format(DateTimeFormatter.ISO_DATE));
        fileNameBuffer.append("-");
        fileNameBuffer.append(resortFolderName);
        fileNameBuffer.append(".csv");

        return fileNameBuffer.toString();
    }

    public static String getReportXlsxFileName(String resortName, LocalDate from, LocalDate to) {

        String resortFolderName = resortName.toLowerCase().replaceAll(" ", "-");

        StringBuffer fileNameBuffer = new StringBuffer();
        fileNameBuffer.append(resortFolderName);
        fileNameBuffer.append("-");
        fileNameBuffer.append(from.format(DateTimeFormatter.ISO_DATE));
        fileNameBuffer.append("-");
        fileNameBuffer.append(to.format(DateTimeFormatter.ISO_DATE));
        fileNameBuffer.append("-");
        fileNameBuffer.append(resortFolderName);
        fileNameBuffer.append(".xlsx");

        return fileNameBuffer.toString();
    }

    public static String getReportFileKey(String resortName, String fileName) {

        String resortFolderName = resortName.toLowerCase().replaceAll(" ", "-");

        StringBuffer fileKeyBuffer = new StringBuffer();
        fileKeyBuffer.append("reports");
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(resortFolderName);
        fileKeyBuffer.append("/");
        fileKeyBuffer.append("csv");
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(fileName);

        System.out.println(fileKeyBuffer.toString());

        return fileKeyBuffer.toString();
    }

    public static String getReportXlsxFileKey(String resortName, String fileName, LocalDate from) {

        String resortFolderName = resortName.toLowerCase().replaceAll(" ", "-");

        StringBuffer fileKeyBuffer = new StringBuffer();
        fileKeyBuffer.append("reports");
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(resortFolderName);
        fileKeyBuffer.append("/");
        fileKeyBuffer.append("xlsx");
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(from.getYear());
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(fileName);

        System.out.println(fileKeyBuffer.toString());

        return fileKeyBuffer.toString();
    }

    public static String getInvoiceFileName(String resortName, LocalDate createDate) {

        String resortFolderName = resortName.toLowerCase().replaceAll(" ", "-");

        StringBuffer fileNameBuffer = new StringBuffer();
        fileNameBuffer.append(resortFolderName);
        fileNameBuffer.append("-");
        fileNameBuffer.append(createDate.format(DateTimeFormatter.ISO_DATE));
        fileNameBuffer.append(".pdf");

        return fileNameBuffer.toString();
    }

    public static String getInvoiceFileKey(String resortName, String fileName) {

        StringBuffer fileKeyBuffer = new StringBuffer();
        fileKeyBuffer.append("resorts");
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(resortName);
        fileKeyBuffer.append("/");
        fileKeyBuffer.append(fileName);

        return fileKeyBuffer.toString();
    }

}
