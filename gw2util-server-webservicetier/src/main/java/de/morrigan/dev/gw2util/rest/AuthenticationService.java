package de.morrigan.dev.gw2util.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

//@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
@Path("/authentication")
public class AuthenticationService {

//	private IAuthenticationService authenticationService;
//
//	@Inject
//	public AuthenticationService(IAuthenticationService authenticationService) {
//		super();
//
//		this.authenticationService = authenticationService;
//	}

	@GET
	@Path("echo/{msg}")
	public String echo(@PathParam("msg") String msg) {
		return "Response: " + msg;
	}

}
