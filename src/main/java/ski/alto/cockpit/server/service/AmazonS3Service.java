package ski.alto.cockpit.server.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.utility.AmazonUtil;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class AmazonS3Service {


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String reportsBucket = "resort-reports";
    private static final String invoiceBucket = "alto-invoices";

    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_2).build();

    public String getAmazonS3ReportUrl(String resortName, LocalDate from, LocalDate to) {

        String result = null;

        try {
            String fileName = AmazonUtil.getReportFileName(resortName, from, to);
            String fileKey = AmazonUtil.getReportFileKey(resortName, fileName);

            result = getAmazonS3Url(fileKey, fileName, reportsBucket);

        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    public String getAmazonS3XlsxReportUrl(String resortName, LocalDate from, LocalDate to) {

        String result = null;

        try {
            String fileName = AmazonUtil.getReportXlsxFileName(resortName, from, to);
            String fileKey = AmazonUtil.getReportXlsxFileKey(resortName, fileName, from);

            result = getAmazonS3Url(fileKey, fileName, reportsBucket);

        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    public String getAmazonS3InvoiceUrl(String resortName, LocalDate createDate) {

        String result = null;

        try {

            String fileName = AmazonUtil.getInvoiceFileName(resortName, createDate);
            String fileKey = AmazonUtil.getInvoiceFileKey(resortName, fileName);

            result = getAmazonS3Url(fileKey, fileName, invoiceBucket);

        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;

    }

    public String getAmazonS3Url(String fileKey, String fileName, String bucketName) {

        String result = null;


        if (s3Client.doesObjectExist(bucketName, fileKey)) {
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 1000 * 60 * 60 * 24; //1 day
            expiration.setTime(milliSeconds);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                    bucketName, fileKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);

            /* ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
            responseHeader.setContentDisposition("attachment; filename=\""
                    + fileName + "\"");
            generatePresignedUrlRequest.setResponseHeaders(responseHeader);*/

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            result = url.toString();
        }


        return result;
    }

    public InputStream getAmazonS3ReportStream(String resortName, LocalDate from, LocalDate to) {

        InputStream result = null;

        try {
            String fileName = AmazonUtil.getReportFileName(resortName, from, to);
            String fileKey = AmazonUtil.getReportFileKey(resortName, fileName);

            result = getAmazonS3Stream(fileKey, fileName, reportsBucket);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    public InputStream getAmazonS3ReportStream(String fileName, String fileKey) {

        InputStream result = null;

        try {
            result = getAmazonS3Stream(fileKey, fileName, reportsBucket);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    public InputStream getAmazonS3Stream(String fileKey, String fileName, String bucketName) {

        InputStream result = null;

        if (s3Client.doesObjectExist(bucketName, fileKey)) {
            S3Object o = s3Client.getObject(bucketName, fileKey);
            result = o.getObjectContent();
        }

        return result;
    }
}
