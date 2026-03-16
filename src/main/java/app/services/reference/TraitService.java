package app.services.reference;

import app.dtos.TraitDTO;
import app.dtos.dnd.DNDTraitDetailDTO;
import app.entities.reference.Trait;
import app.mappers.DTOMapper;
import app.persistence.IReferenceDAO;
import app.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class TraitService implements IReferenceDataService<DNDTraitDetailDTO, TraitDTO>
{
    private final IReferenceDAO<Trait> traitDAO;

    public TraitService(IReferenceDAO<Trait> traitDAO)
    {
        this.traitDAO = traitDAO;
    }

    @Override
    public List<TraitDTO> persistAll(List<DNDTraitDetailDTO> dtos)
    {
        Validator.notEmpty(dtos);
        return dtos.stream()
                .map(dto -> traitDAO.create(buildTrait(dto)))
                .map(DTOMapper::traitToDTO)
                .toList();
    }

    @Override
    public TraitDTO getById(Long id)
    {
        Validator.validId(id);
        Trait trait = traitDAO.getById(id);
        return DTOMapper.traitToDTO(trait);
    }

    @Override
    public TraitDTO getByName(String name)
    {
        Validator.notBlank(name);
        Trait trait = traitDAO.getByName(name);
        return DTOMapper.traitToDTO(trait);
    }

    @Override
    public List<TraitDTO> getAll()
    {
        return traitDAO.getAll()
                .stream()
                .map(DTOMapper::traitToDTO)
                .toList();
    }

    @Override
    public TraitDTO update(DNDTraitDetailDTO dto)
    {
        Validator.notNull(dto);
        Trait trait = traitDAO.update(buildTrait(dto));
        return DTOMapper.traitToDTO(trait);
    }

    @Override
    public List<TraitDTO> updateAll(List<DNDTraitDetailDTO> dtos)
    {
        Validator.notEmpty(dtos);
        return dtos.stream()
                .map(dto -> traitDAO.update(buildTrait(dto)))
                .map(DTOMapper::traitToDTO)
                .toList();
    }

    @Override
    public Long delete(Long id)
    {
        Validator.validId(id);
        return traitDAO.delete(id);
    }

    private Trait buildTrait(DNDTraitDetailDTO dto)
    {
        return new Trait(
                dto.name(),
                dto.descriptions()
        );
    }
}
