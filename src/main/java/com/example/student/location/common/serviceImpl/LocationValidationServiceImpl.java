package com.example.student.location.common.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.service.LocationValidationService;
import com.example.student.location.country.entity.Country;
import com.example.student.location.country.repository.CountryRepository;
import com.example.student.location.district.entity.District;
import com.example.student.location.district.repository.DistrictRepository;
import com.example.student.location.state.entity.State;
import com.example.student.location.state.repository.StateRepository;
import com.example.student.location.tehsil.entity.Tehsil;
import com.example.student.location.tehsil.repository.TehsilRepository;
import com.example.student.location.village.entity.Village;
import com.example.student.location.village.repository.VillageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationValidationServiceImpl
        implements LocationValidationService {

    private final CountryRepository countryRepository;

    private final StateRepository stateRepository;

    private final DistrictRepository districtRepository;

    private final TehsilRepository tehsilRepository;

    private final VillageRepository villageRepository;

    @Override
    public Country validateCountry(Long countryId) {

        return countryRepository
                .findByIdAndStatus(countryId, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Country not found."));
    }

    @Override
    public State validateState(Long stateId) {

        return stateRepository
                .findByIdAndStatus(stateId, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("State not found."));
    }

    @Override
    public District validateDistrict(Long districtId) {

        return districtRepository
                .findByIdAndStatus(districtId, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("District not found."));
    }

    @Override
    public Tehsil validateTehsil(Long tehsilId) {

        return tehsilRepository
                .findByIdAndStatus(tehsilId, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tehsil not found."));
    }

    @Override
    public Village validateVillage(Long villageId) {

        return villageRepository
                .findByIdAndStatus(villageId, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Village not found."));
    }

}
