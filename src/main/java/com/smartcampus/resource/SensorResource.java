package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.Room;
import com.smartcampus.service.DataStore;
import com.smartcampus.exception.LinkedResourceNotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;
import java.util.stream.Collectors;

@Path("/sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null || type.trim().isEmpty()) {
            return DataStore.sensors.values();
        }

        return DataStore.sensors.values()
                .stream()
                .filter(sensor -> sensor.getType() != null &&
                        sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    @GET
    @Path("/room/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorsByRoom(@PathParam("roomId") String roomId) {

        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }

        return Response.ok(room.getSensorIds()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {

        if (sensor.getType() == null || sensor.getType().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Sensor type is required\"}")
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Room ID is required\"}")
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Invalid room ID");
        }

        String id = "S" + (DataStore.sensors.size() + 1);
        sensor.setId(id);

        DataStore.sensors.put(id, sensor);

        room.getSensorIds().add(id);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(id);
        }

        DataStore.sensors.remove(id);

        return Response.ok("{\"message\": \"Sensor deleted successfully\"}")
                .build();
    }
}