package com.sofrecom.cobli.controller;

import com.sofrecom.cobli.models.*;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import com.sofrecom.cobli.repository.NropmRepository;
import com.sofrecom.cobli.repository.PmRepository;
import com.sofrecom.cobli.repository.PrestationRepository;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sofrecom.cobli.controller.service.Services;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nropm")
public class NropmController {

    @Autowired
    NropmRepository nropmRepository;

    @Autowired
    PmRepository pmRepository;

    @Autowired
    PrestationRepository prestationRepository;


    @Autowired
    CollaborateurRepository collaborateurRepository;

    @Autowired
    Services service;

    @PostMapping("/importNropmATraiter")
    public String importerNropmAtraiter(@RequestParam("file") MultipartFile file) {

        Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Lien NRO-PM");

        Prestation prestation=_prestation.get();



        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
                XSSFRow row = sheet.getRow(i);

                Date dateReception=null;
                if(row.getCell(14) == null || row.getCell(14).toString().equals("") ) {
                    dateReception=null;
                }else{

                    dateReception = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(14).toString());
                }

                Date dateDeadline=null ;

                int nbrjoursmax=prestation.getDeadline();

                Calendar c = Calendar.getInstance();
                c.setTime(dateReception);
                c.add(Calendar.DATE, nbrjoursmax);
                dateDeadline = c.getTime();

                Optional<Nropm> nropm_ = nropmRepository.findByCog(row.getCell(26).toString());

