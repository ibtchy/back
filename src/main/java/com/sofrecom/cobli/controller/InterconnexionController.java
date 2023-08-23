package com.sofrecom.cobli.controller;


import com.sofrecom.cobli.controller.service.InterconnexionService;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.Interconnexion;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.InterconnexionRepository;
import com.sofrecom.cobli.repository.PrestationRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/interconnexion")
public class InterconnexionController {



    @Autowired
    InterconnexionRepository interRepo ;
    @Autowired
    InterconnexionService interService ;
    @Autowired
    PrestationRepository presRepo;



    @PostMapping("/addInterconnexion")
    public  Interconnexion addInterconnexion (@RequestBody Interconnexion interconnexion) {

        return interService.InterconnextionAdd(interconnexion);
    }

    @PostMapping("/EditInterconnexion")
    public  Interconnexion EditInterconnexion (@RequestBody Interconnexion interconnexion,@RequestParam String id){

        return  interService.InterconnextionEdit(id ,interconnexion);
    }
    @PostMapping("excelImportPhase1")
    public String excelReaderPhase1(@RequestParam("file") MultipartFile excel) throws Exception {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            String motif = "";
            String ZONE  = "";
            String FI = "";
            Date date_refus= null;
            String motifReaffectation=null;
            String statut_operatonnels = "" ;
            Date date_reafictation = null;
            String COG = "";
            String ui = "";
            Date date_liv_reafictation = null;
            String type_element ="Coupleurs"  ;
            int quantite = 1;
            Date dateReception = null;
            Date dateValidation = null;
            Date dateFlux = null;
            Date dateDepotBanbou = null;
            Date dateRetourBanbou = null;
            String affectation = "";
            Date dateLivraison= null;
            int duree = 1;
            String commentaire = "";
            String refTacheBPU = "" ;
            String priorite ="P1";
            Date dateDeadline = null;


            for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {


                XSSFRow row = sheet.getRow(i);
                if (row == null || interService.isEmptyRow(row)) {
                    break; // Sortir de la boucle si la ligne est vide
                }

                //for(int j=0;j<row.getPhysicalNumberOfCells();j++) {

                System.out.println(i);
                DataFormatter dataformatter = new DataFormatter();
                ui = dataformatter.formatCellValue(row.getCell(0)).toString();
                System.out.println("----------------------------- 2   " );

                COG = dataformatter.formatCellValue(row.getCell(2)).toString();
                System.out.println("----------------------------- 3    " );


              //  quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(7)).toString());

                if (row.getCell(7) == null || row.getCell(7).toString().trim().isEmpty()) {
                    dateFlux = null;
                } else if (row.getCell(7).getCellType() == CellType.NUMERIC) {
                    dateFlux = row.getCell(7).getDateCellValue();
                } else {
                    String dateStr = row.getCell(7).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateFlux = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateFlux = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateFlux = null;
                        }
                    }
                }


