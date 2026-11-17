package br.com.evrcar.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
public class PhotoStorageService {
    private final Cloudinary cloudinary;
    public PhotoStorageService(@Value("${cloudinary.cloud-name:}") String cloud,
        @Value("${cloudinary.api-key:}") String key,
        @Value("${cloudinary.api-secret:}") String secret) {
        this.cloudinary = cloud.isBlank() ? null : new Cloudinary(ObjectUtils.asMap(
            "cloud_name",cloud,"api_key",key,"api_secret",secret,"secure",true));
    }
    public String upload(MultipartFile file) {
        if(cloudinary==null) throw new IllegalStateException("Configure as credenciais do Cloudinary no .env");
        try {
            if(file.isEmpty() || file.getSize()>10*1024*1024) throw new IllegalArgumentException("Imagem vazia ou maior que 10 MB");
            String type=file.getContentType();
            if(type==null || !type.startsWith("image/")) throw new IllegalArgumentException("Envie um arquivo de imagem");
            Map result=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder","evr-car/vehicles","resource_type","image"));
            return (String) result.get("secure_url");
        } catch (Exception e) {
            if(e instanceof RuntimeException re) throw re;
            throw new IllegalStateException("Falha ao enviar imagem",e);
        }
    }
}
