package com.sofrecom.cobli.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import org.springframework.security.access.AccessDeniedException;
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
import com.sofrecom.cobli.exception.ResourceNotFoundException;
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.DESAT;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Pm;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.PrestationRepository;
import com.sofrecom.cobli.repository.TarificationRepository;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ESIMBController {
	@Autowired
	ESIMBRepository ESIMBRepo;
	@Autowired
	Acte_traitementRepository actetraitementRepository;
	@Autowired 
	CollaborateurRepository collaborateurRepository;
	
	@Autowired 
	PrestationRepository prestationRepository;
	//@Autowired 
	//TarificationRepository tarificationRepository;
	
	@Autowired
	Services services;
	@Autowired
	TarificationService tarifService;
	
//----------------------------Import Excel------------------------------------------
	
	@PostMapping("excel")
	public String excelReader(@RequestParam("file") MultipartFile excel) throws Exception {
		
		try {
			
			
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			String refTacheBPU = "";
			String codeBanbou = "";
			String codeImb = "";
			String type_element = "";
			int quantite = 1;
			Date dateReception ;
			Date dateReprise =null;
			Date dateValidation ;
			String affectation = "";
			//Collaborateur affectation;
			Date dateLivraison ;
			int duree = 1;
			Date dateVerification ;
			String commentaire = "";
			String statut_operationnel = "";
			String motif = "";
			String repriseFacturable= "";
			String statutFacturation;
			String priorite ="";
			Date dateDeadline;
			
			/*
			String prenom = "";
			String nom = "";
			int indxagent = 0;*/
			
			for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
				XSSFRow row = sheet.getRow(i);
				//for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
				System.out.println("----------------------------- ok    " );

					DataFormatter dataformatter = new DataFormatter();
					refTacheBPU = dataformatter.formatCellValue(row.getCell(0)).toString();
					System.out.println("----------------------------- ok    " );
					codeBanbou = dataformatter.formatCellValue(row.getCell(1)).toString();
					System.out.println("----------------------------- ok    " );
					codeImb = dataformatter.formatCellValue(row.getCell(2)).toString();
					System.out.println("----------------------------- ok    " );
					
					Optional<Prestation> prestation = prestationRepository.findByNomPrestation("Evolution statut IMB");
					 Prestation pres= prestation.get();
	
					/*type_prestation = dataformatter.formatCellValue(row.getCell(3)).toString();
					System.out.println("----------------------------- ok    " );*/
					type_element = dataformatter.formatCellValue(row.getCell(4)).toString();
					quantite = Integer.parseInt(dataformatter.formatCellValue(row.getCell(5)).toString());
					if (row.getCell(6) == null || row.getCell(6).toString().trim().isEmpty()) {
					    dateReception = null;
					} else if (row.getCell(6).getCellType() == CellType.NUMERIC) {
					    dateReception = row.getCell(6).getDateCellValue();
					} else {
					    String dateStr = row.getCell(6).toString().trim();
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
					//dateReception = dataformatter.formatCellValue(row.getCell(6)).toString();
					if (row.getCell(7) == null || row.getCell(7).toString().trim().isEmpty()) {
					    dateDeadline = null;
					} else if (row.getCell(7).getCellType() == CellType.NUMERIC) {
					    dateDeadline = row.getCell(7).getDateCellValue();
					} else {
					    String dateStr = row.getCell(7).toString().trim();
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
					//dateDeadline = dataformatter.formatCellValue(row.getCell(7)).toString();
					
					priorite = dataformatter.formatCellValue(row.getCell(8)).toString();
					System.out.println("----------------------------- ok    " );

					if (row.getCell(9) == null || row.getCell(9).toString().trim().isEmpty()) {
					    dateValidation = null;
					} else if (row.getCell(9).getCellType() == CellType.NUMERIC) {
					    dateValidation = row.getCell(9).getDateCellValue();
					} else {
					    String dateStr = row.getCell(9).toString().trim();
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
					//dateValidation = dataformatter.formatCellValue(row.getCell(9)).toString();
					affectation = dataformatter.formatCellValue(row.getCell(11)).toString();

					//Optional<Collaborateur> colab = collaborateurRepository.findByCUID(row.getCell(9).toString());
					//Collaborateur colab2= colab.get();
					//affectation = colab2;
					
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

					//dateLivraison = dataformatter.formatCellValue(row.getCell(10)).toString();
					duree =  (Integer.parseInt(dataformatter.formatCellValue(row.getCell(13)).toString()) * 60) + Integer.parseInt(dataformatter.formatCellValue(row.getCell(14)).toString());
					if (row.getCell(15) == null || row.getCell(15).toString().trim().isEmpty()) {
					    dateVerification = null;
					} else if (row.getCell(15).getCellType() == CellType.NUMERIC) {
					    dateVerification = row.getCell(15).getDateCellValue();
					} else {
					    String dateStr = row.getCell(15).toString().trim();
					    if (dateStr.isEmpty()) {
					        dateVerification = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					            dateVerification = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            dateVerification = null;
					        }
					    }
					}

					//dateVerification = dataformatter.formatCellValue(row.getCell(13)).toString();
					commentaire = dataformatter.formatCellValue(row.getCell(16)).toString();
					statut_operationnel = dataformatter.formatCellValue(row.getCell(17)).toString();
					if(row.getCell(18) == null || row.getCell(18).toString().equals("")) {
					    motif = "";
					} else {
					    motif = dataformatter.formatCellValue(row.getCell(18)).toString();
					}

					if (motif.equals("enAttente")) {
					    statutFacturation = "enCoursCds";
					} else {
					    statutFacturation = "facturable";
					}

					row.createCell(19).setCellValue(statutFacturation);
					//motif =  dataformatter.formatCellValue(row.getCell(16)).toString();
					
									
						ESIMB esimb = new ESIMB( 
								"",  refTacheBPU, pres,
								type_element,
								quantite,dateReception, dateLivraison,dateValidation,affectation,duree,
										priorite, commentaire,  motif, dateDeadline,statutFacturation,
										repriseFacturable, codeBanbou, dateReprise,
										dateVerification, codeImb );
						Tarification tarif =  tarifService.calculerTypeTarifEsimb(esimb);
        				  esimb.setTarif(tarif);
						
									
						// affectation.getActeTraitements().add(esimb);
					
				
					if(!services.isExisteEsimb(esimb)) {
						ESIMBRepo.save(esimb);
					}
					
					else {
						System.out.println("existedéja"+ esimb.toString());
					}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";
	}
	
	//Add new Esimb
    @PostMapping("/Add")
	public String addEsimb(@RequestBody ESIMB esimb){
		
        try {
        	
        	if (services.isExisteEsimb(esimb)) {
        		System.out.println("existe");
        		return "existe";
			} 
        	else {
        	
        	    Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Evolution statut IMB");        
                Prestation prestation=_prestation.get();
                             
                Date dateDeadline=null ;

                int nbrjoursmax=prestation.getDeadline();

                Calendar c = Calendar.getInstance();
                c.setTime(esimb.getDateReception());
                c.add(Calendar.DATE, nbrjoursmax);
                dateDeadline = c.getTime();
        	    
                
                			ESIMB esimbb =	new ESIMB(
                					
                						esimb.getIdacte(),
                						    esimb.getRefTacheBPU(),
                						    prestation,
                						    esimb.getType_element(),
                						    esimb.getQuantite(),
                						    esimb.getDateReception(),
                						    esimb.getDateLivraison(),
                						    esimb.getDateValidation(),
                						    esimb.getAffectation(),
                						    esimb.getDuree(),
                						    "P1",
                						    esimb.getCommentaire(),
                						    esimb.getMotif(),
                						    dateDeadline,
                						    esimb.getStatutFacturation(),
                						    esimb.getRepriseFacturable(),
                						    esimb.getCodeBanbou(),
                						    esimb.getDateReprise(),    
                						    esimb.getDateVerification(),
                							esimb.getCodeIMB() 
	                						);
                				Tarification tarif =  tarifService.calculerTypeTarifEsimb(esimbb);
              				  esimbb.setTarif(tarif);
              			  ESIMBRepo.save(esimbb);
           
                return "ok";
        	}
        } catch (Exception e) {
                return "KO : "+e.getMessage();
        }
        
	}

	/*// add Esimb
	@PostMapping("/esimbs")
	public ESIMB create(@Valid @RequestBody ESIMB esimb) {
		Acte_traitement actetrait= new Acte_traitement(esimb.getIdacte(), esimb.getDateVerification());
        ESIMBRepo.save(esimb);
		actetraitementRepository.save(actetrait);
        return esimb;
    }*/
	
	//Get All Esimbs
		@GetMapping("/esimbs")
		public List<ESIMB> getAll(){
			return ESIMBRepo.findAll();
		}
		
		//Get Esimb by code Banbou
		@GetMapping("/getEsimbBycodeBanbou/{codeBanbouString}")
		public List<ESIMB> getEsimbByCodeBanbou(@PathVariable String codeBanbouString){
			return ESIMBRepo.findBycodeBanbouContaining(codeBanbouString);
		}
	
	//Get Esimb by affectation
		@GetMapping("/getEsimbByAffectation/{affectationString}")
		public List<ESIMB> getEsimbByAffectation(@PathVariable String affectationString){
			return ESIMBRepo.findByaffectationContaining(affectationString);
		}

		//Get Esimb by Date de livraison
		@GetMapping("/getEsimbByDL/{dateLivraison}")
		public List<ESIMB> getEsimbByDateLivraison(@PathVariable String dateLivraisonString){
			return ESIMBRepo.findBydateLivraison(dateLivraisonString);
		}
		
		//Get Esimb by CodeIMB
		@GetMapping("/getEsimbBycodeIMB")
		public List<ESIMB> getEsimbBycodeIMB(@RequestParam ("codeIMBString") String codeIMBString ){
			return ESIMBRepo.findBycodeIMBContaining(codeIMBString);
		}
		
		//Get Esimb by motif
		@GetMapping("/getEsimbByMotif/{motifString}")
		public List<ESIMB> getEsimbByMotif(@PathVariable String motifString){
			return ESIMBRepo.findByMotif(motifString);
		}
		
		
		// recherche par motif par ordre 
		 @GetMapping("/SearchParMotif/{motif}")
  public ResponseEntity<List<ESIMB>> rechercherParMotif(@PathVariable("motif") String[] motif){
   List  motifs=Arrays.asList(motif);
   Collections.replaceAll(motifs, "livre", "");
   try {
       List<ESIMB> esimbs = new ArrayList<ESIMB>();
       esimbs=ESIMBRepo.findByMotifInOrMotifInOrderByPrioriteAscDateDeadlineAsc(motifs, Arrays.asList(motif));
       if (esimbs.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(esimbs, HttpStatus.OK);
   } catch (Exception e) {
       return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
   }

} 
		
		@GetMapping("/afficheEsimb/{codeBanbou}")
	    public ResponseEntity<ESIMB> afficheEsimb(@PathVariable("codeBanbou") String codeBanbou){
	        try {

	            Optional<ESIMB> esimb_ = ESIMBRepo.findByCodeBanbou(codeBanbou);


	            if(esimb_.isPresent()){

	                return new ResponseEntity<>(esimb_.get(), HttpStatus.OK);

	            }else {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }


	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	    }
		
		//Update esimb
		@PutMapping("/esimbs/{idacte}")
		public ResponseEntity<ESIMB> updateTutorial(@PathVariable("idacte") String idacte, @RequestBody ESIMB esimb) {
			Optional<ESIMB> esimbData = ESIMBRepo.findById(idacte);

			if (esimbData.isPresent()) {
				ESIMB _esimb = esimbData.get();
				_esimb.setCommentaire(esimb.getCommentaire());
				_esimb.setCodeIMB(esimb.getCodeIMB());
				_esimb.setDateLivraison(esimb.getDateLivraison());
				_esimb.setDateReception(esimb.getDateReception());
				_esimb.setDateReprise(esimb.getDateReprise());
				_esimb.setDateValidation(esimb.getDateValidation());
				_esimb.setDateVerification(esimb.getDateVerification());
				_esimb.setCodeBanbou(esimb.getCodeBanbou());
				_esimb.setDuree(esimb.getDuree());
				_esimb.setMotif(esimb.getMotif());
				_esimb.setCommentaire(esimb.getCommentaire());
				_esimb.setQuantite(esimb.getQuantite());
				_esimb.setRefTacheBPU(esimb.getRefTacheBPU());
				_esimb.setRepriseFacturable(esimb.getRepriseFacturable());
				_esimb.setStatutFacturation(esimb.getStatutFacturation());
						return new ResponseEntity<>(ESIMBRepo.save(_esimb), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
				
		/*@PutMapping("/Update/{idacte}")
		public ResponseEntity<ESIMB> update( @PathVariable String idacte, @RequestBody ESIMB esimb){
					
		//get esimb to update
		Optional<ESIMB> esimbData = ESIMBRepo.findByidacte(idacte);
			        
		//Save the updated esimb
			if (esimbData.isPresent()) {
				System.out.println("esimbData exists");
				ESIMB _esimb = esimbData.get();
				_esimb.setCommentaire(esimb.getCommentaire());
				_esimb.setCodeIMB(esimb.getCodeIMB());
				_esimb.setDateLivraison(esimb.getDateLivraison());
				_esimb.setDateReception(esimb.getDateReception());
				_esimb.setDateReprise(esimb.getDateReprise());
				_esimb.setDateValidation(esimb.getDateValidation());
				_esimb.setDateVerification(esimb.getDateVerification());
				_esimb.setCodeBanbou(esimb.getCodeBanbou());
				_esimb.setDuree(esimb.getDuree());
				_esimb.setMotif(esimb.getMotif());
				_esimb.setCommentaire(esimb.getCommentaire());
				_esimb.setQuantite(esimb.getQuantite());
				_esimb.setRefTacheBPU(esimb.getRefTacheBPU());
				_esimb.setRepriseFacturable(esimb.getRepriseFacturable());
				_esimb.setStatutFacturation(esimb.getStatutFacturation());
						return new ResponseEntity<>(ESIMBRepo.save(_esimb), HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				}*/
		
		
		
		
		/*@PostMapping("/add")
		public String create(@RequestBody Acte_traitement acte_traitement, @RequestParam String idEsimb, @RequestParam String codeIMB, @RequestParam String dateVerification) {
			 try {
		            Acte_traitement _acte = actetraitementRepository.save(
		                new Acte_traitement (acte_traitement.getIdactetrait(),
					acte_traitement.getRefTacheBPU(),
					acte_traitement.getType_prestation(),
					acte_traitement.getType_element(),
					acte_traitement.getQuantite(),
					acte_traitement.getDateReception(),
					acte_traitement.getDateLivraison(),
					acte_traitement.getDateValidation(),
					acte_traitement.getAffectation(),
					acte_traitement.getDuree(),
					acte_traitement.getCommentaire(),
					acte_traitement.getMotif(),
					acte_traitement.getStatutFacturation(),
					acte_traitement.getDateReprise(),
					acte_traitement.getRepriseFacturable()));
		            ESIMBRepo.save(new ESIMB(idEsimb, codeIMB, dateVerification, _acte.getIdactetrait()));
			//ESIMB esimb= new ESIMB(idEsimb, codeIMB, date_verification, _acte.getIdactetrait()));
			//ESIMB esimb= new ESIMB("jghg", "jjh", "jhj");
			//ESIMBRepo.save(esimb);
			
			return ("ok");
			}
			catch(Exception e ) {
				return "KO : "+e.getMessage();
			}
		}*/

	
	/*@GetMapping("/esimbs")
	public ResponseEntity<List<ESIMB>> getAll(@RequestParam(required = false) String codeIMB) {
		try {
			List<ESIMB> esimbs = new ArrayList<ESIMB>();

			if (codeIMB == null)
				ESIMBRepo.findAll().forEach(esimbs::add);
			else
				ESIMBRepo.findBycodeIMBContaining(codeIMB).forEach(esimbs::add);

			if (esimbs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(esimbs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	


	
	
	
	/*@GetMapping("/esimbs/actes/{id_acte}")
	public ResponseEntity<ESIMB> getESIMBByIdActes(@PathVariable("id_acte") String idactetrait) {
		Optional<ESIMB> esimbData = ESIMBRepo.findByidactetrait(idactetrait);

		if (esimbData.isPresent()) {
			return new ResponseEntity<>(esimbData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/esimbs/Esimb")
	public ResponseEntity<List<ESIMB>> getesimb(@RequestParam String codeIMB) {
		try {
			List<ESIMB> esimbs = new ArrayList<ESIMB>();
			if (codeIMB == null)
		    ESIMBRepo.findAll().forEach(esimbs::add);
			else
			ESIMBRepo.findBycodeIMBContaining(codeIMB).forEach(esimbs::add);
			if (esimbs.isEmpty()) {
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(esimbs, HttpStatus.OK);
			} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 }


	/*@GetMapping("/esimbs/idacte/{idacte}")
	public ResponseEntity<ESIMB> getESIMBByIdActe(@PathVariable("idacte") String idacte) {
		Optional<ESIMB> esimbData = ESIMBRepo.findByIdacte(idacte);

		if (esimbData.isPresent()) {
			return new ResponseEntity<>(esimbData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/esimbs/codeIMB/{codeIMB}")
	public ResponseEntity<ESIMB> getESIMBBycodeIMB(@PathVariable("codeIMB") String codeIMB) {
		Optional<ESIMB> esimbData = ESIMBRepo.findByCodeIMB(codeIMB);

		if (esimbData.isPresent()) {
			return new ResponseEntity<>(esimbData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*@GetMapping("/tickets/codeIMB/{codeIMB}")
	public ArrayList<ESIMB> getESIMBByCodeIMB(@PathVariable("codeIMB") String codeIMB) {
		ESIMB es = new ESIMB("123", "12", "hh");
		ESIMB es1 = new ESIMB("123", "12", "hhh");
		ArrayList<ESIMB> a= new ArrayList<ESIMB>();
		a.add(es);
		a.add(es1);
		return a;
		/*Optional<ESIMB> esimbData = ESIMBRepo.findByCodeIMB(codeIMB);

		if (esimbData.isPresent()) {
			
			return new ResponseEntity<>(esimbData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
	
	/*@PutMapping("/esimbs/{idactetrait}")
	public ResponseEntity<ESIMB> updateEsimb(@PathVariable("idactetrait") String idactetrait, @RequestBody ESIMB esimb) {
		Optional<ESIMB> esimbData = ESIMBRepo.findByCodeIMB(idactetrait);

		if (esimbData.isPresent()) {
			ESIMB _esimb = esimbData.get();
			_esimb.setCommentaire(esimb.getCommentaire());
			_esimb.setDate_livraison(esimb.getDate_livraison());
			_esimb.setDate_reception(esimb.getDate_reception());
			_esimb.setDate_reprise(esimb.getDate_reprise());
			_esimb.setDate_validation(esimb.getDate_validation());
			_esimb.setDate_verification(esimb.getDate_verification());
			_esimb.setDuree(esimb.getDuree());
			_esimb.setMotif(esimb.getMotif());
			_esimb.setQuantite(esimb.getQuantite());
			_esimb.setRef_tacheBPU(esimb.getRef_tacheBPU());
			_esimb.setReprise_facturable(esimb.getReprise_facturable());
			_esimb.setStatut_facturation(esimb.getStatut_facturation());
			
			return new ResponseEntity<>(ESIMBRepo.save(_esimb), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/esimbs/{idactetrait}")
	public ResponseEntity<ESIMB> update(@PathVariable(value = "idactetrait") String idactetrait ,
			@Valid @RequestBody ESIMB esimb) throws ResourceNotFoundException {
		ESIMB _esimb = ESIMBRepo.findById(idactetrait)
				.orElseThrow(() -> new ResourceNotFoundException("Esimb not found for this idactetrait :: " + idactetrait));

		_esimb.setCommentaire(esimb.getCommentaire());
		_esimb.setDateLivraison(esimb.getDateLivraison());
		_esimb.setDateReception(esimb.getDateReception());
		_esimb.setDateReprise(esimb.getDateReprise());
		_esimb.setDateValidation(esimb.getDateValidation());
		_esimb.setDateVerification(esimb.getDateVerification());
		_esimb.setDuree(esimb.getDuree());
		_esimb.setMotif(esimb.getMotif());
		_esimb.setQuantite(esimb.getQuantite());
		final ESIMB updatedEsimb = ESIMBRepo.save(esimb);
		return ResponseEntity.ok(updatedEsimb);
	}
	
	

	/*@PutMapping("/tickets")
	public ResponseEntity<ESIMB> update (@RequestBody Acte_traitement acte_traitement, @RequestParam String idacte, @RequestParam String date_verification ) {
		System.out.println("Hi!!");
		Optional<ESIMB> esimbData = ESIMBRepo.findByIdacte(idacte);
		System.out.println(esimbData.toString());
		Optional<Acte_traitement> actetrait = actetraitementRepository.findByidactetrait(acte_traitement.getIdactetrait());
		System.out.println(actetrait.toString());
		//System.out.println("idacte"+ idacte);
		//System.out.println("idactetrait"+ acte_traitement.getIdactetrait());


		if (actetrait.isPresent()) {
			Acte_traitement _actetrait = actetrait.get();
			_actetrait.setType_prestation(acte_traitement.getType_prestation());
			_actetrait.setType_element(acte_traitement.getType_element());
			_actetrait.setQuantite(acte_traitement.getQuantite());
			_actetrait.setDate_reception(acte_traitement.getDate_reception());
			_actetrait.setDate_livraison(acte_traitement.getDate_livraison());
			_actetrait.setDate_validation(acte_traitement.getDate_validation());
			_actetrait.setAffectation(acte_traitement.getAffectation());
			_actetrait.setDuree(acte_traitement.getDuree());
			_actetrait.setCommentaire(acte_traitement.getCommentaire());
			_actetrait.setMotif(acte_traitement.getMotif());
			_actetrait.setStatut_facturation(acte_traitement.getStatut_facturation());
			_actetrait.setDate_reprise(acte_traitement.getDate_reprise());
			_actetrait.setReprise_facturable(acte_traitement.getReprise_facturable());
			//System.out.println("dhgdd!!");
			
		}
	
		

		if (esimbData.isPresent()) {
			ESIMB _esimb = esimbData.get();
			_esimb.setDate_verification(date_verification);
			return new ResponseEntity<>(ESIMBRepo.save(_esimb), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}*/
	
//--------------------1---------------------
				/*//Get Esimbs 
				@GetMapping("/getEsimbs")
				public ResponseEntity<List<Esimb_Resp>> getEsimbs(@RequestParam String cuid,@RequestParam String role){
					try {
						

					List<Esimb_Resp> response = new ArrayList<Esimb_Resp>();
					Esimb_Resp _esimb = new Esimb_Resp();
					List<ESIMB> _allesimbs2 = ESIMBRepo.findByAffectation(cuid);
					List<ESIMB> _allesimbs = ESIMBRepo.findAll();

					_esimb = new Esimb_Resp();

					if(role.equals("PILOTE") ){
						for(int i = 0; i < _allesimbs.size(); i++){
							Optional<Acte_traitement> _acte = actetraitementRepository.findByidacte(_allesimbs.get(i).getIdacte());
							System.out.println(_acte.toString());
							Collaborateur _colab = collaborateurRepository.findByCUID(_acte.get().getAffectation());
							Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid);
							if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
								System.out.println("_colab.getIdequipe() " + _colab.toString());
								System.out.println("_colab_req.getIdequipe() " + _colab_req.toString());

								if (_colab.getIdequipe().equals(_colab_req.getIdequipe()) ) {
									System.out.println("rrr");
									_esimb = new Esimb_Resp(
								    _allesimbs.get(i).getCodeBanbou(),
								    _allesimbs.get(i).getCodeIMB(),
									_allesimbs.get(i).getDateVerification(),
									_acte.get().getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_acte.get().getDuree(),
									_acte.get().getQuantite(),
									_acte.get().getDateLivraison(),
									_acte.get().getCommentaire(),
									_acte.get().getMotif()

									
								);
								response.add(_esimb);
								}
							}	
						}
					}else{
			            for(int i = 0; i < _allesimbs2.size(); i++){
							Optional<Acte_traitement> _acte = actetraitementRepository.findByidacte(_allesimbs2.get(i).getIdacte());
							Collaborateur _colab = collaborateurRepository.findByCUID(_acte.get().getAffectation());
							if(_colab.getCUID().equals(cuid)){
								System.out.println("OK");
								_esimb = new Esimb_Resp(
								_allesimbs2.get(i).getCodeBanbou(),
								_allesimbs2.get(i).getCodeIMB(),
								_allesimbs2.get(i).getDateVerification(),
								_acte.get().getIdacte(),
								_colab.getNom() + " "+_colab.getPrenom(),
								_acte.get().getDuree(),
								_acte.get().getQuantite(),
								_acte.get().getDateLivraison(),
								_acte.get().getCommentaire(),
								_acte.get().getMotif()

							);
							response.add(_esimb);
							}
							
						}
					}
			        return new ResponseEntity<>(response, HttpStatus.OK);
					
					} catch (Exception e) {
					    System.out.println("erreur "+e);
						return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
					}
					
					
				}*/
		
		
		
		
		//-------------2-------------------
		/*//Get Esimbs 
		@GetMapping("/getEsimbs")
		public ResponseEntity<List<Esimb_Resp>> getEsimbs(@RequestParam String cuid,@RequestParam String role){
			try {

			List<Esimb_Resp> response = new ArrayList<Esimb_Resp>();
			Esimb_Resp _esimb = new Esimb_Resp();
			List<ESIMB> _allesimbs = ESIMBRepo.findAll();
			_esimb = new Esimb_Resp();

			if(role.equals("PILOTE") ){
				for(int i = 0; i < _allesimbs.size(); i++){
					Optional<Acte_traitement> _acte = actetraitementRepository.findByidacte(_allesimbs.get(i).getIdacte());
					System.out.println(_acte.toString());
					Collaborateur _colab = collaborateurRepository.findByCUID(_acte.get().getAffectation());
					Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid);
					if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
						System.out.println("_colab.getIdequipe() " + _colab.toString());
						System.out.println("_colab_req.getIdequipe() " + _colab_req.toString());

						if (_colab.getIdequipe().equals(_colab_req.getIdequipe()) ) {
							System.out.println("rrr");
							_esimb = new Esimb_Resp(
						    _allesimbs.get(i).getCodeBanbou(),
						    _allesimbs.get(i).getCodeIMB(),
							_allesimbs.get(i).getDateVerification(),
							_acte.get().getIdacte(),
							_colab.getNom() + " "+_colab.getPrenom(),
							_acte.get().getDuree(),
							_acte.get().getQuantite(),
							_acte.get().getDateLivraison(),
							_acte.get().getCommentaire(),
							_acte.get().getMotif()

							
						);
						response.add(_esimb);
						}
					}	
				}
			}else{
	            for(int i = 0; i < _allesimbs.size(); i++){
					Optional<Acte_traitement> _acte = actetraitementRepository.findByidacte(_allesimbs.get(i).getIdacte());
					Collaborateur _colab = collaborateurRepository.findByCUID(_acte.get().getAffectation());
					if(_colab.getCUID().equals(cuid)){
						System.out.println("OK");
						_esimb = new Esimb_Resp(
							    _allesimbs.get(i).getCodeBanbou(),
								_allesimbs.get(i).getCodeIMB(),
								_allesimbs.get(i).getDateVerification(),
						_acte.get().getIdacte(),
						_colab.getNom() + " "+_colab.getPrenom(),
						_acte.get().getDuree(),
						_acte.get().getQuantite(),
						_acte.get().getDateLivraison(),
						_acte.get().getCommentaire(),
						_acte.get().getMotif()

					);
					response.add(_esimb);
					}
					
				}
			}
	        return new ResponseEntity<>(response, HttpStatus.OK);
			
			} catch (Exception e) {
			    System.out.println("erreur "+e);
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
			
			
		}*/
				
		//------------------------ Get Esimbs by Affectation ---------------------------------------- 
		/*@GetMapping("/getByAffectation")
			public ResponseEntity<List<Esimb_Resp>> getEsimbsbyAffectation(@RequestParam String cuid,@RequestParam String affectation){
				try {
					boolean isPilote = false;
											
				   //response global 
					List<Esimb_Resp> response = new ArrayList<Esimb_Resp>();
					Esimb_Resp _esimb = new Esimb_Resp();
											
				  //get All esimbs containing id acte
					List<ESIMB> _allesimbs = ESIMBRepo.findByAffectation(affectation);
						_esimb = new Esimb_Resp();
											
				//get Collab infos
					Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid);
				//check if PILOTE
					if(_colab_req.getFonction().equals("3")) {
							isPilote = true;
								}
											
					if(isPilote){
			          for(int i = 0; i < _allesimbs.size(); i++){
					
						Collaborateur _colab = collaborateurRepository.findByCUID(_allesimbs.get(i).getAffectation());
						if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
							if (_colab.getIdequipe().equals(_colab_req.getIdequipe()) ) {
								_esimb = new Esimb_Resp(
								_allesimbs.get(i).getCodeBanbou(),	
								_allesimbs.get(i).getCodeIMB(),
								_allesimbs.get(i).getDateVerification(),
								_colab.getNom() + " "+_colab.getPrenom(),
								_allesimbs.get(i).getIdacte(),
								_allesimbs.get(i).getDuree(),
								_allesimbs.get(i).getCommentaire());
											
								response.add(_esimb);
														}
													}	
												
												}
							}else{
							   for(int i = 0; i < _allesimbs.size(); i++){
								Collaborateur _colab = collaborateurRepository.findByCUID(_allesimbs.get(i).getAffectation());
									if(_colab.getCUID().equals(cuid)){
									//System.out.println("OK");
										_esimb = new Esimb_Resp(
												_allesimbs.get(i).getCodeBanbou(),	
												_allesimbs.get(i).getCodeIMB(),
												_allesimbs.get(i).getDateVerification(),
												_colab.getNom() + " "+_colab.getPrenom(),
												_allesimbs.get(i).getIdacte(),
												_allesimbs.get(i).getDuree(),
												_allesimbs.get(i).getCommentaire());
										response.add(_esimb);
													}
													
												
									            }
											}
									      return new ResponseEntity<>(response, HttpStatus.OK);
											
											} catch (Exception e) {
											    System.out.println("erreur "+e);
												return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
											}
											
											
										}*/
	
	
   @GetMapping("/actes")
    public List<Acte_traitement> getActesByCollaborateur(@RequestParam String cuid)
    {
  
        System.out.println("cuid: " + cuid);
        //System.out.println("role: " + role);
        
        Optional<Collaborateur> colab = collaborateurRepository.findByCUID(cuid);
        
        Collaborateur colab2= colab.get();
        
        List<Acte_traitement> actes = actetraitementRepository.findByAffectation(colab2.getCUID());
        List<Acte_traitement> actes2 = actetraitementRepository.findAll();


        if (colab2.getFonction().equals("2")) {
        	System.out.println("tous les actes: " + actes2.size());
            return actes2;
            
        } else  {
        	System.out.println("actes pour " + cuid + ": " + actes.size());
            return actes;
            
        }
  	
  	
  	
    }
    
}
		
		


	
	



