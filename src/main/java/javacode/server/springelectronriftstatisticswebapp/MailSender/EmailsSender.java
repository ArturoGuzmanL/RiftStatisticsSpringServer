package javacode.server.springelectronriftstatisticswebapp.MailSender;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailsSender {
    final Configuration configuration;
    @Autowired
    final JavaMailSender javaMailSender;

    public EmailsSender (JavaMailSender javaMailSender) throws IOException {
        this.javaMailSender = javaMailSender;
        configuration = HtmlFactory.getInstance().getConfiguration();
    }

    public void sendConfirmationEmail(User user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Bienvenido a RiftStatistics");
        helper.setTo(user.getEmail());
        String emailContent = getVerificationEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    String getVerificationEmailContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("verificationlink", "https://riftstatistics.ddns.net/page/users/actions/verification/" + user.getId());
        configuration.getTemplate("RegistrationMail.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    public void sendPassChangeEmail(User user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Cambiar contraseña de RiftStatistics");
        helper.setTo(user.getEmail());
        String emailContent = getPassChangeEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    String getPassChangeEmailContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("PassChangelink", "https://riftstatistics.ddns.net/page/users/actions/passchange/" + user.getId());
        configuration.getTemplate("PassChangeMail.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
