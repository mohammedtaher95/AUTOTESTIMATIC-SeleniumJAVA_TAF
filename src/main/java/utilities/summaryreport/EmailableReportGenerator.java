package utilities.summaryreport;

import com.google.common.io.Files;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.testng.ITestResult;
import utilities.LoggingManager;

public class EmailableReportGenerator {

    private static final List<ITestResult> passedTests = new ArrayList<>();
    private static final List<ITestResult> failedTests = new ArrayList<>();
    private static final List<ITestResult> skippedTests = new ArrayList<>();

    private EmailableReportGenerator() {

    }

    public static void addPassedTest(ITestResult result) {
        passedTests.add(result);
    }

    public static void addFailedTest(ITestResult result) {
        failedTests.add(result);
    }
    public static void addskippedTest(ITestResult result) {
        skippedTests.add(result);
    }


    public static void generateReportAndSendEmail() {
//        String recipientEmail, String senderEmail,
//              String senderPassword
        String logo = null;
        try {
            logo = String.valueOf(Files.readLines(new File("src/main/java/utilities/summaryreport/logo.txt"),
                  Charset.defaultCharset()));
            logo = logo.replace("[","").replace("]","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String report = "<html><body>";
        int passed = 0;
        int failed = 0;
        int skipped = 0;
        int total = 0;

        for (ITestResult result : passedTests) {
            passed++;
            total++;
        }
        for (ITestResult result : failedTests) {
            failed++;
            total++;
        }
        for (ITestResult result : skippedTests) {
            skipped++;
            total++;
        }

        String html = "<!DOCTYPE html>\n" +
              "<html>\n" +
              "<head>\n" +
              "    <title>Test Summary Report</title>\n" +
              "    <style>\n" +
              "        body {\n" +
              "            font-family: 'Vodafone', sans-serif;\n" +
              "            margin: 40px;\n" +
              "            background-color: #f5f5f5;\n" +
              "        }\n" +
              "        .container {\n" +
              "            background-color: white;\n" +
              "            padding: 30px;\n" +
              "            border-radius: 10px;\n" +
              "            box-shadow: 0 0 20px rgba(0,0,0,0.1);\n" +
              "            position: relative;\n" +
              "        }\n" +
              "        .logo {\n" +
              "            position: absolute;\n" +
              "            top: 20px;\n" +
              "            left: 20px;\n" +
              "            width: 120px;\n" +
              "            height: auto;\n" +
              "        }\n" +
              "        .header {\n" +
              "            text-align: center;\n" +
              "            color: #2c3e50;\n" +
              "            margin: 20px 0 40px 0;\n" +
              "            padding-top: 40px;\n" +
              "        }\n" +
              "        .summary-box {\n" +
              "            background: linear-gradient(135deg, #1a365d, #2c5282);\n" +
              "            color: white;\n" +
              "            padding: 25px;\n" +
              "            border-radius: 8px;\n" +
              "            margin-bottom: 30px;\n" +
              "            box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
              "        }\n" +
              "        .chart-container {\n" +
              "            display: flex;\n" +
              "            justify-content: space-between;\n" +
              "            margin: 30px 0;\n" +
              "            gap: 20px;\n" +
              "        }\n" +
              "        .chart {\n" +
              "            width: 48%;\n" +
              "            background: white;\n" +
              "            padding: 25px;\n" +
              "            border-radius: 12px;\n" +
              "            box-shadow: 0 4px 12px rgba(0,0,0,0.08);\n" +
              "        }\n" +
              "        .status-grid {\n" +
              "            display: grid;\n" +
              "            grid-template-columns: repeat(4, 1fr);\n" +
              "            gap: 20px;\n" +
              "            margin-top: 30px;\n" +
              "        }\n" +
              "        .status-item {\n" +
              "            padding: 20px;\n" +
              "            border-radius: 12px;\n" +
              "            text-align: center;\n" +
              "            color: white;\n" +
              "            box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
              "            transition: transform 0.2s;\n" +
              "        }\n" +
              "        .status-item:hover {\n" +
              "            transform: translateY(-5px);\n" +
              "        }\n" +
              "        .grand-total { background: linear-gradient(135deg, #4a5568, #2d3748); }\n" +
              "        .total { background: linear-gradient(135deg, #2b6cb0, #1a4dbc); }\n" +
              "        .passed { background: linear-gradient(135deg, #2f855a, #2ecc71); }\n" +
              "        .failed { background: linear-gradient(135deg, #c53030, #e74c3c); }\n" +
              "        .skipped { background: linear-gradient(135deg, #d69e2e, #f1c40f); }\n" +
              "        \n" +
              "        .status-item h3 {\n" +
              "            font-size: 1.2em;\n" +
              "            margin-bottom: 10px;\n" +
              "            font-weight: 600;\n" +
              "        }\n" +
              "        .status-item p {\n" +
              "            font-size: 1.8em;\n" +
              "            margin: 0;\n" +
              "            font-weight: bold;\n" +
              "        }\n" +
              "        .timestamp {\n" +
              "            color: #718096;\n" +
              "            font-style: italic;\n" +
              "        }\n" +
              "        .failed-scenarios {\n" +
              "            margin-top: 40px;\n" +
              "            background: white;\n" +
              "            padding: 25px;\n" +
              "            border-radius: 12px;\n" +
              "            box-shadow: 0 4px 12px rgba(0,0,0,0.08);\n" +
              "        }\n" +
              "        .failed-scenarios h2 {\n" +
              "            color: #c53030;\n" +
              "            margin-bottom: 20px;\n" +
              "            border-bottom: 2px solid #c53030;\n" +
              "            padding-bottom: 10px;\n" +
              "        }\n" +
              "        .failed-table {\n" +
              "            width: 100%;\n" +
              "            border-collapse: collapse;\n" +
              "            margin-top: 20px;\n" +
              "        }\n" +
              "        .failed-table th, .failed-table td {\n" +
              "            padding: 12px;\n" +
              "            text-align: left;\n" +
              "            border-bottom: 1px solid #e2e8f0;\n" +
              "        }\n" +
              "        .failed-table th {\n" +
              "            background-color: #f7fafc;\n" +
              "            font-weight: 600;\n" +
              "            color: #4a5568;\n" +
              "        }\n" +
              "        .failed-table tr:hover {\n" +
              "            background-color: #f7fafc;\n" +
              "        }\n" +
              "        .error-message {\n" +
              "            color: #c53030;\n" +
              "            font-family: monospace;\n" +
              "            font-size: 0.9em;\n" +
              "        }\n" +
              "    </style>\n" +
              "    <script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>\n" +
              "</head>\n" +
              "<body>\n" +
              "    <div class=\"container\">\n" +
              "        <img src=\"data:image/png;base64,"+logo+"\" alt=\"Company Logo\" style=\"width: 300px; height: auto;\" class=\"logo\">\n" +
              "        <div class=\"header\">\n" +
              "            <h1>Test Execution Summary Report</h1>\n" +
              "            <p class=\"timestamp\">Generated on: <span id=\"datetime\"></span></p>\n" +
              "        </div>\n" +
              "\n" +
              "        <div class=\"summary-box\">\n" +
              "            <h2>Executive Summary</h2>\n" +
              "            <p>Total Test Cases: 150</p>\n" +
              "            <p>Total Duration: 2h 45m</p>\n" +
              "            <p>Test Environment: Production</p>\n" +
              "        </div>\n" +
              "\n" +
              "        <div class=\"chart-container\">\n" +
              "            <div class=\"chart\">\n" +
              "                <canvas id=\"resultChart\"></canvas>\n" +
              "            </div>\n" +
              "            <div class=\"chart\">\n" +
              "                <canvas id=\"trendChart\"></canvas>\n" +
              "            </div>\n" +
              "        </div>\n" +
              "\n" +
              "        <div class=\"status-grid\">\n" +
              "            <div class=\"status-item total\">\n" +
              "                <h3>Executed</h3>\n" +
              "                <p>"+total+"</p>\n" +
              "            </div>\n" +
              "            <div class=\"status-item passed\">\n" +
              "                <h3>Passed</h3>\n" +
              "                <p>"+passed+"</p>\n" +
              "            </div>\n" +
              "            <div class=\"status-item failed\">\n" +
              "                <h3>Failed</h3>\n" +
              "                <p>"+failed+"</p>\n" +
              "            </div>\n" +
              "            <div class=\"status-item skipped\">\n" +
              "                <h3>Skipped</h3>\n" +
              "                <p>"+skipped+"</p>\n" +
              "            </div>\n" +
              "        </div>\n" +
              "\n" +
              "        <div class=\"failed-scenarios\">\n" +
              "            <h2>Failed Scenarios Details</h2>\n" +
              "            <table class=\"failed-table\">\n" +
              "                <thead>\n" +
              "                    <tr>\n" +
              "                        <th>Scenario Name</th>\n" +
              "                        <th>Error Message</th>\n" +
              "                        <th>Duration</th>\n" +
              "                    </tr>\n" +
              "                </thead>\n" +
              "                <tbody>\n";
        for (ITestResult result : failedTests) {
            double duration = (result.getEndMillis() - result.getStartMillis())/1000.0;
            html += "<tr>\n"+
                  "<td>"+result.getName()+"</td>\n"+
                  "<td class=\"error-message\">"+result.getThrowable()+"</td>\n" +
                  "                        <td>"+duration+" sec</td>\n" +
                  "                    </tr>\n";
        }
              html +=
              "                </tbody>\n" +
              "            </table>\n" +
              "        </div>\n" +
              "    </div>\n" +
              "\n" +
              "    <script>\n" +
              "        // Set current datetime\n" +
              "        document.getElementById('datetime').textContent = new Date().toLocaleString();\n" +
              "\n" +
              "        // Result Pie Chart\n" +
              "        const resultCtx = document.getElementById('resultChart').getContext('2d');\n" +
              "        new Chart(resultCtx, {\n" +
              "            type: 'doughnut',\n" +
              "            data: {\n" +
              "                labels: ['Passed', 'Failed', 'Skipped'],\n" +
              "                datasets: [{\n" +
              "                    data: ["+passed+", "+failed+", "+skipped+"],\n" +
              "                    backgroundColor: ['#2ecc71', '#e74c3c', '#f1c40f'],\n" +
              "                    borderWidth: 2\n" +
              "                }]\n" +
              "            },\n" +
              "            options: {\n" +
              "                responsive: true,\n" +
              "                plugins: {\n" +
              "                    title: {\n" +
              "                        display: true,\n" +
              "                        text: 'Test Results Distribution',\n" +
              "                        font: {\n" +
              "                            size: 16,\n" +
              "                            weight: 'bold'\n" +
              "                        }\n" +
              "                    },\n" +
              "                    legend: {\n" +
              "                        position: 'bottom'\n" +
              "                    }\n" +
              "                },\n" +
              "                cutout: '60%'\n" +
              "            }\n" +
              "        });\n" +
              "\n" +
              "        // Trend Line Chart\n" +
              "        const trendCtx = document.getElementById('trendChart').getContext('2d');\n" +
              "        new Chart(trendCtx, {\n" +
              "            type: 'line',\n" +
              "            data: {\n" +
              "                labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],\n" +
              "                datasets: [{\n" +
              "                    label: 'Pass Rate %',\n" +
              "                    data: [82, 85, 87, 84, 85],\n" +
              "                    borderColor: '#2ecc71',\n" +
              "                    backgroundColor: 'rgba(46, 204, 113, 0.1)',\n" +
              "                    tension: 0.3,\n" +
              "                    fill: true\n" +
              "                }]\n" +
              "            },\n" +
              "            options: {\n" +
              "                responsive: true,\n" +
              "                plugins: {\n" +
              "                    title: {\n" +
              "                        display: true,\n" +
              "                        text: 'Weekly Pass Rate Trend',\n" +
              "                        font: {\n" +
              "                            size: 16,\n" +
              "                            weight: 'bold'\n" +
              "                        }\n" +
              "                    },\n" +
              "                    legend: {\n" +
              "                        position: 'bottom'\n" +
              "                    }\n" +
              "                },\n" +
              "                scales: {\n" +
              "                    y: {\n" +
              "                        beginAtZero: true,\n" +
              "                        max: 100,\n" +
              "                        title: {\n" +
              "                            display: true,\n" +
              "                            text: 'Success Rate (%)'\n" +
              "                        }\n" +
              "                    },\n" +
              "                    x: {\n" +
              "                        title: {\n" +
              "                            display: true,\n" +
              "                            text: 'Time Period'\n" +
              "                        }\n" +
              "                    }\n" +
              "                }\n" +
              "            }\n" +
              "        });\n" +
              "    </script>\n" +
              "</body>\n" +
              "</html>\n";
        try(Writer fileWriter = new FileWriter(new File(System.getProperty("user.dir") + "/target/emailReport.html"))) {
            fileWriter.write(html);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        // Set up email properties
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        // Create a new session with an authenticator
//        Authenticator authenticator = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        };
//        Session session = Session.getInstance(props, authenticator);
//
//        try {
//            // Create a new email message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Test Results Report");
//            message.setContent(report, "text/html");
//
//            // Send the email message
//            Transport.send(message);
//            LoggingManager.info("Email sent successfully!");
//        } catch (MessagingException e) {
//            LoggingManager.error(e.getMessage());
//        }

    }

    public static void openHtmlReport() {
        try {
            // Specify the path to your HTML file
            File htmlFile = new File("target/emailReport.html");

            // Check if Desktop is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Check if the open operation is supported
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    // Open the HTML file in the default browser
                    desktop.browse(htmlFile.toURI());
                } else {
                    LoggingManager.error("Browse action not supported!");
                }
            } else {
                LoggingManager.error("Desktop is not supported on this system.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
