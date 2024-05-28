package app.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import app.domain.contact.Contact;
import app.network.ResponseEntity;
import app.network.ResponseEntityList;
import app.network.ResponseMessage;
import app.service.impl.ContactService;

import java.util.Collections;
import java.util.List;

@Path("/api/v1/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactController {

    ContactService service = new ContactService();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Contact contact) {
        Contact contactCreated = service.create(contact);
        if (contactCreated != null) {
            return Response.ok(new ResponseEntity<>(201, "Created",
                            true, contactCreated).toString())
                    .status(Response.Status.CREATED).build();
        } else {
            return Response.ok(new ResponseEntity<>(204, "No Content",
                            false, ResponseMessage.NO_CONTENT).toString())
                    .status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    public Response fetchAll() {
        List<Contact> list = service.fetchAll();
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id: [0-9]+}")
    public Response fetchById(@PathParam("id") Long id) {
        Contact contact = service.fetchById(id);
        if (contact != null) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contact.toString()).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, ResponseMessage.NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Contact contact) {
        Contact contactToUpdate = service.fetchById(id);
        if (contactToUpdate != null) {
            Contact contactUpdated = service.update(id, contact);
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contactUpdated).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, ResponseMessage.NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response delete(@PathParam("id") Long id) {
        if (service.delete(id)) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, ResponseMessage.DELETED).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, ResponseMessage.NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-by-firstname")
    public Response fetchByFirstName(@QueryParam("firstName") String firstName) {
        List<Contact> list = service.fetchByFirstName(firstName);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-by-lastname")
    public Response fetchByLastName(@QueryParam("lastName") String lastName) {
        List<Contact> list = service.fetchByLastName(lastName);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-order-by")
    public Response fetchAllOrderBy(@QueryParam("orderBy") String orderBy) {
        List<Contact> list = service.fetchAllOrderBy(orderBy);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-by-lastname-order-by")
    public Response fetchByLastNameOrderBy(
            @QueryParam("lastName") String lastName,
            @QueryParam("orderBy") String orderBy
    ) {
        List<Contact> list =
                service.fetchByLastNameOrderBy(lastName, orderBy);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-between-ids")
    public Response fetchBetweenIds(
            @QueryParam("from") int from,
            @QueryParam("to") int to
    ) {
        List<Contact> list = service.fetchBetweenIds(from, to);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-lastname-in")
    public Response fetchLastNameIn(
            @QueryParam("lastName1") String lastName1,
            @QueryParam("lastName2") String lastName2
    ) {
        List<Contact> list =
                service.fetchLastNameIn(lastName1, lastName2);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }
}
