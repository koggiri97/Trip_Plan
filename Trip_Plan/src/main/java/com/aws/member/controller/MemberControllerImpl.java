package com.aws.member.controller;

import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aws.member.vo.MemberVO;
import com.aws.member.service.MemberService;


//-----------------------------------------------------------------------------------------------------------
// public class MemberControllerImpl implements MemberController
//-----------------------------------------------------------------------------------------------------------
@Controller("memberController")
@RequestMapping("/member")	// url에서 /member로 시작하는 것들을 처리하는 컨트롤러
public class MemberControllerImpl implements MemberController {
	
	@Autowired
	private MemberVO memberVO;
	
	@Autowired
	private MemberService memberService;
	

	@Autowired
	private JavaMailSender mailSender;


	//-----------------------------------------------------------------------------------------------------------
	// 로그인 화면 띄우기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public ModelAndView SignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/signin");
		return mav;
		
	}

	//-----------------------------------------------------------------------------------------------------------
	// 로그인 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		System.out.println("MemberController 로그인 member.getId() ==> " + member.getId());
		
		// 로그인 한 정보를 가지고 데이터 베이스에 존재하는지 처리를 하고, 그 결과를 가져온다.
		memberVO = memberService.login(member);
		System.out.println("MemberController 로그인 ==> " + memberVO);
		
		// 로그인 정보가 데이터베이스에 존재하는지에 따라 처리를 다르게 한다.
		if(memberVO != null) {	// 로그인 정보에 해당하는 자료가 존재한다면
			
			if(member.getPwd().equals(memberVO.getPwd())) {
				// 아이디와 비밀번호가 일치하면, 세션을 발급한다.
				HttpSession session = request.getSession();
				session.setAttribute("member", 	memberVO);
				session.setAttribute("isLogOn",	true);
				// mav.setViewName("redirect:/member/listMembers.do");
				mav.setViewName("redirect:/"); // 메인화면으로 이동
			} else {
				rAttr.addAttribute("result", "PasswordFailed");
				mav.setViewName("redirect:/member/signin");
			}
			
		} else {	// 로그인 정보에 해당하는 자료가 존재하지 않는다면
			// 로그인 실패 메시지를 가지고 로그인 화면으로 이동한다.
			rAttr.addAttribute("result", "loginFailed");
			mav.setViewName("redirect:/member/signin"); 
		}

		return mav;
	}

	//-----------------------------------------------------------------------------------------------------------
	// 로그아웃 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		
		ModelAndView mav = new ModelAndView();
		// mav.setViewName("redirect:/member/loginForm.do");
		mav.setViewName("redirect:/"); // 메인화면으로 이동
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원가입 화면 접속
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView SignUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/signup");
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 회원 가입 처리
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/addMember", method=RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		System.out.println("MemberController에서 받은 memberVO ==> " + memberVO);
		
		int result = 0;
		// 사용자가 입력한 정보를 서비스에게 넘겨주어 처리하게 한다.
		result = memberService.addMember(memberVO);
		
		ModelAndView mav = new ModelAndView("redirect:/member/signin");

		return mav;
		
	} // End - public ModelAndView addMember

	//-----------------------------------------------------------------------------------------------------------
	// 아이디 중복 검사(Ajax)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value = "/member/idCheck", method = RequestMethod.POST)
	public int idCheck(MemberVO memberVO) throws Exception {
		
		System.out.println("MemberController 아이디 중복 검사(Ajax) id => " + memberVO.getId());
		
		int result = memberService.idCheck(memberVO);
		
		System.out.println("result : " + result);
		return result;
		
	}

	
	//-----------------------------------------------------------------------------------------------------------
	// 이메일 인증(Ajax)
	//-----------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/mailCheck", method=RequestMethod.GET)
    @ResponseBody
    public String mailCheckGET(String email) throws Exception{
        
        /* 뷰(View)로부터 넘어온 데이터 확인 */
    	System.out.println("인증번호 : " + email);
                

        /* 인증번호(난수) 생성 */
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkNum);
        
        String setFrom = "sjinjin6@naver.com";
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = 
                "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br>" + 
                "인증 번호는 " + checkNum + "입니다." + 
                "<br>" + 
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        
        try {
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        String num = Integer.toString(checkNum);
        
        return num;
    }

	//-----------------------------------------------------------------------------------------------------------
	// 아이디 찾기 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/id_search", method=RequestMethod.GET)
	public ModelAndView Id_search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/id_search");
		return mav;
	}

	
	//-----------------------------------------------------------------------------------------------------------
	// 아이디 찾기(Ajax)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/member/id_find", method=RequestMethod.POST)
	public String Id_find(MemberVO memberVO) throws Exception{
		
		System.out.println("MemberController 아이디 찾기 email => " + memberVO.getEmail());
		String result = memberService.Id_find(memberVO); 
		
		System.out.println("result : " + result);
		return result;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 비밀번호 찾기 폼
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@RequestMapping(value="/pw_search", method=RequestMethod.GET)
	public ModelAndView Pw_search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/pw_search");
		return mav;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	// 비밀번호 찾기(Ajax)
	//-----------------------------------------------------------------------------------------------------------
	@Override
	@ResponseBody
	@RequestMapping(value="/member/pw_find", method=RequestMethod.POST)
	public String Pw_find(MemberVO memberVO) throws Exception{
		
		System.out.println("MemberController 아이디 찾기 email => " + memberVO.getEmail());
		String result = memberService.Pw_find(memberVO); 
		
        String setFrom = "sjinjin6@naver.com";
        String toMail = memberVO.getEmail();
        String title = " 비밀번호 발급 이메일 입니다.";
        String content = 
                "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br>" + 
                "회원님의 비밀 번호는 '" + result + "' 입니다." + 
                "<br>" ;
        
        try {
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
		
		System.out.println("result : " + result);
		return result;
	}

		
	//-----------------------------------------------------------------------------------------------------------
	// 이용약관
	//-----------------------------------------------------------------------------------------------------------
	
	@Override
	@RequestMapping(value="/memberRule", method=RequestMethod.GET)
	public ModelAndView memberRule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/memberRule");
		return mav;
	}
	
	

} // End - public class MemberControllerImpl implements MemberController












