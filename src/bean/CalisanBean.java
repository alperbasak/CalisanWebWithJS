package bean;

import com.itextpdf.text.DocumentException;
import model.Calisan;
import util.Util;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import javax.servlet.http.Part;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@ManagedBean(name = "employeeManagedBean")
@RequestScoped
public class CalisanBean {

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    private String employeeID;
    private String employeeName;
    private String employeeSurname;
    private String employeeSalary;


    private String infoMessage;
    private List<Calisan> filterList;
    private List<Calisan> employeeList;
    private Part employeePic;
    private String date;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    private String responseMessage;

    @Resource
    private UserTransaction userTransaction;

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    public List<Calisan> getEmployeeList(){
        return entityManager.createNativeQuery(
                "SELECT * FROM hr.calisanlar",Calisan.class).getResultList();
    }

    public void addEmployee(){
        infoMessage = " Eklenmeye çalışılan EmployeeID: "+ employeeID +" İsim: "+ employeeName +" Soyisim: " +employeeSurname+" Maaş: "+employeeSalary + " Eklenme tarihi: " + new Date();
        try {
            userTransaction.begin();
            byte[] empImageToByte=Util.getInstance().convertImage(employeePic);
            entityManager.persist(new Calisan(employeeID, employeeName, employeeSurname, Double.parseDouble(employeeSalary),empImageToByte,employeePic.getContentType()));
            userTransaction.commit();
            responseMessage= "Ekleme başarılı.";
        } catch (Exception e) {
            try {
                if (e.getCause().toString().contains("unique constraint")) {
                    responseMessage = "Çalışan ID tekrarı";
                }
                if (userTransaction.getStatus()== Status.STATUS_ACTIVE) {
                    responseMessage = "Çalışan eklenemedi. Rolled back";
                    userTransaction.rollback();
                }
            } catch (SystemException e1) {
                responseMessage="Ne çalışan ekledik, ne de rollback yapabildik. Ne bu böyle :)";
            }
        }
    }

    public void deleteCalisan(){
        infoMessage = " Silinmeye çalışılan EmployeeID: "+ employeeID;
        try {
            userTransaction.begin();
            Calisan calisan=entityManager.find(Calisan.class,employeeID);
            entityManager.remove(calisan);
            userTransaction.commit();
            responseMessage="EmployeeID: "+ employeeID + " silindi";

        } catch (Exception e) {
            try {
                responseMessage="EmployeeID: "+ employeeID + " silinemedi";
                userTransaction.rollback();
            } catch (SystemException e1) {
                responseMessage="EmployeeID: "+ employeeID + " silinemedi, rollback de yapamadık";
            }
        }
    }

    public void updateCalisan(){
        infoMessage = " Güncellenmeye çalışılan EmployeeID: "+ employeeID;
        try {
            userTransaction.begin();
            Calisan calisan=entityManager.find(Calisan.class,employeeID);
            if (!employeeName.isEmpty()) calisan.setName(employeeName);
            if (!employeeSurname.isEmpty()) calisan.setLastName(employeeSurname);
            if (!employeeSalary.isEmpty()) calisan.setSalary(Double.parseDouble(employeeSalary));

            entityManager.merge(calisan);
            userTransaction.commit();
            responseMessage="EmployeeID: "+ employeeID + " güncellendi";

        } catch (Exception e) {
            try {
                responseMessage="EmployeeID: "+ employeeID + " güncellenemedi";
                userTransaction.rollback();
            } catch (SystemException e1) {
                responseMessage="EmployeeID: "+ employeeID + " güncellenemedi, rollback de yapamadık";
            }
        }
    }


    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void exportList() {
        try {
            Util.getInstance().exportList(getEmployeeList());
            infoMessage="Liste Util'e paslandı";
        } catch (IOException e) {
            e.printStackTrace();
            responseMessage="Streamde problem oluştu.";
        } catch (DocumentException e) {
            e.printStackTrace();
            responseMessage="Döküman oluşturulamadı.";
        }
    }


    public void setEmployeePic(Part employeePic) {
        this.employeePic = employeePic;
    }

    public Part getEmployeePic() {
        return employeePic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public List<Calisan> getFilterList() {
        if (date!=null&&!date.isEmpty()){
        GregorianCalendar startDate;
        GregorianCalendar endDate;

        startDate=new GregorianCalendar(2017,Integer.parseInt(date),1);
        Timestamp startTime=new Timestamp(startDate.getTimeInMillis());
        if (!date.equals("12")){
            endDate=new GregorianCalendar(2017,Integer.parseInt(date)+1,1);}
        else { endDate=new GregorianCalendar(2017,12,31);}

        Timestamp endTime=new Timestamp(endDate.getTimeInMillis());

        Query query=entityManager.createNativeQuery(
                "SELECT * FROM hr.calisanlar WHERE eklenme_tarihi >= ? AND eklenme_tarihi < ?",Calisan.class);
        query.setParameter(1,startTime);
        query.setParameter(2,endTime);
        return query.getResultList();}
        else {
            return getEmployeeList();
        }

    }
}
