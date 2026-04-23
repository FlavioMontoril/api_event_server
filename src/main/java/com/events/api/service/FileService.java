package com.events.api.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FileService {

    // Define a raiz como um caminho absoluto para evitar erros de localização no
    // Linux
    // private final Path root =
    // Paths.get("uploads/events/").toAbsolutePath().normalize();

    // Injetado o caminho definido em application.properties
    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path root;

    @PostConstruct
    public void init() {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public Resource getFile(String fileName) {
        try {
            // 1. Extrai apenas o nome final (destrói qualquer tentativa de passar caminho
            // sujo)
            String nameImagePath = Paths.get(fileName).getFileName().toString();

            // 2. Resolve o caminho final dentro da pasta root
            Path filePath = root.resolve(nameImagePath).normalize();

            // 3. SEGURANÇA: Valida se o caminho resultante não "fugiu" da pasta uploads
            if (!filePath.startsWith(root)) {
                throw new RuntimeException("Acesso negado: Tentativa de invasão de diretório detectada.");
            }

            // 4. Cria o recurso Spring para o arquivo
            Resource resource = new UrlResource(filePath.toUri());

            // 5. Verifica se o arquivo realmente existe e pode ser lido
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Arquivo não encontrado ou inacessível no disco: " + nameImagePath);
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao converter o caminho do arquivo para recurso: " + e.getMessage());
        }
    }
}