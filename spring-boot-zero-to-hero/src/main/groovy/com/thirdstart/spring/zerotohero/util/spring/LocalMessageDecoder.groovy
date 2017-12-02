package com.thirdstart.spring.zerotohero.util.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
/**
 * Simplifies the process of taking one more more codes representing messages
 * and returning their localized message, defaulting to the code passed (first if plural).
 */
class LocalMessageDecoder {

    @Autowired
    MessageSource messageSource

    /**
     * Returns the first resolved message from a list of codes, returning the first
     * code if none are resolved.
     *
     * @param code
     * @return
     */
    String decode( List<String> codes ) {
        assert codes.size() > 0

        String message

        // TODO: there's got to be a better, non-try/catch way to do this
        for ( int i; i < codes.size(); i++ ) {
            try {
                message = messageSource.getMessage(codes[i], null, LocaleContextHolder.locale)
                break
            } catch ( NoSuchMessageException noSuchMessageException ) {
                // NO-OP
            }
        }

        message = message ?: codes.first()

        return message

    }

    /**
     * Returns the localized message for code, and code if none is available.
     *
     * @param code
     * @return
     */
    String decode( String code ) {
        return decode( [code] )
    }
}
