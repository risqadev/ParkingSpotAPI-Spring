package dev.risqa.parkingcontrol.controllers;

import dev.risqa.parkingcontrol.dtos.ParkingSpotDto;
import dev.risqa.parkingcontrol.models.ParkingSpotModel;
import dev.risqa.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        /* TODO: isolar estas responsabilidades em um custom validator */
        if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Licence plate car is already in use.");
        }
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot number is already in use.");
        }
        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot is already registered for this apartment.");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> optionalParkingSpot = parkingSpotService.findById(id);
        if(optionalParkingSpot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        return ResponseEntity.ok(optionalParkingSpot.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpotById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
        if (parkingSpotOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        parkingSpotService.deleteById(parkingSpotOptional.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putParkingSpotById(@PathVariable(value = "id") UUID id,
                                                     @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
        if (parkingSpotOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        ParkingSpotModel parkingSpotModel = parkingSpotOptional.get();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        parkingSpotService.save(parkingSpotModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
