package rafalotto.picpay.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rafalotto.picpay.client.NotificationClient;
import rafalotto.picpay.entity.Transfer;
import rafalotto.picpay.exception.PicPayException;

@Service
public class NotificationService {
    private final NotificationClient notificationClient;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {

        try {
            logger.info("Send notification.");
            var resp = notificationClient.sendNotification(transfer);
            if (resp.getStatusCode().isError()) {
                logger.error("Error while sending notification. status code is not OK");
            }
        } catch (PicPayException e) {
            logger.error("Error while sending notification.", e);
        }
    }
}
