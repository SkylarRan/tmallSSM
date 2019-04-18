package com.tmall.controller;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.util.ImageUtil;
import com.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_productImage_list")
    public String list(int pid,Model model){
        Product p = productService.get(pid);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);

        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        productImageService.add(pi);
        //图片文件名为id.jpg
        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }else{
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();

        uploadedImageFile.getImage().transferTo(f);
        BufferedImage img = ImageUtil.change2jpg(f);
        ImageIO.write(img, "jpg", f);

        if(ProductImageService.type_single.equals(pi.getType())){
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);

            ImageUtil.resizeImage(f,56,56,f_small);
            ImageUtil.resizeImage(f,217,190,f_middle);
        }

        return "redirect:/admin_productImage_list?pid="+pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session){
        ProductImage pi = productImageService.get(id);
        //删除图片
        String filename = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small;
        String imageFolder_middle;
        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");

            File f_small = new File(imageFolder_small, filename);
            File f_middle = new File(imageFolder_middle, filename);
            f_small.delete();
            f_middle.delete();
        }else{
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }

        File file = new File(imageFolder, filename);
        file.delete();

        productImageService.delete(id);

        return "redirect:/admin_productImage_list?pid="+pi.getPid();

    }
}
