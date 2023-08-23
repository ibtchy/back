package com.sofrecom.cobli.controller;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sofrecom.cobli.controller.service.Services;
import com.sofrecom.cobli.controller.service.TarificationService;
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.ControleTravaux;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import com.sofrecom.cobli.repository.DESATRepository;
import com.sofrecom.cobli.repository.PrestationRepository;
import com.sofrecom.cobli.repository.TarificationRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DesatController {

	@Autowired
	DESATRepository desatRepo;
	
	@Autowired
	PrestationRepository prestationRepo;
	
	@Autowired
	Acte_traitementRepository actetraitementRepository;
	
	@Autowired 
	CollaborateurRepository collaborateurRepository;
	
	@Autowired
	TarificationRepository repoTarif;
	
	@Autowired
	TarificationService tarifService;
	@Autowired
	Services service;
	
	
	// import fichier desat 
	
	@PostMapping("dexcel")
	public String excelReader(@RequestParam("file") MultipartFile excel) throws Exception {
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
			String affectation = "";
			Date dateLivraison= null;
			int duree = 1;
			String commentaire = "";
			String refTacheBPU = "" ;
			String priorite ="P1";
			Date dateDeadline = null;
			
			
			/*
			String prenom = "";
			String nom = "";
			int indxagent = 0;*/
			
			for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
				XSSFRow row = sheet.getRow(i);
				//for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
				System.out.println("----------------------------- 1    " );

					DataFormatter dataformatter = new DataFormatter();
					ui = dataformatter.formatCellValue(row.getCell(0)).toString();
					System.out.println("----------------------------- 2   " );
					
					COG = dataformatter.formatCellValue(row.getCell(2)).toString();
					System.out.println("----------------------------- 3    " );
					
                   //type_prestation = dataformatter.formatCellValue(row.getCell(3)).toString();
					//System.out.println("----------------------------- ok    " );
					
					//type_element = dataformatter.formatCellValue(row.getCell(4)).toString();
					System.out.println("----------------------------- 4    " );
					
				    quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(5)).toString());
					System.out.println("----------------------------- 5    " );
					
					ZONE = dataformatter.formatCellValue(row.getCell(6)).toString();
					System.out.println("----------------------------- 5    " );
					
					FI = dataformatter.formatCellValue(row.getCell(7)).toString();
					System.out.println("----------------------------- 6    " );
					
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
					
					System.out.println("----------------------------- 7   " );
					if (row.getCell(9) == null || row.getCell(9).toString().trim().isEmpty()) {
						dateDeadline = null;
					} else if (row.getCell(9).getCellType() == CellType.NUMERIC) {
						dateDeadline = row.getCell(9).getDateCellValue();
					} else {
					    String dateStr = row.getCell(9).toString().trim();
					    if (dateStr.isEmpty()) {
					    	dateDeadline = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					        	dateDeadline = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            dateDeadline = null;
					        }
					    }
					}

					System.out.println("----------------------------- 8    " );
					//date_reception = dataformatter.formatCellValue(row.getCell(8)).toString();
					//System.out.println("----------------------------- ok    " );
					
					if (row.getCell(11) == null || row.getCell(11).toString().trim().isEmpty()) {
						date_refus = null;
					} else if (row.getCell(11).getCellType() == CellType.NUMERIC) {
						date_refus = row.getCell(11).getDateCellValue();
					} else {
					    String dateStr = row.getCell(11).toString().trim();
					    if (dateStr.isEmpty()) {
					    	date_refus = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					        	date_refus = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            date_refus = null;
					        }
					    }
					}
					//date_refus = dataformatter.formatCellValue(row.getCell(9)).toString();
					//System.out.println("----------------------------- ok    " );
					System.out.println("----------------------------- 9    " );
				if (row.getCell(12) == null || row.getCell(12).toString().trim().isEmpty()) {
					    dateLivraison = null;
					} else if (row.getCell(12).getCellType() == CellType.NUMERIC) {
					    dateLivraison = row.getCell(12).getDateCellValue();
					} else {
					    String dateStr = row.getCell(12).toString().trim();
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
				System.out.println("----------------------------- 10   " );
					//date_livraison  = dataformatter.formatCellValue(row.getCell(10)).toString();
					//System.out.println("----------------------------- ok    " );
					
					if (row.getCell(13) == null || row.getCell(12).toString().trim().isEmpty()) {
						date_reafictation = null;
					} else if (row.getCell(13).getCellType() == CellType.NUMERIC) {
						date_reafictation = row.getCell(13).getDateCellValue();
					} else {
					    String dateStr = row.getCell(13).toString().trim();
					    if (dateStr.isEmpty()) {
					    	date_reafictation = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					        	date_reafictation = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            dateLivraison = null;
					        }
					    }
					}
					System.out.println("----------------------------- 11   " );
					//date_reafictation  = dataformatter.formatCellValue(row.getCell(12)).toString();
					//System.out.println("----------------------------- ok    " );
					
					if (row.getCell(14) == null || row.getCell(14).toString().trim().isEmpty()) {
						date_liv_reafictation = null;
					} else if (row.getCell(14).getCellType() == CellType.NUMERIC) {
						date_liv_reafictation = row.getCell(14).getDateCellValue();
					} else {
					    String dateStr = row.getCell(14).toString().trim();
					    if (dateStr.isEmpty()) {
					    	date_liv_reafictation = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					        	date_liv_reafictation = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            dateLivraison = null;
					        }
					    }
					}
					System.out.println("----------------------------- 12    " );
					//date_liv_reafictation  = dataformatter.formatCellValue(row.getCell(13)).toString();
					//System.out.println("----------------------------- ok    " );
					
				
					duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(15)).toString()) * 60) + Integer.parseInt(dataformatter.formatCellValue(row.getCell(16)).toString());
					System.out.println("----------------------------- 13    " );
					
					if (row.getCell(17) == null || row.getCell(17).toString().trim().isEmpty()) {
					    dateValidation = null;
					} else if (row.getCell(17).getCellType() == CellType.NUMERIC) {
					    dateValidation = row.getCell(17).getDateCellValue();
					} else {
					    String dateStr = row.getCell(17).toString().trim();
					    if (dateStr.isEmpty()) {
					        dateValidation = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					            dateValidation = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            dateValidation = null;
					        }
					    }
					}
					System.out.println("----------------------------- 14   " );
					//date_validation = dataformatter.formatCellValue(row.getCell(16)).toString();
					//System.out.println("----------------------------- ok    " );
					
					affectation = dataformatter.formatCellValue(row.getCell(18)).toString();
					System.out.println("----------------------------- 15    " );
					
					commentaire = dataformatter.formatCellValue(row.getCell(22)).toString();
					System.out.println("----------------------------- 16    " );
					
					statut_operatonnels = dataformatter.formatCellValue(row.getCell(23)).toString();
					System.out.println("----------------------------- 17   " );
					
					motif =  dataformatter.formatCellValue(row.getCell(24)).toString(); 
					
					
		                if(row.getCell(27) == null || row.getCell(27).toString().equals("")) {
		                    motifReaffectation=null;
		                }else{
		                    motifReaffectation = row.getCell(27).toString();
		                }
					System.out.println("----------------------------- 18   " );
					priorite =  dataformatter.formatCellValue(row.getCell(10)).toString(); 
					System.out.println("----------------------------- 20  " );
					Optional<Prestation> prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
					 Prestation pres= prestation.get();
					  String statutFacturation;

		                if(motif.equals("En attente")){
		                    statutFacturation="enCoursCds";
		                }else{
		                    statutFacturation="facturable";
		                }
			    	DESAT desat = new DESAT(   
								                  ZONE,
								                  FI,
								                  date_refus,
								                  motifReaffectation,
								                  statut_operatonnels,							              
								                  date_reafictation,// date_reafictation
								                  COG,
								                  ui,
								                  date_liv_reafictation,// date_liv_reafictation
											      pres,//type_prestation
												  type_element,//type_element
												  dateReception,//dateReception
												  quantite,//quantite
												  dateLivraison,//dateLivraison
												  dateValidation,//dateValidation
												  affectation,//affectation
												  commentaire,//commentaire
												  duree,//duree  
												  refTacheBPU,
												  motif,//refTacheBPU
												  dateDeadline,
												  priorite,
												  statutFacturation
												  );
					
				
					if(!service.isExisteDesat(desat)) {
						desatRepo.save(desat);
					}
					
					else {
						System.out.println("existedéja"+ desat.toString());
					}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";
	}
	// import les dossier COG
	
	@PostMapping("/importDesatATraiter")
	public String importerDesatAtraiter(@RequestParam("file") MultipartFile file) {
	
	String message = "" ;
	String motif_desat="nonTraite";
	String type_element="Coupleurs";
	Optional<Prestation> prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
	Prestation pres= prestation.get();

	try {
	XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	XSSFSheet sheet = workbook.getSheetAt(0);

	for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
	XSSFRow row = sheet.getRow(i);
	
	 Date dateReception=null;
     if(row.getCell(22) == null || row.getCell(22).toString().equals("") ) {
         dateReception=null;
     }else{

         dateReception = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(22).toString());
     }
     
     Date dateDeadline=null ;

     int nbrjoursmax=pres.getDeadline();
     Calendar c = Calendar.getInstance();
     c.setTime(dateReception);
     c.add(Calendar.DATE, nbrjoursmax);
     dateDeadline = c.getTime();
     
      Optional<DESAT> desat_ = desatRepo.findByCog(row.getCell(20).toString());
	
       if(!desat_.isPresent()) {

	//DESAT desat = new DESAT(row.getCell(20).toString(),motif_desat,type_element, row.getCell(24).toString(),pres,row.getCell(4).toString());
	DESAT desat = new DESAT(row.getCell(20).toString(),row.getCell(4).toString(),motif_desat,type_element, row.getCell(24).toString(),pres,0,"enCoursCds", dateReception,dateDeadline);
	desatRepo.save(desat);

	} else{
        DESAT desat=desat_.get();
        if(desat.getDateLivraison() != null){
        	desat.setMotifReaffectation("nonTraite");
        	desat.setPriorite("P1");
        	desatRepo.save(desat);
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

	// add desat methode 1 
		 @PostMapping("/desatAdd")
			public String addDesat(@RequestBody DESAT desat){
		    
				
		        try {
		         if (service.isExisteDesat(desat)) {
		        		System.out.println("existe");
		        		return "existe";
					} 
		        
		        	else {
		        	 //Type prestation
		        		 Optional<Prestation> _prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
		                 Prestation prestation = _prestation.get();		      
		                 Date dateDeadline = null;
		                 int nbrjoursmax = prestation.getDeadline();
		                 Calendar c = Calendar.getInstance();
		                 c.setTime(desat.getDateReception());
		                 c.add(Calendar.DATE, nbrjoursmax);
		                 dateDeadline = c.getTime();
		      
		                DESAT desatt= new DESAT(
	                    		 desat.getIdacte(),
	                    		 desat.getRefTacheBPU(),
	     						prestation,
	     						desat.getType_element(),
	     						desat.getQuantite(),
	     						desat.getDateReception(),
	     						desat.getDateLivraison(),
	     						desat.getDateValidation(),
	     						desat.getAffectation(),
	     						desat.getDuree(),
	     						desat.getCommentaire(),
	     				        desat.getMotif(),
	     				        desat.getStatutFacturation(),
	     				        desat.getDateReprise(),
	     				        desat.getRepriseFacturable(),
	     				        dateDeadline,
	     				        "P1",  
	     				        desat.getZone(),
	     				        desat.getFi(),
	     				        desat.getDate_refus(),
	     				        desat.getMotifReaffectation(),
	     				        desat.getStatut_operatonnels(),
	     				        desat.getDate_reafictation(),
	     				        desat.getCog(),
	     				        desat.getUi(),
	     				        desat.getDate_liv_reafictation()
	     				      
	                    		 );
		                Tarification tarif =  tarifService.calculerTypeTarifDesat(desatt);
		           	 desatt.setTarif(tarif);
		           	 desatRepo.save(desatt);
		           	 
		                System.out.println("done");
		                return "ok";
		        	}
		       } catch (Exception e) {
		               return "KO : "+e.getMessage();
		       }
		        
			}
	 
	 
	/* @PostMapping("/affectationTarifDesat")
	 public DESAT affectationTarifDesat(@RequestBody DESAT desat) {
		 
		 Tarification tarif =  tarifService.calculerTypeTarifDesat(desat);
       	 desat.setTarif(tarif);
       	 desatRepo.save(desat);
		 return desat;
		}*/
	 
	 
	// traiter desat methode 1 (ne marche pas)
		/* @PostMapping("/traiter")
			public String TraiterDesat(@RequestBody DESAT desat){
		    
			
		        		// Type prestation
		        		 Optional<Prestation> _prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
		                 Prestation prestation = _prestation.get();
		                 // Calcul date deadline
		                 Date dateDeadline = null;
		                 int nbrjoursmax = prestation.getDeadline();
		                 Calendar c = Calendar.getInstance();
		                 c.setTime(desat.getDateReception());
		                 c.add(Calendar.DATE, nbrjoursmax);
		                 dateDeadline = c.getTime();
		                 
		                 DESAT _desat = desatRepo
		                         .save(new DESAT(
		                        		 desat.getIdacte(),
		                        		 desat.getRefTacheBPU(),
	             						prestation,
	             						desat.getType_element(),
	             						desat.getQuantite(),
	             						desat.getDateReception(),
	             						desat.getDateLivraison(),
	             						desat.getDateValidation(),
	             						desat.getAffectation(),
	             						desat.getDuree(),
	             						desat.getCommentaire(),
	             				        desat.getMotif(),
	             				        desat.getStatutFacturation(),
	             				        desat.getDateReprise(),
	             				        desat.getRepriseFacturable(),
	             				        dateDeadline,
	             				        "P1",   
	             				        desat.getZone(),
	             				        desat.getFi(),
	             				        desat.getDate_refus(),
	             				        desat.getMotifReaffectation(),
	             				        desat.getStatut_operatonnels(),
	             				        desat.getDate_reafictation(),
	             				        desat.getCog(),
	             				        desat.getUi(),
	             				        desat.getDate_liv_reafictation()
	       
		                        		 ));
		                System.out.println("done");
		                return "ok";
		        
		        
			}*/
		 
	// add desat methode 2
		@PostMapping("/desats")
		public String create(@Valid @RequestBody DESAT desat) {
			 try {
		        	if (service.isExisteDesat(desat)) {
		        		System.out.println("existe");
		        		return "existe";
					} else {
						Optional<Prestation> _prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
		                 Prestation prestation = _prestation.get();
						Acte_traitement actetrait= new Acte_traitement(desat.getIdacte(),prestation);
				        desatRepo.save(desat);
						actetraitementRepository.save(actetrait);
				        return "ok";
					       }
		        	}catch (Exception e) {
		                return "KO : "+e.getMessage();
			        }
			
			 
	    }
		
		
		 //Update desat
		@PutMapping("/desats/{idacte}")
		public ResponseEntity<DESAT> updateDesat(@PathVariable("idacte") String idacte, @RequestBody DESAT desat) {
			Optional<DESAT> desatData = desatRepo.findById(idacte);

	
			if (desatData.isPresent()) {
				DESAT _desat = desatData.get();
				_desat.setRefTacheBPU(desat.getRefTacheBPU());
				_desat.setType_prestation(desat.getType_prestation());
				_desat.setType_element(desat.getType_element());
				_desat.setQuantite(desat.getQuantite());
				_desat.setDateReception(desat.getDateReception());
				_desat.setDateLivraison(desat.getDateLivraison());
				_desat.setDateValidation(desat.getDateValidation());
				_desat.setAffectation(desat.getAffectation());
				_desat.setDuree(desat.getDuree());
				_desat.setMotif(desat.getMotif());
				_desat.setCommentaire(desat.getCommentaire());
				_desat.setRepriseFacturable(desat.getRepriseFacturable());
				_desat.setStatutFacturation(desat.getStatutFacturation());
				_desat.setDateReprise(desat.getDateReprise());		
				_desat.setFi(desat.getFi());
				_desat.setZone(desat.getZone());
				_desat.setDate_refus(desat.getDate_refus());
				_desat.setStatut_operatonnels(desat.getStatut_operatonnels());
				_desat.setDate_reafictation(desat.getDate_reafictation());
				_desat.setCog(desat.getCog());
				_desat.setUi(desat.getUi());
				_desat.setDate_liv_reafictation(desat.getDate_liv_reafictation());
				_desat.setPriorite(desat.getPriorite());
				_desat.setDateDeadline(desat.getDateDeadline());
			
				System.out.println("modif");
						return new ResponseEntity<>(desatRepo.save(_desat), HttpStatus.OK);
						
			} else {
				System.out.println("pas modifier");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}
		// reaff desat methode 1 
				 @PostMapping("/desatAddReaff")
					public String reaffDesat(@RequestBody DESAT desat){
				    
						
				        try {
				         if ((service.isExisteDesat(desat)) || (service.isExisteDesatR(desat))) {
				        		System.out.println("existe");
				        		return "existe";
							} 
				        
				        	else {
				        	 //Type prestation
				        		 Optional<Prestation> _prestation = prestationRepo.findByNomPrestation("Désaturations Coupleurs");
				                 Prestation prestation = _prestation.get();
				              
				                 Date dateDeadline = null;
				                 int nbrjoursmax = prestation.getDeadline();
				                 Calendar c = Calendar.getInstance();
				                 c.setTime(desat.getDateReception());
				                 c.add(Calendar.DATE, nbrjoursmax);
				                 dateDeadline = c.getTime();
				                
				               /*  int reaffectationCount = 1;
			                        String newCog = desat.getCog() + "-R" + reaffectationCount;
			                        while (desatRepo.findByCog(newCog).isPresent()) {
			                            reaffectationCount++;
			                            newCog = desat.getCog() + "-R" + reaffectationCount;
			                        }*/
			                       
				                DESAT desatt= new DESAT(
			                    		 desat.getIdacte(),
			                    		 desat.getRefTacheBPU(),
			     						prestation,
			     						desat.getType_element(),
			     						desat.getQuantite(),
			     						desat.getDateReception(),
			     						desat.getDateLivraison(),
			     						desat.getDateValidation(),
			     						desat.getAffectation(),
			     						desat.getDuree(),
			     						desat.getCommentaire(),
			     				        desat.getMotif(),
			     				        desat.getStatutFacturation(),
			     				        desat.getDateReprise(),
			     				        desat.getRepriseFacturable(),
			     				        dateDeadline,
			     				        "P1",  
			     				        desat.getZone(),
			     				        desat.getFi(),
			     				        desat.getDate_refus(),
			     				        desat.getMotifReaffectation(),
			     				        desat.getStatut_operatonnels(),
			     				        desat.getDate_reafictation(),
			     				        desat.getCog(),
			     				        desat.getUi(),
			     				        desat.getDate_liv_reafictation()
			     				      
			                    		 );
				                System.out.println(desat.getCog());
				                Tarification tarif =  tarifService.calculerTypeTarifDesat(desatt);
				                desatt.setTarif(tarif);
				           	   
		                         
		                   
				           	 //desatt.setCog(newCog);
				           	 desatRepo.save(desatt);
				         	 
				                System.out.println("done");
				               
				                
				                return "ok";
				                
				        	}
				           
				       } catch (Exception e) {
				               return "KO : "+e.getMessage();
				       }	        
					}
				 
				 @GetMapping("/checkCogExistence/{cog}")
				 public boolean existsByCog(String cog) {
				        return desatRepo.existsByCog(cog);
				    }
		
		 //Traiter desat
		@PutMapping("/traiter/{idacte}")
		public ResponseEntity<DESAT> TraiterDesat(@PathVariable("idacte") String idacte, @RequestBody DESAT desat) {
			Optional<DESAT> desatData = desatRepo.findById(idacte);

	
			if (desatData.isPresent()) {
				DESAT _desat = desatData.get();
				_desat.setRefTacheBPU(desat.getRefTacheBPU());
				_desat.setType_prestation(desat.getType_prestation());
				_desat.setType_element(desat.getType_element());
				_desat.setQuantite(desat.getQuantite());
				_desat.setDateReception(desat.getDateReception());
				_desat.setDateLivraison(desat.getDateLivraison());
				_desat.setDateValidation(desat.getDateValidation());
				_desat.setAffectation(desat.getAffectation());
				_desat.setDuree(desat.getDuree());
				_desat.setMotif(desat.getMotif());
				_desat.setCommentaire(desat.getCommentaire());
				_desat.setRepriseFacturable(desat.getRepriseFacturable());
				_desat.setStatutFacturation(desat.getStatutFacturation());
				_desat.setDateReprise(desat.getDateReprise());		
				_desat.setFi(desat.getFi());
				_desat.setZone(desat.getZone());
				_desat.setDate_refus(desat.getDate_refus());
				_desat.setStatut_operatonnels(desat.getStatut_operatonnels());
				_desat.setDate_reafictation(desat.getDate_reafictation());
				_desat.setCog(desat.getCog());
				_desat.setUi(desat.getUi());
				_desat.setDate_liv_reafictation(desat.getDate_liv_reafictation());
				_desat.setPriorite(desat.getPriorite());
				_desat.setDateDeadline(desat.getDateDeadline());
			
				System.out.println("traiter");
						return new ResponseEntity<>(desatRepo.save(_desat), HttpStatus.OK);
						
			} else {
				System.out.println("non traiter");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}
		
			
		
		
	    //Get All Desats
		 @Scheduled(fixedRate = 10000)
			@GetMapping("/desats")
			public List<DESAT> getAll(){
				return desatRepo.findAll();		
			}
			/*@Scheduled(fixedRate = 10000)
			public String test() {
				System.out.println("testt réussi");
				return "test";
			}
			*/
			
			 //Get desat by COG
		/*	@GetMapping("/getDesatByCog/{cogString}")
			public List<DESAT> getDesatByCog(@RequestParam String cogString){
				Optional <DESAT> desat = desatRepo.findByCog(cogString);
				 List<DESAT> desatt = new ArrayList<DESAT>() ;
				 desatt.add(desat.get());
				 return desatt;
			}*/
						
			//Get Esimb by CodeIMB
			@GetMapping("/getDesatByCog")
			public List<DESAT> getDesatByCog(@RequestParam ("cogString") String cogString ){
				return desatRepo.findByCogContaining(cogString);
			}
			
			
			 //Get Desat by affectation
			@GetMapping("/getDesatByAffectation/{affectationString}")
			public List<DESAT> getDesatByAffectation(@PathVariable String affectationString){
				return desatRepo.findByAffectation(affectationString);
			}
			
			 //Get Desat by motif
			@GetMapping("/getDesatByMotif/{motifString}")
			public List<DESAT> getDesatByMotif(@PathVariable String motifString){
				return desatRepo.findByMotif(motifString);
			}
			
			
			// recherche par motif par ordre 
			 @GetMapping("/rechercheParMotif/{motif}")
       public ResponseEntity<List<DESAT>> rechercherParMotif(@PathVariable("motif") String[] motif){
        List  motifs=Arrays.asList(motif);
        Collections.replaceAll(motifs, "livre", "");
        try {
            List<DESAT> desats = new ArrayList<DESAT>();
            desats=desatRepo.findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(motifs, Arrays.asList(motif));
            if (desats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(desats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    } 
			
			// get motif recherche multiple
		/*	@GetMapping("/rechercheParMotif/{motif}")
			public ResponseEntity<List<DESAT>> rechercherParMotif(@PathVariable("motif") String[] motif){

				try {
				List<DESAT> desats = new ArrayList<DESAT>();
				desats=desatRepo.findByMotifInOrMotifReaffectationIn(Arrays.asList(motif), Arrays.asList(motif));
				if (desats.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}

				return new ResponseEntity<>(desats, HttpStatus.OK);
				} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}

				} */
			
			//Get desat by FI
					@GetMapping("/getDesatByFI/{FI}")
					public List<DESAT> getDesatByFI(@PathVariable String FI){
						return desatRepo.findByFi(FI);
					}
					
					//Get desat by FI
					@GetMapping("/getDesatByUi/{ui}")
					public List<DESAT> getDesatByUi(@PathVariable String ui){
						return desatRepo.findByUi(ui);
					}
				
	    //Get Desat by Date de livraison
		@GetMapping("/getDesatByDL/{dateLivraison}")
		public List<DESAT> getDesatByDateLivraison(@PathVariable String dateLivraisonString){
			return desatRepo.findByDateLivraison(dateLivraisonString);
		}
		
		
		
		 @GetMapping("/afficheDesat/{idacte}")
	    public ResponseEntity<DESAT> afficheDesat(@PathVariable String idacte){
	        try {
	            Optional<DESAT> desat_ = desatRepo.findById(idacte);
	            if(desat_.isPresent()){
	                return new ResponseEntity<>(desat_.get(), HttpStatus.OK);
	            }else {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		 
		 
		 
		 
		 
		 
		@GetMapping("/empty-delivery-date")
		 public List<DESAT> getDesatsWithEmptyDeliveryDate() {
		        return desatRepo.findByDateLivraisonIsNull();
		    }
		
		
		@GetMapping("/non-empty-delivery-date")
	    public ResponseEntity<List<DESAT>> getDesatsWithNonEmptyDeliveryDate() {
	        List<DESAT> desats = desatRepo.findByDateLivraisonIsNotNull();
	        return new ResponseEntity<>(desats, HttpStatus.OK);
	    }
		
	/*	@GetMapping("/desatNonTraites")
public ResponseEntity<List<DESAT>> getDesatsNonTraites(){
try {
List<DESAT> desats = new ArrayList<DESAT>();

Collection<String> motif_desat=Arrays.asList("nonTraite", "En Attente","enCours");

desats=desatRepo.findByMotifDesatIn(motif_desat);


if (desats.isEmpty()) {
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

return new ResponseEntity<>(desats, HttpStatus.OK);
} catch (Exception e) {
return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}

} */
		
		
		@PutMapping("/mettreEnCours/{idacte}")
		public ResponseEntity<DESAT> mettreEnCours(@PathVariable("idacte") String idacte, @RequestBody DESAT desat) {
			Optional<DESAT> desatdata = desatRepo.findByIdacte(idacte);

			if (desatdata.isPresent()) {

				DESAT _desat = desatdata.get();

				if(_desat.getDateLivraison() == null) {
					_desat.setMotif("enCours");
				}else{
					_desat.setMotifReaffectation("enCours");
				}


				return new ResponseEntity<>(desatRepo.save(_desat), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@PutMapping("/annulerEnCours/{idacte}")
		public ResponseEntity<DESAT> annulerEnCours(@PathVariable("idacte") String idacte, @RequestBody DESAT desat) {
			Optional<DESAT> desatData = desatRepo.findByIdacte(idacte);

			if (desatData.isPresent()) {
				DESAT _desat = desatData.get();

				if(_desat.getDateLivraison() == null) {
					_desat.setMotif("nonTraite");
				}else {
					_desat.setMotifReaffectation("nonTraite");
				}


				return new ResponseEntity<>(desatRepo.save(_desat), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		
	  	
		
		
}
