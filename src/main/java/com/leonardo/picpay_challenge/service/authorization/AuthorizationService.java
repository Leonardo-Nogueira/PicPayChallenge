package com.leonardo.picpay_challenge.service.authorization;

import com.leonardo.picpay_challenge.domain.authorization.Authorization;
import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import com.leonardo.picpay_challenge.exception.UnathorizedException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class AuthorizationService {

    private RestClient restClient;

    public AuthorizationService(RestClient.Builder builder){
        this.restClient = builder.baseUrl("https://run.mocky.io/v3/203acf5e-d566-4f51-8087-70fd629fb2a2")
                .build();
    }

    public void authorize(Transaction transaction){
        log.info("Authorizing Transaction {}",transaction);
        var response = restClient.get().retrieve().toEntity(Authorization.class);

        if(response.getStatusCode().isError() || !response.getBody().isAuthorized()){
            throw new UnathorizedException("Unauthorized Transaction");
        }
        log.info("Transaction Authorized {}",transaction);
    }
}
