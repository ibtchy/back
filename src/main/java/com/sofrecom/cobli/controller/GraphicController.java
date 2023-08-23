package com.sofrecom.cobli.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.core.env.Environment;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Api_Response;
import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import com.sofrecom.cobli.repository.FacturationRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;
import com.sofrecom.cobli.models.request.Grafic_Resp;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/graphic")
public class GraphicController {
	@Autowired
    private Graphic_Repository graphic_Repository;
    @Autowired
	Acte_traitementRepository actetraitementRepository;
	@Autowired
	CollaborateurRepository collaborateurRepository;
	@Autowired
	FacturationRepository facturationRepository;
	@Autowired 
	PrestationRepository prestationRepository;
	@Autowired
	Services services;
	
// DONE -------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------Import Excel----------------------------------------
	
	@PostMapping("excel")
	public String excelReader(@RequestParam("file") MultipartFile excel) throws Exception {
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			String affectation = "";
			String id_Grafic = "";
			String iar = "";
			String code_imb = "";
			String groupe_operation = "";
			Date date_traitement;
			int duree = 1;
			String statut_graphic = "";
			String traitement_effectue = "";
			String type_traitement = "";
			String commentaire = "";
			String priorite = "";
			Date dateDeadline = null;
			Date dateReception = null;
			Date dateLivraison = null;
			Date dateValidation = null;
			int quantite = 1;
			String motif = "";
			String type_element= "";
			Date dateReprise = null;
			/*
			String prenom = "";
			String nom = "";
			int indxagent = 0;*/
			
			for(int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
				XSSFRow row = sheet.getRow(i);
				//for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
					DataFormatter dataformatter = new DataFormatter();
					affectation = dataformatter.formatCellValue(row.getCell(0)).toString();
					id_Grafic = dataformatter.formatCellValue(row.getCell(1)).toString();
					iar = dataformatter.formatCellValue(row.getCell(2)).toString();
					code_imb = dataformatter.formatCellValue(row.getCell(3)).toString();
					groupe_operation = dataformatter.formatCellValue(row.getCell(4)).toString();
					if (row.getCell(5) == null || row.getCell(5).toString().trim().isEmpty()) {
						date_traitement = null;
					} else if (row.getCell(5).getCellType() == CellType.NUMERIC) {
						date_traitement = row.getCell(5).getDateCellValue();
					} else {
					    String dateStr = row.getCell(5).toString().trim();
					    if (dateStr.isEmpty()) {
					    	date_traitement = null;
					    } else {
					        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					        try {
					        	date_traitement = dateFormat.parse(dateStr);
					        } catch (ParseException e) {
					            // handle parse exception
					            e.printStackTrace();
					            date_traitement = null;
					        }
					    }
					}
					//date_traitement = dataformatter.formatCellValue(row.getCell(5)).toString();
					/*Date date1=new SimpleDateFormat("dd/MM/yy").parse(date_traitement);  
					System.out.println("---------------  "+date_traitement+"  To date :  "+date1);*/
					duree =  Integer.parseInt(dataformatter.formatCellValue(row.getCell(7)).toString());
					statut_graphic = dataformatter.formatCellValue(row.getCell(8)).toString();
					traitement_effectue = dataformatter.formatCellValue(row.getCell(9)).toString();
					type_traitement = dataformatter.formatCellValue(row.getCell(10)).toString();
					commentaire = row.getCell(11).toString();
					Optional<Prestation> prestation = prestationRepository.findByNomPrestation("Grafic");
					 Prestation pres= prestation.get();
					/*// get CUID from Nom & Prenom
					for(int x =0;x<agent.length();x++) {
						if(agent.charAt(x) == '.') {
							indxagent = x;
						}					
					}
					
					nom = agent.substring(0, indxagent);
					prenom = agent.substring(indxagent, agent.length());
					
					System.out.println("----------------------------- nom    " + nom + "----------------------------- prenom    " + prenom);*/
					Graphic grafic = new Graphic(

							"", "",pres, type_element,quantite,
							dateReception, dateLivraison, dateValidation,
							affectation, dateDeadline,
							priorite, duree, commentaire, motif, "",
							dateReprise, "", id_Grafic, iar, code_imb,
							groupe_operation,null,  statut_graphic,traitement_effectue,
							type_traitement, true
							
							
							
							
							
							
							
							/*"",//idacte
												  "",//refTacheBPU
												  pres,//type_prestation
												  "",//type_element
												  0,//quantite
												  null,//dateReception
												  null,//dateLivraison
												  null,//dateValidation
												  agent,//affectation
												  duree,//duree
												  commentaire,//commentaire
												  "",//motif
												  "",//statutFacturation
												  null,//dateReprise
												  "",//repriseFacturable
												  id_Grafic,//idGrafic
												  iar,//iar
												  code_imb,//code_imb
												  groupe_operation,//groupe_operation
												  date_traitement,//dateTraitement
												  statut_grafic,//statut_graphic
												  traitement_effectue,//traitement_effectue
												  type_traitement,//type_traitement
												  priorite,
												  dateDeadline,
												  true//active*/
												  );
					if(!services.isExisteGrafic(grafic)) {
						graphic_Repository.save(grafic);
					}
					else {
						System.out.println("existedéja"+ grafic.toString());
					}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";
	}
	@GetMapping("/test")
	public List<Graphic> test() throws IOException {
		String query="";
        StringBuilder builder = new StringBuilder();
 
        try (BufferedReader buffer = new BufferedReader(new FileReader("D:/DEV/Spring_workspace/CELLULE_OUTILS_BACK/generated/query_facturation.txt"))) {
            String str;
            while ((str = buffer.readLine()) != null) {
                builder.append(str).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
      
        // Returning a string
        query =  builder.toString();
        System.out.println("---------- query :" + query);
        //query = "Select code_imb from graphic";
		return facturationRepository.getFacturation();
	}
	
	@GetMapping("/test1")
	public List<Graphic> test1() throws IOException {
		String query="";
        StringBuilder builder = new StringBuilder();
 
        try (BufferedReader buffer = new BufferedReader(new FileReader("D:/DEV/Spring_workspace/CELLULE_OUTILS_BACK/generated/query_facturation1.txt"))) {
            String str;
            while ((str = buffer.readLine()) != null) {
                builder.append(str).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
      
        // Returning a string
        query =  builder.toString();
        System.out.println("---------- query :" + query);
        query = "statut_facturation";
		return facturationRepository.getFacturationgrafic(query);
	}
	
	
	//Add new graphic
    @PostMapping("/Add")
	public String addGraphic(@RequestBody Graphic graphic){
    	Api_Response response = new Api_Response();
        try {
        	
        	if (services.isExisteGrafic(graphic)) {
        		response.setCode_response("ko");// 1 = Error
        		response.setMessage_response("Acte Already Existe");//message
        		return "1";
        	}else {
        		 Optional<Prestation> _prestation = prestationRepository.findByNomPrestation("Grafic");

                 Prestation prestation=_prestation.get();



                 Date dateDeadline=null ;

                 int nbrjoursmax=prestation.getDeadline();

                 Calendar c = Calendar.getInstance();
                 c.setTime(graphic.getDateReception());
                 c.add(Calendar.DATE, nbrjoursmax);
                 dateDeadline = c.getTime();
        	graphic_Repository.save(
                				new Graphic(
					
                						graphic.getIdacte(),
                						    graphic.getRefTacheBPU(),
                						    prestation,
                						    graphic.getType_element(),
                						    graphic.getQuantite(),
                						    graphic.getDateReception(),
                						    graphic.getDateLivraison(),
                						    graphic.getDateValidation(),
                						    graphic.getAffectation(),
                						    dateDeadline,
                						    "P1",
                						    graphic.getDuree(),
                						    graphic.getCommentaire(),
                						    graphic.getMotif(),
                						    graphic.getStatutFacturation(),
                						    graphic.getDateReprise(),
                						    graphic.getRepriseFacturable(),
                							graphic.getidGrafic(),
	                						graphic.getIar(),
	                						graphic.getCode_imb(),
	                						graphic.getgroupe_operation(),
	                						graphic.getDateTraitement(),
	                						graphic.getStatut_graphic(),
	                						graphic.getTraitement_effectue(),
	                						graphic.getType_traitement(),
	                						
	                						
	                						true));
        	response.setCode_response("ok");// 0 = ok
    		response.setMessage_response("Acte Added successfully !");//message
    		return "0";
        	}    
        	//return response;
        	
        } catch (Exception e) {
        	response.setCode_response("ko");// 1 = Error
    		response.setMessage_response("There is a problem please try again !");//message
    		System.out.println("exception is :" + e);
            //return response;
    		return "2";
        }
        
	}
    
  //----------------------------------------------------------------
  	//Get Active Grafics
  	@GetMapping("/getActiveGraphics")
  	public ResponseEntity<List<Grafic_Resp>> getGraphics(@RequestParam String cuid){
  		try {
  		boolean isPilote = false;
  		
  		//response global 
  		List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
  		Grafic_Resp _graphic = new Grafic_Resp();
  		
  		//get All grafics
  		List<Graphic> _allgraphics = graphic_Repository.findAll();
  		_graphic = new Grafic_Resp();
  		
  		//get Collab infos
  		Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
  		//check if PILOTE
  		if(_colab_req.getFonction().equals("3")) {
  			isPilote = true;
  		}
  		
  		if(isPilote){
  			for(int i = 0; i < _allgraphics.size(); i++){
  				if(_allgraphics.get(i).isActive()) {
  				Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
  				if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
  					if (_colab.getEquipe().equals(_colab_req.getEquipe().getIdEquipe()) ) {
  						_graphic = new Grafic_Resp(
  						_allgraphics.get(i).getidGrafic(),
  						_allgraphics.get(i).getIar(),
  						_allgraphics.get(i).getCode_imb(),
  						_allgraphics.get(i).getgroupe_operation(),
  						_allgraphics.get(i).getDateTraitement(),
  						_allgraphics.get(i).getStatut_graphic(),
  						_allgraphics.get(i).getTraitement_effectue(),
  						_allgraphics.get(i).getType_traitement(),
  						_allgraphics.get(i).getIdacte(),
  						_colab.getNom() + " "+_colab.getPrenom(),
  						_allgraphics.get(i).getDuree(),
  						_allgraphics.get(i).getCommentaire()
  					);
  		
  					response.add(_graphic);
  					}
  				}	
  			}
  			}
  		}else{
              for(int i = 0; i < _allgraphics.size(); i++){
              	if(_allgraphics.get(i).isActive()) {
  				Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
  				if(_colab.getCUID().equals(cuid)){
  					//System.out.println("OK");
  					_graphic = new Grafic_Resp(
  					_allgraphics.get(i).getidGrafic(),
  					_allgraphics.get(i).getIar(),
  					_allgraphics.get(i).getCode_imb(),
  					_allgraphics.get(i).getgroupe_operation(),
  					_allgraphics.get(i).getDateTraitement(),
  					_allgraphics.get(i).getStatut_graphic(),
  					_allgraphics.get(i).getTraitement_effectue(),
  					_allgraphics.get(i).getType_traitement(),
  					_allgraphics.get(i).getIdacte(),
  					_colab.getNom() + " "+_colab.getPrenom(),
  					_allgraphics.get(i).getDuree(),
  					_allgraphics.get(i).getCommentaire()
  				);
  				response.add(_graphic);
  				}
  				
  			}
              }
  		}
          return new ResponseEntity<>(response, HttpStatus.OK);
  		
  		} catch (Exception e) {
  		    System.out.println("erreur "+e);
  			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
  		}	
  	}
  	
//----------------------------------------------------------------
  //Get Non Active Grafics
  @GetMapping("/getNonActiveGraphics")
  public ResponseEntity<List<Grafic_Resp>> getGraphicsNonActive(@RequestParam String cuid){
  	try {
  		boolean isPilote = false;
  			
  		//response global 
  		List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
  		Grafic_Resp _graphic = new Grafic_Resp();
  			
  		//get All grafics
  		List<Graphic> _allgraphics = graphic_Repository.findAll();
  		_graphic = new Grafic_Resp();
  			
  		//get Collab infos
  		Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
  		//check if PILOTE
  		if(_colab_req.getFonction().equals("3")) {
  			isPilote = true;}
  		if(isPilote){
  			for(int i = 0; i < _allgraphics.size(); i++){
  				if(!(_allgraphics.get(i).isActive())) {
  					Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
  					if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
  						if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
  							_graphic = new Grafic_Resp(
  							_allgraphics.get(i).getidGrafic(),
  							_allgraphics.get(i).getIar(),
  							_allgraphics.get(i).getCode_imb(),
  							_allgraphics.get(i).getgroupe_operation(),
  							_allgraphics.get(i).getDateTraitement(),
  							_allgraphics.get(i).getStatut_graphic(),
  							_allgraphics.get(i).getTraitement_effectue(),
  							_allgraphics.get(i).getType_traitement(),
  							_allgraphics.get(i).getIdacte(),
  							_colab.getNom() + " "+_colab.getPrenom(),
  							_allgraphics.get(i).getDuree(),
  							_allgraphics.get(i).getCommentaire()
  						);
  			
  						response.add(_graphic);
  						}
  					}	
  				}
  				}
  			}else{
  	            for(int i = 0; i < _allgraphics.size(); i++){
  	            	if(!(_allgraphics.get(i).isActive())) {
  					Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
  					if(_colab.getCUID().equals(cuid)){
  						//System.out.println("OK");
  						_graphic = new Grafic_Resp(
  						_allgraphics.get(i).getidGrafic(),
  						_allgraphics.get(i).getIar(),
  						_allgraphics.get(i).getCode_imb(),
  						_allgraphics.get(i).getgroupe_operation(),
  						_allgraphics.get(i).getDateTraitement(),
  						_allgraphics.get(i).getStatut_graphic(),
  						_allgraphics.get(i).getTraitement_effectue(),
  						_allgraphics.get(i).getType_traitement(),
  						_allgraphics.get(i).getIdacte(),
  						_colab.getNom() + " "+_colab.getPrenom(),
  						_allgraphics.get(i).getDuree(),
  						_allgraphics.get(i).getCommentaire()
  					);
  					response.add(_graphic);
  					}
  					
  				}
  	            }
  			}
  	        return new ResponseEntity<>(response, HttpStatus.OK);
  			
  			} catch (Exception e) {
  			    System.out.println("erreur "+e);
  				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
  			}
  			
  			
  		}

  

//------------------------ Get Non Active Grafics by id grafic ----------------------------------------
   @GetMapping("/getActiveByIdGrafic")
		public ResponseEntity<List<Grafic_Resp>> getGraphicsActivebyIdGrafic(@RequestParam String cuid,@RequestParam String idGraphic){
			try {
					boolean isPilote = false;
					
					//response global 
					List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
					Grafic_Resp _graphic = new Grafic_Resp();
					
					//get All grafics containing id grafic
					List<Graphic> _allgraphics = graphic_Repository.findByidGraficContaining(idGraphic);
					_graphic = new Grafic_Resp();
					
					//get Collab infos
					Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
					//check if PILOTE
					if(_colab_req.getFonction().equals("3")) {
						isPilote = true;
					}
					
					if(isPilote){
						for(int i = 0; i < _allgraphics.size(); i++){
							if(_allgraphics.get(i).isActive()) {
							Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
							if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
								if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
									_graphic = new Grafic_Resp(
									_allgraphics.get(i).getidGrafic(),
									_allgraphics.get(i).getIar(),
									_allgraphics.get(i).getCode_imb(),
									_allgraphics.get(i).getgroupe_operation(),
									_allgraphics.get(i).getDateTraitement(),
									_allgraphics.get(i).getStatut_graphic(),
									_allgraphics.get(i).getTraitement_effectue(),
									_allgraphics.get(i).getType_traitement(),
									_allgraphics.get(i).getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_allgraphics.get(i).getDuree(),
									_allgraphics.get(i).getCommentaire()
								);
					
								response.add(_graphic);
								}
							}	
						}
						}
					}else{
			            for(int i = 0; i < _allgraphics.size(); i++){
			            	if(_allgraphics.get(i).isActive()) {
							Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
							if(_colab.getCUID().equals(cuid)){
								//System.out.println("OK");
								_graphic = new Grafic_Resp(
								_allgraphics.get(i).getidGrafic(),
								_allgraphics.get(i).getIar(),
								_allgraphics.get(i).getCode_imb(),
								_allgraphics.get(i).getgroupe_operation(),
								_allgraphics.get(i).getDateTraitement(),
								_allgraphics.get(i).getStatut_graphic(),
								_allgraphics.get(i).getTraitement_effectue(),
								_allgraphics.get(i).getType_traitement(),
								_allgraphics.get(i).getIdacte(),
								_colab.getNom() + " "+_colab.getPrenom(),
								_allgraphics.get(i).getDuree(),
								_allgraphics.get(i).getCommentaire()
							);
							response.add(_graphic);
							}
							
						}
			            }
					}
			        return new ResponseEntity<>(response, HttpStatus.OK);
					
					} catch (Exception e) {
					    System.out.println("erreur "+e);
						return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
					}
					
					
				}	


//----------  Get Non Active Grafics by id grafic ------------------------------------------------------
	@GetMapping("/getNonActiveByIdGrafic")
				public ResponseEntity<List<Grafic_Resp>> getGraphicsNonActivebyIdGrafic(@RequestParam String cuid,@RequestParam String idGraphic){
					try {
							boolean isPilote = false;
							
							//response global 
							List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
							Grafic_Resp _graphic = new Grafic_Resp();
							
							//get All grafics containing id grafic
							List<Graphic> _allgraphics = graphic_Repository.findByidGraficContaining(idGraphic);
							_graphic = new Grafic_Resp();
							
							//get Collab infos
							Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
							//check if PILOTE
							if(_colab_req.getFonction().equals("3")) {
								isPilote = true;
							}
							
							if(isPilote){
								for(int i = 0; i < _allgraphics.size(); i++){
									if(!(_allgraphics.get(i).isActive())) {
									Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
									if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
										if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
											_graphic = new Grafic_Resp(
											_allgraphics.get(i).getidGrafic(),
											_allgraphics.get(i).getIar(),
											_allgraphics.get(i).getCode_imb(),
											_allgraphics.get(i).getgroupe_operation(),
											_allgraphics.get(i).getDateTraitement(),
											_allgraphics.get(i).getStatut_graphic(),
											_allgraphics.get(i).getTraitement_effectue(),
											_allgraphics.get(i).getType_traitement(),
											_allgraphics.get(i).getIdacte(),
											_colab.getNom() + " "+_colab.getPrenom(),
											_allgraphics.get(i).getDuree(),
											_allgraphics.get(i).getCommentaire()
										);
							
										response.add(_graphic);
										}
									}	
								}
								}
							}else{
					            for(int i = 0; i < _allgraphics.size(); i++){
					            	if(!(_allgraphics.get(i).isActive())) {
									Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
									if(_colab.getCUID().equals(cuid)){
										//System.out.println("OK");
										_graphic = new Grafic_Resp(
										_allgraphics.get(i).getidGrafic(),
										_allgraphics.get(i).getIar(),
										_allgraphics.get(i).getCode_imb(),
										_allgraphics.get(i).getgroupe_operation(),
										_allgraphics.get(i).getDateTraitement(),
										_allgraphics.get(i).getStatut_graphic(),
										_allgraphics.get(i).getTraitement_effectue(),
										_allgraphics.get(i).getType_traitement(),
										_allgraphics.get(i).getIdacte(),
										_colab.getNom() + " "+_colab.getPrenom(),
										_allgraphics.get(i).getDuree(),
										_allgraphics.get(i).getCommentaire()
									);
									response.add(_graphic);
									}
									
								}
					            }
							}
					        return new ResponseEntity<>(response, HttpStatus.OK);
							
							} catch (Exception e) {
							    System.out.println("erreur "+e);
								return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
							}
							
							
						}	

