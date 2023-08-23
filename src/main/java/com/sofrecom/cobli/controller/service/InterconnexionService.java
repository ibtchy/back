package com.sofrecom.cobli.controller.service;


import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Interconnexion;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.InterconnexionRepository;

import com.sofrecom.cobli.repository.PrestationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InterconnexionService {



    @Autowired
    PrestationRepository prestationrepo ;
    @Autowired
    InterconnexionRepository interRepo ;



    public Interconnexion InterconnextionAdd(Interconnexion interconnexion){
        Optional<Prestation> prestation = prestationrepo.findByNomPrestation("Interconnexion RIP Tiers");


        Interconnexion interc = new Interconnexion() ;
        interc.setCog(interconnexion.getCog());
        interc.setUi(interconnexion.getUi());
        interc.setDateDepBan(interconnexion.getDateDepBan());

        interc.setDateRetourBan(interconnexion.getDateRetourBan());
        interc.setDateLivraison(interconnexion.getDateLivraison());
        interc.setDateFlux(interconnexion.getDateFlux());
        interc.setMotif(interconnexion.getMotif());
        interc.setDateReception(interconnexion.getDateReception());
        interc.setStatutFacturation(interconnexion.getStatutFacturation());
        interc.setAffectation(interconnexion.getAffectation());
        interc.setQuantite(1);
        interc.setRefTacheBPU("");
        interc.setPriorite(interconnexion.getPriorite());
        interc.setCommentaire(interconnexion.getCommentaire());
        interc.setUi(interconnexion.getUi());
        interc.setDateLivraison(interconnexion.getDateLivraison());
interc.setPhase(interconnexion.getPhase());
        interc.setType_prestation(prestation.get());
        interc.setType_element(null);
        interc.setPriorite("P1");
        interc.setDuree(7);
        interc.setRepriseFacturable("");

        return   interRepo.save(interc);

    }

    public Interconnexion InterconnextionEdit(String id , Interconnexion interconnexion){
        Optional<Interconnexion> inter = interRepo.findById(id);
        if(inter.isPresent()){
            return   interRepo.save(interconnexion);
        }
        else {
            return  null;
        }
    }

    public String InterconnextionRemove( Interconnexion interconnexion){
        interRepo.delete(interconnexion);
        return  "supprimer avec succes";


    }

    public boolean isExisteInterConnexion(Interconnexion interconnexion) {

        List<Interconnexion> inters = new ArrayList<Interconnexion>();
        interRepo.findByCog(interconnexion.getCog()).forEach(inters::add);

        if(inters.size() > 0) {
            for(int i =0;i<inters.size();i++) {
                if (inters.get(i).getCog().equals(interconnexion.getCog()))  {

                    return true;
                }
            }
        }

        return false;
    }


    public boolean isEmptyRow(XSSFRow row) {
        for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
            XSSFCell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                return false; // Si une cellule non vide est trouvée, la ligne n'est pas vide
            }
        }
        return true; // Toutes les cellules sont vides, la ligne est vide
    }

    public List<Interconnexion> retrieveall()
    {
       return interRepo.findAll() ;
    }

}
