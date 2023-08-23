package com.sofrecom.cobli.controller;

import java.util.*;

import com.sofrecom.cobli.authentification.models.Role;
import com.sofrecom.cobli.authentification.models.User;
import com.sofrecom.cobli.authentification.repository.RoleRepository;
import com.sofrecom.cobli.authentification.repository.UserRepository;
import com.sofrecom.cobli.controller.service.IServiceCollaborateur;
import com.sofrecom.cobli.models.Nropm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.repository.CollaborateurRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController

public class CollaborateurController {
    @Autowired
    CollaborateurRepository collaborateurRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private  IServiceCollaborateur serviceCollaborateur;

    @GetMapping("/collaborateur/{cuid}")
    public Optional<Collaborateur> getCollaborateur(@PathVariable String cuid) {
        return collaborateurRepository.findByCUID(cuid);
    }

    @GetMapping("/collaborateur/getAllCollaborateursbypilote/{cuid}")
    public List<Collaborateur> getAllCollaborateursbypilote(@PathVariable String cuid) {
        Collaborateur pilote = collaborateurRepository.findByCUID(cuid).get();
        return collaborateurRepository.findByEquipe(pilote.getEquipe());
    }

    @GetMapping("/collaborateurs")
    public List<Collaborateur> getAllCollaborateurs() {
        List<Collaborateur> collaborateurs = new ArrayList<Collaborateur>();

        collaborateurs = collaborateurRepository.findAllByOrderByDateInscriptionDesc();


        return collaborateurs;

    }
	
	/*@GetMapping("/collaborateur/getAllCollaborateursbypilote/{cuid}")
	public List<Collaborateur> getAllCollaborateursbypilote(@PathVariable String cuid){
		Collaborateur pilote = collaborateurRepository.findByCUID(cuid);
		return collaborateurRepository.findByidequipe(pilote.getIdequipe());
	}*/

    @PutMapping("/activerCompte/{cuid}")
    public ResponseEntity<Collaborateur> activerCompte(@PathVariable("cuid") String cuid, @RequestBody Collaborateur collaborateur) throws MessagingException {
        Optional<Collaborateur> collaborateurData = collaborateurRepository.findByCUID(cuid);

        if (collaborateurData.isPresent()) {

            Collaborateur _collaborateur = collaborateurData.get();
            _collaborateur.setActive(true);

            User user=userRepository.findByUsername(cuid).get();

            user.setActive(true);

            userRepository.save(user);

            String from = "bensaidimen98@gmail.com";
            String to = _collaborateur.getEmail();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject("Activation du compte");
            helper.setFrom(from);
            helper.setTo(to);

            boolean html = true;
            helper.setText("<b>Cher collaborateur,</b>,<br><i>Votre compte a été activé.</i>", html);

            mailSender.send(message);


            return new ResponseEntity<>(collaborateurRepository.save(_collaborateur), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/desactiverCompte/{cuid}")
    public ResponseEntity<Collaborateur> desactiverCompte(@PathVariable("cuid") String cuid, @RequestBody Collaborateur collaborateur) throws MessagingException {
        Optional<Collaborateur> collaborateurData = collaborateurRepository.findByCUID(cuid);

        if (collaborateurData.isPresent()) {

            Collaborateur _collaborateur = collaborateurData.get();
            _collaborateur.setActive(false);

            User user=userRepository.findByUsername(cuid).get();

            user.setActive(false);

            userRepository.save(user);

            String from = "bensaidimen98@gmail.com";
            String to = _collaborateur.getEmail();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject("Désactivation du compte");
            helper.setFrom(from);
            helper.setTo(to);

            boolean html = true;
            helper.setText("<b>Cher collaborateur,</b>,<br><i>Votre compte a été désactivé.</i>", html);

            mailSender.send(message);


            return new ResponseEntity<>(collaborateurRepository.save(_collaborateur), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getRoles/{cuid}")
    public ResponseEntity<List<String>> getRoles(@PathVariable("cuid") String cuid) {

        try {
            User usre = userRepository.findByUsername(cuid).get();

            Set<Role> roles = usre.getRoles();

            List<String> roles2 = new ArrayList<>();

            for (Role role : roles) {

                roles2.add(role.getName().toString());

            }


            return new ResponseEntity<>(roles2, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    
    //  pour calendrier 
    
    @GetMapping("collaborateur/getAllCollaborateurs")
    public List<Collaborateur> getAllCollaborateur() {
        return serviceCollaborateur.getAllCollaborateurs();
    }

    @GetMapping("collaborateur/getCollaborateurById/{CUID}")
    public Collaborateur getCollaborateurById(@PathVariable String CUID) {
        return serviceCollaborateur.getCollaborateurById(CUID);
    }

    @PostMapping("collaborateur/addCollaborateur")
    public Collaborateur addCollaborateur(@RequestBody Collaborateur collaborateur) {
        return serviceCollaborateur.addCollaborateur(collaborateur);
    }

    @PutMapping("collaborateur/updateCollaborateur")
    public Collaborateur updateCollaborateur(@RequestBody Collaborateur collaborateur) {
        return serviceCollaborateur.updateCollaborateur(collaborateur);
    }

    @DeleteMapping("collaborateur/deleteCollaborateur/{CUID}")
    public void deleteCollaborateur(@PathVariable String CUID) {
        serviceCollaborateur.deleteCollaborateur(CUID);
    }


}