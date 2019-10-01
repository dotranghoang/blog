package com.codegym.repository.impl;

import com.codegym.model.Blog;
import com.codegym.repository.BlogRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BlogRepositoryImpl implements BlogRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Blog findById(Long id) {
        TypedQuery<Blog> query = em.createQuery("select c from Blog c where c.id=:id", Blog.class);
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Blog> finAll() {
        TypedQuery<Blog> query = em.createQuery("select q from Blog q",Blog.class);
        return query.getResultList();
    }

    @Override
    public void save(Blog blog) {
        if(blog.getId() != null) {
            em.merge(blog);
        } else {
            em.persist(blog);
        }
    }

    @Override
    public void remove(Long e) {
        Blog blog = findById(e);
        if(blog != null) {
            em.remove(blog);
        }
    }
}
