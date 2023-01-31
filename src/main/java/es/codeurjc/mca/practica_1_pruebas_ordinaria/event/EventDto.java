package es.codeurjc.mca.practica_1_pruebas_ordinaria.event;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class EventDto{

    private MultipartFile multiparImage;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;

    private Double price;

    private int max_capacity;

    public MultipartFile getMultiparImage() {
        return multiparImage;
    }

    public void setMultiparImage(MultipartFile multiparImage) {
        this.multiparImage = multiparImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    @Override
    public String toString() {
        return "EventDto [name=" + name + ", description=" + description + ", date=" + date + ", price=" + price
                + ", max_capacity=" + max_capacity + "]";
    }

    
    
}
