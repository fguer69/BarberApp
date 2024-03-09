package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.MailService.EmailServiceImpl;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@RestController
public class RecuperoPassword {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private DipendenteDAO dipendenteDAO;

    @Autowired
    private TitolareDAO titolareDAO;

    @Autowired
    private EmailServiceImpl mailService;

    private SecureRandom secureRandom = new SecureRandom();

    private String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    int length = 6;

    @PostMapping("/recover/recover-password")
    public String recoverPassword(@RequestBody String email){
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }
        String codeTemp = code.toString();
        String codeHashed = null;
        try{
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(codeTemp.getBytes(StandardCharsets.UTF_8));
            codeHashed = "";
            for (int i=0; i<hash.length; i++)
            {
                codeHashed += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
            }
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            return null;
        }
        email = email.replace("{", "");
        email = email.replace("}", "");
        email = email.replace(" ", "");
        email = email.replace("\"","");
        String email2 = email.substring(8);
        System.out.println(email2);
        try {
            mailService.sendSimpleMessage(email2, "Recupero Password", codeTemp);
        }
        catch (Exception e){
            return null;
        }
        return codeHashed;
    }

}
