package resttemplate.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class HttpClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        // 공통 Header 추가
        request.getHeaders().add("Custom-Header", "Custom-Value");

        // Request 정보 로깅
        log.info("-----------------------------------------------------------------------------");
        log.info("<Request>");
        log.info("요청 URI : {}", request.getURI());
        log.info("요청 Method : {}", request.getMethod());
        log.info("요청 Headers : {}", request.getHeaders());
        log.info("요청 Body : {}", new String(body, "UTF-8"));
        log.info("-----------------------------------------------------------------------------");

        // 다음 인터셉터 또는 요청 실행
        ClientHttpResponse response = execution.execute(request, body);

        // Response 정보 로깅
        log.info("<Response>");
        log.info("응답 Status : {}", response.getStatusCode());
        log.info("응답 Headers : {}", response.getHeaders());

        // List 형식 등 복잡한 json body도 모두 읽을 수 있게 출력
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        log.info("응답 Body : {}", sb);
        log.info("-----------------------------------------------------------------------------");

        reader.close();
        return response;
    }
}