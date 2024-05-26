package rafalotto.picpay.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rafalotto.picpay.client.dto.AuthorizationResponse;
import rafalotto.picpay.entity.Transfer;

@FeignClient(name = "NotificationClient", url = "${client.notification-service.url}")
public interface NotificationClient {
    @GetMapping
    ResponseEntity<Void> sendNotification(@RequestBody Transfer transfer);
}
