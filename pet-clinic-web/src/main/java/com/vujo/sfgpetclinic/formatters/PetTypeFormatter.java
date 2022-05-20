package com.vujo.sfgpetclinic.formatters;

import com.vujo.sfgpetclinic.model.PetType;
import com.vujo.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Set<PetType> petTypes = petTypeService.findAll();

        for (PetType petType : petTypes) {
            if (petType.getName().equals(text)) {
                return petType;
            }
        }

        throw new ParseException("PetType string can't be converted.", 0);
    }

    @Override
    public String print(PetType object, Locale locale) {
        return object.getName();
    }
}
