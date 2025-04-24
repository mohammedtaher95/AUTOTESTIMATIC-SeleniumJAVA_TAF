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

public class ReportHelper {

    private static final List<ITestResult> passedTests = new ArrayList<>();
    private static final List<ITestResult> failedTests = new ArrayList<>();
    private static final List<ITestResult> skippedTests = new ArrayList<>();

    private ReportHelper() {

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


    public static void generateReport(long elapsedTime) {
        String logo;
        try {
            logo = String.valueOf(Files.readLines(new File("src/main/java/utilities/summaryreport/logo.txt"),
                  Charset.defaultCharset()));
            logo = logo.replace("[","").replace("]","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int passed = passedTests.size();
        int failed = failedTests.size();
        int skipped = skippedTests.size();
        int total = passed + failed + skipped;

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
              "            display: flex;\n" +
              "            flex-direction: column;\n" +
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
              "\n" +
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
              "        .info-box {\n" +
              "            display: flex;\n" +
              "            align-items: center;\n" +
              "            padding: 15px;\n" +
              "            background: #f8fafc;\n" +
              "            border-radius: 8px;\n" +
              "            margin: 10px 0;\n" +
              "        }\n" +
              "        .info-icon {\n" +
              "            font-size: 24px;\n" +
              "            margin-right: 15px;\n" +
              "            color: #4a5568;\n" +
              "        }\n" +
              "        .info-content {\n" +
              "            flex-grow: 1;\n" +
              "        }\n" +
              "        .info-label {\n" +
              "            font-size: 0.9em;\n" +
              "            color: #718096;\n" +
              "        }\n" +
              "        .info-value {\n" +
              "            font-size: 1.2em;\n" +
              "            color: #2d3748;\n" +
              "            font-weight: 600;\n" +
              "        }\n" +
              "    </style>\n" +
              "    <script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>\n" +
              "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css\">\n" +
              "</head>\n" +
              "<body>\n" +
              "<div class=\"container\">\n" +
              "    <div class=\"header\" style=\"\n" +
              "    margin-bottom: 20px;\n" +
              "    padding-top: 0px;\n" +
              "    margin-top: 0px;\">\n" +
              "        <div class=\"logo\"><img\n" +
              "                src=\"data:image/png;base64,"+logo+"\"\n" +
              "                alt=\"Company Logo\" style=\"width: 300px; height: auto;\" class=\"logo\">\n" +
              "        </div>\n" +
              "        <div class=\"title\" style=\"display: inline-block; text-align: right; width: 100%\"><h1>Test Execution Summary\n" +
              "            Report</h1>\n" +
              "            <p class=\"timestamp\">Generated on: <span id=\"datetime\"></span></p></div>\n" +
              "    </div>\n" +
              "\n" +
              "    <div class=\"chart-container\">\n" +
              "        <div class=\"chart\">\n" +
              "            <canvas id=\"resultChart\"></canvas>\n" +
              "        </div>\n" +
              "        <div class=\"chart\">\n" +
              "            <div class=\"info-box\">\n" +
              "                <i class=\"fas fa-clock info-icon\"></i>\n" +
              "                <div class=\"info-content\">\n" +
              "                    <div class=\"info-label\">Total Duration</div>\n" +
              "                    <div class=\"info-value\">"+elapsedTime+" sec </div>\n" +
              "                </div>\n" +
              "            </div>\n" +
              "            <div class=\"info-box\">\n" +
              "                <i class=\"fas fa-calendar-alt info-icon\"></i>\n" +
              "                <div class=\"info-content\">\n" +
              "                    <div class=\"info-label\">Start Date & Time</div>\n" +
              "                    <div class=\"info-value\" id=\"startDateTime\"></div>\n" +
              "                </div>\n" +
              "            </div>\n" +
              "            <div class=\"status-grid\">\n" +
              "                <div class=\"status-item total\">\n" +
              "                    <h3>Executed</h3>\n" +
              "                    <p>"+total+"</p>\n" +
              "                </div>\n" +
              "                <div class=\"status-item passed\">\n" +
              "                    <h3>Passed</h3>\n" +
              "                    <p>"+passed+"</p>\n" +
              "                </div>\n" +
              "                <div class=\"status-item failed\">\n" +
              "                    <h3>Failed</h3>\n" +
              "                    <p>"+failed+"</p>\n" +
              "                </div>\n" +
              "                <div class=\"status-item skipped\">\n" +
              "                    <h3>Skipped</h3>\n" +
              "                    <p>"+skipped+"</p>\n" +
              "                </div>\n" +
              "            </div>\n" +
              "        </div>\n" +
              "    </div>\n" +
              "\n" +
              "    <div class=\"failed-scenarios\">\n" +
              "        <h2>Failed Scenarios Details</h2>\n" +
              "        <table class=\"failed-table\">\n" +
              "            <thead>\n" +
              "            <tr>\n" +
              "                <th>Scenario Name</th>\n" +
              "                <th>Error Message</th>\n" +
              "                <th>Duration</th>\n" +
              "            </tr>\n" +
              "            </thead>\n" +
              "            <tbody>\n";
        for (ITestResult result : failedTests) {
            double duration = (result.getEndMillis() - result.getStartMillis())/1000.0;
            html += "<tr>\n"+
                  "<td>"+result.getName()+"</td>\n"+
                  "<td class=\"error-message\">"+result.getThrowable()+"</td>\n" +
                  "                        <td>"+duration+" sec</td>\n" +
                  "                    </tr>\n";
        }
              html +="            </tbody>\n" +
              "        </table>\n" +
              "    </div>\n" +
              "</div>\n" +
              "\n" +
              "<script>\n" +
              "    // Set current datetime\n" +
              "    document.getElementById('datetime').textContent = new Date().toLocaleString();\n" +
              "    document.getElementById('startDateTime').textContent = new Date().toLocaleString();\n" +
              "\n" +
              "    // Result Pie Chart\n" +
              "    const resultCtx = document.getElementById('resultChart').getContext('2d');\n" +
              "    new Chart(resultCtx, {\n" +
              "        type: 'doughnut',\n" +
              "        data: {\n" +
              "            labels: ['Passed', 'Failed', 'Skipped'],\n" +
              "            datasets: [{\n" +
              "                data: [1, 0, 0],\n" +
              "                backgroundColor: ['#2ecc71', '#e74c3c', '#f1c40f'],\n" +
              "                borderWidth: 2\n" +
              "            }]\n" +
              "        },\n" +
              "        options: {\n" +
              "            responsive: true,\n" +
              "            plugins: {\n" +
              "                title: {\n" +
              "                    display: true,\n" +
              "                    text: 'Test Results Distribution',\n" +
              "                    font: {\n" +
              "                        size: 16,\n" +
              "                        weight: 'bold'\n" +
              "                    }\n" +
              "                },\n" +
              "                legend: {\n" +
              "                    position: 'bottom'\n" +
              "                }\n" +
              "            },\n" +
              "            cutout: '60%'\n" +
              "        }\n" +
              "    });\n" +
              "</script>\n" +
              "</body>\n" +
              "</html>";

        try(Writer fileWriter = new FileWriter(
              System.getProperty("user.dir") + "/target/emailReport.html")) {
            fileWriter.write(html);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            LoggingManager.error(e.getMessage());
        }
    }
}
