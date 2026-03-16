package app.services.reference;

import app.dtos.RaceDTO;
import app.dtos.SubraceDTO;
import app.dtos.dnd.DNDSubraceDetailDTO;
import app.entities.reference.Race;
import app.entities.reference.Subrace;
import app.entities.reference.Trait;
import app.mappers.DTOMapper;
import app.persistence.IReferenceDAO;
import app.utils.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubraceService implements IReferenceDataService <DNDSubraceDetailDTO, SubraceDTO>
{
    private final IReferenceDAO<Subrace> subraceDAO;
    private final IReferenceDAO<Race> raceDAO;
    private final IReferenceDAO<Trait> traitDAO;

    public SubraceService(IReferenceDAO<Subrace> subraceDAO, IReferenceDAO<Race> raceDAO, IReferenceDAO<Trait> traitDAO)
    {
        this.subraceDAO = subraceDAO;
        this.raceDAO = raceDAO;
        this.traitDAO = traitDAO;
    }

    @Override
    public List<SubraceDTO> persistAll(List<DNDSubraceDetailDTO> dtos)
    {
        Validator.notEmpty(dtos);
        return dtos.stream()
                .map(dto -> subraceDAO.create(buildSubrace(dto)))
                .map(DTOMapper::subraceToDTO)
                .toList();
    }

    @Override
    public SubraceDTO getById(Long id)
    {
        Validator.validId(id);
        Subrace subrace = subraceDAO.getById(id);
        return DTOMapper.subraceToDTO(subrace);
    }

    @Override
    public SubraceDTO getByName(String name)
    {
        Validator.notBlank(name);
        Subrace subrace = subraceDAO.getByName(name);
        return DTOMapper.subraceToDTO(subrace);
    }

    @Override
    public List<SubraceDTO> getAll()
    {
        return subraceDAO.getAll()
                .stream()
                .map(DTOMapper::subraceToDTO)
                .toList();
    }

    @Override
    public SubraceDTO update(DNDSubraceDetailDTO dto)
    {
        Validator.notNull(dto);
        Subrace subrace = subraceDAO.update(buildSubrace(dto));
        return DTOMapper.subraceToDTO(subrace);
    }

    @Override
    public List<SubraceDTO> updateAll(List<DNDSubraceDetailDTO> dtos)
    {
        Validator.notEmpty(dtos);
        return dtos.stream()
                .map(dto -> subraceDAO.update(buildSubrace(dto)))
                .map(DTOMapper::subraceToDTO)
                .toList();
    }

    @Override
    public Long delete(Long id)
    {
        Validator.validId(id);
        return subraceDAO.delete(id);
    }

    private Subrace buildSubrace(DNDSubraceDetailDTO dto)
    {
        Set<Trait> traits = dto.traits().stream()
                .map(trait -> traitDAO.getByName(trait.name()))
                .collect(Collectors.toSet());

        Race race = raceDAO.getByName(dto.race().name());

        return new Subrace(
                dto.name(),
                dto.description(),
                race,
                DTOMapper.toAbilityBonusMap(dto.abilityBonuses()),
                traits
        );
    }
}
