package com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface HwTestProxy {

	@Path("hw")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String hw();
	
}
