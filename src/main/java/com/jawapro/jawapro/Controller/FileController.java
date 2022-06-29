package com.jawapro.jawapro.Controller;

import com.jawapro.jawapro.Entity.File;
import com.jawapro.jawapro.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/add")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file){
        return ResponseEntity.ok(fileService.saveFile(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id){
        File file = fileService.getFile(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getName()+"\"").body(file.getData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id){
        fileService.deleteFile(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestBody MultipartFile file){
        return ResponseEntity.ok(fileService.updateFile(id,file));
    }
}
