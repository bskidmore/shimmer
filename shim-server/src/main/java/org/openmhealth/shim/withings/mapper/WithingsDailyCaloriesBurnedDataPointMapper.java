package org.openmhealth.shim.withings.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.openmhealth.schema.domain.omh.*;

import java.time.*;
import java.util.Optional;

import static org.openmhealth.shim.common.mapper.JsonNodeMappingSupport.asOptionalString;
import static org.openmhealth.shim.common.mapper.JsonNodeMappingSupport.asRequiredLong;


/**
 * This only captures calories that are burned from activity that is captured by a Withings device or application, and
 * may not be an accurate representation of all the calories burned from metabolic resting or activities not captured.
 * Created by Chris Schaefbauer on 7/5/15.
 */
public class WithingsDailyCaloriesBurnedDataPointMapper extends WithingsListDataPointMapper<CaloriesBurned> {

    @Override
    Optional<DataPoint<CaloriesBurned>> asDataPoint(JsonNode node) {
        long caloriesBurnedValue = asRequiredLong(node, "calories");
        CaloriesBurned.Builder caloriesBurnedBuilder =
                new CaloriesBurned.Builder(new KcalUnitValue(KcalUnit.KILOCALORIE, caloriesBurnedValue));

        Optional<String> dateString = asOptionalString(node, "date");
        Optional<String> timeZoneFullName = asOptionalString(node, "timezone");

        if (dateString.isPresent() && timeZoneFullName.isPresent()) {
            LocalDateTime localDateTime = LocalDate.parse(dateString.get()).atStartOfDay();
            ZoneId zoneId = ZoneId.of(timeZoneFullName.get());
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
            ZoneOffset offset = zonedDateTime.getOffset();
            OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);
            caloriesBurnedBuilder.setEffectiveTimeFrame(
                    TimeInterval.ofStartDateTimeAndDuration(offsetDateTime, new DurationUnitValue(
                            DurationUnit.DAY,
                            1))); //Duration is for one day as the datapoint refers to an entire day of calories burned
        }

        Optional<String> userComment = asOptionalString(node, "comment");
        if (userComment.isPresent()) {
            caloriesBurnedBuilder.setUserNotes(userComment.get());
        }

        CaloriesBurned caloriesBurned = caloriesBurnedBuilder.build();
        DataPoint<CaloriesBurned> caloriesBurnedDataPoint =
                newDataPoint(caloriesBurned, RESOURCE_API_SOURCE_NAME, null, true, null);
        return Optional.of(caloriesBurnedDataPoint);
    }

    /**
     * Returns the list name for splitting out individual activity measure items that can then be mapped.
     *
     * @return the name of the array containing the individual activity measure nodes
     */
    @Override
    String getListNodeName() {
        return "activities";
    }
}
