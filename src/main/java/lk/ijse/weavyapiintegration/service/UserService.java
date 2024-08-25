package lk.ijse.weavyapiintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.weavyapiintegration.dto.UserDTO;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class UserService {

    @Autowired
    private OkHttpClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${weavy.api.url}")
    private String weavyApiUrl;

    @Value("${weavy.api.token}")
    private String weavyApiToken;

    public UserDTO saveUser(UserDTO userDTO) {
        String endpoint = weavyApiUrl + "/api/users";
        try {

            String jsonBody = objectMapper.writeValueAsString(userDTO);

            RequestBody body = okhttp3.RequestBody.create(
                    jsonBody,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + weavyApiToken)
                    .addHeader("Content-Type", "application/json")
                    .build();


            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return objectMapper.readValue(responseBody, UserDTO.class);
                } else {
                    throw new RuntimeException("Failed to save user: " + response.message());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting user to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }

    public UserDTO getUser(String userId, Boolean trashed) {
        String endpoint = weavyApiUrl + "/api/users/" + userId;
        if (trashed != null) {
            endpoint += "?trashed=" + trashed;
        }

        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .addHeader("Authorization", "Bearer " + weavyApiToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, UserDTO.class);
            } else {
                throw new RuntimeException("Failed to get user: " + response.message());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting response to UserDTO", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }
}
