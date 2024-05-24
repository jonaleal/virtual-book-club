package com.udea.bookclub.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.udea.bookclub.dtos.EventRequest;
import com.udea.bookclub.services.facade.ICalendarService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CalendarService implements ICalendarService {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String CALENDAR_ID = "c_1267ca4fd311afee83a06b5b95e87d7c94573e3a5ba36301e2931d4f666908f3@group.calendar.google.com";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = CalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Override
    public Event createEvent(EventRequest eventRequest) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        // Create a new event
        Event event = new Event();
        event.setSummary(eventRequest.summary());
        event.setDescription(eventRequest.description());

        // Set the start time
        DateTime startDateTime = new DateTime(eventRequest.startDateTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Bogota");
        event.setStart(start);

        // Set the end time
        DateTime endDateTime = new DateTime(eventRequest.endDateTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Bogota");
        event.setEnd(end);

        // Add attendees
        List<EventAttendee> attendees = new ArrayList<>();
        for (String attendee : eventRequest.attendees()) {
            EventAttendee eventAttendee = new EventAttendee();
            eventAttendee.setEmail(attendee);
            attendees.add(eventAttendee);
        }
        event.setAttendees(attendees);

        // Create meet conference request
        ConferenceSolutionKey conferenceSolutionKey = new ConferenceSolutionKey()
                .setType("hangoutsMeet");
        CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest()
                .setRequestId("biy-enmm-qmz")
                .setConferenceSolutionKey(conferenceSolutionKey);
        ConferenceData conferenceData = new ConferenceData()
                .setCreateRequest(createConferenceRequest);
        event.setConferenceData(conferenceData);

        // Add the event to the calendar
        var resultEvent = service.events().insert(CALENDAR_ID, event)
                .setConferenceDataVersion(1)
                .execute();

        return resultEvent;
    }

    @Override
    public Event getEvent(String meetLink) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        return service.events().get(CALENDAR_ID, meetLink).execute();
    }

    @Override
    public Event updateEvent(EventRequest eventRequest, String meetLink) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        // Ge a new event
        Event event = service.events().get(CALENDAR_ID, meetLink).execute();
        event.setSummary(eventRequest.summary());
        event.setDescription(eventRequest.description());

        // Set the start time
        DateTime startDateTime = new DateTime(eventRequest.startDateTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Bogota");
        event.setStart(start);

        // Set the end time
        DateTime endDateTime = new DateTime(eventRequest.endDateTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Bogota");
        event.setEnd(end);

        // Add attendees
        List<EventAttendee> attendees = new ArrayList<>();
        for (String attendee : eventRequest.attendees()) {
            EventAttendee eventAttendee = new EventAttendee();
            eventAttendee.setEmail(attendee);
            attendees.add(eventAttendee);
        }
        event.setAttendees(attendees);

        // Add the event to the calendar
        var resultEvent = service.events().update(CALENDAR_ID, meetLink, event)
                .setConferenceDataVersion(1)
                .execute();

        return resultEvent;
    }
}


