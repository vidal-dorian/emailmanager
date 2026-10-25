package fr.iutrodez.emailmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;

@SpringBootTest
class PersonRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private static final String URL = "/api/personnes";
    private static final String JSON_PERSONNE
            = "{\"id\":0,\"nom\":\"Dupont\",\"prenom\":\"Jean\"}";

    // Pour une personne non authentifiée, elle ne peut pas accéder à l'API
    @Test
    void utilisateurNonAuthentifie_nePeutPasAccederALApi() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }

    // Pour un administrateur, il peut accéder à la création d'une personne
    @Test
    @WithMockUser(roles = "ADMIN")
    void admin_peutCreerUnePersonne() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_PERSONNE))
                .andExpect(status().isCreated());
    }

    // Pour un administrateur, il peut accéder à la liste des personnes
    @Test
    @WithMockUser(roles = "ADMIN")
    void admin_peutListerLesPersonnes() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    // Pour un utilisateur, il ne peut pas accéder à la création d'une personne
    @Test
    @WithMockUser(roles = "USER")
    void user_nePeutPasCreerUnePersonne() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_PERSONNE))
                .andExpect(status().isForbidden());
    }

    // Pour un utilisateur, il peut accéder à la liste des personnes
    @Test
    @WithMockUser(roles = "USER")
    void user_peutListerLesPersonnes() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }
}