                if (row.getCell(8) == null || row.getCell(8).toString().trim().isEmpty()) {
                    dateReception = null;
                } else if (row.getCell(8).getCellType() == CellType.NUMERIC) {
                    dateReception = row.getCell(8).getDateCellValue();
                } else {
                    String dateStr = row.getCell(8).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateReception = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateReception = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateReception = null;
                        }
                    }
                }



                if (row.getCell(9) == null || row.getCell(9).toString().trim().isEmpty()) {
                    dateDepotBanbou = null;
                } else if (row.getCell(9).getCellType() == CellType.NUMERIC) {
                    dateDepotBanbou = row.getCell(9).getDateCellValue();
                } else {
                    String dateStr = row.getCell(9).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateDepotBanbou = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateDepotBanbou = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateDepotBanbou = null;
                        }
                    }
                }

                if (row.getCell(10) == null || row.getCell(10).toString().trim().isEmpty()) {
                    dateRetourBanbou = null;
                } else if (row.getCell(10).getCellType() == CellType.NUMERIC) {
                    dateRetourBanbou = row.getCell(10).getDateCellValue();
                } else {
                    String dateStr = row.getCell(10).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateRetourBanbou = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateRetourBanbou = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateRetourBanbou = null;
                        }
                    }
                }
                if (row.getCell(11) == null || row.getCell(11).toString().trim().isEmpty()) {
                    dateLivraison = null;
                } else if (row.getCell(11).getCellType() == CellType.NUMERIC) {
                    dateLivraison = row.getCell(11).getDateCellValue();
                } else {
                    String dateStr = row.getCell(11).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateLivraison = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateLivraison = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateLivraison = null;
                        }
                    }
                }

                //duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(14)).toString()) );


                affectation = dataformatter.formatCellValue(row.getCell(17)).toString();

                commentaire = dataformatter.formatCellValue(row.getCell(18)).toString();


                statut_operatonnels = dataformatter.formatCellValue(row.getCell(19)).toString();


                motif =  dataformatter.formatCellValue(row.getCell(19)).toString();

                Optional<Prestation> prestation = presRepo.findByNomPrestation("Interconnexion RIP Tiers");
                Prestation pres= prestation.get();
                String statutFacturation;

                if(motif.equals("enAttente")){
                    statutFacturation="enCoursCds";
                }else{
                    statutFacturation="facturable";
                }
             Interconnexion interc = new Interconnexion() ;
                interc.setCog(COG);
                interc.setDateDepBan(dateDepotBanbou);
                interc.setDateRetourBan(dateRetourBanbou);
                interc.setDateLivraison(dateLivraison);
                interc.setDateFlux(dateFlux);
                interc.setMotif(motif);
                interc.setDateReception(dateReception);
                interc.setStatutFacturation(statutFacturation);
                interc.setAffectation(affectation);
                interc.setQuantite(1);
                interc.setRefTacheBPU(refTacheBPU);
                interc.setPriorite(priorite);
                interc.setCommentaire(commentaire);
                interc.setUi(ui);

                interc.setType_prestation(prestation.get());
                interc.setType_element(null);
                interc.setPriorite("P1");
                interc.setDuree(7);
                interc.setRepriseFacturable("");
                interc.setPhase("phase 1");



                if(!interService.isExisteInterConnexion(interc)) {
                    interRepo.save(interc);
                }

                else {
                    System.out.println("existedéja"+ interc.toString());
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Success";
    }

    @PostMapping("excelImportPhase2")
    public String excelReaderPhase2(@RequestParam("file") MultipartFile excel) throws Exception {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            String motif = "";
            String ZONE  = "";
            String FI = "";
            Date date_refus= null;
            String motifReaffectation=null;
            String statut_operatonnels = "" ;
            Date date_reafictation = null;
            String COG = "";
            String ui = "";
            Date date_liv_reafictation = null;
            String type_element ="Coupleurs"  ;
            int quantite = 1;
            Date dateReception = null;
            Date dateValidation = null;
            Date dateFlux = null;
            Date dateDepotBanbou = null;
            Date dateRetourBanbou = null;
            String affectation = "";
            Date dateLivraison= null;
            int duree = 1;
            String commentaire = "";
            String refTacheBPU = "" ;
            String priorite ="P1";
            Date dateDeadline = null;


            for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {


                XSSFRow row = sheet.getRow(i);
                if (row == null || interService.isEmptyRow(row)) {
                    break; // Sortir de la boucle si la ligne est vide
                }

                //for(int j=0;j<row.getPhysicalNumberOfCells();j++) {

                System.out.println(i);
                DataFormatter dataformatter = new DataFormatter();
                ui = dataformatter.formatCellValue(row.getCell(0)).toString();
                System.out.println("----------------------------- 2   " );

                COG = dataformatter.formatCellValue(row.getCell(2)).toString();
                System.out.println("----------------------------- 3    " );


                //  quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(7)).toString());

                if (row.getCell(7) == null || row.getCell(7).toString().trim().isEmpty()) {
                    dateFlux = null;
                } else if (row.getCell(7).getCellType() == CellType.NUMERIC) {
                    dateFlux = row.getCell(7).getDateCellValue();
                } else {
                    String dateStr = row.getCell(7).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateFlux = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateFlux = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateFlux = null;
                        }
                    }
                }


                if (row.getCell(8) == null || row.getCell(8).toString().trim().isEmpty()) {
                    dateReception = null;
                } else if (row.getCell(8).getCellType() == CellType.NUMERIC) {
                    dateReception = row.getCell(8).getDateCellValue();
                } else {
                    String dateStr = row.getCell(8).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateReception = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateReception = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateReception = null;
                        }
                    }
                }



                if (row.getCell(9) == null || row.getCell(9).toString().trim().isEmpty()) {
                    dateDepotBanbou = null;
                } else if (row.getCell(9).getCellType() == CellType.NUMERIC) {
                    dateDepotBanbou = row.getCell(9).getDateCellValue();
                } else {
                    String dateStr = row.getCell(9).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateDepotBanbou = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateDepotBanbou = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateDepotBanbou = null;
                        }
                    }
                }

                if (row.getCell(10) == null || row.getCell(10).toString().trim().isEmpty()) {
                    dateRetourBanbou = null;
                } else if (row.getCell(10).getCellType() == CellType.NUMERIC) {
                    dateRetourBanbou = row.getCell(10).getDateCellValue();
                } else {
                    String dateStr = row.getCell(10).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateRetourBanbou = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateRetourBanbou = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateRetourBanbou = null;
                        }
                    }
                }
                if (row.getCell(11) == null || row.getCell(11).toString().trim().isEmpty()) {
                    dateLivraison = null;
                } else if (row.getCell(11).getCellType() == CellType.NUMERIC) {
                    dateLivraison = row.getCell(11).getDateCellValue();
                } else {
                    String dateStr = row.getCell(11).toString().trim();
                    if (dateStr.isEmpty()) {
                        dateLivraison = null;
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            dateLivraison = dateFormat.parse(dateStr);
                        } catch (ParseException e) {
                            // handle parse exception
                            e.printStackTrace();
                            dateLivraison = null;
                        }
                    }
                }

                //duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(14)).toString()) );


                affectation = dataformatter.formatCellValue(row.getCell(17)).toString();

                commentaire = dataformatter.formatCellValue(row.getCell(18)).toString();


                statut_operatonnels = dataformatter.formatCellValue(row.getCell(19)).toString();


                motif =  dataformatter.formatCellValue(row.getCell(19)).toString();

                Optional<Prestation> prestation = presRepo.findByNomPrestation("Interconnexion RIP Tiers");
                Prestation pres= prestation.get();
                String statutFacturation;

                if(motif.equals("enAttente")){
                    statutFacturation="enCoursCds";
                }else{
                    statutFacturation="facturable";
                }
                Interconnexion interc = new Interconnexion() ;
                interc.setCog(COG);
                interc.setDateDepBan(dateDepotBanbou);
                interc.setDateRetourBan(dateRetourBanbou);
                interc.setDateLivraison(dateLivraison);
                interc.setDateFlux(dateFlux);
                interc.setMotif(motif);
                interc.setDateReception(dateReception);
                interc.setStatutFacturation(statutFacturation);
                interc.setAffectation(affectation);
                interc.setQuantite(1);
                interc.setRefTacheBPU(refTacheBPU);
                interc.setPriorite(priorite);
                interc.setCommentaire(commentaire);
                interc.setUi(ui);

                interc.setType_prestation(prestation.get());
                interc.setType_element(null);
                interc.setPriorite("P1");
                interc.setDuree(7);
                interc.setRepriseFacturable("");
                interc.setPhase("phase 2");



                if(!interService.isExisteInterConnexion(interc)) {
                    interRepo.save(interc);
                }

                else {
                    System.out.println("existedéja"+ interc.toString());
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Success";
    }


    @GetMapping("/retrieveall")
    public List<Interconnexion> retrieveall(){
        return interService.retrieveall();
    }

}


