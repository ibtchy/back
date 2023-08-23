package com.sofrecom.cobli.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sofrecom.cobli.models.ControleTravaux;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.Nropm;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.CollaborateurRepository;

import com.sofrecom.cobli.repository.ControleTravauxRepository;
import com.sofrecom.cobli.repository.DESATRepository;
import com.sofrecom.cobli.repository.PrestationRepository;
import com.sofrecom.cobli.repository.TarificationRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ControleTravauxDesatController {
	
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
	@Autowired
	ControleTravauxRepository ctrlDesatRepo;
	@Autowired
	DESATRepository desatRepo ;
	
	
	// Importer fichier Spi 
	
	/*@PostMapping("/importTrvSpiDesat")
	public List<ControleTravaux> importerTrvAtraiter(@RequestParam("file") MultipartFile file) throws ParseException, IOException {
	    List<ControleTravaux> trlist = new ArrayList<>();
	    String message = "";
	    String motif = "nonTraite";
	    String type_element = "RR";

	    Optional<Prestation> prestation = prestationRepo.findByNomPrestation("Controle travaux et SI");
	    Prestation pres = prestation.get();

	    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	        XSSFRow row = sheet.getRow(i);

	        String cell20Value = row.getCell(20) != null ? row.getCell(20).toString() : "";
	        String cell4Value = row.getCell(4) != null ? row.getCell(4).toString() : "";
	        String cell24Value = row.getCell(24) != null ? row.getCell(24).toString() : "";

	        Date dateFlux = null;
	        if (row.getCell(22) != null && !row.getCell(22).toString().equals("")) {
	            dateFlux = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(22).toString());
	            System.out.println(dateFlux);
	        }

	        Date dateReception = null;
	        if (row.getCell(23) != null && !row.getCell(23).toString().equals("")) {
	            dateReception = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(23).toString());
	            System.out.println(dateReception);
	        }

	        Date dateDeadline = null;
	        int nbrjoursmax = pres.getDeadline();
	        Calendar c = Calendar.getInstance();
	        if (dateReception != null) {
	            c.setTime(dateReception);
	            c.add(Calendar.DATE, nbrjoursmax);
	            dateDeadline = c.getTime();
	            System.out.println(dateDeadline);
	        }

	        ControleTravaux TravauxDesat = new ControleTravaux(
	            cell20Value,
	            cell4Value,
	            motif,
	            type_element,
	            cell24Value,
	            pres,
	            1,
	            "enCoursCds",
	            null,
	            null,
	            null
	        );

	        trlist.add(TravauxDesat);
	    }

	    return trlist;
	}*/

    @PostMapping("/importTrvSpiDesat")
	public String importerTrvAtraiter(@RequestParam("file") MultipartFile file) {
	
	String message = "" ;
	String motif="nonTraite";
	String type_element="CouCou";
	String typeTravaux="Désaturations Coupleurs";
	Optional<Prestation> prestation = prestationRepo.findByNomPrestation("Controle travaux et SI");
	Prestation pres= prestation.get();

	try {
	XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	XSSFSheet sheet = workbook.getSheetAt(0);

	for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
	XSSFRow row = sheet.getRow(i);
	
	Date dateFlux=null;
     if(row.getCell(22) == null || row.getCell(22).toString().equals("") ) {
         dateFlux=null;
     }else{
         dateFlux = new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(22).toString());
     }
	
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
     
      Optional<ControleTravaux> trv_ = ctrlDesatRepo.findByCog(row.getCell(20).toString());
	
       if(!trv_.isPresent()) {
    	   String cog = "";
    	   String fi = "";
    	   String commentaire = "";
    	   String dateFluxValue = "";

    	   if (row.getCell(20) != null) {
    	       cog = row.getCell(20).toString();
    	   }

    	   if (row.getCell(4) != null) {
    	       fi = row.getCell(4).toString();
    	   }

    	   if (row.getCell(24) != null) {
    	       commentaire = row.getCell(24).toString();
    	   }

    	  
    	   ControleTravaux trvx = new ControleTravaux(cog, fi, motif, type_element, commentaire, pres, 1, "enCoursCds", dateReception, dateDeadline, dateFlux,typeTravaux);

	
	//ControleTravaux trvx = new ControleTravaux(row.getCell(20).toString(),row.getCell(4).toString(),motif,type_element, row.getCell(24).toString(),pres,1,"enCoursCds", dateReception,dateDeadline,dateFlux);
	ctrlDesatRepo.save(trvx);
	

	} else{
		ControleTravaux trvx=trv_.get();
        if(trvx.getDateLivraison() != null){
        	trvx.setMotifReaffectation("nonTraite");
        	trvx.setPriorite("P1");
        	ctrlDesatRepo.save(trvx);
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


	
	
	 
   
	   
	   
	   
	 // les recherches 
	   
	               //Get desat by FI
		        	@GetMapping("/getTrvByFI/{FI}")
					public List<ControleTravaux> getDesatByFI(@PathVariable String FI){
						return ctrlDesatRepo.findByFi(FI);
					}
					
					//Get desat by ui
					@GetMapping("/getTrvByUi/{ui}")
					public List<ControleTravaux> getTrvByUi(@PathVariable String ui){
						return ctrlDesatRepo.findByUi(ui);
					}
					
					//Get desat by type travaux
					@GetMapping("/getTrvByTypeTrv/{typeTravaux}")
					public List<ControleTravaux> getTrvByTypeTravaux(@PathVariable String typeTravaux){
						return ctrlDesatRepo.findByTypeTravaux(typeTravaux);
					}
					 //Get Desat by affectation
			        @GetMapping("/getTrvByAffectation/{affectationString}")
			        public List<ControleTravaux> getTrvByAffectation(@PathVariable String affectationString){
				   return ctrlDesatRepo.findByAffectation(affectationString);
			        }
			
			       //Get Desat by motif
			       @GetMapping("/getTrvByMotif/{motifString}")
			       public List<ControleTravaux> getTrvByMotif(@PathVariable String motifString){
				   return ctrlDesatRepo.findByMotif(motifString);
			       }
			       
			       
			       //Get Esimb by Cog
					@GetMapping("/getTrvByCog")
					public List<ControleTravaux> getTrvByCog(@RequestParam ("cogString") String cogString ){
						return ctrlDesatRepo.findByCogContaining(cogString);
					}
					 
			// recherche par motif par ordre 
			 @GetMapping("/rechParMotif/{motif}")
                  public ResponseEntity<List<ControleTravaux>> rechercherParMotif(@PathVariable("motif") String[] motif){
                  List  motifs=Arrays.asList(motif);
                  Collections.replaceAll(motifs, "livre", "");
                  try {
                   List<ControleTravaux> trvs = new ArrayList<ControleTravaux>();
                   trvs=ctrlDesatRepo.findByMotifInOrMotifReaffectationInOrderByPrioriteAscDateDeadlineAsc(motifs, Arrays.asList(motif));
                  if (trvs.isEmpty()) {
                  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                  }
                  return new ResponseEntity<>(trvs, HttpStatus.OK);
                  } catch (Exception e) {
                  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                  }

    } 
					
	   
	  // fonction ajout 
					
					 // add desat methode 1 (ne marche pas)
		 @PostMapping("/TrvDesatAdd")
			public String addTrvDesat(@RequestBody ControleTravaux trvDesat,@RequestParam String cog){
		      Optional<DESAT> des = desatRepo.findByCog(cog);
	          trvDesat.setCog(cog);

	          if (des.isPresent() )
	  	    {
	        	  DESAT desat = des.get();
	        	  trvDesat.setCog(cog);
	 	    	 if (!"enAttente".equals(desat.getMotif())) {
	  	    	if(!service.isExisteCT(trvDesat)) {
	  	       
		        	 //Type prestation
	        		 Optional<Prestation> _prestation = prestationRepo.findByNomPrestation("Controle travaux et SI");
	                 Prestation prestation = _prestation.get();
	               // Calcul date deadline
	                 Date dateDeadline = null;
	                 int nbrjoursmax = prestation.getDeadline();
	                 Calendar c = Calendar.getInstance();
	                 c.setTime(trvDesat.getDateReception());
	                 c.add(Calendar.DATE, nbrjoursmax);
	                 dateDeadline = c.getTime();

	                 ControleTravaux trvDesatt= new ControleTravaux(
		                	    trvDesat.getIdacte(),
		                		trvDesat.getRefTacheBPU(),
	     						prestation,
	     						trvDesat.getType_element(),
	     						trvDesat.getQuantite(),
	     						trvDesat.getDateReception(),
	     						trvDesat.getDateLivraison(),
	     						trvDesat.getDateValidation(),
	     						trvDesat.getAffectation(),
	     						trvDesat.getDuree(),
	     						trvDesat.getCommentaire(),
	     						trvDesat.getMotif(),
	     						trvDesat.getStatutFacturation(),
	     						trvDesat.getDateReprise(),
	     						trvDesat.getRepriseFacturable(),
	     				        dateDeadline,
	     				        "P1",  
	     				        trvDesat.getUi(),   				       
	     				        trvDesat.getCog(),
	     				        trvDesat.getFi(),
	     				        trvDesat.getDateFlux(),
	     				        trvDesat.getMotifReaffectation(),
	     				        trvDesat.getDateReafictation(),
	     				        trvDesat.getDatelivReafictation(),
	     				        trvDesat.getTypeTravaux()
	                    		 );
	               trvDesat.setCog(cog);
	               Tarification tarif =  tarifService.calculerTypeTarifCT(trvDesatt);
	               trvDesatt.setTarif(tarif);
	           	 ctrlDesatRepo.save(trvDesatt);
	             return "Traitement Controle Travaux";
	  		    }
	  	    
	  		    return "Pas de traitement";
	  		}
	 	    	 return "motif test";
	  	    }
	         
		 
	  		    return "Cog n'existe pas";
	  		}
		 
	 
		 
		 
		 // fonction de modification
		@PutMapping("/trvs/{idacte}")
		public ResponseEntity<ControleTravaux> updateTrv(@PathVariable("idacte") String idacte, @RequestBody ControleTravaux trv) {
			Optional<ControleTravaux> trvData = ctrlDesatRepo.findById(idacte);

	
			if (trvData.isPresent()) {
				ControleTravaux _trv = trvData.get();
				_trv.setRefTacheBPU(trv.getRefTacheBPU());
				_trv.setType_prestation(trv.getType_prestation());
				_trv.setType_element(trv.getType_element());
				_trv.setQuantite(trv.getQuantite());
				_trv.setDateReception(trv.getDateReception());
				_trv.setDateLivraison(trv.getDateLivraison());
				_trv.setDateValidation(trv.getDateValidation());
				_trv.setAffectation(trv.getAffectation());
				_trv.setDuree(trv.getDuree());
				_trv.setMotif(trv.getMotif());
				_trv.setCommentaire(trv.getCommentaire());
				_trv.setRepriseFacturable(trv.getRepriseFacturable());
				_trv.setStatutFacturation(trv.getStatutFacturation());
				_trv.setDateReprise(trv.getDateReprise());		
				_trv.setFi(trv.getFi());
				_trv.setDateReafictation(trv.getDateReafictation());
				_trv.setCog(trv.getCog());
				_trv.setUi(trv.getUi());
				_trv.setDatelivReafictation(trv.getDatelivReafictation());
				_trv.setPriorite(trv.getPriorite());
				_trv.setDateDeadline(trv.getDateDeadline());
				_trv.setTypeTravaux(trv.getTypeTravaux());
			
				System.out.println("modif");
						return new ResponseEntity<>(ctrlDesatRepo.save(_trv), HttpStatus.OK);
						
			} else {
				System.out.println("pas modifier");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}
		 // fonction de traiter trv
		@PutMapping("/traiterTrvs/{idacte}")
		public ResponseEntity<ControleTravaux> TraiterTrv(@PathVariable("idacte") String idacte, @RequestBody ControleTravaux trv) {
			Optional<ControleTravaux> trvData = ctrlDesatRepo.findById(idacte);

	
			if (trvData.isPresent()) {
				ControleTravaux _trv = trvData.get();
				_trv.setRefTacheBPU(trv.getRefTacheBPU());
				_trv.setType_prestation(trv.getType_prestation());
				_trv.setType_element(trv.getType_element());
				_trv.setQuantite(trv.getQuantite());
				_trv.setDateReception(trv.getDateReception());
				_trv.setDateLivraison(trv.getDateLivraison());
				_trv.setDateValidation(trv.getDateValidation());
				_trv.setAffectation(trv.getAffectation());
				_trv.setDuree(trv.getDuree());
				_trv.setMotif(trv.getMotif());
				_trv.setCommentaire(trv.getCommentaire());
				_trv.setRepriseFacturable(trv.getRepriseFacturable());
				_trv.setStatutFacturation(trv.getStatutFacturation());
				_trv.setDateReprise(trv.getDateReprise());		
				_trv.setFi(trv.getFi());
				_trv.setDateReafictation(trv.getDateReafictation());
				_trv.setCog(trv.getCog());
				_trv.setUi(trv.getUi());
				_trv.setDatelivReafictation(trv.getDatelivReafictation());
				_trv.setPriorite(trv.getPriorite());
				_trv.setDateDeadline(trv.getDateDeadline());
				_trv.setTypeTravaux(trv.getTypeTravaux());
			
				System.out.println("modif");
						return new ResponseEntity<>(ctrlDesatRepo.save(_trv), HttpStatus.OK);
						
			} else {
				System.out.println("pas modifier");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}

	   //Get All Desats
			@GetMapping("/trvs")
			public List<ControleTravaux> getAll(){
				return ctrlDesatRepo.findAll();
				
			}
			
			
			//Import Historique Excel
	  		@PostMapping("excelTrv")
	  		public String excelReader(@RequestParam("file") MultipartFile excel) throws Exception {

	  			
	  			try {
	  				XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
	  				XSSFSheet sheet = workbook.getSheetAt(0);	
	  				String ui= "";
	  				String refTacheBPU = "";
	  				String typeTravaux="";
	  				String cog="";
	  				String fi = "";
	  				Date dateFlux;
	  				Date dateReafictation = null;
	  				Date datelivReafictation = null;
	  				Date dateReprise =null;
	  				String type_element = "";
	  				int quantite = 1;
	  				Date dateReception ;
	  				Date dateLivraison ;
	  				int duree = 1;
	  				Date dateValidation;
	  				String affectation = "";
	  				String commentaire = "";
	  				String statut_operationnel = "";
	  				String motif = "";
	  				String repriseFacturable= "";
	  				String statutFacturation;
	  				String priorite ="";
	  				Date dateDeadline = null;
	  				String motifReaffectation= "";
	  				/*
	  				String prenom = "";
	  				String nom = "";
	  				int indxagent = 0;*/
	  				
	  				for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
	  					XSSFRow row = sheet.getRow(i);
	  					//for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
	  					System.out.println("----------------------------- ok 1	   " );

	  						DataFormatter dataformatter = new DataFormatter();
	  						ui = dataformatter.formatCellValue(row.getCell(0)).toString();
	  						System.out.println("----------------------------- ok2    " );
	  						refTacheBPU = dataformatter.formatCellValue(row.getCell(1)).toString();
	  						System.out.println("----------------------------- ok  3  " );
	  						typeTravaux = dataformatter.formatCellValue(row.getCell(2)).toString();
	  						System.out.println("----------------------------- ok 4   " );
	  						cog = dataformatter.formatCellValue(row.getCell(3)).toString();
	  						System.out.println("----------------------------- ok 5   " );
	  						fi = dataformatter.formatCellValue(row.getCell(4)).toString();
	  						System.out.println("----------------------------- ok 6   " );
	  						
	  						Optional<Prestation> prestation = prestationRepo.findByNomPrestation("Controle travaux et SI");
	  						 Prestation pres= prestation.get();
	  						System.out.println("----------------------------- ok 7   " );
	  		
	  						type_element = dataformatter.formatCellValue(row.getCell(6)).toString();
	  						System.out.println("----------------------------- ok  8  " );
	  						quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(7)).toString());
	  						System.out.println("----------------------------- ok  9  " );
	  						
	  						if (row.getCell(8) == null || row.getCell(8).toString().trim().isEmpty()) {
	  							dateFlux = null;
	  						} else if (row.getCell(8).getCellType() == CellType.NUMERIC) {
	  							dateFlux = row.getCell(8).getDateCellValue();
	  							System.out.println("----------------------------- ok10    " );
	  						} else {
	  						    String dateStr = row.getCell(8).toString().trim();
	  						    if (dateStr.isEmpty()) {
	  						    	dateFlux = null;
	  						    } else {
	  						        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	  						        try {
	  						        	dateFlux = dateFormat.parse(dateStr);
	  						        	System.out.println("----------------------------- ok11  " );
	  						        } catch (ParseException e) {
	  						            e.printStackTrace();
	  						          dateFlux = null;
	  						        }
	  						    }
	  						}
	  						
	  							
	  						if (row.getCell(9) == null || row.getCell(9).toString().trim().isEmpty()) {
	  						    dateReception = null;
	  						} else if (row.getCell(9).getCellType() == CellType.NUMERIC) {
	  						    dateReception = row.getCell(9).getDateCellValue();
	  						  System.out.println("----------------------------- ok 12  " );
	  						} else {
	  						    String dateStr = row.getCell(9).toString().trim();
	  						    if (dateStr.isEmpty()) {
	  						        dateReception = null;
	  						    } else {
	  						        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	  						        try {
	  						            dateReception = dateFormat.parse(dateStr);
	  						          System.out.println("----------------------------- ok13    " );
	  						        } catch (ParseException e) {
	  						            // handle parse exception
	  						            e.printStackTrace();
	  						            dateReception = null;
	  						        }
	  						    }
	  						}
	  						
	  						if (row.getCell(10) == null || row.getCell(10).toString().trim().isEmpty()) {
	  						    dateLivraison = null;
	  						} else if (row.getCell(10).getCellType() == CellType.NUMERIC) {
	  						    dateLivraison = row.getCell(10).getDateCellValue();
	  						    
	  						  System.out.println("----------------------------- ok14    " );
	  						} else {
	  						    String dateStr = row.getCell(10).toString().trim();
	  						    if (dateStr.isEmpty()) {
	  						        dateLivraison = null;
	  						    } else {
	  						        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	  						        try {
	  						            dateLivraison = dateFormat.parse(dateStr);
	  						          System.out.println("----------------------------- ok14    " );
	  						        } catch (ParseException e) {
	  						            // handle parse exception
	  						            e.printStackTrace();
	  						            dateLivraison = null;
	  						        }
	  						    }
	  						}
	  						
	  						duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(11)).toString()) * 60) + Integer.parseInt(dataformatter.formatCellValue(row.getCell(12)).toString());
	  						System.out.println("----------------------------- ok15    " );
	  						if (row.getCell(13) == null || row.getCell(13).toString().trim().isEmpty()) {
	  						    dateValidation = null;
	  						} else if (row.getCell(13).getCellType() == CellType.NUMERIC) {
	  						    dateValidation = row.getCell(13).getDateCellValue();
	  						  System.out.println("----------------------------- ok 16   " );
	  						} else {
	  						    String dateStr = row.getCell(13).toString().trim();
	  						    if (dateStr.isEmpty()) {
	  						        dateValidation = null;
	  						    } else {
	  						        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	  						        try {
	  						            dateValidation = dateFormat.parse(dateStr);
	  						          System.out.println("----------------------------- ok17    " );
	  						        } catch (ParseException e) {
	  						            // handle parse exception
	  						            e.printStackTrace();
	  						            dateValidation = null;
	  						        }
	  						    }
	  						}
	  						
	  						affectation = dataformatter.formatCellValue(row.getCell(14)).toString();
	  						System.out.println("----------------------------- ok18    " );
	  						commentaire = dataformatter.formatCellValue(row.getCell(15)).toString();
	  						System.out.println("----------------------------- ok 19   " );
	  						statut_operationnel = dataformatter.formatCellValue(row.getCell(16)).toString();
	  						System.out.println("----------------------------- ok 20   " );
	  						
	  						
	  						if(row.getCell(17) == null || row.getCell(17).toString().equals("")) {
	  						    motif = "";
	  						} else {
	  						    motif = dataformatter.formatCellValue(row.getCell(17)).toString();
	  						  System.out.println("----------------------------- ok21    " );
	  						}

	  						if (motif.equals("En attente")) {
	  						    statutFacturation = "en cours CDS";
	  						} else {
	  						    statutFacturation = "facturable";
	  						}

	  						row.createCell(18).setCellValue(statutFacturation);

	  						System.out.println("----------------------------- ok22    " );
	  						
	  										
	  							ControleTravaux ct = new ControleTravaux( 
	  									
	  									"",  refTacheBPU, pres,
	  									type_element,
	  									quantite,dateReception, dateLivraison,dateValidation,affectation, duree,commentaire,
	  									motif,priorite, dateDeadline, statutFacturation, repriseFacturable, cog,
	  									dateReprise, ui, fi, dateFlux, dateReafictation, datelivReafictation, typeTravaux , motifReaffectation
	  											  
	  											);
	  							System.out.println("----------------------------- ok 23   " );
	  							Tarification tarif =  tarifService.calculerTypeTarifCT(ct);
	  	        				  ct.setTarif(tarif);
	  							
	  							  											
	  						if(!service.isExisteCT(ct)) {
	  							ctrlDesatRepo.save(ct);
	  						}
	  						
	  						else {
	  							System.out.println("existedéja"+ ct.toString());
	  						}
	  				}
	  				
	  			} catch (IOException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
	  			
	  			return "Success";
	  		}
	  		
	  		 	
		@PutMapping("/mettreEnCoursTrv/{idacte}")
		public ResponseEntity<ControleTravaux> mettreEnCours(@PathVariable("idacte") String idacte, @RequestBody ControleTravaux trv) {
			Optional<ControleTravaux> trvdata = ctrlDesatRepo.findByIdacte(idacte);

			if (trvdata.isPresent()) {

				ControleTravaux _trv = trvdata.get();

				if(_trv.getDateLivraison() == null) {
					_trv.setMotif("enCours");
				}else{
					_trv.setMotifReaffectation("enCours");
				}


				return new ResponseEntity<>(ctrlDesatRepo.save(_trv), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@PutMapping("/annulerEnCoursTrv/{idacte}")
		public ResponseEntity<ControleTravaux> annulerEnCours(@PathVariable("idacte") String idacte, @RequestBody ControleTravaux trv) {
			Optional<ControleTravaux> trvData = ctrlDesatRepo.findByIdacte(idacte);

			if (trvData.isPresent()) {
				ControleTravaux _trv = trvData.get();

				if(_trv.getDateLivraison() == null) {
					_trv.setMotif("nonTraite");
				}else {
					_trv.setMotifReaffectation("nonTraite");
				}


				return new ResponseEntity<>(ctrlDesatRepo.save(_trv), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		
		// get all trvs 
		 @GetMapping("/afficheTrv/{idacte}")
	    public ResponseEntity<ControleTravaux> afficheTrv(@PathVariable String idacte){
	        try {
	            Optional<ControleTravaux> trv_ = ctrlDesatRepo.findById(idacte);
	            if(trv_.isPresent()){
	                return new ResponseEntity<>(trv_.get(), HttpStatus.OK);
	            }else {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		 
		
	
	
	 
}
	   
	   
