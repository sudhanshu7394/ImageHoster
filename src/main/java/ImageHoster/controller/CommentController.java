package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@RequestParam("comment") String commentText, @PathVariable("imageId") Integer imageId, HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("loggeduser");
        Comment comment = new Comment();
        Image image = imageService.getImage(imageId);

        comment.setText(commentText);
        comment.setUser(user);
        comment.setImage(image);
        comment.setCreatedDate(new Date());
        commentService.commentUpdate(comment, imageId);
        List<Comment> comments = commentService.getComments(imageId);
        model.addAttribute("image", image);
        model.addAttribute("comments", comments);

        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }

}
