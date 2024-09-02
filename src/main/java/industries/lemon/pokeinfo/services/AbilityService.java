package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.repositories.AbilityRepository;
import org.springframework.stereotype.Service;

@Service
public class AbilityService {
    private final AbilityRepository abilityRepository;

    public AbilityService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    public Ability findByIdOrCreate(int abilityId) {
        return abilityRepository.findByAbilityId(abilityId)
            .orElseGet(() -> {
                Ability newAbility = new Ability();
                newAbility.setAbilityId(abilityId);
                abilityRepository.save(newAbility);
                return newAbility;
            });
    }
}
