package com.universidadeafit.appeafit.Adapters.ApiRest;


import com.universidadeafit.appeafit.Model.Usuario;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by LUISM on 27/12/2016.
 */

public class ServerResponse {

    private String result;
    private String message;
    private List user;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List getUser() {
        return user;
    }
}
