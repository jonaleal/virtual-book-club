package com.udea.bookclub.services.facade;

import com.google.api.services.calendar.model.Event;
import com.udea.bookclub.dtos.EventRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ICalendarService {

    Event createEvent(EventRequest eventRequest) throws IOException, GeneralSecurityException;

    Event getEvent(String meetLink) throws IOException, GeneralSecurityException;

    Event updateEvent(EventRequest eventRequest, String meetLink) throws IOException, GeneralSecurityException;
}
