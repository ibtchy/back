package com.sofrecom.cobli.controller.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;

@Service
public class AttachementsService {
	
	@Autowired
	private Graphic_Repository Graphic_repository;
	
	@Autowired
	ESIMBRepository ESIMBRepo;
	
	@Autowired
	PrestationRepository prestationRepository;
	
	@Autowired
    private Acte_traitementRepository acteTraitementRepo;
	
	
	/*
	 @GetMapping("/getNombreActes")
	
	public Map<String, Integer> getNombreActes() {
	    List<Prestation> prestations = prestationRepository.findAll();
	    Map<String, Integer> nombreActesParPrestation = new HashMap<>();
	    for (Prestation prestation : prestations) {
	        int nombreActes = acteTraitementRepo.countByType_prestation(prestation);
	        nombreActesParPrestation.put(prestation.getNomPrestation(), nombreActes);
	    }
	    return nombreActesParPrestation;
	} */

	            


	
	public void generateExcel(HttpServletResponse response,String date_Debut_s,String date_Fin_s) throws Exception {
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
	    Date date_Fin = dateFormat.parse(date_Fin_s);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("ATTACH_GTC");
		HSSFFont font = workbook.createFont();
		HSSFFont font_bold = workbook.createFont();
		font_bold.setBold(true);
		font_bold.setFontHeightInPoints((short)11);  
		font_bold.setFontName("Calibri");  
		font.setFontHeightInPoints((short)11);  
		font.setFontName("Calibri");  
		
		/* Imaaage */
		
		/* Read the input image into InputStream */
        InputStream my_banner_image = new FileInputStream("generated/logo_sof.png");
        /* Convert Image to byte array */
        byte[] bytes = IOUtils.toByteArray(my_banner_image);
        /* Add Picture to workbook and get a index for the picture */
        int picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        /* Close Input Stream */
        my_banner_image.close();                
        /* Create the drawing container */
        HSSFPatriarch drawing = sheet.createDrawingPatriarch();
        /* Create an anchor point */
        ClientAnchor my_anchor = new HSSFClientAnchor();
        /* Define top left corner, and we can resize picture suitable from there */
        my_anchor.setCol1(6);
        my_anchor.setRow1(0);           
        /* Invoke createPicture and pass the anchor point and ID */
        HSSFPicture  my_picture = drawing.createPicture(my_anchor, picture_id);
        /* Call resize method, which resizes the image */
        my_picture.resize(1.5,4.5);
        
		
		
		HSSFRow row1 = sheet.createRow(1);
		row1.createCell(1).setCellValue("Projet : ");
		row1.createCell(2).setCellValue("G2R");
		
		
		HSSFRow row2 = sheet.createRow(3);
		row2.createCell(1).setCellValue("Pour la période du : 03/04/2023 au 07/04/2023");
		sheet.addMergedRegion(new CellRangeAddress(3,3,1,5));
		
// -------------------------------------------------------------------------------------------------------------------		
		//style ----
		HSSFCellStyle style5 = workbook.createCellStyle();
		HSSFCellStyle style5_ = workbook.createCellStyle();
		style5.setFont(font_bold);
		style5.setBorderBottom(BorderStyle.THIN);
		style5.setBorderLeft(BorderStyle.THIN);
		style5.setBorderRight(BorderStyle.THIN);
		style5.setBorderTop(BorderStyle.THIN);
		style5_.setBorderBottom(BorderStyle.THIN);
		style5_.setBorderLeft(BorderStyle.THIN);
		style5_.setBorderRight(BorderStyle.THIN);
		style5_.setBorderTop(BorderStyle.THIN);
		style5.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style
		
		HSSFRow row5 = sheet.createRow(5);
		row5.createCell(2).setCellValue("Sofrecom :");	
		
		row5.getCell(2).setCellStyle(style5);
		row5.createCell(3).setCellStyle(style5_);
		row5.createCell(4).setCellStyle(style5_);
	
		row5.createCell(5).setCellValue("Client :");
		row5.getCell(5).setCellStyle(style5);
		row5.createCell(6).setCellStyle(style5_);
		row5.createCell(7).setCellStyle(style5_);

		sheet.addMergedRegion(new CellRangeAddress(5,5,2,4));
		sheet.addMergedRegion(new CellRangeAddress(5,5,5,7));
// -------------------------------------------------------------------------------------------------------------------			
		//style ----
				HSSFCellStyle style6_ = workbook.createCellStyle();
				style6_.setFont(font);
				style6_.setBorderBottom(BorderStyle.THIN);
				style6_.setBorderLeft(BorderStyle.THIN);
				style6_.setBorderRight(BorderStyle.THIN);
				style6_.setBorderTop(BorderStyle.THIN);
				style6_.setVerticalAlignment(VerticalAlignment.CENTER);
				style6_.setWrapText(true);
				// -- End Style
				
		HSSFRow row6 = sheet.createRow(6);
		HSSFRow row7 = sheet.createRow(7);
		row6.createCell(1).setCellValue("Entreprise :");
		row6.createCell(2).setCellValue("Sofrecom \n"
				+ "24 Avenue du Petit Parc \n"
				+ "94300 Vincennes");
		
		
		row6.getCell(1).setCellStyle(style6_);
		row6.getCell(2).setCellStyle(style6_);
		
		row6.createCell(3).setCellStyle(style6_);
		row6.createCell(4).setCellStyle(style6_);
		row6.createCell(5).setCellStyle(style6_);
		row6.createCell(6).setCellStyle(style6_);
		row6.createCell(7).setCellStyle(style6_);
		row7.createCell(1).setCellStyle(style6_);
		row7.createCell(2).setCellStyle(style6_);
		row7.createCell(3).setCellStyle(style6_);
		row7.createCell(4).setCellStyle(style6_);
		row7.createCell(5).setCellStyle(style6_);
		row7.createCell(6).setCellStyle(style6_);
		row7.createCell(7).setCellStyle(style6_);
		
		sheet.addMergedRegion(new CellRangeAddress(6,7,1,1));
		sheet.addMergedRegion(new CellRangeAddress(6,7,2,4));
		sheet.addMergedRegion(new CellRangeAddress(6,7,5,7));
		
		
		
// -------------------------------------------------------------------------------------------------------------------		
		HSSFRow row8 = sheet.createRow(8);
		HSSFRow row9 = sheet.createRow(9);
		row8.createCell(1).setCellValue("Contact :");
		row8.createCell(2).setCellValue("Clément GREGOIRE\n"
										 + "clement.gregoire@sofrecom.com\n"
										 + "tél : 06.74.80.73.67");
		
		row8.getCell(1).setCellStyle(style6_);
		row8.getCell(2).setCellStyle(style6_);
		
		row8.createCell(3).setCellStyle(style6_);
		row8.createCell(4).setCellStyle(style6_);
		row8.createCell(5).setCellStyle(style6_);
		row8.createCell(6).setCellStyle(style6_);
		row8.createCell(7).setCellStyle(style6_);
		row9.createCell(1).setCellStyle(style6_);
		row9.createCell(2).setCellStyle(style6_);
		row9.createCell(3).setCellStyle(style6_);
		row9.createCell(4).setCellStyle(style6_);
		row9.createCell(5).setCellStyle(style6_);
		row9.createCell(6).setCellStyle(style6_);
		row9.createCell(7).setCellStyle(style6_);
		
		sheet.addMergedRegion(new CellRangeAddress(8,9,1,1));
		sheet.addMergedRegion(new CellRangeAddress(8,9,2,4));
		sheet.addMergedRegion(new CellRangeAddress(8,9,5,7));
		
// -------------------------------------------------------------------------------------------------------------------			
		//style ----
		HSSFCellStyle style12 = workbook.createCellStyle();
		style12.setFont(font_bold);
		style12.setBorderBottom(BorderStyle.THIN);
		style12.setBorderLeft(BorderStyle.THIN);
		style12.setBorderRight(BorderStyle.THIN);
		style12.setBorderTop(BorderStyle.THIN);
		style12.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style
		
		HSSFRow row12 = sheet.createRow(12);
		row12.createCell(1).setCellValue("Prestations");
		row12.createCell(3).setCellValue("BPU (€)");
		row12.createCell(4).setCellValue("Unité");
		row12.createCell(5).setCellValue("Commandes");
		row12.createCell(6).setCellValue("Quantité");
		row12.createCell(7).setCellValue("Prix total HT");
		row12.createCell(9).setCellValue("Quantité Totale");
		
		
		row12.getCell(1).setCellStyle(style12);
		row12.createCell(2).setCellStyle(style12);
		row12.getCell(3).setCellStyle(style12);
		row12.getCell(4).setCellStyle(style12);
		row12.getCell(5).setCellStyle(style12);
		row12.getCell(6).setCellStyle(style12);
		row12.getCell(7).setCellStyle(style12);
		row12.getCell(9).setCellStyle(style12);
		
		sheet.addMergedRegion(new CellRangeAddress(12,12,1,2));
// -------------------------------------------------------------------------------------------------------------------
		//style ----
		HSSFCellStyle style13 = workbook.createCellStyle();
		style13.setFont(font);
		style13.setBorderBottom(BorderStyle.THIN);
		style13.setBorderLeft(BorderStyle.THIN);
		style13.setBorderRight(BorderStyle.THIN);
		style13.setBorderTop(BorderStyle.THIN);
		style13.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style
				
		HSSFRow row13 = sheet.createRow(13);
		row13.createCell(1).setCellValue("Evolution Statut IMB");
		row13.createCell(3).setCellValue("XX.XX");
		row13.createCell(4).setCellValue("Forfait");
		row13.createCell(5).setCellValue("1");
		row13.createCell(6).setCellValue("4");
		row13.createCell(7).setCellValue("0,00 €");
		row13.createCell(9).setCellValue("4");
		
		row13.getCell(1).setCellStyle(style13);
		row13.createCell(2).setCellStyle(style13);
		row13.getCell(3).setCellStyle(style13);
		row13.getCell(4).setCellStyle(style13);
		row13.getCell(5).setCellStyle(style13);
		row13.getCell(6).setCellStyle(style13);
		row13.getCell(7).setCellStyle(style13);
		row13.getCell(9).setCellStyle(style13);
		
		sheet.addMergedRegion(new CellRangeAddress(13,13,1,2));
		sheet.addMergedRegion(new CellRangeAddress(13,14,9,9));
		
		HSSFRow row14 = sheet.createRow(14);
		row14.createCell(1).setCellValue("Lien NRO-PM");
		row14.createCell(3).setCellValue("XX.XX");
		row14.createCell(4).setCellValue("Forfait");
		row14.createCell(5).setCellValue("0");
		row14.createCell(6).setCellValue("0");
		row14.createCell(7).setCellValue("0,00 €");
		row14.createCell(9).setCellValue("37");
		
		row14.getCell(1).setCellStyle(style13);
		row14.createCell(2).setCellStyle(style13);
		row14.getCell(3).setCellStyle(style13);
		row14.getCell(4).setCellStyle(style13);
		row14.getCell(5).setCellStyle(style13);
		row14.getCell(6).setCellStyle(style13);
		row14.getCell(7).setCellStyle(style13);
		row14.getCell(9).setCellStyle(style13);
		
		sheet.addMergedRegion(new CellRangeAddress(14,14,1,2));
		
		HSSFRow row15 = sheet.createRow(15);
		row15.createCell(1).setCellValue("Désaturations Coupleurs");
		row15.createCell(3).setCellValue("XX.XX");
		row15.createCell(4).setCellValue("Forfait");
		row15.createCell(5).setCellValue("0");
		row15.createCell(6).setCellValue("0");
		row15.createCell(7).setCellValue("0,00 €");
		
		row15.getCell(1).setCellStyle(style13);
		row15.createCell(2).setCellStyle(style13);
		row15.getCell(3).setCellStyle(style13);
		row15.getCell(4).setCellStyle(style13);
		row15.getCell(5).setCellStyle(style13);
		row15.getCell(6).setCellStyle(style13);
		row15.getCell(7).setCellStyle(style13);
		
		sheet.addMergedRegion(new CellRangeAddress(15,15,1,2));
// -------------------------------------------------------------------------------------------------------------------			
		//style ----
		HSSFCellStyle style16 = workbook.createCellStyle();
		style16.setFont(font_bold);
		style16.setBorderBottom(BorderStyle.THIN);
		style16.setBorderLeft(BorderStyle.THIN);
		style16.setBorderRight(BorderStyle.THIN);
		style16.setBorderTop(BorderStyle.THIN);
		// -- End Style
		
		HSSFRow row16 = sheet.createRow(16);
		row16.createCell(1).setCellValue("Facturation totale :");
		row16.createCell(7).setCellValue("0,00 €");
		
		row16.getCell(1).setCellStyle(style16);
		row16.createCell(2).setCellStyle(style16);
		row16.createCell(3).setCellStyle(style16);
		row16.createCell(4).setCellStyle(style16);
		row16.createCell(5).setCellStyle(style16);
		row16.createCell(6).setCellStyle(style16);
		row16.getCell(7).setCellStyle(style16);
		
		sheet.addMergedRegion(new CellRangeAddress(16,16,1,6));
		
// -------------------------------------------------------------------------------------------------------------------		
		HSSFRow row20 = sheet.createRow(20);
		row20.createCell(1).setCellValue("Pour Sofrecom :");
		row20.createCell(7).setCellValue("Pour Orange :");
		
		HSSFRow row21 = sheet.createRow(21);
		row21.createCell(1).setCellValue("(nom, date, signature)");
		row21.createCell(7).setCellValue("(nom, date, signature)");
// -------------------------------------------------------------------------------------------------------------------			
		//style ----
		HSSFCellStyle style22 = workbook.createCellStyle();
		style22.setFont(font_bold);
		style22.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style
		
		HSSFRow row22 = sheet.createRow(22);
		row22.createCell(0).setCellValue("Détail des tâches facturées");
		row22.getCell(0).setCellStyle(style22);
		sheet.addMergedRegion(new CellRangeAddress(22,22,0,7));
// -------------------------------------------------------------------------------------------------------------------		
		//style ----
		HSSFCellStyle style24 = workbook.createCellStyle();
		style24.setFont(font_bold);
		style24.setBorderBottom(BorderStyle.THIN);
		style24.setBorderLeft(BorderStyle.THIN);
		style24.setBorderRight(BorderStyle.THIN);
		style24.setBorderTop(BorderStyle.THIN);
		style24.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style
		
		HSSFRow row24 = sheet.createRow(24);
		row24.createCell(0).setCellValue("Tâches");
		row24.createCell(2).setCellValue("Réf Dossier");
		row24.createCell(4).setCellValue("Unités");
		row24.createCell(5).setCellValue("Quantité");
		row24.createCell(6).setCellValue("Prix BPU (Forfait)");
		row24.createCell(7).setCellValue("Prix Total HT");
		
		row24.getCell(0).setCellStyle(style24);
		row24.createCell(1).setCellStyle(style24);
		row24.getCell(2).setCellStyle(style24);
		row24.createCell(3).setCellStyle(style24);
		row24.getCell(4).setCellStyle(style24);
		row24.getCell(5).setCellStyle(style24);
		row24.getCell(6).setCellStyle(style24);
		row24.getCell(7).setCellStyle(style24);
		
		sheet.addMergedRegion(new CellRangeAddress(24,24,0,1));
		sheet.addMergedRegion(new CellRangeAddress(24,24,2,3));
		
// -------------------------------------------------------------------------------------------------------------------			
		
		//List<ESIMB> esimbs = new ArrayList<ESIMB>();
		//List<Acte_traitement> actes = new ArrayList<Acte_traitement>();

		//List<Acte_traitement> actes = new ArrayList<Acte_traitement>();
		
		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);

