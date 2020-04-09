/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.marconivr.microblog.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
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
