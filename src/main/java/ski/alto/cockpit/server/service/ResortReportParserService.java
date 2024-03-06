package ski.alto.cockpit.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.CockpitResortReportDetailsDTO;
import ski.alto.cockpit.server.model.CockpitResortReports;
import ski.alto.cockpit.server.model.ResortReportDTO;
import ski.alto.cockpit.server.model.Resorts;
import ski.alto.cockpit.server.repository.CockpitResortReportsRepository;
import ski.alto.cockpit.server.repository.ResortsRepository;
import ski.alto.cockpit.server.service.AmazonS3Service;
import ski.alto.cockpit.server.utility.AmazonUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class ResortReportParserService {

    @Autowired
    AmazonS3Service amazonS3Service;

    @Autowired
    ResortsRepository resortsRepository;

    @Autowired
    CockpitResortReportsRepository cockpitResortReportsRepository;

    /*private static final int CARD_NUMBER = 0;
    private static final int STRIPE_TRANSACTION_REFERENCE = 1;
    private static final int TRANSACTION_DATE = 2;
    private static final int TRANSACTION_TIME = 3;
    private static final int PRODUCT_NAME = 7;
    private static final int TRANSACTION_AMOUNT_GROSS = 11;
    private static final int TRANSACTION_AMOUNT_NET = 15;
    private static final int REPORT_ID = 19;*/

    private static final int   CARD_NUMBER  =  0  ;
    private static final int   STRIPE_TRANSACTION_REFERENCE  =  1  ;
    private static final int   TRANSACTION_DATE  =  2  ;
    private static final int   FIRST_ENTRY_TIME  =  3  ;
    private static final int   LAST_ENTRY_TIME  =  4  ;
    private static final int   DTA_ORDER_NUMBER  =  5  ;
    private static final int   DTA_CONFIRMATION_NUMBER  =  6  ;
    private static final int   PRODUCT_NAME  =  7  ;
    private static final int   PRODUCT_ID  =  8  ;
    private static final int   CATEGORY  =  9  ;
    private static final int   CATEGORY_ID  =  10  ;
    private static final int   TRANSACTION_AMOUNT_GROSS  =  11  ;
    private static final int   CREDIT_CARD_PROCESSING_FEES  =  12  ;
    private static final int   ALTOSKI_PROCESSING_FEES  =  13  ;
    private static final int   TOTAL_FEES  =  14  ;
    private static final int   TRANSACTION_AMOUNT_NET  =  15  ;
    private static final int   CHARGE_ITEM_ID  =  16  ;
    private static final int   CHARGE_ID  =  17  ;
    private static final int   VISIT_ID  =  18  ;
    private static final int   REPORT_ID  =  19  ;



    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void parseS3ReportsAllResorts(LocalDate startDate, String ownership) {

        List<Resorts> resorts = resortsRepository.findAll();
        for (Resorts resort: resorts) {
            logger.info("Starting reports parsing for resort " + resort.getName() + " starting from " + startDate);
            parseS3Report(resort, startDate, ownership);
            logger.info("Finished reports parsing for resort " + resort.getName());
        }
    }

    public void parseS3ReportsByResortName(String resortName, LocalDate startDate, String ownership) {
        List<Resorts> resorts = resortsRepository.findByName(resortName);
        if (resorts != null && resorts.size() > 0) {
            parseS3Report(resorts.get(0), startDate, ownership);
        }
    }

    public void parseS3Report(Resorts resort, LocalDate startDate, String ownership) {
        parseS3Report(resort, startDate, false, ownership);
    }

    public void parseS3Report(Resorts resort, LocalDate startDate, boolean parseSingleMonth, String ownership) {

        LocalDate parsingLimitDate = LocalDate.now().withDayOfMonth(1);
        if (parseSingleMonth) {
            parsingLimitDate = startDate.plusMonths(1);
        }

        while (startDate.isBefore(parsingLimitDate)) {

            logger.info("Looking for reports for resort " + resort.getName() + " at " + startDate);

            try {
                InputStream is = amazonS3Service.getAmazonS3ReportStream(resort.getName(),
                        startDate.withDayOfMonth(1),
                        startDate.with(lastDayOfMonth()), ownership);

                if (is != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = reader.readLine(); //skip header
                    while (line != null) {
                        line = reader.readLine();
                        if (line != null) {
                            logger.info(line);
                            String[] fields = line.split(",", 22);

                            boolean isNewReportFormat = false;
                            if (fields.length > 20) {
                                isNewReportFormat = true;
                            }

                            CockpitResortReports report = new CockpitResortReports();

                            // field names are based on the original order of elements
                            // these have changed, so field names no longer match their content
                            if (isNewReportFormat) {
                                report.setProductName(fields[9])
                                        .setResortId(resort.getId())
                                        .setTransactionDate(LocalDateTime.parse(fields[TRANSACTION_DATE] + " " + fields[5],
                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                                        .setTransactionAmountGross(Double.parseDouble(fields[13]))
                                        .setTransactionAmountNet(Double.parseDouble(fields[17]));
                                if (fields[21] != null && !fields[21].isEmpty()) {
                                    report.setReportId(Integer.valueOf(fields[21]));
                                }
                            } else {
                                report.setProductName(fields[PRODUCT_NAME])
                                        .setResortId(resort.getId())
                                        .setTransactionDate(LocalDateTime.parse(fields[TRANSACTION_DATE] + " " + fields[FIRST_ENTRY_TIME],
                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                                        .setTransactionAmountGross(Double.parseDouble(fields[TRANSACTION_AMOUNT_GROSS]))
                                        .setTransactionAmountNet(Double.parseDouble(fields[TRANSACTION_AMOUNT_NET]));
                                if (fields[REPORT_ID] != null && !fields[REPORT_ID].isEmpty()) {
                                    report.setReportId(Integer.valueOf(fields[REPORT_ID]));
                                }
                            }

                            cockpitResortReportsRepository.save(report);
                            logger.info("Created report for " + resort.getName() + ", at " + startDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

            startDate = startDate.withDayOfMonth(1).plusMonths(1);
        }
    }

    public List<CockpitResortReportDetailsDTO> parseS3ReportDetails(Resorts resort, String resortName, String fileName
    		, String ownership) {

        logger.info("Parsing report details for resort " + resort.getName());

        List<CockpitResortReportDetailsDTO> result = new ArrayList<>();

        String fileKey = AmazonUtil.getReportFileKey(resortName, fileName);

        try {
            InputStream is = amazonS3Service.getAmazonS3ReportStream(fileName, fileKey, ownership);

            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line = reader.readLine(); //skip header
                while (line != null) {
                    line = reader.readLine();
                    if (line != null) {
                        logger.info(line);
                        String[] fields = line.split(",", 20);

                        CockpitResortReportDetailsDTO details = new CockpitResortReportDetailsDTO(
                                fields[CARD_NUMBER],
                                fields[STRIPE_TRANSACTION_REFERENCE],
                                LocalDate.parse(fields[TRANSACTION_DATE], DateTimeFormatter.ISO_DATE),
                                LocalTime.parse(fields[FIRST_ENTRY_TIME], DateTimeFormatter.ISO_TIME),
                                LocalTime.parse(fields[LAST_ENTRY_TIME], DateTimeFormatter.ISO_TIME),
                                fields[DTA_ORDER_NUMBER],
                                fields[DTA_CONFIRMATION_NUMBER],
                                fields[PRODUCT_NAME],
                                fields[PRODUCT_ID],
                                fields[CATEGORY],
                                fields[CATEGORY_ID],
                                Double.valueOf(fields[TRANSACTION_AMOUNT_GROSS]),
                                Double.valueOf(fields[CREDIT_CARD_PROCESSING_FEES]),
                                Double.valueOf(fields[ALTOSKI_PROCESSING_FEES]),
                                Double.valueOf(fields[TOTAL_FEES]),
                                Double.valueOf(fields[TRANSACTION_AMOUNT_NET]),
                                Integer.valueOf(fields[CHARGE_ITEM_ID]),
                                Integer.valueOf(fields[CHARGE_ID]),
                                Integer.valueOf(fields[VISIT_ID]),
                                (fields[REPORT_ID] != null && !fields[REPORT_ID].isEmpty()) ? Integer.valueOf(fields[REPORT_ID]) : null
                        );

                        result.add(details);
                        logger.info("Created report for " + resort.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    public List<ResortReportDTO> getResortReportList(String resortName, Integer numberOfMonthsToDisplay, String ownership) {

        List<ResortReportDTO> result = new ArrayList<>();

        List<Resorts> resorts = resortsRepository.findByName(resortName);
        if (resorts != null && !resorts.isEmpty()) {
            Resorts resort = resorts.get(0);

            LocalDate now = LocalDate.now();
            for (int i = 0; i < numberOfMonthsToDisplay; i++) {
                now = now.minusMonths(1);

                List<CockpitResortReports> resortReports = cockpitResortReportsRepository.findByResortIdAndTransactionDateBetween(
                        resort.getId(),
                        now.withDayOfMonth(1).atStartOfDay(),
                        now.with(lastDayOfMonth()).plusDays(1).atStartOfDay());

                if (resortReports == null ||
                        resortReports.size() == 0) {
                    // reports were not parsed, parsing now
                    parseS3Report(resort, now.withDayOfMonth(1), true, ownership);
                    //try again
                    resortReports = cockpitResortReportsRepository.findByResortIdAndTransactionDateBetween(
                            resort.getId(),
                            now.withDayOfMonth(1).atStartOfDay(),
                            now.with(lastDayOfMonth()).plusDays(1).atStartOfDay());
                }

                Integer totalSoldProducts = 0;
                Double totalIncomeGross = 0.0;
                Double totalIncomeNet = 0.0;
                for (CockpitResortReports resortReport: resortReports) {
                    totalSoldProducts++;
                    totalIncomeGross += resortReport.getTransactionAmountGross();
                    totalIncomeNet += resortReport.getTransactionAmountNet();
                }

                ResortReportDTO report = new ResortReportDTO(
                        now.getMonth().name() + " " + now.getYear(),
                        amazonS3Service.getAmazonS3XlsxReportUrl(
                                resortName,
                                now.withDayOfMonth(1),
                                now.with(lastDayOfMonth()), ownership),
                        amazonS3Service.getAmazonS3InvoiceUrl(
                                resortName,
                                now.withDayOfMonth(1)),
                        totalSoldProducts,
                        totalIncomeGross,
                        totalIncomeNet
                );
                result.add(report);
            }

        }
        return result;
    }
}
