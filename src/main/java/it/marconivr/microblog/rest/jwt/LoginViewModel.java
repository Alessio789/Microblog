package it.marconivr.microblog.rest.jwt;

import lombok.Getter;
import lombok.Setter;

public class LoginViewModel {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;
}
