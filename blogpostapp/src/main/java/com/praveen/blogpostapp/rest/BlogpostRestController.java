package com.praveen.blogpostapp.rest;

import java.text.SimpleDateFormat;
import java.util.*;

import com.praveen.blogpostapp.entity.Tags;
import com.praveen.blogpostapp.service.TagsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.praveen.blogpostapp.entity.Comments;
import com.praveen.blogpostapp.entity.Posts;
import com.praveen.blogpostapp.entity.UserInfo;
import com.praveen.blogpostapp.service.CommentsService;
import com.praveen.blogpostapp.service.PostsService;
import com.praveen.blogpostapp.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BlogpostRestController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private PostsService postsService;
    private CommentsService commentsService;
    private TagsService tagsService;

    public BlogpostRestController(UserService userService, PostsService postsService, CommentsService commentsService, TagsService tagsService) {

        this.userService = userService;
        this.postsService = postsService;
        this.commentsService = commentsService;
        this.tagsService = tagsService;
    }

    @GetMapping("/registration")
    public String regstration(Model model) {
        model.addAttribute("user", new UserInfo());
        return "registration";
    }

    @GetMapping("/regcompleted")
    public String regcompleted(@ModelAttribute UserInfo user, Model model, UserInfo user2) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);
        model.addAttribute("user", user2);
        return "login";
    }

    @GetMapping("/loginPage")
    public String loginPage(@ModelAttribute UserInfo userInfo) {
        System.err.println("You are on login Page");
        return "login";
    }

    @GetMapping("/pagingController")
    public String PagingManager(ModelMap model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

        List<Posts> postsList = (List<Posts>) session.getAttribute("mainList");

        model.addAttribute("value", "main");

        int totalElements = postsList.size();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);


        List<Posts> subList = postsList.subList(start, end);

        Set<Tags> tagsList = new HashSet<>();

        Set<Posts> authorList = new HashSet<>();

        Set<String> authorsCheck = new HashSet<>();

        for (Posts post : postsList) {

            tagsList.addAll(post.getTags());
            if (authorsCheck.contains(post.getAuthor()) != true) {
                authorsCheck.add(post.getAuthor());
                authorList.add(post);
            }
        }

        Page<Posts> page = new PageImpl<>(subList, pageable, totalElements);

        model.addAttribute("authorList", authorList);
        model.addAttribute("publishedAtList", page);
        model.addAttribute("size", subList.size());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("sortList", subList);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("list", page);

        return "/displaypostsdata";

    }

    @GetMapping("/pagingForSearch")
    public String PagingSearchManager(Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

        List<Posts> postsList = (List<Posts>) session.getAttribute("searchList");

        System.out.println("search List size" + postsList.size());

        model.addAttribute("value", "search");
        int totalElements = postsList.size();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);


        List<Posts> subList = postsList.subList(start, end);

        Set<Tags> tagsList = new HashSet<>();
        Set<Posts> authorList = new HashSet<>();

        Set<String> authorsCheck = new HashSet<>();

        for (Posts post : postsList) {

            tagsList.addAll(post.getTags());
            if (authorsCheck.contains(post.getAuthor()) != true) {
                authorsCheck.add(post.getAuthor());
                authorList.add(post);
            }
        }

        Page<Posts> page = new PageImpl<>(subList, pageable, totalElements);
        model.addAttribute("authorList", authorList);
        model.addAttribute("publishedAtList", page);
        model.addAttribute("size", subList.size());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("sortList", subList);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("list", page);

        return "/displaypostsdata";
    }

    @GetMapping("/pagingForFilter")
    public String PagingFilterManager(Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {


        List<Posts> postsList = (List<Posts>) session.getAttribute("filterList");

        System.out.println("filteredList" + postsList);

        List<String> checkList = (List<String>) session.getAttribute("checkList");

        System.out.println("check List" + checkList);

        model.addAttribute("value", "filter");

        int totalElements = postsList.size();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);


        List<Posts> subList = postsList.subList(start, end);

        Set<Tags> tagsList = new HashSet<>();
        Set<Posts> authorList = new HashSet<>();

        Set<String> authorsCheck = new HashSet<>();

        for (Posts post : postsList) {

            tagsList.addAll(post.getTags());
            if (authorsCheck.contains(post.getAuthor()) != true) {
                authorsCheck.add(post.getAuthor());
                authorList.add(post);
            }
        }


        Page<Posts> page = new PageImpl<>(subList, pageable, totalElements);
        model.addAttribute("authorList", authorList);
        model.addAttribute("publishedAtList", page);
        model.addAttribute("size", subList.size());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("sortList", subList);
        model.addAttribute("checkList", checkList);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("list", page);

        System.err.println("you are on pagin of filter");

        return "/displaypostsdata";

    }

    @GetMapping("/pagingSearchController")
    public String PagingManagerForSearchResults(@RequestParam("search") String searchWord, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

        List<Posts> list = postsService.findByFullText(searchWord);
        list.addAll(postsService.findBySearchWord(searchWord));

        session.setAttribute("searchList", list);

        return "redirect:/pagingForSearch";

    }

    @GetMapping("/pagingFilterController")
    public String PagingManagerForFilterdResults(ModelMap model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session, @RequestParam MultiValueMap<String, String> map, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

        List<Posts> postsList = new ArrayList<>();
        List<Posts> serachList= (List<Posts>) session.getAttribute("searchList");
        List<String> authorName = map.get("AuthorId");
        List<String> tagName = map.get("tagId");
        List<String> checkList = new ArrayList<>();
        checkList.add(" ");
        if (authorName != null) {
            checkList.addAll(authorName);
        }
        if (tagName != null) {
            checkList.addAll(tagName);
        }
        System.out.println(map);

        if (authorName != null && tagName != null) {
            postsList.addAll((Collection<? extends Posts>) postsService.findByAuthorNameAndTagName(authorName, tagName));
        } else if (authorName != null) {
            postsList.addAll((Collection<? extends Posts>) postsService.findByAuthorName(authorName));
        } else if (tagName != null) {
            postsList.addAll((Collection<? extends Posts>) postsService.findByTagName(tagName));
        }
        if (fromDate != null && toDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            try {
                Date fromdate = formatter.parse(fromDate);
                Date todate = formatter.parse(toDate);
                List<Posts> postsHavingDate = postsService.findByDate(fromdate, todate);
                postsList.addAll(postsHavingDate);
            } catch (Exception e) {
                System.err.println(e.getClass().getName());
            }
        }
        List<Posts> resultList=new ArrayList<>();
        if(serachList!=null){
            Set<Integer> searchIds=new HashSet<>();
            for(Posts post:serachList){
                searchIds.add(post.getId());
            }
            for(Posts post:postsList){
                if(searchIds.contains(post.getId())==true){
                    resultList.add(post);
                }
            }
        }else{
            resultList=postsList;
        }
        session.setAttribute("filterList", resultList);
        session.setAttribute("checkList", checkList);
        return "redirect:/pagingForFilter";

    }

    @GetMapping("/displaydata")
    public String showData(ModelMap model, HttpSession session) {
        System.out.println("You are on displaydata");
        List<Posts> list = postsService.findAll();

        List<Tags> tagsList = tagsService.findAll();

        session.setAttribute("mainList", list);
        session.setAttribute("searchList",null);
        return "redirect:/pagingController";

    }


    @GetMapping("/createpost")
    public String createNewPost(@ModelAttribute Posts posts) {

        return "newpost";
    }

    @GetMapping("/processPost")
    public String processForm(@ModelAttribute Posts post, @RequestParam String tags) {

        String values[] = tags.split(",");

        for (String x : values) {
            Tags search_tag = tagsService.findByName(x);
            if (search_tag == null) {
                Tags tag = new Tags();
                tag.setName(x);
                tag.setCreatedAt(new Date());
                post.addTag(tag);
            } else {
                post.addTag(search_tag);
            }
        }
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        post.setAuthor(currentUserName);
        post.setCreatedAt(new Date());
        post.setPublishedAt(new Date());
        postsService.save(post);

        return "redirect:/displaydata";
    }

    @GetMapping("/processComment/{id}")
    public RedirectView processComment(@ModelAttribute Comments comments, @PathVariable("id") int post_id) {

        Posts post = postsService.findById(post_id);
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        comments.setName(username);
        comments.setPost(post);
        commentsService.save(comments);

        RedirectView redirectView = new RedirectView();
        redirectView.addStaticAttribute("id", post_id);
        redirectView.setUrl("/showpost/{id}");

        return redirectView;
    }

    @GetMapping("/updatepost/{id}")
    public String updatePost(@PathVariable("id") int id, Model model) {
        Posts post = postsService.findById(id);
        post.setUpdatedAt(new Date());
        List<Tags> tagsList = post.getTags();
        String tags = "";
        for (int i = 0; i < tagsList.size(); i++) {
            tags += tagsList.get(i).getName();
            if (tags.equals("") == false && i < tagsList.size() - 1) {
                tags += ",";
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("posts", post);
        return "/editpost";
    }

    @GetMapping("/showpost/{post_id}")
    public String getUser(@PathVariable("post_id") int userId, Model model, @ModelAttribute Comments comments, HttpSession session) {
        System.err.println("You are on showpostpage");
        Posts post = postsService.findById(userId);
        List<Comments> commentsList = post.getComment();
        session.setAttribute("post_id", userId);
        model.addAttribute("commentList", commentsList);
        model.addAttribute("postid", userId);
        model.addAttribute("post", post);
        return "displayPost";
    }

    @GetMapping("/deletepost/{id}")
    public String deletePost(@PathVariable("id") int id) {

        Posts post = postsService.findById(id);

        postsService.deletePost(post);

        return "redirect:/displaydata";
    }


    @GetMapping("/deleteComment/{id}")
    public RedirectView deleteComment(@PathVariable("id") int id, HttpSession session) {
        int post_id = -1;
        try {
            post_id = (int) session.getAttribute("post_id");
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }

        Comments comment = commentsService.findById(id);
        commentsService.delete(comment);
        RedirectView redirectView = new RedirectView();
        redirectView.addStaticAttribute("id", post_id);
        redirectView.setUrl("/showpost/{id}");
        return redirectView;
    }

    @GetMapping("/updateComment/{id}")
    public RedirectView updateComment(@PathVariable("id") int id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Comments comments = commentsService.findById(id);

        int post_id = (int) session.getAttribute("post_id");
        redirectAttributes.addFlashAttribute(comments);
        model.addAttribute("comment_id", id);
        RedirectView redirectView = new RedirectView();
        redirectView.addStaticAttribute("id", post_id);
        redirectView.setUrl("/showpost/{id}");
        return redirectView;

    }

    @PostMapping("/sortByAsc/{offset}")
    public String sortByAsc(@RequestParam("value") String value, @RequestParam("subList") List<Posts> subList, Model model, @PathVariable("offset") int pageNumber, HttpSession session) {

        List<Posts> postsList = subList;
        System.err.println("sort by asc " + postsList);
        List<Posts> mainList = null;

        List<String> checkList = (List<String>) session.getAttribute("checkList");

        if (value.equals("search")) {
            mainList = (List<Posts>) session.getAttribute("searchList");
            model.addAttribute("value", value);
        }
        if (value.equals("filter")) {
            mainList = (List<Posts>) session.getAttribute("filterList");
            model.addAttribute("value", value);
        }
        if (value.equals("main")) {
            mainList = (List<Posts>) session.getAttribute("mainList");
            model.addAttribute("value", value);
        }
        Collections.sort(postsList, new Comparator<Posts>() {
            public int compare(Posts m1, Posts m2) {
                return m1.getPublishedAt().compareTo(m2.getPublishedAt());
            }
        });
        Set<Tags> tagsList = new HashSet<>();
        Set<Posts> authorList = new HashSet<>();

        Set<String> authorsCheck = new HashSet<>();

        for (Posts post : mainList) {

            tagsList.addAll(post.getTags());
            if (authorsCheck.contains(post.getAuthor()) != true) {
                authorsCheck.add(post.getAuthor());
                authorList.add(post);
            }
        }
        int totalElements = mainList.size();
        int pageSize = 6;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Posts> page = new PageImpl<>(postsList, pageable, totalElements);

        model.addAttribute("authorList", authorList);
        model.addAttribute("publishedAtList", page);
        model.addAttribute("size", subList.size());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("sortList", postsList);
        model.addAttribute("checkList", checkList);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("list", page);

        return "/displaypostsdata";

    }

    @PostMapping("/sortByDesc/{offset}")
    public String sortByDesc(@RequestParam("value") String value, @RequestParam("subList") List<Posts> subList, ModelMap model, @PathVariable("offset") int pageNumber, HttpSession session) {

        List<Posts> postsList = subList;

        List<Posts> mainList = null;

        List<String> checkList = (List<String>) session.getAttribute("checkList");

        System.out.println(value);
        if (value.equals("search")) {
            mainList = (List<Posts>) session.getAttribute("searchList");
            model.addAttribute("value", value);
        }
        if (value.equals("filter")) {
            mainList = (List<Posts>) session.getAttribute("filterList");
            model.addAttribute("value", value);
        }
        if (value.equals("main")) {
            mainList = (List<Posts>) session.getAttribute("mainList");
            model.addAttribute("value", value);
        }
        Collections.sort(postsList, new Comparator<Posts>() {
            public int compare(Posts m1, Posts m2) {
                return m1.getPublishedAt().compareTo(m2.getPublishedAt());
            }
        });

        Collections.reverse(postsList);
        Set<Tags> tagsList = new HashSet<>();
        Set<Posts> authorList = new HashSet<>();

        Set<String> authorsCheck = new HashSet<>();

        for (Posts post : mainList) {

            tagsList.addAll(post.getTags());
            if (authorsCheck.contains(post.getAuthor()) != true) {
                authorsCheck.add(post.getAuthor());
                authorList.add(post);
            }
        }
        int totalElements = mainList.size();
        int pageSize = 6;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Posts> page = new PageImpl<>(postsList, pageable, totalElements);

        model.addAttribute("authorList", authorList);
        model.addAttribute("publishedAtList", page);
        model.addAttribute("size", subList.size());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("checkList", checkList);
        model.addAttribute("sortList", postsList);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("list", page);

        return "displaypostsdata";
    }
    @ExceptionHandler(value = Exception.class)
    public String ExceptionHandlers(Model model) {
        model.addAttribute("msg", Exception.class);
        return "error";
    }
}