//------------------------ Get  Active Grafics by Date de Traitement ----------------------------------------
	   @GetMapping("/getActiveByIdDateTraitement")
			public ResponseEntity<List<Grafic_Resp>> getGraphicsActivebyDateTraitement(@RequestParam String cuid,@RequestParam String dateTtraitement){
				try {
						boolean isPilote = false;
						
						//response global 
						List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
						Grafic_Resp _graphic = new Grafic_Resp();
						
						//get All grafics containing id grafic
						List<Graphic> _allgraphics = graphic_Repository.findBydateTraitement(dateTtraitement);
						_graphic = new Grafic_Resp();
						
						//get Collab infos
						Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
						//check if PILOTE
						if(_colab_req.getFonction().equals("3")) {
							isPilote = true;
						}
						
						if(isPilote){
							for(int i = 0; i < _allgraphics.size(); i++){
								if(_allgraphics.get(i).isActive()) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
									if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
										_graphic = new Grafic_Resp(
										_allgraphics.get(i).getidGrafic(),
										_allgraphics.get(i).getIar(),
										_allgraphics.get(i).getCode_imb(),
										_allgraphics.get(i).getgroupe_operation(),
										_allgraphics.get(i).getDateTraitement(),
										_allgraphics.get(i).getStatut_graphic(),
										_allgraphics.get(i).getTraitement_effectue(),
										_allgraphics.get(i).getType_traitement(),
										_allgraphics.get(i).getIdacte(),
										_colab.getNom() + " "+_colab.getPrenom(),
										_allgraphics.get(i).getDuree(),
										_allgraphics.get(i).getCommentaire()
									);
						
									response.add(_graphic);
									}
								}	
							}
							}
						}else{
				            for(int i = 0; i < _allgraphics.size(); i++){
				            	if(_allgraphics.get(i).isActive()) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(_colab.getCUID().equals(cuid)){
									//System.out.println("OK");
									_graphic = new Grafic_Resp(
									_allgraphics.get(i).getidGrafic(),
									_allgraphics.get(i).getIar(),
									_allgraphics.get(i).getCode_imb(),
									_allgraphics.get(i).getgroupe_operation(),
									_allgraphics.get(i).getDateTraitement(),
									_allgraphics.get(i).getStatut_graphic(),
									_allgraphics.get(i).getTraitement_effectue(),
									_allgraphics.get(i).getType_traitement(),
									_allgraphics.get(i).getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_allgraphics.get(i).getDuree(),
									_allgraphics.get(i).getCommentaire()
								);
								response.add(_graphic);
								}
								
							}
				            }
						}
				        return new ResponseEntity<>(response, HttpStatus.OK);
						
						} catch (Exception e) {
						    System.out.println("erreur "+e);
							return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
						}
						
						
					}	
	
	   
