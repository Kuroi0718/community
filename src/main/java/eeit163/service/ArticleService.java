package eeit163.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Article;
import eeit163.model.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository aDao;

	public void insertArticle(Article Article) {
		aDao.save(Article);
	}

	@Transactional
	public void updateById(Article Article) {
		aDao.updateById(Article.getId(), Article.getAuthorId(), Article.getAuthorName(), Article.getTitle(), Article.getContent(),
				Article.getLikes(), Article.getPostDate());
	}

	@Transactional
	public void deleteById(Integer id) {
		aDao.deleteById(id);
	}

	public List<Article> findAllById(List<Integer> list) {
		return aDao.findAllById(list);
	}

	public Article findById(Integer id) throws NoSuchElementException {
		return aDao.findById(id).get();
	}

	public List<Article> findByTitle(String title) {
		return aDao.findByTitle(title);
	}
	
	public List<Article> findByAuthorId(Integer id) {
		return aDao.findByAuthorId(id);
	}
	
	public List<Article> findByLock() {
		return aDao.findByLock();
	}

	public Page<Article> findAllByPage(Integer pageNumber, String sort, String order, String where, String search) {
		PageRequest pgb;

		if ("asc".equals(order)) {
			pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, sort);
		} else {
			pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, sort);
		}
		Page<Article> page = aDao.findAll(pgb);
		List<Article> list;
		String[] str;
		String a;
		ArrayList<Integer> intlist = new ArrayList<Integer>();
		if ("id".equals(where)) {
			if (!"".equals(search)) {
				for (int i = 0; i < search.length(); i++) {
					a = search.substring(i, i + 1);
					int c = 1;
					for (int j = 0; j <= 9; j++) {
						if (String.valueOf(j).equals(a)) {
							c = 0;
						}
					}
					if (c == 1) {
						search = search.replaceFirst(a, ",");
					}
				}
				str = search.split(",");
				for (int i = 0; i < str.length; i++) {
					if (!"".equals(str[i])) {
						intlist.add(Integer.valueOf(str[i]));
					}
				}
				list = aDao.findAllById(intlist);
				System.out.println("===========456654===========" + list.size());
				System.out.println("===========456654===========" + pageNumber);
				System.out.println("===========456654==========="
						+ (10 * pageNumber > list.size() ? list.size() : 10 * pageNumber));
				page = new PageImpl<Article>(list.subList(10 * pageNumber - 10,
						(10 * pageNumber > list.size() ? list.size() : 10 * pageNumber)), pgb, list.size());
			}
		} else if ("title".equals(where)) {
			switch (order) {
			case "desc":
				switch (sort) {
				case "id":
					list = aDao.findByTitleLikeOrderByIdDesc(search);
					break;
				case "authorId":
					list = aDao.findByTitleLikeOrderByAuthorIdDesc(search);
					break;
				case "authorName":
					list = aDao.findByTitleLikeOrderByAuthorNameDesc(search);
					break;
				case "title":
					list = aDao.findByTitleLikeOrderByTitleDesc(search);
					break;
				case "content":
					list = aDao.findByTitleLikeOrderByContentDesc(search);
					break;
				case "likes":
					list = aDao.findByTitleLikeOrderByLikesDesc(search);
					break;
				case "postDate":
					list = aDao.findByTitleLikeOrderByPostDateDesc(search);
					break;
				default:
					list = aDao.findByTitleLikeOrderByIdDesc(search);
				}
				break;
			default:
				switch (sort) {
				case "id":
					list = aDao.findByTitleLikeOrderByIdAsc(search);
					break;
				case "authorId":
					list = aDao.findByTitleLikeOrderByAuthorIdAsc(search);
					break;
				case "authorName":
					list = aDao.findByTitleLikeOrderByAuthorNameAsc(search);
					break;
				case "title":
					list = aDao.findByTitleLikeOrderByTitleAsc(search);
					break;
				case "content":
					list = aDao.findByTitleLikeOrderByContentAsc(search);
					break;
				case "likes":
					list = aDao.findByTitleLikeOrderByLikesAsc(search);
					break;
				case "postDate":
					list = aDao.findByTitleLikeOrderByPostDateAsc(search);
					break;
				default:
					list = aDao.findByTitleLikeOrderByIdAsc(search);
				}
			}
			page = new PageImpl<Article>(
					list.subList(10 * pageNumber - 10, (10 * pageNumber > list.size() ? list.size() : 10 * pageNumber)),
					pgb, list.size());
		}else if ("content".equals(where)) {
			switch (order) {
			case "desc":
				switch (sort) {
				case "id":
					list = aDao.findByContentLikeOrderByIdDesc(search);
					break;
				case "authorId":
					list = aDao.findByContentLikeOrderByAuthorIdDesc(search);
					break;
				case "authorName":
					list = aDao.findByContentLikeOrderByAuthorNameDesc(search);
					break;
				case "title":
					list = aDao.findByContentLikeOrderByTitleDesc(search);
					break;
				case "content":
					list = aDao.findByContentLikeOrderByContentDesc(search);
					break;
				case "likes":
					list = aDao.findByContentLikeOrderByLikesDesc(search);
					break;
				case "postDate":
					list = aDao.findByContentLikeOrderByPostDateDesc(search);
					break;
				default:
					list = aDao.findByContentLikeOrderByIdDesc(search);
				}
				break;
			default:
				switch (sort) {
				case "id":
					list = aDao.findByContentLikeOrderByIdAsc(search);
					break;
				case "authorId":
					list = aDao.findByContentLikeOrderByAuthorIdAsc(search);
					break;
				case "authorName":
					list = aDao.findByContentLikeOrderByAuthorNameAsc(search);
					break;
				case "title":
					list = aDao.findByContentLikeOrderByTitleAsc(search);
					break;
				case "content":
					list = aDao.findByContentLikeOrderByContentAsc(search);
					break;
				case "likes":
					list = aDao.findByContentLikeOrderByLikesAsc(search);
					break;
				case "postDate":
					list = aDao.findByContentLikeOrderByPostDateAsc(search);
					break;
				default:
					list = aDao.findByContentLikeOrderByIdAsc(search);
				}
			}
			page = new PageImpl<Article>(
					list.subList(10 * pageNumber - 10, (10 * pageNumber > list.size() ? list.size() : 10 * pageNumber)),
					pgb, list.size());
		}else if ("authorName".equals(where)) {
			switch (order) {
			case "desc":
				switch (sort) {
				case "id":
					list = aDao.findByAuthorNameLikeOrderByIdDesc(search);
					break;
				case "authorId":
					list = aDao.findByAuthorNameLikeOrderByAuthorIdDesc(search);
					break;
				case "authorName":
					list = aDao.findByAuthorNameLikeOrderByAuthorNameDesc(search);
					break;
				case "title":
					list = aDao.findByAuthorNameLikeOrderByTitleDesc(search);
					break;
				case "content":
					list = aDao.findByAuthorNameLikeOrderByContentDesc(search);
					break;
				case "likes":
					list = aDao.findByAuthorNameLikeOrderByLikesDesc(search);
					break;
				case "postDate":
					list = aDao.findByAuthorNameLikeOrderByPostDateDesc(search);
					break;
				default:
					list = aDao.findByAuthorNameLikeOrderByIdDesc(search);
				}
				break;
			default:
				switch (sort) {
				case "id":
					list = aDao.findByAuthorNameLikeOrderByIdAsc(search);
					break;
				case "authorId":
					list = aDao.findByAuthorNameLikeOrderByAuthorIdAsc(search);
					break;
				case "authorName":
					list = aDao.findByAuthorNameLikeOrderByAuthorNameAsc(search);
					break;
				case "title":
					list = aDao.findByAuthorNameLikeOrderByTitleAsc(search);
					break;
				case "content":
					list = aDao.findByAuthorNameLikeOrderByContentAsc(search);
					break;
				case "likes":
					list = aDao.findByAuthorNameLikeOrderByLikesAsc(search);
					break;
				case "postDate":
					list = aDao.findByAuthorNameLikeOrderByPostDateAsc(search);
					break;
				default:
					list = aDao.findByAuthorNameLikeOrderByIdAsc(search);
				}
			}
			page = new PageImpl<Article>(
					list.subList(10 * pageNumber - 10, (10 * pageNumber > list.size() ? list.size() : 10 * pageNumber)),
					pgb, list.size());
		}
		return page;
	}

}
