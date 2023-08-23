package com.sofrecom.cobli.controller.service;


import org.springframework.stereotype.Service;

import com.sofrecom.cobli.exception.ResourceNotFoundException;
import com.sofrecom.cobli.models.BPU;
import com.sofrecom.cobli.repository.BPURepository;


@Service
public class BpuService {
    private final BPURepository bpuRepository;

    public BpuService(BPURepository bpuRepository) {
        this.bpuRepository = bpuRepository;
    }

    public BPU updateBpu(Long id, BPU bpuToUpdate) throws ResourceNotFoundException {
        BPU bpu = bpuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bpu not found with id " + id));
        bpu.update(bpuToUpdate);
        return bpuRepository.save(bpu);
    }
    
}