//------------------------ Get Non Active Grafics by Date de Traitement ----------------------------------------
	   @GetMapping("/getNonActiveByIdDateTraitement")
			public ResponseEntity<List<Grafic_Resp>> getGraphicsNonActivebyDateTraitement(@RequestParam String cuid,@RequestParam String dateTtraitement){
				try {
						boolean isPilote = false;
						
						//response global 
						List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
						Grafic_Resp _graphic = new Grafic_Resp();
						
						//get All grafics containing id grafic
						List<Graphic> _allgraphics = graphic_Repository.findBydateTraitement(dateTtraitement);
						_graphic = new Grafic_Resp();
						
						//get Collab infos
						Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
						//check if PILOTE
						if(_colab_req.getFonction().equals("3")) {
							isPilote = true;
						}
						
						if(isPilote){
							for(int i = 0; i < _allgraphics.size(); i++){
								if(!(_allgraphics.get(i).isActive())) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
									if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
										_graphic = new Grafic_Resp(
										_allgraphics.get(i).getidGrafic(),
										_allgraphics.get(i).getIar(),
										_allgraphics.get(i).getCode_imb(),
										_allgraphics.get(i).getgroupe_operation(),
										_allgraphics.get(i).getDateTraitement(),
										_allgraphics.get(i).getStatut_graphic(),
										_allgraphics.get(i).getTraitement_effectue(),
										_allgraphics.get(i).getType_traitement(),
										_allgraphics.get(i).getIdacte(),
										_colab.getNom() + " "+_colab.getPrenom(),
										_allgraphics.get(i).getDuree(),
										_allgraphics.get(i).getCommentaire()
									);
						
									response.add(_graphic);
									}
								}	
							}
							}
						}else{
				            for(int i = 0; i < _allgraphics.size(); i++){
				            	if(!(_allgraphics.get(i).isActive())) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(_colab.getCUID().equals(cuid)){
									//System.out.println("OK");
									_graphic = new Grafic_Resp(
									_allgraphics.get(i).getidGrafic(),
									_allgraphics.get(i).getIar(),
									_allgraphics.get(i).getCode_imb(),
									_allgraphics.get(i).getgroupe_operation(),
									_allgraphics.get(i).getDateTraitement(),
									_allgraphics.get(i).getStatut_graphic(),
									_allgraphics.get(i).getTraitement_effectue(),
									_allgraphics.get(i).getType_traitement(),
									_allgraphics.get(i).getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_allgraphics.get(i).getDuree(),
									_allgraphics.get(i).getCommentaire()
								);
								response.add(_graphic);
								}
								
							}
				            }
						}
				        return new ResponseEntity<>(response, HttpStatus.OK);
						
						} catch (Exception e) {
						    System.out.println("erreur "+e);
							return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
						}
						
						
					}	
	   

