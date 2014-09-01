package org.openmhealth.schema.pojos.generic;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.openmhealth.schema.pojos.serialize.dates.ISODateDeserializer;
import org.openmhealth.schema.pojos.serialize.dates.ISODateSerializer;
import org.openmhealth.schema.pojos.serialize.dates.SimpleDateDeserializer;
import org.openmhealth.schema.pojos.serialize.dates.SimpleDateSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeInterval {

    public final static String FULLDATE_FORMAT = "yyyy-MM-dd";

    @JsonProperty(value = "date", required = false)
    @JsonSerialize(using = SimpleDateSerializer.class)
    @JsonDeserialize(using = SimpleDateDeserializer.class)
    private DateTime date;

    @JsonProperty(value = "start-time", required = false)
    @JsonSerialize(using = ISODateSerializer.class)
    @JsonDeserialize(using = ISODateDeserializer.class)
    private DateTime startTime;

    @JsonProperty(value = "end-time", required = false)
    @JsonSerialize(using = ISODateSerializer.class)
    @JsonDeserialize(using = ISODateDeserializer.class)
    private DateTime endTime;

    @JsonProperty(value = "duration", required = false)
    private DurationUnitValue duration;

    @JsonProperty(value = "part-of-day", required = false)
    private PartOfDay partOfDay;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public DurationUnitValue getDuration() {
        return duration;
    }

    public void setDuration(DurationUnitValue duration) {
        this.duration = duration;
    }

    public PartOfDay getPartOfDay() {
        return partOfDay;
    }

    public void setPartOfDay(PartOfDay partOfDay) {
        this.partOfDay = partOfDay;
    }
}