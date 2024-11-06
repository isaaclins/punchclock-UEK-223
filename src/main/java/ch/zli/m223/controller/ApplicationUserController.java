package ch.zli.m223.controller;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.service.ApplicationUserService;

@Path("/users")
@Tag(name = "Users", description = "Handling of users.")
@RolesAllowed({"user", "admin"})
public class ApplicationUserController {

  @Inject
  ApplicationUserService applicationUserService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Index all users.", description = "Returns a list of all users.")
  public List<ApplicationUser> index() {
    return applicationUserService.findAll();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @PermitAll
  @Operation(summary = "Creates a new user.",
      description = "Creates a new user and returns the newly added user.")
  public ApplicationUser create(ApplicationUser applicationUser) {
    return applicationUserService.createApplicationUser(applicationUser);
  }

  @DELETE
  @Operation(summary = "Deletes a user.", description = "Deletes a user irrecoverarble.")
  @Path("/{id}")
  public void delete(@PathParam("id") long id) {
    applicationUserService.deleteApplicationUser(id);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updates a user.", description = "Updates a user.")
  @Path("/{id}")
  public ApplicationUser update(ApplicationUser applicationUser, @PathParam("id") long id) {
    applicationUser.setId(id);
    return applicationUserService.updateApplicationUser(applicationUser);
  }

}
