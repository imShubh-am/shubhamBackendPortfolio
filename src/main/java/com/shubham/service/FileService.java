package com.shubham.service;

import com.shubham.dto.FileData;
import com.shubham.repository.FileRepository;
import com.shubham.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        FileData fileData = fileRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(FileUtils.compressFile(file.getBytes())).build());
        if(fileData != null){
            return "File uploaded successfully, " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadFile() throws DataFormatException, IOException {
        Optional<FileData> file = fileRepository.findFirstByOrderByIdDesc();
        byte[] decompressFile = FileUtils.decompressFile(file.get().getFileData());
        return decompressFile;
    }
}
