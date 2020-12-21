package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public List<Comment> commentUpdate(Comment newComment, Integer imageId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        List<Comment> comments=getAllCommentsByImageId(imageId);
        comments.add(newComment);
        return comments;
    }

    public List<Comment> getAllCommentsByImageId(Integer imageId) {
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Image> typedQuery = entityManager.createQuery("SELECT i from Image i where i.id =:imageId", Image.class).setParameter("imageId", imageId);
        Image image = typedQuery.getSingleResult();
        TypedQuery<Comment> typedQuery1 = entityManager.createQuery("SELECT c from Comment c where c.image=:image ", Comment.class).setParameter("image",image);
        List<Comment> list = typedQuery1.getResultList();

        return list;
    }
}
