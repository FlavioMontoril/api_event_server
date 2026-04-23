package com.events.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    // Caminho onde os arquivos serão armazenados
    // private final String uploadDir = "uploads/events/";

    // Injetado o caminho definido em application.properties
    @Value("${app.upload.dir}")
    private String uploadDir;

    public String uploadImg(MultipartFile multipartFile) {
        try {
            // 1. Cria a pasta física no Linux se ela não existir
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 2. Gera um nome único e substitui espaços por underscores para evitar erro na
            // URL
            String originalName = multipartFile.getOriginalFilename();
            String cleanName = (originalName != null) ? originalName.replace(" ", "_") : "file";
            String fileName = UUID.randomUUID() + "_" + cleanName;

            // 3. Define o caminho completo de destino
            Path filePath = Paths.get(uploadDir).resolve(fileName).toAbsolutePath().normalize();

            // 4. Salva o arquivo no disco (transferTo é mais seguro que Files.write)
            multipartFile.transferTo(filePath.toFile());

            // 5. Retorna apenas o nome do arquivo para o banco de dados
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o upload do arquivo: " + e.getMessage());
        }
    }
}