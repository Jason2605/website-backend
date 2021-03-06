package dev.codesupport.web.common.service.controller.throwparser.parser;

import lombok.NoArgsConstructor;
import dev.codesupport.web.common.service.controller.throwparser.AbstractThrowableParser;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

/**
 * Used to parse Spring's {@link HttpMessageNotReadableException} type throwables.
 *
 * @see HttpMessageNotReadableException
 * @see AbstractThrowableParser
 */
@Component
@NoArgsConstructor
class HttpMessageNotReadableExceptionParser extends AbstractThrowableParser<HttpMessageNotReadableException> {

    @Override
    protected AbstractThrowableParser<HttpMessageNotReadableException> instantiate() {
        return new HttpMessageNotReadableExceptionParser();
    }

    @Override
    protected String responseMessage() {
        return "Could not parse JSON payload";
    }
}
