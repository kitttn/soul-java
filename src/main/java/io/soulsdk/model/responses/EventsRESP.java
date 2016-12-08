package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.event.Event;
import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain list of Events
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class EventsRESP extends GeneralResponse {

    private List<Event> events;
    private Meta _meta;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

}
