package com.spbsu.pattern;

import com.spbsu.pattern.validator.BricsRegExpAutomaton;
import com.spbsu.pattern.validator.JregexValidator;
import com.spbsu.pattern.validator.RegExpValidator;
import com.spbsu.pattern.validator.Validator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Created by Sergey Afonin on 26.03.2017.
 */
@Path("/tasks")
@Produces({"application/json"})
public class PatternService {

    Validator validator;

    @POST
    @Path("/")
    @Consumes({"application/json"})
    public Response add(Message message) {
        message.setValid(validator.validate(message.getMessage()));
        return Response.ok(message).build();
    }


    public PatternService() {
        this.validator = new RegExpValidator();
    }
}
