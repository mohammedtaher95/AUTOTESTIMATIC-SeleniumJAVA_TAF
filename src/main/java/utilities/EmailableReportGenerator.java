package utilities;

import org.testng.ITestResult;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;

public class EmailableReportGenerator {

    private static final List<ITestResult> passedTests = new ArrayList<>();
    private static final List<ITestResult> failedTests = new ArrayList<>();

    private EmailableReportGenerator(){

    }

    public static void addPassedTest(ITestResult result) {
        passedTests.add(result);
    }

    public static void addFailedTest(ITestResult result) {
        failedTests.add(result);
    }

    public static void generateReportAndSendEmail(String recipientEmail, String senderEmail, String senderPassword) {
        String report = "<html><body>";
        report += "<h2>Passed Tests</h2>";
        report += "<ul>";
        for (ITestResult result : passedTests) {
            report += "<li>" + result.getName() + "</li>";
        }
        report += "</ul>";
        report += "<h2>Failed Tests</h2>";
        report += "<ul>";
        for (ITestResult result : failedTests) {
            report += "<li>" + result.getName() + "</li>";
            report += "<pre>" + result.getThrowable().getMessage() + "</pre>";
        }
        report += "</ul>";
        report += "</body></html>";

        // Set up email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a new session with an authenticator
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };
        Session session = Session.getInstance(props, authenticator);

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Test Results Report");
            message.setContent(report, "text/html");

            // Send the email message
            Transport.send(message);
            LoggingManager.info("Email sent successfully!");
        } catch (MessagingException e) {
            LoggingManager.error(e.getMessage());
        }
    }
}
