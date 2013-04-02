/**
 * 
 */
package org.mspring.mlog.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.cache.CacheService;

import com.google.gson.annotations.Expose;

/**
 * @author Gao Youbo
 * @since 2012-7-27
 * @Description
 * @TODO
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = CacheService.CacheName.LAZY_CACHE_NAME)
public class Comment implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4508095889920768217L;

    @Expose
    private Long id;
    @Expose
    private String content;
    @Expose
    private Date createTime;
    @Expose
    private String postIp;
    @Expose
    private String agent;
    @Expose
    private Post post;
    @Expose
    private String status;

    private Comment parent;
    @Expose
    private User author;

    /**
     * 
     */
    public Comment() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     */
    public Comment(Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 30)
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    @Column(name = "content", nullable = false, length = 5000)
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the createTime
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, length = 50)
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the postIp
     */
    @Column(name = "post_ip", length = 50)
    public String getPostIp() {
        return postIp;
    }

    /**
     * @param postIp
     *            the postIp to set
     */
    public void setPostIp(String postIp) {
        this.postIp = postIp;
    }

    /**
     * @return the agent
     */
    @Column(name = "agent", length = 1000)
    public String getAgent() {
        return agent;
    }

    /**
     * @param agent
     *            the agent to set
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * @return the post
     */
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = CacheService.CacheName.LAZY_CACHE_NAME)
    @ManyToOne( fetch = FetchType.LAZY,  optional = false, targetEntity = Post.class)
    @JoinColumn(name = "post")
    public Post getPost() {
        return post;
    }

    /**
     * @param post
     *            the post to set
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * @return the status
     */
    @Column(name = "status", nullable = false, length = 30)
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = Comment.class)
    @JoinColumn(name = "parent")
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    /**
     * @return the author
     */
    @ManyToOne( fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    @JoinColumn(name = "author", nullable = false)
    public User getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Hibernate默认为懒加载Parent Comment，该方法为获取完成的Parent Comment
     * 
     * @return
     */
    @Transient
    public Comment getParentEager() {
        if (this.parent != null && this.getParent().getId() != null && this.getParent().getId() != new Long(0)) {
            Comment parent = ServiceFactory.getCommentService().getCommentById(this.getParent().getId());
            this.setParent(parent);
            return parent;
        }
        return null;
    }

    @Transient
    public User getAuthorEager() {
        if (this.author != null && this.author.getId() != null && this.author.getId() != new Long(0)) {
            User author = ServiceFactory.getUserService().getUserById(this.author.getId());
            this.setAuthor(author);
            return author;
        }
        return null;
    }

    @Transient
    public Post getPostEager() {
        Post post = ServiceFactory.getPostService().getPostById(this.getPost().getId());
        this.setPost(post);
        return post;
    }

    public static final class Status {
        public static final String APPROVED = "approved";
        public static final String WAIT_FOR_APPROVE = "wait_for_approve";
        public static final String SPAM = "spam";
        public static final String RECYCLE = "recycle";
        public static final List<String> COMMENT_STATUS = Arrays.asList(new String[] { APPROVED, WAIT_FOR_APPROVE, SPAM, RECYCLE });

        public static final Map<String, String> getCommentStatusMap() {
            Map<String, String> commentStatus = new HashMap<String, String>();
            commentStatus.put(APPROVED, "审核通过");
            commentStatus.put(WAIT_FOR_APPROVE, "等待审核");
            commentStatus.put(SPAM, "垃圾评论");
            commentStatus.put(RECYCLE, "回收站");
            return commentStatus;
        }
    }

}
=======
/**
 * 
 */
package org.mspring.mlog.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.mspring.mlog.core.ServiceFactory;
import org.mspring.mlog.entity.security.User;
import org.mspring.mlog.service.cache.CacheService;

import com.google.gson.annotations.Expose;

/**
 * @author Gao Youbo
 * @since 2012-7-27
 * @Description
 * @TODO
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = CacheService.CacheName.LAZY_CACHE_NAME)
public class Comment implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4508095889920768217L;

    @Expose
    private Long id;
    @Expose
    private String content;
    @Expose
    private Date createTime;
    @Expose
    private String postIp;
    @Expose
    private String agent;
    @Expose
    private Post post;
    @Expose
    private String status;

    private Comment parent;
    @Expose
    private User author;

    /**
     * 
     */
    public Comment() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     */
    public Comment(Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 30)
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    @Column(name = "content", nullable = false, length = 5000)
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the createTime
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, length = 50)
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the postIp
     */
    @Column(name = "post_ip", length = 50)
    public String getPostIp() {
        return postIp;
    }

    /**
     * @param postIp
     *            the postIp to set
     */
    public void setPostIp(String postIp) {
        this.postIp = postIp;
    }

    /**
     * @return the agent
     */
    @Column(name = "agent", length = 1000)
    public String getAgent() {
        return agent;
    }

    /**
     * @param agent
     *            the agent to set
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * @return the post
     */
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = CacheService.CacheName.LAZY_CACHE_NAME)
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Post.class)
    @JoinColumn(name = "post")
    public Post getPost() {
        return post;
    }

    /**
     * @param post
     *            the post to set
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * @return the status
     */
    @Column(name = "status", nullable = false, length = 30)
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = Comment.class)
    @JoinColumn(name = "parent")
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    /**
     * @return the author
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = User.class)
    @JoinColumn(name = "author", nullable = false)
    public User getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Hibernate默认为懒加载Parent Comment，该方法为获取完成的Parent Comment
     * 
     * @return
     */
    @Transient
    public Comment getParentEager() {
        if (this.parent != null && this.getParent().getId() != null && this.getParent().getId() != new Long(0)) {
            Comment parent = ServiceFactory.getCommentService().getCommentById(this.getParent().getId());
            this.setParent(parent);
            return parent;
        }
        return null;
    }

    @Transient
    public User getAuthorEager() {
        if (this.author != null && this.author.getId() != null && this.author.getId() != new Long(0)) {
            User author = ServiceFactory.getUserService().getUserById(this.author.getId());
            this.setAuthor(author);
            return author;
        }
        return null;
    }

    @Transient
    public Post getPostEager() {
        if (this.post != null && this.post.getId() != null) {
            Post post = ServiceFactory.getPostService().getPostById(this.getPost().getId());
            this.setPost(post);
            return post;
        }
        return null;
    }

    public static final class Status {
        public static final String APPROVED = "approved";
        public static final String WAIT_FOR_APPROVE = "wait_for_approve";
        public static final String SPAM = "spam";
        public static final String RECYCLE = "recycle";
        public static final List<String> COMMENT_STATUS = Arrays.asList(new String[] { APPROVED, WAIT_FOR_APPROVE, SPAM, RECYCLE });

        public static final Map<String, String> getCommentStatusMap() {
            Map<String, String> commentStatus = new HashMap<String, String>();
            commentStatus.put(APPROVED, "审核通过");
            commentStatus.put(WAIT_FOR_APPROVE, "等待审核");
            commentStatus.put(SPAM, "垃圾评论");
            commentStatus.put(RECYCLE, "回收站");
            return commentStatus;
        }
    }

}
>>>>>>> branch 'master' of https://github.com/M-LOG/M-LOG.git
