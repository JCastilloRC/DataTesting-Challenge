package jpamanager;

import entities.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class JPAManager {
    private static final Logger LOGGER = LogManager.getLogger(JPAManager.class);
    private final Properties hibernateSettings;
    private EntityManager manager;
    public JPAManager() throws IOException {
        hibernateSettings = new Properties();
        hibernateSettings.load(new FileInputStream("src/test/resources/hibernateSettings.properties"));
        manager = Persistence.
                            createEntityManagerFactory(hibernateSettings.get("PERSISTENCE_UNIT_NAME").toString(),
                                    getCredentials()).
                            createEntityManager();
    }
    private Map<String, String> getCredentials() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("jakarta.persistence.jdbc.url", hibernateSettings.get("URL").toString());
        credentials.put("jakarta.persistence.jdbc.user", hibernateSettings.get("USER").toString() );
        credentials.put("jakarta.persistence.jdbc.password", hibernateSettings.get("PASSWORD").toString());
        return credentials;
    }
    public List<?> getAllEntities(String table){ //No me gustó el uso de genéricos aquí
        try {
            return manager.createQuery("FROM " + table).getResultList();
        }
        catch(Exception e){
            LOGGER.error("Can't get Entities");
            return null;
        }
    }
    public List<?> getEntitiesByParam(String table, String param, String value){//No me gustó el uso de genéricos aquí
        try {
            return manager.createQuery("FROM " + table + " WHERE " + param + "= :value")
                    .setParameter("value", value)
                    .getResultList();
        }
        catch(Exception e){
            LOGGER.error("Can't get Entities");
            return null;
        }
    }
    public <T extends BaseEntity> T getEntityById(Class<T> type, int id){
        try {
            return manager.find(type, id);
        }
        catch(Exception e){
            LOGGER.error("Can't get Entity");
            return null;
        }
    }
    public void insertEntity(BaseEntity baseEntity) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(baseEntity);
        try {
            transaction.commit();
            LOGGER.info("Entry successfully made");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.error("Database wasn't updated");
        }
    }
    public void updateEntity(BaseEntity baseEntity){
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(baseEntity);
        manager.merge(baseEntity);
        try {
            transaction.commit();
            LOGGER.info("Entry successfully updated");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.error("Database wasn't updated");
        }
    }
    public <T extends BaseEntity> void deleteEntityById(Class<T> type, int id){
        BaseEntity entity = getEntityById(type, id);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(entity);
        manager.remove(entity);
        try {
            transaction.commit();
            LOGGER.info("Entry successfully deleted");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.error("Database wasn't updated");
        }
    }
}
