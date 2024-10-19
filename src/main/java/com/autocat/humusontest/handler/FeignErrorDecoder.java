package com.autocat.humusontest.handler;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> {
                log.error("Status code 400: Bad Request");
                yield new BadRequestException("Bad Request");
            }
            case 404 -> {
                log.error("Status code 404: Not Found");
                yield new NotFoundException("Not Found");
            }
            case 500 -> {
                log.error("Status code 500: Internal Server Error");
                yield new InternalServerErrorException("Internal Server Error");
            }
            default -> {
                log.error("Status code: {}", response.status());
                yield new Exception("Gneric Error");
            }
        };
    }
}
