package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.service.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/sensors/{sensorId}/readings")
public class SensorReadingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings(@PathParam("sensorId") String sensorId) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }

        List<SensorReading> readings =
                DataStore.readings.getOrDefault(sensorId, new ArrayList<>());

        return Response.ok(readings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(@PathParam("sensorId") String sensorId,
                              SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }

        if (reading.getValue() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Value must be positive\"}")
                    .build();
        }

        String id = "SR" + (DataStore.readings.size() + 1);
        reading.setId(id);
        reading.setSensorId(sensorId);
        reading.setTimestamp(System.currentTimeMillis());

        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}