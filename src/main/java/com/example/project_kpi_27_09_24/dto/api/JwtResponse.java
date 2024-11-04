package com.example.project_kpi_27_09_24.dto.api;



import com.example.project_kpi_27_09_24.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String tokenType;

    {
        try {
            tokenType = AppUtils.getEnv("app.tokenType");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String tokenBody;
    private String userName;
    private String refreshToken;
    private String timestamp = AppUtils.getCurrentDateTime();


    public JwtResponse(String tokenBody, String userName, String refreshToken) throws IOException {
        this.tokenBody = tokenBody;
        this.userName = userName;
        this.refreshToken = refreshToken;
    }


    public JwtResponse(String tokenBody) throws IOException {

        this.tokenBody = tokenBody;

    }

}
