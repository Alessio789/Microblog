package it.marconivr.microblog.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * Json Response Body
 *
 * @author Alessio Trentin
 */
@AllArgsConstructor
public class JsonResponseBody {

    @Getter @Setter
    private int server;
    
    @Getter @Setter
    private Object response; 
}
