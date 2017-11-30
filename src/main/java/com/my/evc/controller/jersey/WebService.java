package com.my.evc.controller.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class WebService {
    @GET
    @Path(value="ping/{name}")
    @Produces("application/json")
    public String ping(@PathParam("name") String name) {
        System.out.println("==============Log===="+name+"=========");
        return "succeed";
    }
}
