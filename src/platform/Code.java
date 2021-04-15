package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Component
@Table(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID ID;

    private LocalDateTime expiryDate;

    private int time;

    private int views;

    @Column(length = 3000)
    private String code;

    private LocalDateTime date;

    public Boolean isTimeRestricted;
    public Boolean isViewsRestricted;

    public Code(){
    }
    @Autowired
    public Code(String code, int time, int views){
        this.code = code;
        date = LocalDateTime.now();
        this.expiryDate = date.plusSeconds(time);
        this.views = views;
        isViewsRestricted = views > 0;
        isTimeRestricted = time > 0;
        this.time =time;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }
    public void setExpiryDate(int time) {
        this.expiryDate = LocalDateTime.now().plusSeconds(time);
    }
    public void setViews(int views) {
        this.views = views;
    }
    public  void setCode(String code){
        this.code = code;
    }
    public  void setDate(){
        this.date = LocalDateTime.now();
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public boolean isSecret(){
        return isTimeRestricted || isViewsRestricted;
    }
    public int getTimeInSeconds() {
        return time;
    }
    public int getTime() {
        if (isTimeRestricted)
            return (int) Duration.between(LocalDateTime.now(), expiryDate).toSeconds();
        else return time;
    }
    public int getViews() {
        return views;
    }
    public UUID getID() {
        return ID;
    }
    public String getCode(){
        return code;
    }
    public LocalDateTime getDate(){
        if(date !=null)
            return date;
        else return LocalDateTime.of(2001,1,1,0,0);
    }
}


