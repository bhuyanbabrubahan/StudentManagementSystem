package com.example.student.location.common.service;

import com.example.student.location.country.entity.Country;
import com.example.student.location.district.entity.District;
import com.example.student.location.state.entity.State;
import com.example.student.location.tehsil.entity.Tehsil;
import com.example.student.location.village.entity.Village;

public interface LocationValidationService {

    Country validateCountry(Long countryId);

    State validateState(Long stateId);

    District validateDistrict(Long districtId);

    Tehsil validateTehsil(Long tehsilId);

    Village validateVillage(Long villageId);
    
    

}