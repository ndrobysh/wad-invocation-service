package com.wad.invocation.config;

import com.wad.invocation.model.BaseMonster;
import com.wad.invocation.model.SkillTemplate;
import com.wad.invocation.repository.BaseMonsterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final BaseMonsterRepository repository;

    public DataInitializer(BaseMonsterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            log.info("Initialisation des monstres de base...");

            BaseMonster dragon = new BaseMonster(null, "Dragon Flamboyant", "feu", 120, 150, 80, 100, 0.05,
                    List.of(
                            new SkillTemplate("Souffle de Feu", 50, 2, "atk", 1.2),
                            new SkillTemplate("Griffes Acérées", 30, 0, "atk", 1.0),
                            new SkillTemplate("Écailles Cuivrées", 0, 4, "def", 2.0)
                    ));

            BaseMonster slime = new BaseMonster(null, "Slime Aqueux", "eau", 80, 40, 60, 50, 0.60,
                    List.of(
                            new SkillTemplate("Rebond", 10, 0, "hp", 0.5),
                            new SkillTemplate("Éclaboussure", 15, 1, "atk", 0.8),
                            new SkillTemplate("Régénération", 0, 3, "hp", 0.3)
                    ));

            BaseMonster golem = new BaseMonster(null, "Golem de Pierre", "terre", 200, 80, 150, 20, 0.15,
                    List.of(
                            new SkillTemplate("Poing de Fer", 40, 1, "def", 1.5),
                            new SkillTemplate("Coup de Tête", 20, 0, "atk", 1.0),
                            new SkillTemplate("Forteresse", 0, 5, "def", 3.0)
                    ));

            BaseMonster fenix = new BaseMonster(null, "Phénix Cendré", "feu", 100, 110, 70, 130, 0.20,
                    List.of(
                            new SkillTemplate("Plume Enflammée", 35, 1, "atk", 1.1),
                            new SkillTemplate("Picorement", 15, 0, "vit", 0.9),
                            new SkillTemplate("Renaissance", 0, 6, "hp", 5.0)
                    ));

            repository.saveAll(List.of(dragon, slime, golem, fenix));
            log.info("4 monstres de base insérés avec succès.");
        }
    }
}
