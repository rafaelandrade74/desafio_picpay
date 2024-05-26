package rafalotto.picpay.service;


import org.springframework.stereotype.Service;
import rafalotto.picpay.client.AuthorizationClient;
import rafalotto.picpay.dto.TransferDto;
import rafalotto.picpay.exception.PicPayException;

@Service
public class AuthorizationService {
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public  boolean isAuthorized(TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();
        if(resp.getStatusCode().isError()){
            throw new PicPayException();
        }

        return resp.getBody().authorized();
    }
}
