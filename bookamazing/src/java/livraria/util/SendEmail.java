
package livraria.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;



public class SendEmail {
    private HtmlEmail email;
    
    public void configure(){
        try {
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("rodolfofelipe.teste@gmail.com", "demoro02"));
            email.setSSLOnConnect(true);
            email.setFrom("rodolfofelipe.teste@gmail.com");
        } catch (EmailException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendHtmlEmail(String destino, String htmlPage){
        try {
            email = new HtmlEmail();
            configure();
            
            email.addTo(destino);
            email.setSubject("Recibo!");
            email.setHtmlMsg(htmlPage);
            
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
