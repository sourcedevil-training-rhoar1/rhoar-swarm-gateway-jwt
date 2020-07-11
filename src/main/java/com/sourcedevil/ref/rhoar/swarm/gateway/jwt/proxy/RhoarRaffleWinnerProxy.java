package com.sourcedevil.ref.rhoar.swarm.gateway.jwt.proxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface RhoarRaffleWinnerProxy {

	@Path("ganador")
	@Produces("application/json")
	@GET
	public String getWinner();
	
}
