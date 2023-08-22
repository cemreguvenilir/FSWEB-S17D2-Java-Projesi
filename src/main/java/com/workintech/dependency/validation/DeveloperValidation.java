package com.workintech.dependency.validation;

import com.workintech.dependency.model.Developer;
import com.workintech.dependency.response.DeveloperResponse;

public class DeveloperValidation {
    public static boolean isIdValid(int id){
       return id>0;
    }
    public static boolean isCredentialsValid(Developer developer){
        return isIdValid(developer.getId()) && developer.getName() != null && !developer.getName().isEmpty() && developer.getSalary()>25000;
}

}
