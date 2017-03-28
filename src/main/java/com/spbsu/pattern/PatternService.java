package com.spbsu.pattern;

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

    @GET
    @Path("/{id}")
    public Message get(@PathParam("id") Integer id) {
        return new Message("Message" + id);
    }

    @POST
    @Path("/")
    @Consumes({"application/json"})
    public Response add(Message message) {
        message.setValid(validator.validate(message.getMessage()));
        return Response.ok(message).build();
    }

//    /**
//     * Update task with specific identifier
//     *
//     * @param uuid identifier of task, that be updated.
//     * @param task task with new properties.
//     * @return http response.
//     */
//    @PUT
//    @Path("/{uuid}")
//    @Consumes({"application/json"})
//    public Response update(@PathParam("uuid") UUID uuid, Task task) {
//        Task originTask = dao.getByUUID(uuid);
//        boolean isNewTask = originTask == null;
//        if (isNewTask) {
//            originTask = new Task();
//            originTask.setUUID(uuid);
//        }
//        originTask.setName(task.getName());
//        originTask.setDescription(task.getDescription());
//        originTask.setCompleted(task.isCompleted());
//        dao.save(originTask);
//        URI location = URI.create("/tasks/" + task.getUUID());
//        if (isNewTask) {
//            return Response.created(location).build();
//        } else {
//            return Response.noContent().location(location).build();
//        }
//    }
//
//
//    /**
//     * Delete task with specific identifier.
//     *
//     * @param uuid identifier of task, that be removed.
//     * @return http response.
//     */
//    @DELETE
//    @Path("/{uuid}")
//    public Response delete(@PathParam("uuid") UUID uuid) {
//        Task originTask = dao.getByUUID(uuid);
//        if (originTask == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        dao.remove(originTask);
//        return Response.noContent().build();
//    }
//
//    /**
//     * Find all completed or uncompleted tasks.
//     *
//     * @param isCompleted mark of completed state for search.
//     * @return collection with completed (if argument is true) or uncompleted (if argument is false) tasks.
//     */
//    @GET
//    @Path("/find")
//    public Collection<Task> find(@QueryParam("completed") Boolean isCompleted) {
//        if (isCompleted == null) {
//            return null;
//        }
//        return dao.findByStatus(isCompleted);
//    }
//
//    /**
//     * Get list of all tasks.
//     *
//     * @return list of tasks.
//     */
//    @GET
//    public Collection<Task> getAll() {
//        return dao.getAll();
//    }

    public PatternService() {
        this.validator = RegExpValidator.buildValidator();
    }
}
