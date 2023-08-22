package com.workintech.dependency.rest;

import com.workintech.dependency.model.DeveloperFactory;
import com.workintech.dependency.model.JuniorDeveloper;
import com.workintech.dependency.response.DeveloperResponse;
import com.workintech.dependency.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import com.workintech.dependency.model.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.workintech.dependency.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Taxable tax;
    private Map<Integer, Developer> developers;

    @PostConstruct
    public void init(){
        developers = new HashMap<>();
    }

    @Autowired

    public DeveloperController(@Qualifier("developerTax") Taxable tax) {
        this.tax = tax;
    }

    @GetMapping("/")
    public List<Developer> get(){
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable int id){
        if(!DeveloperValidation.isIdValid(id)){
            return new DeveloperResponse(null, "id is not valid", 400);
        }
        if(!developers.containsKey(id)){
            return new DeveloperResponse(null, "there is no such id", 400);
        }
        return new DeveloperResponse(developers.get(id), "success", 200);
    }

    @PostMapping("/")
    public DeveloperResponse save(@RequestBody Developer developer){
        Developer savedDeveloper = DeveloperFactory.createDeveloper(developer, tax);
        if(savedDeveloper == null){
            return new DeveloperResponse(null, "developer is not valid", 400);
        }
        if(developers.containsKey(developer.getId())){
            return new DeveloperResponse(null, "developer is already exist", 400);
        }
        if(!DeveloperValidation.isCredentialsValid(developer)){
            return new DeveloperResponse(null, "credentials not valid", 400);
        }

        developers.put(developer.getId(), savedDeveloper);
        return new DeveloperResponse(developers.get(developer.getId()), "success", 200);
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable int id, @RequestBody Developer developer){
    if(!developers.containsKey(id)){
        return new DeveloperResponse(null, "developer is not exist", 400);
    }

    developer.setId(id);
    Developer updatedDeveloper = DeveloperFactory.createDeveloper(developer, tax);
    if(updatedDeveloper == null){
        return new DeveloperResponse(null, "developer is not valid", 400);
    }
    if(!DeveloperValidation.isCredentialsValid(developer)){
        return new DeveloperResponse(null, "credentials not valid", 400);
    }
    developers.put(id, updatedDeveloper);
    return new DeveloperResponse(developers.get(id), "success", 200);
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable int id){
        if(!developers.containsKey(id)){
            return new DeveloperResponse(null, "invalid id", 400 );
        }
        Developer developer = developers.get(id);
        developers.remove(id, developer);
        return new DeveloperResponse(developer, "success", 200);
    }





}
