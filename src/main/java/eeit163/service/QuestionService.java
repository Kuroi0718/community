package eeit163.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit163.model.Question;
import eeit163.model.QuestionRepository;


	@Service
	public class QuestionService {
		@Autowired
		private QuestionRepository qDao;
		
		
		public void addQuestion(Question question) {
			qDao.save(question);
		}
		
		public List<Question> findUnansweredQuestions(Integer ownerId){
			return qDao.findUnansweredQuestions(ownerId);
		}
		
		public List<Question> findProductQuestions(Integer productId) {
			return qDao.findProductQuestions(productId);
		}
		
		@Transactional
		public void answerByQuestionId(String answer, Date date,Integer questionId) {
			qDao.answerByQuestionId(answer, date, questionId);
		}
		
//		@Transactional
//		public void updateQuestion() {
//		}
//		 
//		@Transactional
//		public void deleteQuestion() {
//		}
//		
//		public Question findQuestion(){
//			return qDao.findQuestion();
//		}
//		
		
	

		

	}