                if(!nropm_.isPresent()) {

                    Nropm nropm = new Nropm(prestation, "FIBRE", 0, "enCoursCds", "nonTraite",dateReception,dateDeadline, row.getCell(26).toString());
                    nropmRepository.save(nropm);
                    
                    //prestation.getActesTraitement().add(nropm);


                }else{
                    Nropm nropm=nropm_.get();
                    if(nropm.getDateLivraison() != null){
                        nropm.setMotifReaffectation("nonTraite");
                        nropm.setPriorite("P1");

                        nropmRepository.save(nropm);
                    }

                }

            }

            return "ok";

        } catch (IOException e) {

            e.printStackTrace();

            return "ko";
        }catch (ParseException e) {
            throw new RuntimeException(e);

        }

    }

    @PostMapping("/importHistoriqueNropm")
    public String importerHistoriqueNropm(@RequestParam("file") MultipartFile file) {


        Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Lien NRO-PM");

        Prestation prestation=_prestation.get();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);


            for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {

                XSSFRow row = sheet.getRow(i);

                Optional<Collaborateur> affectation_ = collaborateurRepository.findByCUID(row.getCell(17).toString());


                int minute=0;
                if(row.getCell(15) == null || row.getCell(15).toString().equals("")  ) {
                    minute=0;
                }else{
                    float minute_ = Float.parseFloat(row.getCell(15).toString());
                    minute = (int)minute_;
                }
                int duree =  minute;


                String motif;
                if(row.getCell(23) == null)
                {
                    motif = "";
                }else
                {
                    if (row.getCell(23).toString().equals("")) {
                        motif = "";

                    } else if(row.getCell(23).toString().equals("En attente")){
                        motif = "enAttente";
                    }else{
                        motif =row.getCell(23).toString();
                    }
                }

                String statutFacturation;

                if(motif.equals("enAttente")){
                    statutFacturation="enCoursCds";
                }else{
                    statutFacturation="facturable";
                }

                Boolean statutTravaux=false;
                if(row.getCell(25) == null || row.getCell(25).toString().equals("")) {
                    statutTravaux=false;
                }else{
                    if (row.getCell(25).toString().equals("Travaux fini"))
                        statutTravaux = true;
                    else
                        statutTravaux = false;

                }

                Date dateReception=null;
                Date dateLivraison=null;
                Date dateReaffectation=null;
                Date dateReaffectationLivraison=null;
                Date dateDeadline=null;

                if(row.getCell(8) == null || row.getCell(8).toString().equals("") ) {
                    dateReception=null;
                }else{

                    dateReception = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(8).toString());
                }

                if(row.getCell(11) == null || row.getCell(11).toString().equals("")) {
                    dateLivraison=null;
                }else{
                    dateLivraison = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(11).toString());
                }

                if( row.getCell(12) == null || row.getCell(12).toString().equals("")) {
                    dateReaffectation=null;
                }else{
                    dateReaffectation = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(12).toString());
                }

                if(row.getCell(13) == null || row.getCell(13).toString().equals("")) {
                    dateReaffectationLivraison=null;
                }else{
                    dateReaffectationLivraison = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(13).toString());
                }

                if(row.getCell(9) == null || row.getCell(9).toString().equals("")) {
                    dateDeadline=null;
                }else{
                    dateDeadline = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(9).toString());
                }

                String commentaire=null;
                if(row.getCell(21) == null || row.getCell(21).toString().equals("")) {
                    commentaire=null;
                }else{
                    commentaire = row.getCell(21).toString();
                }



                String motifReaffectation=null;
                if(row.getCell(24) == null || row.getCell(24).toString().equals("")) {
                    motifReaffectation=null;
                }else{
                    motifReaffectation = row.getCell(24).toString();
                }




                Optional<Nropm> nropm_ = nropmRepository.findByCog(row.getCell(2).toString());


                Nropm nropm;

                Set pms = new HashSet();


                if(affectation_.isPresent())
                {
                    Collaborateur affectation=affectation_.get();
                    if (!nropm_.isPresent()) {
                        nropm = new Nropm(prestation, row.getCell(6).toString(),
                                0, dateReception,
                                dateLivraison, dateLivraison, row.getCell(17).toString(), duree, commentaire,
                                motif,statutFacturation, dateDeadline,row.getCell(10).toString(),row.getCell(0).toString(),
                                row.getCell(2).toString(),
                                dateReaffectation, dateReaffectationLivraison,
                                motifReaffectation, statutTravaux);

                        nropm.setPms(pms);
                        nropmRepository.save(nropm);


                    } else {

                        nropm = nropm_.get();

                    }


                    Optional<Pm> pm_=pmRepository.findByRefPm(row.getCell(3).toString());

                    if(!pm_.isPresent()) {
                        Pm pm = new Pm(row.getCell(3).toString(),
                                row.getCell(4).toString(), nropm);

                        nropm.setQuantite(nropm.getQuantite() + 1);

                        pmRepository.save(pm);

                        nropm.getPms().add(pm);
                    }

                }

            }

            return "ok";

        } catch (IOException e) {

            e.printStackTrace();

            return "ko";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/nropmsNonTraites")
    public ResponseEntity<List<Nropm>> getNropmsNonTraites(){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();

            Collection<String> motifs=Arrays.asList("nonTraite", "enAttente","enCours");

             nropms=nropmRepository.findByMotifIn(motifs);


            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/nropmsTraites")
    public ResponseEntity<List<Nropm>> getNropmsTraites(){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();


            Collection<String> motifs=Arrays.asList("nonTraite", "enAttente","enCours");

            nropms=nropmRepository.findByMotifNotIn(motifs);


            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/affichePms/{cog}")
    public ResponseEntity<List<Pm>> getPms(@PathVariable("cog") String cog){
        try {
            List<Pm> pms= new ArrayList<Pm>();

            Optional<Nropm> nropm_ = nropmRepository.findByCog(cog);


            if(nropm_.isPresent()){

                pms= pmRepository.findByNropm(nropm_.get());


            }


            if (pms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/afficheNropm/{cog}")
    public ResponseEntity<Nropm> afficheNropm(@PathVariable("cog") String cog){
        try {

            Optional<Nropm> nropm_ = nropmRepository.findByCog(cog);


            if(nropm_.isPresent()){

                return new ResponseEntity<>(nropm_.get(), HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/rechercheParUi/{ui}")
    public ResponseEntity<List<Nropm>> rechercherParUi(@PathVariable("ui") String ui){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();




            nropms=nropmRepository.findByUniteIntervention(ui);


            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/rechercheParCog/{cog}")
    public ResponseEntity<List<Nropm>> rechercherParCog(@PathVariable("cog") String cog){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();



            Optional<Nropm> nropm_=nropmRepository.findByCog(cog);

            if (!nropm_.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                nropms.add(nropm_.get());
                return new ResponseEntity<>(nropms, HttpStatus.OK);

            }


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/rechercheParAffectation/{affectation}")
    public ResponseEntity<List<Nropm>> rechercherParAffectation(@PathVariable("affectation") String affectation){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();




            nropms=nropmRepository.findByAffectation(affectation);


            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/ajouterNropm")
    public String creerNropm(@RequestBody Nropm nropm) {
        try {


            if (service.isExisteNropm(nropm)) {
                System.out.println("existe");
                return "existe";
            }
            else {

                Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Lien NRO-PM");

                Prestation prestation = _prestation.get();


                Date dateDeadline = null;

                int nbrjoursmax = prestation.getDeadline();

                Calendar c = Calendar.getInstance();
                c.setTime(nropm.getDateReception());
                c.add(Calendar.DATE, nbrjoursmax);
                dateDeadline = c.getTime();


                Nropm _nropm = nropmRepository
                        .save(new Nropm(prestation, nropm.getType_element(), nropm.getQuantite(),
                                nropm.getDateReception(), nropm.getDateLivraison(), nropm.getDateLivraison(),
                                nropm.getAffectation(), nropm.getDuree(), nropm.getCommentaire(), nropm.getMotif(),
                                nropm.getStatutFacturation(), dateDeadline, "P1", nropm.getUniteIntervention(), nropm.getCog(),
                                nropm.getStatutTravaux(), nropm.getPms()));
                //prestation.getActesTraitement().add(_nropm);

                for (Pm pm : _nropm.getPms()) {
                    pm.setNropm(_nropm);
                    pmRepository.save(pm);
                }

                return "bien enregistré";
            }
        } catch (Exception e) {
            return "Exception";
        }
    }


    @GetMapping("/nropms")
    public ResponseEntity<List<Nropm>> getNropms(){
        try {
            List<Nropm> nropms = new ArrayList<Nropm>();


            nropms=nropmRepository.findAll();


            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/rechercheParMotif/{motif}")
    public ResponseEntity<List<Nropm>> rechercherParMotif(@PathVariable("motif") String[] motif){

        List  motifs=Arrays.asList(motif);

        Collections.replaceAll(motifs, "livre", "");


        try {
            List<Nropm> nropms = new ArrayList<Nropm>();

          /*  if(motif.equals("nonTraite")){
                nropms=nropmRepository.findByMotifOrMotifReaffectation("nonTraite","nonTraite");
            }else if(motif.equals("enAttente")){
                nropms=nropmRepository.findByMotif("enAttente");
            }if(motif.equals("enCours")){
                nropms=nropmRepository.findByMotifOrMotifReaffectation("enCours","enCours");
            }if(motif.equals("livre")){
                nropms=nropmRepository.findByMotif("");
            }if(motif.equals("rejet")){
                nropms=nropmRepository.findByMotifNotIn(motifs);
            }*/


            nropms=nropmRepository.findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(motifs, Arrays.asList(motif));




            if (nropms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(nropms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/modifierNropm/{cog}")
    public ResponseEntity<Nropm> updateNropm(@PathVariable("cog") String cog, @RequestBody Nropm nropm) {
        Optional<Nropm> nropmData = nropmRepository.findByCog(cog);

        if (nropmData.isPresent()) {
            Nropm _nropm = nropmData.get();

            _nropm.setRefTacheBPU(nropm.getRefTacheBPU());

            _nropm.setType_prestation(nropm.getType_prestation());
            _nropm.setType_element(nropm.getType_element());
            _nropm.setQuantite(nropm.getQuantite());
            _nropm.setDateReception((nropm.getDateReception()));
            _nropm.setDateLivraison(nropm.getDateLivraison());
            _nropm.setDateValidation(nropm.getDateValidation());
            _nropm.setAffectation(nropm.getAffectation());
            _nropm.setDuree(nropm.getDuree());
            _nropm.setCommentaire(nropm.getCommentaire());
            _nropm.setMotif(nropm.getMotif());
            _nropm.setStatutFacturation(nropm.getStatutFacturation());
            _nropm.setDateReprise(nropm.getDateReprise());
            _nropm.setRepriseFacturable(nropm.getRepriseFacturable());
            _nropm.setUniteIntervention((nropm.getUniteIntervention()));
            _nropm.setCog(nropm.getCog());
            _nropm.setDateReafectation(nropm.getDateReafectation());
            _nropm.setDateLivraisonReaffectation(nropm.getDateLivraisonReaffectation());
            _nropm.setMotifReaffectation(nropm.getMotifReaffectation());
            _nropm.setStatutTravaux(nropm.getStatutTravaux());
            _nropm.setPms(nropm.getPms());



            return new ResponseEntity<>(nropmRepository.save(_nropm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/traiterNropm/{cog}")
    public ResponseEntity<Nropm> traiterNropm(@PathVariable("cog") String cog, @RequestBody Nropm nropm) {
        Optional<Nropm> nropmData = nropmRepository.findByCog(cog);

        if (nropmData.isPresent()) {
            Nropm _nropm = nropmData.get();

            _nropm.setRefTacheBPU(nropm.getRefTacheBPU());

            _nropm.setType_prestation(nropm.getType_prestation());
            _nropm.setType_element(nropm.getType_element());
            _nropm.setQuantite(nropm.getQuantite());
            _nropm.setDateReception((nropm.getDateReception()));
            _nropm.setDateLivraison(nropm.getDateLivraison());
            _nropm.setDateValidation(nropm.getDateValidation());
            _nropm.setAffectation(nropm.getAffectation());
            _nropm.setDuree(nropm.getDuree());
            _nropm.setCommentaire(nropm.getCommentaire());
            _nropm.setMotif(nropm.getMotif());
            _nropm.setStatutFacturation(nropm.getStatutFacturation());
            _nropm.setDateReprise(nropm.getDateReprise());
            _nropm.setRepriseFacturable(nropm.getRepriseFacturable());
            _nropm.setUniteIntervention((nropm.getUniteIntervention()));
            _nropm.setCog(nropm.getCog());
            _nropm.setDateReafectation(nropm.getDateReafectation());
            _nropm.setDateLivraisonReaffectation(nropm.getDateLivraisonReaffectation());
            _nropm.setMotifReaffectation(nropm.getMotifReaffectation());
            _nropm.setStatutTravaux(nropm.getStatutTravaux());
            _nropm.setPms(nropm.getPms());


            for (Pm pm : _nropm.getPms()) {
                pm.setNropm(_nropm);
                pmRepository.save(pm);
            }



            return new ResponseEntity<>(nropmRepository.save(_nropm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/mettreEnCours/{cog}")
    public ResponseEntity<Nropm> mettreEnCours(@PathVariable("cog") String cog, @RequestBody Nropm nropm) {
        Optional<Nropm> nropmData = nropmRepository.findByCog(cog);

        if (nropmData.isPresent()) {

            Nropm _nropm = nropmData.get();

            if(_nropm.getDateLivraison() == null) {
                _nropm.setMotif("enCours");
            }else{
                _nropm.setMotifReaffectation("enCours");
            }


            return new ResponseEntity<>(nropmRepository.save(_nropm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/annulerEnCours/{cog}")
    public ResponseEntity<Nropm> annulerEnCours(@PathVariable("cog") String cog, @RequestBody Nropm nropm) {
        Optional<Nropm> nropmData = nropmRepository.findByCog(cog);

        if (nropmData.isPresent()) {
            Nropm _nropm = nropmData.get();

            if(_nropm.getDateLivraison() == null) {
                _nropm.setMotif("nonTraite");
            }else {
                _nropm.setMotifReaffectation("nonTraite");
            }


            return new ResponseEntity<>(nropmRepository.save(_nropm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
















}
