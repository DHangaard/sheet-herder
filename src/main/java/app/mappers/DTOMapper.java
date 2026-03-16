package app.mappers;

import app.dtos.LanguageDTO;
import app.dtos.RaceDTO;
import app.dtos.SubraceDTO;
import app.dtos.TraitDTO;
import app.dtos.dnd.DNDAbilityBonusDTO;
import app.entities.reference.Language;
import app.entities.reference.Race;
import app.entities.reference.Subrace;
import app.entities.reference.Trait;
import app.enums.Ability;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DTOMapper
{
    public static LanguageDTO languageToDTO(Language language)
    {
        return new LanguageDTO(
                language.getId(),
                language.getName(),
                language.getDescription(),
                language.getType(),
                language.getTypicalSpeakers(),
                language.getScript()
        );
    }

    public static TraitDTO traitToDTO(Trait trait)
    {
        return new TraitDTO(
                trait.getId(),
                trait.getName(),
                trait.getDescriptions()
        );
    }

    public static RaceDTO raceToDTO(Race race)
    {
        return new RaceDTO(
                race.getId(),
                race.getName(),
                race.getSpeed(),
                race.getAbilityBonuses(),
                race.getAgeDescription(),
                race.getAlignment(),
                race.getSize(),
                race.getSizeDescription(),
                nullSafeSet(race.getLanguages(), DTOMapper::languageToDTO),
                nullSafeSet(race.getTraits(), DTOMapper::traitToDTO)
        );
    }

    public static SubraceDTO subraceToDTO(Subrace subrace)
    {
        return new SubraceDTO(
                subrace.getId(),
                subrace.getName(),
                subrace.getDescription(),
                DTOMapper.raceToDTO(subrace.getRace()),
                subrace.getAbilityBonuses(),
                nullSafeSet(subrace.getTraits(), DTOMapper::traitToDTO)
        );
    }

    public static Map<Ability, Integer> toAbilityBonusMap(List<DNDAbilityBonusDTO> bonuses)
    {
        return bonuses.stream()
                .collect(Collectors.toMap(
                        bonus -> Ability.fromValue(bonus.dndAbilityScoreDTO().name()),
                        DNDAbilityBonusDTO::bonus
                ));
    }

    private static <T, R> Set<R> nullSafeSet(Set<T> source, Function<T, R> mapper)
    {
        if (source == null) return Set.of();
        return source.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }
}


