package com.donats.backend.donation.liqpay;

import com.donats.backend.donation.dto.DonationInitResponse;
import com.donats.backend.exceptions.DonationCloseException;
import com.donats.backend.exceptions.DonationInitException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class LiqPayService {

    private final String publicKey;
    private final String privateKey;
    private final String serverUrl;
    private final ObjectMapper objectMapper;

    public LiqPayService(
            @Value("${liqpay.public-key}") String publicKey,
            @Value("${liqpay.private-key}") String privateKey,
            @Value("${liqpay.server-url}") String serverUrl,
            ObjectMapper objectMapper) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.serverUrl = serverUrl;
        this.objectMapper = objectMapper;
    }

    public DonationInitResponse generateLiqPayParams(BigDecimal amount, String orderId, String description) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("version", "7");
            params.put("public_key", publicKey);
            params.put("action", "paydonate");
            params.put("amount", amount);
            params.put("currency", "UAH");
            params.put("description", description);
            params.put("order_id", orderId);
            params.put("server_url", serverUrl + "/api/donations/liqpay/server");

            String json = objectMapper.writeValueAsString(params);
            String data = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

            String signature = generateSignature(data);

            return new DonationInitResponse(data, signature);
        } catch (Exception e) {
            throw new DonationInitException("Не вдалося розпочати донат");
        }
    }

    public boolean verifySignature(String data, String signature) {
        return signature.equals(generateSignature(data));
    }

    public Map<String, Object> decodeData(String data) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(data);
            String json = new String(decodedBytes, StandardCharsets.UTF_8);
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new DonationCloseException("Помилка завершення донату");
        }
    }

    private String generateSignature(String data) {
        try {
            String signString = privateKey + data + privateKey;
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] digest = md.digest(signString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new DonationInitException("Не вдалося розпочати донат");
        }
    }
}
