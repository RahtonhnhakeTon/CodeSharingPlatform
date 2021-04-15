package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CRUDoperations {
    @Autowired
    private CodeRepository codeDB;

    public UUID getRecentID(){
        return codeDB.findTopByOrderByDateDesc().getID();
    }
    public Code getEntry(UUID id) throws NoSuchElementException, EmptyResultDataAccessException {
        Code out = codeDB.findById(id).get();
        if (out.isSecret()) {
            if(out.isViewsRestricted){
                out.setViews(out.getViews() - 1);
                if(out.getViews() <= 0) {
                    codeDB.deleteById(out.getID());
                    return out;
                }
            }
            if(out.getTime()<0 && out.isTimeRestricted){
                codeDB.deleteById(out.getID());
                throw new NoSuchElementException("ELEMENT EXPIRED OR DOESNT EXIST");
            }
        }
        codeDB.save(out);
        return out;
    }
    public long size(){
        return codeDB.count();
    }
    public List<Code> getLatest() {
        return (List<Code>) codeDB.findFirst10ByIsTimeRestrictedAndIsViewsRestrictedOrderByDateDesc(false,false);
    }
    public List<Code> findAll() {
        return (List<Code>) codeDB.findAll();
    }
    public void createNewEntry(String code,int time, int views){
        codeDB.save(new Code(code, time, views));
    }
}
