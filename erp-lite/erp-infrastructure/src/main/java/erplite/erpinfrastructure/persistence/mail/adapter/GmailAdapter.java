package erplite.erpinfrastructure.persistence.mail.adapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import erplite.domain.entities.order.OrderId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import erplite.domain.ports.services.OrderConfirmEmailServicePort;
import erplite.domain.shared.Email;
import erplite.domain.shared.Money;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GmailAdapter implements OrderConfirmEmailServicePort {
    
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("classpath:templates/email-order-confirm-template.html")
    private Resource emailTemplate;

    @Value("${email.company:ERP Lite}")
    private String companyName;

    @Override
    public void sendMail(
            Email email,
            OrderId orderId,
            String orderNumber,
            Money money,
            String customerName,
            Integer itemsCount) {

        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true); //antipatron

            messageHelper.setFrom(fromEmail); //Origen
            messageHelper.setTo(email.value()); //Destino
            messageHelper.setSubject("Orden " + orderNumber + " Confirmed "); //Asunto

            final var html = this.buildHtmlContent(
                    orderNumber, orderId, money, customerName, itemsCount
            );
            //Cuerpo mensaje
            messageHelper.setText(html, true); //antipatron
            this.mailSender.send(mimeMessage);

            log.info("Email sent to: {} successfully", email.value());
        } catch (MessagingException e) {
            log.error("Error sending mail", e);
            throw new RuntimeException(e);
        }


    }

    /**
     * Se genera el cuerpo del mensaje y se reemplazan los placeholders por los valores correspondientes
     */
    private String buildHtmlContent(
            String orderNumber,
            OrderId orderId,
            Money totalAmount,
            String customerName,
            int itemsCount
    ) {
        try {
            String template = emailTemplate.getContentAsString(StandardCharsets.UTF_8);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formattedDate = now.format(formatter);

            String currentYear = String.valueOf(now.getYear());

            return template
                    .replace("{{orderNumber}}", orderNumber)
                    .replace("{{orderId}}", orderId.value().toString())
                    .replace("{{orderDate}}", formattedDate)
                    .replace("{{customerName}}", customerName)
                    .replace("{{itemsCount}}", String.valueOf(itemsCount))
                    .replace("{{totalAmount}}", totalAmount.amount().toString())
                    .replace("{{currency}}", totalAmount.currency().getCurrencyCode())
                    .replace("{{year}}", currentYear)
                    .replace("{{companyName}}", companyName);

        } catch (IOException e) {
            log.error("Error loading email template", e);
            throw new RuntimeException("Failed to load email template", e);
        }
    }
}
