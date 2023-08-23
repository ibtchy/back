package com.sofrecom.cobli.repository;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.ESIMB;
import com.sofrecom.cobli.models.Graphic;


@Repository
public interface FacturationRepository extends JpaRepository<Graphic, String>{
	public static String query = "select active,\r\n"
			+ "	   code_imb,\r\n"
			+ "	   date_traitement,\r\n"
			+ "	   groupe_operation,\r\n"
			+ "	   iar,\r\n"
			+ "	   id_grafic,\r\n"
			+ "	   statut_graphic,\r\n"
			+ "	   traitement_effectue,\r\n"
			+ "	   type_traitement,\r\n"
			+ "	   graphic.idacte,\r\n"
			+ "	   acte_traitement.idacte,\r\n"
			+ "	   affectation, \r\n"
			+ "	   commentaire,\r\n"
			+ "	   date_livraison,\r\n"
			+ "	   date_reception,\r\n"
			+ "	   date_reprise,\r\n"
			+ "	   date_validation,\r\n"
			+ "	   duree,\r\n"
			+ "	   idacte,\r\n"
			+ "	   motif,\r\n"
			+ "	   quantite,\r\n"
			+ "	   ref_tachebpu,\r\n"
			+ "	   reprise_facturable,\r\n"
			+ "	   type_element,\r\n"
			+ "	   type_prestation,\r\n"
			+ "case \r\n"
			+ "when date_traitement is not null and statut_graphic='Préparation de l''envoi' THEN 'Tarif 1'\r\n"
			+ "when date_traitement is not null and statut_graphic='Attente retour client' THEN 'Tarif 2'\r\n"
			+ "when date_traitement is not null and statut_graphic='Libéré' THEN 'Tarif 3'\r\n"
			+ "END AS statut_facturation\r\n"
			+ "from graphic  join acte_traitement on graphic.idacte = acte_traitement.idacte";

/*@Query(value = "select active,\r\n"
			+ "	   code_imb,\r\n"
			+ "	   date_traitement,\r\n"
			+ "	   groupe_operation,\r\n"
			+ "	   iar,\r\n"
			+ "	   id_grafic,\r\n"
			+ "	   statut_graphic,\r\n"
			+ "	   traitement_effectue,\r\n"
			+ "	   type_traitement,\r\n"
			+ "	   graphic.idactetrait,\r\n"
			+ "	   acte_traitement.idactetrait,\r\n"
			+ "	   affectation, \r\n"
			+ "	   commentaire,\r\n"
			+ "	   date_livraison,\r\n"
			+ "	   date_reception,\r\n"
			+ "	   date_reprise,\r\n"
			+ "	   date_validation,\r\n"
			+ "	   duree,\r\n"
			+ "	   idacte,\r\n"
			+ "	   motif,\r\n"
			+ "	   quantite,\r\n"
			+ "	   ref_tachebpu,\r\n"
			+ "	   reprise_facturable,\r\n"
			+ "	   type_element,\r\n"
			+ "	   type_prestation,\r\n"
			+ "case \r\n"
			+ "when date_traitement isnull and statut_graphic='arc' THEN 'tarif 1'\r\n"
			+ "when date_traitement is not null and statut_graphic='l' THEN 'tarif 2'\r\n"
			+ "END AS statut_facturation\r\n"
			+ "from graphic  join acte_traitement on graphic.idactetrait = acte_traitement.idactetrait", 
    nativeQuery = true)
	public List<Graphic> getFacturation();*/
	@Query(nativeQuery = true,value = query)
	public List<Graphic> getFacturation();
	
	
	
	@Query(nativeQuery = true,value = "select active,\r\n"
			+ "	   code_imb,\r\n"
			+ "	   date_traitement,\r\n"
			+ "	   groupe_operation,\r\n"
			+ "	   iar,\r\n"
			+ "	   id_grafic,\r\n"
			+ "	   statut_graphic,\r\n"
			+ "	   traitement_effectue,\r\n"
			+ "	   type_traitement,\r\n"
			+ "	   graphic.idacte,\r\n"
			+ "	   acte_traitement.idacte,\r\n"
			+ "	   affectation, \r\n"
			+ "	   commentaire,\r\n"
			+ "	   date_livraison,\r\n"
			+ "	   date_reception,\r\n"
			+ "	   date_reprise,\r\n"
			+ "	   date_validation,\r\n"
			+ "	   duree,\r\n"
			+ "	   idacte,\r\n"
			+ "	   motif,\r\n"
			+ "	   quantite,\r\n"
			+ "	   ref_tachebpu,\r\n"
			+ "	   reprise_facturable,\r\n"
			+ "	   type_element,\r\n"
			+ "	   type_prestation,\r\n"
			+ "case \r\n"
			+ "when date_traitement isnull and statut_graphic='arc' THEN 'tarif 1'\r\n"
			+ "when date_traitement is not null and statut_graphic='l' THEN 'tarif 2'\r\n"
			+ "END AS statut_Facturation \r\n"
			+ "from graphic  join acte_traitement on graphic.idacte = acte_traitement.idacte")
	public List<Graphic> getFacturationgrafic(@Param("querygrafic") String querygrafic);
	
	
	
	@Query(nativeQuery = true,value = "select code_Banbou , \r\n"
			+ "	   codeIMB, \r\n"
			+ "	   date_Verification , \r\n"
			+ "	   active , \r\n"
			+ "	   idacte , \r\n"
			+ "	   ref_TacheBPU , \r\n"
			+ "	   type_prestation , \r\n"
			+ "	   type_element , \r\n"
			+ "	   quantite , \r\n"
			+ "	   date_Reception , \r\n"
			+ "	   date_Livraison , \r\n"
			+ "	   date_Validation , \r\n"
			+ "	   affectation , \r\n"
			+ "	   duree , \r\n"
			+ "	   commentaire , \r\n"
			+ "	   motif , \r\n"
			+ "	   statut_Facturation ,\r\n"
			+ "	   date_Reprise , \r\n"
			+ "	   reprise_Facturable \r\n"
			+ "from acte_traitement join esimb on acte_traitement.idacte = esimb.idacte where acte_traitement.idacte = '2'")
	public List<ESIMB> getAttachements();
	
	
	@Query(nativeQuery = true,value = "select idacte from acte_traitement")
	public List<Attachements> getAttachementss();




}
