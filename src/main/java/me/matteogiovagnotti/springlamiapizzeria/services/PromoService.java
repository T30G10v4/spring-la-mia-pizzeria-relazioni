package me.matteogiovagnotti.springlamiapizzeria.services;

import me.matteogiovagnotti.springlamiapizzeria.models.Promo;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoService {

    @Autowired
    private PromoRepository promoRepository;

    public Promo create(Promo formPromo) {

        return promoRepository.save(formPromo);

    }

}
