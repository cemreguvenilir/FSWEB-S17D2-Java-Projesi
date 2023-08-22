package com.workintech.dependency.model;

import com.workintech.dependency.tax.Taxable;

public class DeveloperFactory {
   public static Developer createDeveloper(Developer developer, Taxable tax){
        Developer savedDeveloper;
        if(developer.getExperience().name().equalsIgnoreCase("junior")){
            savedDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * tax.getSimpleTaxRate(), developer.getExperience());
        } else if(developer.getExperience().name().equalsIgnoreCase("mid")){
            savedDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * tax.getMiddleTaxRate(), developer.getExperience());
        } else if(developer.getExperience().name().equalsIgnoreCase("senior")){
            savedDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * tax.getUpperTaxRate(), developer.getExperience());
        } else {
            savedDeveloper = null;
        }
        return savedDeveloper;
    }
}
