package services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Student;
import model.StudentTable;

@WebService(serviceName = "StudentWebService")
public class StudentWebService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("componentBaseW13_Ex2PU");

    @WebMethod(operationName = "findStudentById")
    public Student findStudentById(@WebParam(name = "id") int id) {
       EntityManager entitymanager = emf.createEntityManager();
       Student edu = entitymanager.find(Student.class, id);
        return edu;
    }

    @WebMethod(operationName = "insertStudent")
    public int insertStudent(@WebParam(name = "id") int id, @WebParam(name = "name") String name, @WebParam(name = "GPA") double GPA) {
        
         Student temp = new Student(id, name, GPA);
         EntityManager entitymanager = emf.createEntityManager();
        try {
            entitymanager.getTransaction().begin();
            Student target = entitymanager.find(Student.class, temp.getId());
            if (target != null) {
                return 0;
            }
            entitymanager.persist(temp);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            
        }
        finally {
            entitymanager.close();
        }
        return 1;
    }
    
    @WebMethod(operationName = "updateStudent")
    public String updateStudent(@WebParam(name = "id") int id, @WebParam(name = "name") String name, @WebParam(name = "GPA") double GPA) {
         EntityManager entitymanager = emf.createEntityManager();
         
        Student std = new Student(id, name, GPA);
        try {
            entitymanager.getTransaction().begin();
            Student target = entitymanager.find(Student.class, std.getId());
            if (target == null) {
                return "Id does'nt exist";
            }
            target.setName(std.getName());
            target.setGPA(std.getGPA());
            entitymanager.persist(target);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            
        }
        finally {
            entitymanager.close();
        }
        return "SUCCESS";
    }
    
    @WebMethod(operationName = "removeStudent")
    public String removeStudent(@WebParam(name = "id") int id) {
          EntityManager entitymanager = emf.createEntityManager();
        try {
            entitymanager.getTransaction().begin();
            Student target = entitymanager.find(Student.class, id);
            if (target == null) {
                return "Id does'nt exist";
            }
            entitymanager.remove(target);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            
        }
        finally {
            entitymanager.close();
        }
        return "REMOVE SUCESS";
    }

    private void persist(Object object) {
        EntityManager entitymanager = emf.createEntityManager();
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(object);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            entitymanager.getTransaction().rollback();
        } finally {
            entitymanager.close();
        }
    }

    @WebMethod(operationName = "findAllStudent")
    public List<Student> findAllStudent() {
        EntityManager std = emf.createEntityManager();
        List<Student> stdList = null;
        try {
            stdList = (List<Student>) std.createNamedQuery("Student.findAll").getResultList();         
        } catch (Exception e) {
            throw new RuntimeException(e);
            
        }
        finally {
            std.close();
        }
        return stdList;
    }

    

    

    
    
}
