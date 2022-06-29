package com.jawapro.jawapro.DTO.Response;

import com.jawapro.jawapro.Entity.File;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private String name;
    private String url;

    public FileResponse(File file){
        this.name=file.getName();
    }
}
