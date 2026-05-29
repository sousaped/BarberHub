package br.com.barberhub.service;

import br.com.barberhub.dto.ServiceItemRequestDTO;
import br.com.barberhub.dto.ServiceItemResponseDTO;
import br.com.barberhub.dto.ServiceItemUpdateDTO;
import br.com.barberhub.entities.ServiceItem;
import br.com.barberhub.exceptions.BadRequestException;
import br.com.barberhub.exceptions.NotFoundException;
import br.com.barberhub.repository.IServiceItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceItemService {

    private final IServiceItem repository;

    public List<ServiceItemResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ServiceItemResponseDTO::new)
                .toList();
    }


    public ServiceItemResponseDTO findById(Long id) {

        return repository.findById(id)
                .map(ServiceItemResponseDTO::new)
                .orElseThrow(()-> new NotFoundException("Item service not found"));

    }

    public ServiceItemResponseDTO createServiceItem(ServiceItemRequestDTO dto){

        if(repository.findByName(dto.name()).isPresent()){
            throw new BadRequestException("This service item already exists");
        }

        ServiceItem serviceItem = new ServiceItem();

        serviceItem.setName(dto.name());
        serviceItem.setPrice(dto.price());
        serviceItem.setDurationInMinutes(dto.durationInMinutes());

        return new ServiceItemResponseDTO(repository.save(serviceItem));

    }


    public ServiceItemResponseDTO updateServiceItem(Long id, ServiceItemUpdateDTO dto){

        ServiceItem serviceItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item service not found"));


        if(dto.price() != null){
            serviceItem.setPrice(dto.price());
        }

        if(dto.durationInMinutes() != null){
            serviceItem.setDurationInMinutes(dto.durationInMinutes());
        }


        return new ServiceItemResponseDTO(repository.save(serviceItem));
    }



    public void disableServiceItem(Long id) {
        ServiceItem serviceItem = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item service not found"));

        serviceItem.disable();
        repository.save(serviceItem);
    }


}
