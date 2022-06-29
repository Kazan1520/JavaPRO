package com.jawapro.jawapro.Service;

import com.jawapro.jawapro.Entity.File;
import com.jawapro.jawapro.Repository.FileRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Objects;
@Transactional
@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @SneakyThrows
    @Transactional
    public File saveFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File file2 = File.builder().name(fileName).data(file.getBytes()).build();
        return fileRepository.save(file2);
    }

    public File getFile(Long id){
        if(fileRepository.findById(id).isPresent())
            return fileRepository.findById(id).get();
        else
            return null;
    }

    @SneakyThrows
    @Transactional
    public File updateFile(Long id, MultipartFile file){
        if(fileRepository.findById(id).isPresent()) {
            File oldFile = fileRepository.findById(id).get();
            oldFile.setName(file.getOriginalFilename());
            oldFile.setData(file.getBytes());
            return fileRepository.save(oldFile);
        } else
            return null;
    }

    public void deleteFile(Long id) {
        if(fileRepository.findById(id).isPresent()) fileRepository.delete(fileRepository.findById(id).get());
    }
}
