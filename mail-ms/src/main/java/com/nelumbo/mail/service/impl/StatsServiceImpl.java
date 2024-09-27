package com.nelumbo.mail.service.impl;

import com.nelumbo.mail.dto.EmailCountDto;
import com.nelumbo.mail.dto.ParkingCountDto;
import com.nelumbo.mail.dto.VehicleCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
;import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sortByCount;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl {

    private final MongoTemplate mongoTemplate;

    public List<VehicleCountDto> findTop10VehicleRegistered() {
        Aggregation aggregation = Aggregation.newAggregation(
                sortByCount("vehicle_plate"),
                limit(10)
        );
        AggregationResults<VehicleCountDto> result = mongoTemplate.aggregate(aggregation, "email_parking", VehicleCountDto.class);
        return result.getMappedResults();
    }

    public List<EmailCountDto> findTop10UserMostRegisteredToEmail() {
        Aggregation aggregation = Aggregation.newAggregation(
                    sortByCount("to_email"),
                limit(10)
        );
        AggregationResults<EmailCountDto> result = mongoTemplate.aggregate(aggregation, "email_parking", EmailCountDto.class);
        return result.getMappedResults();
    }

    public List<ParkingCountDto> findTop10UserMostRegisteredParking() {
        Aggregation aggregation = Aggregation.newAggregation(
                sortByCount("parking_name"),
                limit(10)
        );
        AggregationResults<ParkingCountDto> result = mongoTemplate.aggregate(aggregation, "email_parking", ParkingCountDto.class);
        return result.getMappedResults();
    }
}
