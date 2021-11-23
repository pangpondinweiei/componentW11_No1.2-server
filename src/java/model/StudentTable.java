
package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StudentTable {
      private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("componentBAseW11_Ex2PU");
    
    public static List<Student> findAllStudent() {
        EntityManager student = emf.createEntityManager();
        List<Student> studentList = null;
        try {
            studentList = (List<Student>) student.createNamedQuery("Student.findAll").getResultList();         
        } catch (Exception e) {
            throw new RuntimeException(e);
            
        }
        finally {
            student.close();
        }
        return studentList;
    }
    
        public static int insertStudent(Student temp) {
        EntityManager student = emf.createEntityManager();
        try {
            student.getTransaction().begin();
            Student target = student.find(Student.class, temp.getId());
            if (target != null) {
                return 0;
            }
            student.persist(temp);
            student.getTransaction().commit();
        } catch (Exception e) {
            student.getTransaction().rollback();
            
        }
        finally {
            student.close();
        }
        return 1;
    }
        
    public static Student findStudentById(int id) {
        EntityManager entitymanager = emf.createEntityManager();
        Student student = null;
        try {
            student = entitymanager.find(Student.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            entitymanager.close();
        }
        return student;
    }
    
    public static int removeStudent(int id) {
        EntityManager entitymanager = emf.createEntityManager();
        try {
            entitymanager.getTransaction().begin();
            Student target = entitymanager.find(Student.class, id);
            if (target == null) {
                return 0;
            }
            entitymanager.remove(target);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            
        }
        finally {
            entitymanager.close();
        }
        return 1;
    }
    
    public static int updateStudent(Student student) {
        EntityManager entitymanager = emf.createEntityManager();
        try {
            entitymanager.getTransaction().begin();
            Student target = entitymanager.find(Student.class, student.getId());
            if (target == null) {
                return 0;
            }
            target.setName(student.getName());
            target.setGPA(student.getGPA());
            entitymanager.persist(target);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            
        }
        finally {
            entitymanager.close();
        }
        return 1;
        
    }
    
}