		    List<Attachements> response1 = new ArrayList<Attachements>();

		    for (Acte_traitement actetrait : actes) {
		        if (actetrait.getMotif().equals("") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
		        } else if (actetrait.getMotif().equals("Requalification") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
		        } else if (actetrait.getMotif().equals("ORT") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
		        } else if (actetrait.getMotif().equals("Historique") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
		        }
				else if (actetrait.getMotif().equals("Immeuble") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("Inexistence IMB optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")){
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("Incohérence") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("Rami") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("PB inexistant sur GFI") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("Blocage Optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("IMB raccordable sur Optimum") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")){
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("Clé en main client") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("PB saturé") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("Inexistence IMB Ipon") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("Position IMB introuvable") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("PB inexistant Ipon") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 1"));
				}
				else if (actetrait.getMotif().equals("Non evolution stat IMB") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals(""))  {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Tarif 2"));
				}
				else if (actetrait.getMotif().equals("En attente") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "En cours CDS"));
				}
				else if (actetrait.getMotif().equals("Reprise non facturable") && actetrait.getDateLivraison() != null && !actetrait.getDateLivraison().toString().equals("")) {
		            response1.add(new Attachements(actetrait.getIdacte(), actetrait.getType_prestation(), actetrait.getType_element(), actetrait.getQuantite(), 0, 0, "Non facturable"));
				}
			
		    }
		
		
		int dataRowIndex = 25;
		//style ----
		HSSFCellStyle style25 = workbook.createCellStyle();
		style25.setFont(font);
		style25.setBorderBottom(BorderStyle.THIN);
		style25.setBorderLeft(BorderStyle.THIN);
		style25.setBorderRight(BorderStyle.THIN);
		style25.setBorderTop(BorderStyle.THIN);
		style25.setAlignment(HorizontalAlignment.CENTER);
		// -- End Style		
		
		for (Attachements attachement : response1) {
			
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(attachement.getType_prestation().getNomPrestation().toString());
			dataRow.createCell(2).setCellValue(attachement.getIdacte());
			dataRow.createCell(4).setCellValue(attachement.getTypeelement());
			dataRow.createCell(5).setCellValue(attachement.getQuantite());
			dataRow.createCell(6).setCellValue(attachement.getPrix_BPU());
			dataRow.createCell(7).setCellValue(attachement.getPrix_total());
			
			
			dataRow.getCell(0).setCellStyle(style25);
			dataRow.createCell(1).setCellStyle(style25);
			dataRow.getCell(2).setCellStyle(style25);
			dataRow.createCell(3).setCellStyle(style25);
			dataRow.getCell(4).setCellStyle(style25);
			dataRow.getCell(5).setCellStyle(style25);
			dataRow.getCell(6).setCellStyle(style25);
			dataRow.getCell(7).setCellStyle(style25);
			
			sheet.addMergedRegion(new CellRangeAddress(dataRowIndex,dataRowIndex,0,1));
			sheet.addMergedRegion(new CellRangeAddress(dataRowIndex,dataRowIndex,2,3));
			
			
			dataRowIndex++;
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();

	}
	
	
}
	
	
