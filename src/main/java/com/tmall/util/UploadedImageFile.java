package com.tmall.util;

import org.springframework.web.multipart.MultipartFile;

public class UploadedImageFile {

    MultipartFile image; //属性名image必须与jsp页面的上传图片name名一致

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
