package com.haulmont.sample.petclinic.web.pet.pet;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import com.haulmont.reports.gui.actions.RunReportAction;
import com.haulmont.sample.petclinic.entity.pet.Pet;

import javax.inject.Inject;

@UiController("petclinic_Pet.edit")
@UiDescriptor("pet-edit.xml")
@EditedEntityContainer("petDc")
@LoadDataBeforeShow
public class PetEdit extends StandardEditor<Pet> {

    @Inject
    protected Button patientRecordBtn;

    @Subscribe
    protected void onInit(InitEvent event) {
        patientRecordBtn.setAction(new EditorPrintFormAction(this, null));
    }

}