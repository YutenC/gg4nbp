package gg.nbp.web.Forum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import gg.nbp.core.dao.CoreDao;
import gg.nbp.web.Forum.entity.Article;

@Repository
public interface ArticleDao extends CoreDao<Article, Integer> {
	
    int insert(Article article);
    int update(Article article);
    void delete(Article article);
    Article selectById(Integer articleId);
    List<Article> selectAll();

}