//------------------------ Get Active Grafics by Affectation ---------------------------------------- 
@GetMapping("/getActiveByAffectation")
			public ResponseEntity<List<Grafic_Resp>> getGraphicsActivebyDateAffectation(@RequestParam String cuid,@RequestParam String affectation){
				try {
						boolean isPilote = false;
						
						//response global 
						List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
						Grafic_Resp _graphic = new Grafic_Resp();
						
						//get All grafics containing id grafic
						List<Graphic> _allgraphics = graphic_Repository.findByAffectation(affectation);
						_graphic = new Grafic_Resp();
						
						//get Collab infos
						Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
						//check if PILOTE
						if(_colab_req.getFonction().equals("3")) {
							isPilote = true;
						}
						
						if(isPilote){
							for(int i = 0; i < _allgraphics.size(); i++){
								if(_allgraphics.get(i).isActive()) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
									if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
										_graphic = new Grafic_Resp(
										_allgraphics.get(i).getidGrafic(),
										_allgraphics.get(i).getIar(),
										_allgraphics.get(i).getCode_imb(),
										_allgraphics.get(i).getgroupe_operation(),
										_allgraphics.get(i).getDateTraitement(),
										_allgraphics.get(i).getStatut_graphic(),
										_allgraphics.get(i).getTraitement_effectue(),
										_allgraphics.get(i).getType_traitement(),
										_allgraphics.get(i).getIdacte(),
										_colab.getNom() + " "+_colab.getPrenom(),
										_allgraphics.get(i).getDuree(),
										_allgraphics.get(i).getCommentaire()
									);
						
									response.add(_graphic);
									}
								}	
							}
							}
						}else{
				            for(int i = 0; i < _allgraphics.size(); i++){
				            	if(_allgraphics.get(i).isActive()) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(_colab.getCUID().equals(cuid)){
									//System.out.println("OK");
									_graphic = new Grafic_Resp(
									_allgraphics.get(i).getidGrafic(),
									_allgraphics.get(i).getIar(),
									_allgraphics.get(i).getCode_imb(),
									_allgraphics.get(i).getgroupe_operation(),
									_allgraphics.get(i).getDateTraitement(),
									_allgraphics.get(i).getStatut_graphic(),
									_allgraphics.get(i).getTraitement_effectue(),
									_allgraphics.get(i).getType_traitement(),
									_allgraphics.get(i).getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_allgraphics.get(i).getDuree(),
									_allgraphics.get(i).getCommentaire()
								);
								response.add(_graphic);
								}
								
							}
				            }
						}
				        return new ResponseEntity<>(response, HttpStatus.OK);
						
						} catch (Exception e) {
						    System.out.println("erreur "+e);
							return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
						}
						
						
					}

