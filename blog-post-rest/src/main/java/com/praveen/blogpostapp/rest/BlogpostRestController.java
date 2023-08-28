package com.praveen.blogpostapp.rest;

import java.text.SimpleDateFormat;
import java.util.*;

import com.praveen.blogpostapp.entity.Tags;
import com.praveen.blogpostapp.service.TagsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.AnonymousDsl;
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

@RestController
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
    public String registration(@RequestBody UserInfo user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);

        return "User added";
    }

    @GetMapping("/pagingController")
    public List<Posts> pagingController(ModelMap model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

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
        session.setAttribute("sortList", postsList);
        return subList;
    }
    @GetMapping("/pagingForSearch")
    public List<Posts> PagingForSearch(Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

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
        session.setAttribute("sortList", postsList);

        return postsList;
    }

    @GetMapping("/pagingForFilter")
    public List<Posts> PagingFilterController(Model model, @RequestParam(value="pageNumber",defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize",defaultValue = "6") int pageSize, HttpSession session) {

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
        session.setAttribute("sortList", postsList);

        return subList;

    }

    @GetMapping("/search")
    public List<Posts> PagingManagerForSearchResults(@RequestParam("word") String searchWord, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session) {

        List<Posts> list = postsService.findByFullText(searchWord);
        list.addAll(postsService.findBySearchWord(searchWord));

        session.setAttribute("searchList", list);

        return PagingForSearch(model,pageNumber,pageSize,session);

    }

    @RequestMapping("/filter")
    public List<Posts> PagingManagerForFilteredResults(Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "6") int pageSize, HttpSession session, @RequestParam MultiValueMap<String, String> map) {

        List<Posts> postsList = new ArrayList<>();
        List<Posts> serachList= (List<Posts>) session.getAttribute("searchList");
        List<String> authorName = map.get("author");
        List<String> tagName = map.get("tag");
        String fromDate=null;
        if(map.get("fromDate")!=null){
            fromDate= map.get("fromDate").toString();
        }
        String toDate=null;
        if(map.get("toDate")!=null){
            toDate=map.get("toDate").toString();
        }
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
        return PagingFilterController(model,pageNumber,pageSize,session);
    }

    @GetMapping("/")
    public List<Posts> showData(ModelMap model, HttpSession session,@RequestParam(defaultValue = "0",value = "pageNumber") int pageNumber) {
        List<Posts> list = postsService.findAll();

        List<Tags> tagsList = tagsService.findAll();

        session.setAttribute("mainList", list);
        session.setAttribute("searchList",null);

        return pagingController(model,pageNumber,6,session);
    }
    @GetMapping("/savePost")
    public String processForm(@RequestBody Posts post,@RequestParam(value="tags") String tags) {

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
        System.out.println(currentUserName);
        if(currentUserName.equals("anonymousUser")){
            return "please login to save the post";
        }
        post.setAuthor(currentUserName);
        post.setCreatedAt(new Date());
        post.setPublishedAt(new Date());
        postsService.save(post);

        return "You are post has been saved";
    }

    @GetMapping("/saveComment/{id}")
    public String processComment(@RequestBody Comments comments, @PathVariable("id") int post_id) {

        Posts post = postsService.findById(post_id);

        comments.setPost(post);
        commentsService.save(comments);

        return "comment saved";
    }

    @GetMapping("/updatePost")
    public String updatePost(@RequestBody Posts post,@RequestParam(value="tags",defaultValue = "") String tags,HttpServletRequest request) {
        String values[] = tags.split(",");

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(currentUserName!=post.getAuthor() && !request.isUserInRole("ADMIN")){
            return "You are not allowed to do this";
        }

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
        post.setUpdatedAt(new Date());
        postsService.save(post);

        return "successfully updated";
    }

    @RequestMapping("/post/{post_id}")
    public Posts getUser(@PathVariable("post_id") int userId, Model model, @ModelAttribute Comments comments, HttpSession session) {

        Posts post = postsService.findById(userId);
        List<Comments> commentsList = post.getComment();
        post.setComment(commentsList);
        return post;

    }

    @RequestMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") int id,HttpServletRequest request) {

        Posts post = postsService.findById(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(currentUserName!=post.getAuthor() && !request.isUserInRole("ADMIN")){
            return "You are not allowed to do this";
        }
        postsService.deletePost(post);

        return "Post Has been deleted";

    }

    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") int id, HttpSession session,HttpServletRequest request) {

        Comments comment = commentsService.findById(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(currentUserName!=comment.getName() && !request.isUserInRole("ADMIN")){
            return "You are not allowed to do this";
        }
        commentsService.delete(comment);

        return "comment has been deleted";
    }

    @GetMapping("/updateComment/{id}")
    public String updateComment(@PathVariable("id") int id,@RequestBody Comments comment, HttpSession session, RedirectAttributes redirectAttributes,HttpServletRequest request) {

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(currentUserName!=comment.getName() && !request.isUserInRole("ADMIN")){
            return "You are not allowed to do this";
        }
        comment.setUpdatedAt(new Date());
        commentsService.save(comment);
        return "comment has been updated";

    }

    @GetMapping("/sort")
    public List<Posts> sortByAsc(@RequestParam(value="pageNumber",defaultValue = "0") int pageNumber, @RequestParam(value="order",defaultValue = "asc") String order, HttpSession session,Model model) {

        List<Posts> postsList = (List<Posts>) session.getAttribute("sortList");

        int totalElements = postsList.size();

        Pageable pageable = PageRequest.of(pageNumber, 6);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);


        List<Posts> subList = postsList.subList(start, end);

        Collections.sort(subList, new Comparator<Posts>() {
            public int compare(Posts m1, Posts m2) {
                return m1.getPublishedAt().compareTo(m2.getPublishedAt());
            }
        });
        if(order.equals("desc")){
            Collections.reverse(subList);
        }

        return subList;
    }

   }