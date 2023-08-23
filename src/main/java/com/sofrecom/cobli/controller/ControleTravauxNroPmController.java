package com.sofrecom.cobli.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Nropm;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.ControleTravauxRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.NropmRepository;
import com.sofrecom.cobli.repository.PrestationRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ControleTravauxNroPmController {
	@Autowired
	ControleTravauxRepository controleTravauxRepo;	
	@Autowired 
	PrestationRepository prestationRepository;
	@Autowired
	Services services;
	@Autowired
	TarificationService tarifService;
	@Autowired
	NropmRepository nropmRepo;
	
	
	//Add new CT NROPM
	@PostMapping("/AddTvx")
	public String addCT(@RequestBody ControleTravaux ct, @RequestParam String cog) {
	    Optional<Nropm> nro = nropmRepo.findByCog(cog);
	   
   
	    if (nro.isPresent() )
	    {
	    	Nropm nropm = nro.get();
	    	 ct.setCog(cog);
	    	 if (!"enAttente".equals(nropm.getMotif())) {
	    	if(!services.isExisteCT(ct)) {
	        Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Controle travaux et SI");
	        Prestation prestation = _prestation.get();
	        
	        Date dateDeadline = null;
	        int nbrjoursmax = prestation.getDeadline();

	        Calendar c = Calendar.getInstance();
	        c.setTime(ct.getDateReception());
	        c.add(Calendar.DATE, nbrjoursmax);
	        dateDeadline = c.getTime();

	        ControleTravaux controletravaux = new ControleTravaux(
	        		 ct.getIdacte(),
             		ct.getRefTacheBPU(),
						prestation,
						ct.getType_element(),
						ct.getQuantite(),
						ct.getDateReception(),
						ct.getDateLivraison(),
						ct.getDateValidation(),
						ct.getAffectation(),
						ct.getDuree(),
						ct.getCommentaire(),
						ct.getMotif(),
						ct.getStatutFacturation(),
						ct.getDateReprise(),
						ct.getRepriseFacturable(),
				        dateDeadline,
				        "P1",  
				        ct.getUi(),   				       
				        ct.getCog(),
				        ct.getFi(),
				        ct.getDateFlux(),
				        ct.getMotifReaffectation(),
				        ct.getDateReafictation(),
				        ct.getDatelivReafictation(),
				        ct.getTypeTravaux()
	        );
	        controletravaux.setCog(cog);
	        Tarification tarif = tarifService.calculerTypeTarifCT(controletravaux);
	        controletravaux.setTarif(tarif);
	        controleTravauxRepo.save(controletravaux);

	        return "Traitement Controle Travaux";
	    }
	    	
	    	 }
	    return "Motif test ";
	}
	    return "Cog n'existe pas";
	}
	
	
	
	

    
    
  //Update CT
  		@PutMapping("/cts/{idacte}")
  		public ResponseEntity<ControleTravaux> updateCT(@PathVariable("idacte") String idacte, @RequestBody ControleTravaux ct) {
  			Optional<ControleTravaux> ctData = controleTravauxRepo.findById(idacte);

  			if (ctData.isPresent()) {
  				ControleTravaux controleT  = ctData.get();
  				controleT.setCommentaire(ct.getCommentaire());
  				controleT.setCog(ct.getCog());
  				controleT.setDateLivraison(ct.getDateLivraison());
  				controleT.setDateReception(ct.getDateReception());
  				controleT.setDateReprise(ct.getDateReprise());
  				controleT.setDateValidation(ct.getDateValidation());
  				controleT.setFi(ct.getFi());
  				controleT.setUi(ct.getUi());
  				controleT.setDuree(ct.getDuree());
  				controleT.setMotif(ct.getMotif());
  				controleT.setCommentaire(ct.getCommentaire());
  				controleT.setQuantite(ct.getQuantite());
  				controleT.setRefTacheBPU(ct.getRefTacheBPU());
  				controleT.setRepriseFacturable(ct.getRepriseFacturable());
  				controleT.setStatutFacturation(ct.getStatutFacturation());
  				controleT.setDateReafictation(ct.getDateReafictation());
  				controleT.setDatelivReafictation(ct.getDatelivReafictation());
  				controleT.setTypeTravaux(ct.getTypeTravaux());
  				controleT.setMotifReaffectation(ct.getMotifReaffectation());
  						return new ResponseEntity<>(controleTravauxRepo.save(controleT), HttpStatus.OK);
  			} else {
  				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  			}
  		}
  		
  		
  	//Get All CT
		@GetMapping("/cts")
		public List<ControleTravaux> getAll(){
			return controleTravauxRepo.findAll();
			
		}
  		
  	//Import Historique Excel
  		@PostMapping("tvxexcel")
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
  				String motifReaffectation= "";
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

  				
  				/*
  				String prenom = "";
  				String nom = "";
  				int indxagent = 0;*/
  				
  				for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
  					XSSFRow row = sheet.getRow(i);
  					//for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
  					System.out.println("----------------------------- ok    " );

  						DataFormatter dataformatter = new DataFormatter();
  						ui = dataformatter.formatCellValue(row.getCell(0)).toString();
  						System.out.println("----------------------------- ok    " );
  						refTacheBPU = dataformatter.formatCellValue(row.getCell(1)).toString();
  						System.out.println("----------------------------- ok    " );
  						typeTravaux = dataformatter.formatCellValue(row.getCell(2)).toString();
  						System.out.println("----------------------------- ok    " );
  						cog = dataformatter.formatCellValue(row.getCell(3)).toString();
  						System.out.println("----------------------------- ok    " );
  						fi = dataformatter.formatCellValue(row.getCell(4)).toString();
  						System.out.println("----------------------------- ok    " );
  						
  						Optional<Prestation> prestation = prestationRepository.findByNomPrestation("Controle Travaux");
  						 Prestation pres= prestation.get();
  		
  						type_element = dataformatter.formatCellValue(row.getCell(6)).toString();
  						quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(7)).toString());
  						
  						if (row.getCell(8) == null || row.getCell(8).toString().trim().isEmpty()) {
  							dateFlux = null;
  						} else if (row.getCell(8).getCellType() == CellType.NUMERIC) {
  							dateFlux = row.getCell(8).getDateCellValue();
  						} else {
  						    String dateStr = row.getCell(8).toString().trim();
  						    if (dateStr.isEmpty()) {
  						    	dateFlux = null;
  						    } else {
  						        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
  						        try {
  						        	dateFlux = dateFormat.parse(dateStr);
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
  						} else {
  						    String dateStr = row.getCell(9).toString().trim();
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
  						
  						if (row.getCell(10) == null || row.getCell(10).toString().trim().isEmpty()) {
  						    dateLivraison = null;
  						} else if (row.getCell(10).getCellType() == CellType.NUMERIC) {
  						    dateLivraison = row.getCell(10).getDateCellValue();
  						} else {
  						    String dateStr = row.getCell(10).toString().trim();
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
  						
  						duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(11)).toString()) * 60) + Integer.parseInt(dataformatter.formatCellValue(row.getCell(12)).toString());

  						if (row.getCell(13) == null || row.getCell(13).toString().trim().isEmpty()) {
  						    dateValidation = null;
  						} else if (row.getCell(13).getCellType() == CellType.NUMERIC) {
  						    dateValidation = row.getCell(13).getDateCellValue();
  						} else {
  						    String dateStr = row.getCell(13).toString().trim();
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
  						
  						affectation = dataformatter.formatCellValue(row.getCell(14)).toString();
  						commentaire = dataformatter.formatCellValue(row.getCell(15)).toString();
  						statut_operationnel = dataformatter.formatCellValue(row.getCell(16)).toString();
  						
  						
  						if(row.getCell(17) == null || row.getCell(17).toString().equals("")) {
  						    motif = "";
  						} else {
  						    motif = dataformatter.formatCellValue(row.getCell(17)).toString();
  						}

  						if (motif.equals("En attente")) {
  						    statutFacturation = "en cours CDS";
  						} else {
  						    statutFacturation = "facturable";
  						}

  						row.createCell(18).setCellValue(statutFacturation);

  		
  						
  										
							ControleTravaux ct = new ControleTravaux( 
  									
  									"",  refTacheBPU, pres,
  									type_element,
  									quantite,dateReception, dateLivraison,dateValidation,affectation, duree,commentaire,
  									motif,priorite, dateDeadline, statutFacturation, repriseFacturable, cog,
  									dateReprise, ui, fi, dateFlux, dateReafictation, datelivReafictation, typeTravaux, motifReaffectation
  											  
  											);
  							Tarification tarif =  tarifService.calculerTypeTarifCT(ct);
  	        				  ct.setTarif(tarif);
  							
  							  											
  						if(!services.isExisteCT(ct)) {
  							controleTravauxRepo.save(ct);
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
  		
  	//Get CT by Cog
		@GetMapping("/getCTByCog")
		public List<ControleTravaux> getCTByCog(@RequestParam ("cogString") String cogString ){
			return controleTravauxRepo.findByCogContaining(cogString);
		}
		
		
		 //Get CT by affectation
		@GetMapping("/getCTByAffectation/{affectationString}")
		public List<ControleTravaux> getCTByAffectation(@PathVariable String affectationString){
			return controleTravauxRepo.findByAffectation(affectationString);
		}
		
		 //Get CT by motif
		@GetMapping("/getCTByMotif/{motifString}")
		public List<ControleTravaux> getCTByMotif(@PathVariable String motifString){
			return controleTravauxRepo.findByMotif(motifString);
		}
		//Get CT by FI
		@GetMapping("/getCTByFI/{FI}")
		public List<ControleTravaux> getCTByFI(@PathVariable String FI){
			return controleTravauxRepo.findByFi(FI);
		}
		
		//Get CT by UI
		@GetMapping("/getCTByUi/{ui}")
		public List<ControleTravaux> getCTByUi(@PathVariable String ui){
			return controleTravauxRepo.findByUi(ui);
		}
		
		
		// Importer fichier Spi 
		
		@PostMapping("/importTrvSpiNRO")
		public String importerTrvAtraiter(@RequestParam("file") MultipartFile file) {
		
		String message = "" ;
		String motif="nonTraite";
		String type_element="";
		
		Optional<Prestation> prestation = prestationRepository.findByNomPrestation("Controle travaux et SI");
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
	      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	   Date dateReception = new Date();
	    String dateRecep = formatter.format(dateReception);
	     Date dateDeadline=null ;

	     int nbrjoursmax=pres.getDeadline();
	     Calendar c = Calendar.getInstance();
	     c.setTime(formatter.parse(dateRecep));
	     c.add(Calendar.DATE, nbrjoursmax);
	     dateDeadline = c.getTime();
	     //dateDeadline = new java.sql.Date(c.getTimeInMillis());

	     
	      Optional<ControleTravaux> TravauxNRO_ = controleTravauxRepo.findByCog(row.getCell(20).toString());
		
	       if(!TravauxNRO_.isPresent()) {
	     	ControleTravaux TravauxNRO = new ControleTravaux(row.getCell(20).toString(),row.getCell(4).toString(),motif,type_element, row.getCell(24).toString(),pres,1,"enCoursCds", dateReception,dateDeadline,dateFlux);
		    controleTravauxRepo.save(TravauxNRO);

		    } else{
	            ControleTravaux TravauxNRO=TravauxNRO_.get();
	         if(TravauxNRO.getDateLivraison() != null){
	        	//TravauxDesat.setMotifReaffectation("nonTraite");
	        	 TravauxNRO.setPriorite("P1");
	        	controleTravauxRepo.save(TravauxNRO);
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


}
