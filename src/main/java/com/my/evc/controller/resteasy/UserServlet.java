package com.my.evc.controller.resteasy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserServlet {
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMsg(@PathParam("param") String name) {
		String msg = "Rest say: good " + name;
		System.out.println("============" + msg + "============");
		return msg;
	}
}
