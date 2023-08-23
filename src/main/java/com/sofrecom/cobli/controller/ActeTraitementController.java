package com.sofrecom.cobli.controller;


import com.sofrecom.cobli.models.*;
import com.sofrecom.cobli.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/acteTraitement")
public class ActeTraitementController {


@Autowired
Acte_traitementRepository acteTraitementRepository;

@Autowired
NropmRepository nropmRepository;

@Autowired
ESIMBRepository esimbRepository;

@Autowired
Graphic_Repository graphicRepository;

@Autowired
DESATRepository desatRepository;

@Autowired
PrestationRepository prestationRepository;


//Kpis
/*@GetMapping("/actes")
public List<Acte_traitement> getActes(@RequestParam String date_Debut_s,@RequestParam String date_Fin_s) throws ParseException{

List<Acte_traitement> actes = new ArrayList<>();


DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Date date_Debut = dateFormat.parse(date_Debut_s);
Date date_Fin = dateFormat.parse(date_Fin_s);
System.out.println("Date début : " + date_Debut);
System.out.println("Date fin : " + date_Fin);
actes = acteTraitementRepository.findActeKpi(date_Debut, date_Fin);
System.out.println("Actes trouvés : " + actes);
System.out.println("Nombre d'actes trouvés : " + actes.size());


//actes=acteTraitementRepository.findByMotifOrStatutFacturation("en attente", "facturable");

return actes;

}*/



@GetMapping("/actesNonTraites")
public List<Acte_traitement> getActesNonTraites(){

List<Acte_traitement> actes = new ArrayList<>();


actes=acteTraitementRepository.findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc("enCoursCds");



return actes;

}


@GetMapping("/getActe/{idacte}")
public ResponseEntity<Acte_traitement> getActe(@PathVariable("idacte") String idacte){
try {

Optional<Acte_traitement> acteTraitement_ = acteTraitementRepository.findByidacte(idacte);


if(acteTraitement_.isPresent()){

return new ResponseEntity<>(acteTraitement_.get(), HttpStatus.OK);

}else {
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}


} catch (Exception e) {
return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}

}


@PutMapping("/modifierPriorite/{idacte}")
public ResponseEntity<Acte_traitement> modifierPriorite(@PathVariable("idacte") String idacte, @RequestBody Acte_traitement acteTraitement) {

Optional<Acte_traitement> acteTraitementData = acteTraitementRepository.findByidacte(idacte);

if (acteTraitementData.isPresent()) {

Acte_traitement _acteTraitement = acteTraitementData.get();

_acteTraitement.setPriorite(acteTraitement.getPriorite());


return new ResponseEntity<>(acteTraitementRepository.save(_acteTraitement), HttpStatus.OK);
} else {
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}



@GetMapping("/rechercheParId/{typePrestation}/{id}")
public ResponseEntity<List<Acte_traitement>> rechercheParId(@PathVariable("typePrestation") String typePrestationn,@PathVariable("id") String id){
try {
List<Acte_traitement> actes = new ArrayList<>();

Optional<Prestation> _prestation = prestationRepository.findByNomPrestation(typePrestationn);

Prestation prestation = _prestation.get();

if(typePrestationn.equals("Lien NRO-PM") && !id.equals("Pas d'ID")) {
actes.add(nropmRepository.findByCog(id).get());
}else if(typePrestationn.equals("Lien NRO-PM") && id.equals("Pas d'ID")) {
actes.addAll(nropmRepository.findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc("enCoursCds"));
}else if(typePrestationn.equals("Evolution statut IMB") && !id.equals("Pas d'ID")) {
actes.add(esimbRepository.findByCodeBanbou(id).get());
}else if(typePrestationn.equals("Evolution statut IMB") && id.equals("Pas d'ID"))
{
actes.addAll(esimbRepository.findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc("enCoursCds"));
}else if(typePrestationn.equals("Grafic") && !id.equals("Pas d'ID")) {
actes.add(graphicRepository.findByidacte(id));
}else if(typePrestationn.equals("Grafic") && id.equals("Pas d'ID"))
{
actes.addAll(graphicRepository.findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc("enCoursCds"));
}else if(typePrestationn.equals("Désaturations Coupleurs") && !id.equals("Pas d'ID")){
actes.add(desatRepository.findByCog(id).get());
}else if(typePrestationn.equals("Désaturations Coupleurs") && id.equals("Pas d'ID")){
actes.addAll(desatRepository.findByStatutFacturationOrderByPrioriteAscDateDeadlineAsc("enCoursCds"));
}



if (actes.isEmpty()) {
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

return new ResponseEntity<>(actes, HttpStatus.OK);

} catch (Exception e) {
return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}

}



		@GetMapping("/actes")
		public List<Acte_traitement> getAll(){
			return acteTraitementRepository.findAll();
		}
}