//------------------------ Get Non Active Grafics by Affectation ---------------------------------------- 
@GetMapping("/getNonActiveByAffectation")
			public ResponseEntity<List<Grafic_Resp>> getGraphicsNonActivebyDateAffectation(@RequestParam String cuid,@RequestParam String affectation){
				try {
						boolean isPilote = false;
						
						//response global 
						List<Grafic_Resp> response = new ArrayList<Grafic_Resp>();
						Grafic_Resp _graphic = new Grafic_Resp();
						
						//get All grafics containing id grafic
						List<Graphic> _allgraphics = graphic_Repository.findByAffectation(affectation);
						_graphic = new Grafic_Resp();
						
						//get Collab infos
						Collaborateur _colab_req = collaborateurRepository.findByCUID(cuid).get();
						//check if PILOTE
						if(_colab_req.getFonction().equals("3")) {
							isPilote = true;
						}
						
						if(isPilote){
							for(int i = 0; i < _allgraphics.size(); i++){
								if(!(_allgraphics.get(i).isActive())) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(Objects.nonNull(_colab) && Objects.nonNull(_colab_req)){
									if (_colab.getEquipe().equals(_colab_req.getEquipe()) ) {
										_graphic = new Grafic_Resp(
										_allgraphics.get(i).getidGrafic(),
										_allgraphics.get(i).getIar(),
										_allgraphics.get(i).getCode_imb(),
										_allgraphics.get(i).getgroupe_operation(),
										_allgraphics.get(i).getDateTraitement(),
										_allgraphics.get(i).getStatut_graphic(),
										_allgraphics.get(i).getTraitement_effectue(),
										_allgraphics.get(i).getType_traitement(),
										_allgraphics.get(i).getIdacte(),
										_colab.getNom() + " "+_colab.getPrenom(),
										_allgraphics.get(i).getDuree(),
										_allgraphics.get(i).getCommentaire()
									);
						
									response.add(_graphic);
									}
								}	
							}
							}
						}else{
				            for(int i = 0; i < _allgraphics.size(); i++){
				            	if(!(_allgraphics.get(i).isActive())) {
								Collaborateur _colab = collaborateurRepository.findByCUID(_allgraphics.get(i).getAffectation()).get();
								if(_colab.getCUID().equals(cuid)){
									//System.out.println("OK");
									_graphic = new Grafic_Resp(
									_allgraphics.get(i).getidGrafic(),
									_allgraphics.get(i).getIar(),
									_allgraphics.get(i).getCode_imb(),
									_allgraphics.get(i).getgroupe_operation(),
									_allgraphics.get(i).getDateTraitement(),
									_allgraphics.get(i).getStatut_graphic(),
									_allgraphics.get(i).getTraitement_effectue(),
									_allgraphics.get(i).getType_traitement(),
									_allgraphics.get(i).getIdacte(),
									_colab.getNom() + " "+_colab.getPrenom(),
									_allgraphics.get(i).getDuree(),
									_allgraphics.get(i).getCommentaire()
								);
								response.add(_graphic);
								}
								
							}
				            }
						}
				        return new ResponseEntity<>(response, HttpStatus.OK);
						
						} catch (Exception e) {
						    System.out.println("erreur "+e);
							return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
						}
						
						
					}	


	//Update graphic
	@PutMapping("/Update")
	public String update(@RequestBody Graphic grafic){
		//System.out.println("grafic.getIdactetrait()  : "+grafic.getIdacte());
		//get the graphic to update
		Graphic graphicData = graphic_Repository.findByidacte(grafic.getIdacte());

      
		//Update Acte Traitment
		if (Objects.nonNull(graphicData)) {
			graphicData.setActive(true);
			graphic_Repository.save(grafic);
			return "ok";
		}else {
			return "KO";
		}

	}
	
	//Deactive graphic
		@PutMapping("/Deactive")
		public String Deactive(@RequestBody Graphic grafic){
			//get the graphic to deactive
			Graphic graphicData = graphic_Repository.findByidacte(grafic.getIdacte());
			//Update Acte Traitment
			if (Objects.nonNull(graphicData)) {
				graphicData.setActive(false);
				graphic_Repository.save(graphicData);
				return "ok";
			}else {
				return "KO";
			}

		}
    
	//Deactive graphic
	@PutMapping("/Active")
	public String Active(@RequestBody Graphic grafic){
		//get the graphic to deactive
		Graphic graphicData = graphic_Repository.findByidacte(grafic.getIdacte());
		//Update Acte Traitment
		if (Objects.nonNull(graphicData)) {
			graphicData.setActive(true);
			graphic_Repository.save(graphicData);
			return "OK";
		}else {
			return "KO";
		}
     }
// DONE -------------------------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/afficheGrafic/{idGrafic}")
    public ResponseEntity<Graphic> afficheGrafic(@PathVariable("id_Grafic") String id_Grafic ){
        try {

            Optional<Graphic> grafic_ = graphic_Repository.findByIdGrafic(id_Grafic);
            if(grafic_.isPresent()){

                return new ResponseEntity<>(grafic_.get(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
