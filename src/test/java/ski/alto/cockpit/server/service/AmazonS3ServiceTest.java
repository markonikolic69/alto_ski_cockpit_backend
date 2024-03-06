package ski.alto.cockpit.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AmazonS3ServiceTest {

    @Autowired
    AmazonS3Service amazonS3Service;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Test
    void getAmazonS3ReportUrl() {
        String url = amazonS3Service.getAmazonS3ReportUrl("Madonna di Campiglio",
                LocalDate.parse("2021-12-01"),
                LocalDate.parse("2021-12-31"), "SKICLUB_GB");
        logger.info(url);
    }

    @Test
    void getAmazonS3InvoiceUrl() {
        String url = amazonS3Service.getAmazonS3InvoiceUrl("Madonna di Campiglio",
                LocalDate.parse("2021-12-01"));
        logger.info(url);
    }
}