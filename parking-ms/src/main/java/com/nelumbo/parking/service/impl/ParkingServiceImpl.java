package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.exceptions.ParkingNotFoundException;
import com.nelumbo.parking.exceptions.UserNotFoundException;
import com.nelumbo.parking.feign.AuthClient;
import com.nelumbo.parking.feign.UserDto;
import com.nelumbo.parking.mapper.IParkingMapping;
import com.nelumbo.parking.projection.ParkingProjection;
import com.nelumbo.parking.repository.IParkingRepository;
import com.nelumbo.parking.service.IParkingService;
import com.nelumbo.parking.utils.AuthUtils;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    public ParkingDto save(ParkingDto parking, String token) throws BadRequestException {
        if (validateUser(token, parking.getEmailUser())){
            Parking saveParking = parkingRepository.save(parkingMapper.parkingDtoToParking(parking));
            return parkingMapper.parkingToParkingDto(saveParking);
        }
        throw new BadRequestException(Constants.Message.USER_NO_REQUIRED_ROLE);
    }

    @Override
    public Optional<ParkingDto> findById(UUID id){
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            return Optional.of(parkingMapper.parkingToParkingDto(parking.get()));
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public ParkingDto update(UUID idParking ,ParkingDto parking, String token){
        Optional<ParkingDto> oldParking = findById(idParking);
        if (oldParking.isPresent()) {
            Parking updateParking = parkingMapper.parkingDtoToParking(oldParking.get());
            updateParking.setDirection(parking.getDirection());
            updateParking.setCostHour(parking.getCostHour());
            updateParking.setMaxCapacity(parking.getMaxCapacity());
            updateParking.setName(parking.getName());
            if (validateMaxCapacity(updateParking.getCurrentCapacity(),parking.getMaxCapacity())){
                updateParking.setMaxCapacity(parking.getMaxCapacity());
            }
            if (validateUser(token, parking.getEmailUser())){
                updateParking.setEmailUser(parking.getEmailUser());
            }
            return parkingMapper.parkingToParkingDto(parkingRepository.save(updateParking));
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public String delete(UUID idParking){
        Optional<ParkingDto> parking = findById(idParking);
        if(parking.isPresent()){
            if (parking.get().getCurrentCapacity() > 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.Message.PARKING_DELETE_FAILED);
            }
            parkingRepository.deleteById(parking.get().getId());
            return "Parking deleted successfully";
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public void updateCurrentCapacity(UUID idParking, boolean entry){
        Optional<ParkingDto> oldParking = findById(idParking);
        int countEntry = (entry) ? 1 : -1;
        if (oldParking.isPresent() && entry) {
            oldParking.get().setCurrentCapacity(oldParking.get().getCurrentCapacity() + countEntry);
            parkingRepository.saveAndFlush(parkingMapper.parkingDtoToParking(oldParking.get()));
        }
    }

    @Override
    public List<ParkingDto> findAll(){
        List<Parking> parkingList = parkingRepository.findAll();
        return parkingMapper.parkingListToParkingDtoList(parkingList);
    }

    public List<ParkingDto> findAllPage(){
        List<Parking> parkingList = parkingRepository.findAll();
        return parkingMapper.parkingListToParkingDtoList(parkingList);
    }

    @Override
    public List<ParkingDto> findAllBySocio(String emailUser){
        AuthUtils auth = new AuthUtils();
        List<Parking> parkingList = parkingRepository.findAllByEmailUser(emailUser);
        return parkingMapper.parkingListToParkingDtoList(parkingList);
    }

    private boolean validateUser(String token, String email){
        ResponseEntity<UserDto> response = authClient.getUserByEmail(token, email);
        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto user = response.getBody();
            if (user != null && user.getRol().equals(ROLE_SOCIO)) {
                return true;
            }
        }
        throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
    }

    private boolean validateMaxCapacity(int currentCapacity, int maxCapacity){
        if (currentCapacity < maxCapacity) {
            return true;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.Message.PARKING_MAX_CAPACITY_FAILED);
    }

    public void isParkingSocioAsociated(UUID parkingId){
        AuthUtils authUtils = new AuthUtils();
        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if(parking.isPresent()){
            String ROLE_ADMIN = "ADMIN";
            if (authUtils.getEmailAuthentication().equals(parking.get().getEmailUser()) || authUtils.getRolAuthentication().equals(ROLE_ADMIN)){
                return;
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,Constants.Message.PARKING_UNAUTHORIZED);
            }
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    public Page<ParkingProjection> getParkingProjections(Pageable pageable) {
        return parkingRepository.findAllProjection(pageable);
    }

}
