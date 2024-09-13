package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.feign.AuthClient;
import com.nelumbo.parking.feign.UserDto;
import com.nelumbo.parking.mapper.IParkingMapping;
import com.nelumbo.parking.repository.IParkingRepository;
import com.nelumbo.parking.service.IParkingService;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements IParkingService {

    private final IParkingRepository parkingRepository;
    private final IParkingMapping parkingMapper = IParkingMapping.INSTANCE;
    private final AuthClient authClient;

    private final String ROLE_SOCIO = Constants.rol.SOCIO.name();

    @Override
    public ParkingDto save(ParkingDto parking, String token){
        if (validateUser(token, parking.getEmailUser())){
            Parking saveParking = parkingRepository.save(parkingMapper.parkingDtoToParking(parking));
            return parkingMapper.parkingToParkingDto(saveParking);
        }
        throw new RuntimeException(Constants.Message.USER_NO_REQUIRED_ROLE);
    }

    @Override
    public Optional<ParkingDto> findById(UUID id){
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            return Optional.of(parkingMapper.parkingToParkingDto(parking.get()));
        }
        throw new RuntimeException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public ParkingDto update(ParkingDto parking, String token){
        Optional<ParkingDto> oldParking = findById(parking.getId());
        if (oldParking.isPresent()) {
            Parking updateParking = parkingMapper.parkingDtoToParking(oldParking.get());
            updateParking.setDirection(parking.getDirection());
            updateParking.setCostHour(parking.getCostHour());
            updateParking.setMaxCapacity(parking.getMaxCapacity());
            updateParking.setName(parking.getName());
            if (validateMaxCapacity(updateParking.getCurrentCapacity(),parking.getMaxCapacity())){
                updateParking.setMaxCapacity(parking.getMaxCapacity());
            }
            if (validateUser(token, updateParking.getEmailUser())){
                updateParking.setEmailUser(parking.getEmailUser());
            }
            return parkingMapper.parkingToParkingDto(parkingRepository.save(updateParking));
        }
        throw new RuntimeException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public void delete(UUID idParking){
        Optional<ParkingDto> parking = findById(idParking);
        if(parking.isPresent()){
            if (parking.get().getCurrentCapacity() > 0){
                throw new RuntimeException(Constants.Message.PARKING_DELETE_FAILED);
            }
            parkingRepository.deleteById(parking.get().getId());
        }
        throw new RuntimeException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public void updateCurrentCapacity(UUID idParking){
        Optional<ParkingDto> oldParking = findById(idParking);
        if (oldParking.isPresent()) {
            oldParking.get().setCurrentCapacity(oldParking.get().getCurrentCapacity() - 1);
            parkingRepository.saveAndFlush(parkingMapper.parkingDtoToParking(oldParking.get()));
        }
    }

    private boolean validateUser(String token, String email){
        ResponseEntity<UserDto> response = authClient.getUserByEmail(token, email);
        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto user = response.getBody();
            if (user != null && user.getRol().equals(ROLE_SOCIO)) {
                return true;
            }else{
                return false;
            }
        }
        throw new RuntimeException(Constants.Message.USER_NOT_FOUND);
    }

    private boolean validateMaxCapacity(int currentCapacity, int maxCapacity){
        if (currentCapacity < maxCapacity) {
            return true;
        }
        throw new RuntimeException(Constants.Message.PARKING_MAX_CAPACITY_FAILED);
    }



}
