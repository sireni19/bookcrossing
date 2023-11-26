package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.Subgenre;
import com.prokopovich.bookcrossing.repositories.SubgenreRepository;
import com.prokopovich.bookcrossing.services.SubgenreService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class SubgenreServiceImpl implements SubgenreService {
    private SubgenreRepository subgenreRepository;
    @Override
    public List<Subgenre> getAllSubgenres() {
        return subgenreRepository.findAllOrderBySubgenreAsc();
    }

    @Override
    public void addSubgenre(Subgenre subgenre) throws DataIntegrityViolationException {
        subgenreRepository.save(subgenre);
    }

    @Override
    public Subgenre getSubgenreById(Integer id) {
        return subgenreRepository.findSubgenreById(id);
    }
}
