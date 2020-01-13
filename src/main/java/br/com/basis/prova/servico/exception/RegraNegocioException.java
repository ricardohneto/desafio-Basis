package br.com.basis.prova.servico.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Erro de negócio.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String message) {
        this(message, null);
    }

    public RegraNegocioException(String message, Throwable cause) {
        super(message, cause);
    }

}
