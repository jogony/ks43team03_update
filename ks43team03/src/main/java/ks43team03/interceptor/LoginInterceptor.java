package ks43team03.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	// 로그인 페이지 이동 전, 현재 페이지를 Session에 저장하는 메소드
	private void saveDest(HttpServletRequest req) {

	    String uri = req.getRequestURI();
	    log.info("uri: " + (uri));
	    String query = req.getQueryString();
	    log.info("query: " + (query));
	    
	    // 기존 URI에 parameter가 있을 경우, 이를 포함
	    if(query == null || query.equals("null")) {
	    	query = "";
	    } else {
	        query = "?" + query;
	    }
	    
	    // GET 으로 넘어오는 경우엔 true 를 반환
	    if(req.getMethod().equals("GET")) {
	        log.info("dest: " + (uri + query));
	        req.getSession().setAttribute("dest", uri + query);
	    }
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		/**
		 * 기본 타입 오브젝트 -> String으로 형변환
		 */
		String sessionId = (String)session.getAttribute("SID");
		if(sessionId == null) {
			
			saveDest(request);
			
			response.sendRedirect("/login");
			
			return false;
		}
			
		return true;
	}
}
